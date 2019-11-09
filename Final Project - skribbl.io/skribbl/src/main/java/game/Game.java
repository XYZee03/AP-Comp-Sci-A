package main.java.game;

import java.util.*;

public class Game {

    private List<Player> players = new ArrayList<>();

    private Player currentPlayer;

    private ArrayList<String> words;

    private ArrayList<String> unusedWords;

    public final static int PLAYER_COUNT_TOO_LOW = 1;

    private final static int BEGIN = 2;

    public final static int PLAYER_COUNT_TOO_LOW_AGAIN = 3;

    private final static int END_OF_TURN = 4;

    private final static int END_OF_ROUND = 5;

    private final static int PAUSE = 5;

    private int status;

    private int turnsPlayed;

    private int correctCount;

    private String word;

    private ArrayList<Player> newPlayers = new ArrayList<>();

    private Random randomGenerator = new Random();

    private Timer timer;

    private boolean correctGuess;

    public Game(ArrayList<String> words) {
        timer = new Timer();
        this.words = words;
        this.status = PLAYER_COUNT_TOO_LOW;
        unusedWords = new ArrayList<>(words);
    }

    private class NextActionTask extends TimerTask {
        @Override
        public void run()
        {
            next();
        }
    }

    public int getStatus() {
        return status;
    }

    private void newGame() {
        correctGuess = false;
        players.addAll(newPlayers);
        newPlayers.clear();
        if (players.size() < 2)
        {
            status = PLAYER_COUNT_TOO_LOW;
            return;
        }
        for (Player player : players)
        {
            player.setStatus(Player.GUESSING);
        }

        currentPlayer = players.get(0);
        turnsPlayed = 0;
        reset();
        status = BEGIN;
        sendToPlayers();
        next();
    }

    private void sendToPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(players);
        allPlayers.addAll(newPlayers);
        for (Player player : allPlayers)
        {
            player.startGame();
        }
    }

    private void sendToPlayers(int event, Player exclude) {
        ArrayList<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(players);
        allPlayers.addAll(newPlayers);
        for (Player player : allPlayers)
        {
            if (player.equals(exclude))
            {
                continue;
            }
            if (event == Player.DRAWING)
            {
                player.startDrawClient(exclude.getUsername());
                if (!newPlayers.contains(player))
                {
                    player.startGuess(word);
                }
            }
            else if (event == Player.GUESSING)
            {
                player.stopDrawClient(exclude.getUsername());
                if (!correctGuess)
                {
                    player.revealWord(word);
                }

            }
        }
    }

    private void reset() {
        for (Player player : players)
        {
            player.setStatus(Player.GUESSING);
        }

    }

    private void chooseWord() {
        if (unusedWords.size() == 0)
        {
            unusedWords = new ArrayList<>(words);
        }
        int currentWordIndex = randomGenerator.nextInt(unusedWords.size());
        word = unusedWords.remove(currentWordIndex);
        correctGuess = false;
    }

    public void next()
    {
        if (players.size() < 2)
        {
            status = PLAYER_COUNT_TOO_LOW_AGAIN;
            return;
        }
        if (status == END_OF_TURN)
        {
            status = BEGIN;
        }
        if (status == END_OF_ROUND)
        {
            nextTask(PAUSE * 2);
        }
        else if (status == BEGIN)
        {
            if (currentPlayer.getStatus() == Player.GUESSING)
            {
                chooseWord();
                currentPlayer.setStatus(Player.DRAWING);
                currentPlayer.startDraw(word);
                sendToPlayers(Player.DRAWING, currentPlayer);

            }
            else if (currentPlayer.getStatus() == Player.DRAWING && correctCount == players.size() - 1)
            {
                currentPlayer.stopDraw();
                turnsPlayed += 1;
                correctCount = 0;
                sendToPlayers(Player.GUESSING, currentPlayer);
                currentPlayer.setStatus(Player.GUESSING);
                if (turnsPlayed < players.size())
                {
                    endTurn();
                }
                else
                {
                    endRound();

                }
                nextTask(PAUSE);
            }
        }
    }

    private void nextTask(int seconds)
    {
        timer.schedule(new NextActionTask(), seconds * 1000);
    }

    private void endTurn(){
        status = END_OF_TURN;
        currentPlayer = players.get(players
                .indexOf(currentPlayer) + 1);
    }

    private void endRound() {
        status = END_OF_ROUND;
        List<String> players = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        for (Player player : this.players)
        {
            players.add(player.getUsername());
            scores.add(player.getScore());
        }

        this.players.addAll(newPlayers);
        for (Player player : this.players)
        {
            player.scoreMessage(players, scores);
        }
        newPlayers.clear();
    }

    public void join(Player player) {
        player.setStatus(Player.GUESSING);
        newPlayers.add(player);
    }

    public void start(){
        newGame();
    }

    public void playerLeft(Player player) {
        players.remove(player);
        if(player.getStatus() == Player.DRAWING)
        {
            timer.cancel();
            timer = new Timer();
            next();
        }
        else if(players.size() == 1)
        {
            next();
        }
    }

    public boolean isPlayer(Player player)
    {
        return (players.contains(player));
    }

    public void guess(Player guessingPlayer, String word) {
        if (status != BEGIN)
        {
            System.err.println("Error: a player guessed when the game wasn't running");
            return;
        }
        if (!players.contains(guessingPlayer))
        {
            return;
        }
        else if (guessingPlayer.getStatus() == Player.DRAWING)
        {
            return;
        }
        if (word.equalsIgnoreCase(this.word))
        {
            correctCount++;
            correctGuess = true;
            currentPlayer.setScore(currentPlayer.getScore() + 5 * (players.size() - correctCount));
            guessingPlayer.setScore(guessingPlayer.getScore() + 10 * (players.size() - correctCount));
            guessingPlayer.correct(this.word);
            for (Player player : players)
            {
                if (player.equals(guessingPlayer))
                {
                    continue;
                }
                player.correctMessage(guessingPlayer.getUsername(),
                        this.word);
            }
            if (correctCount == players.size()-1)
                next();
        }
        else
        {
            for (Player player : players)
            {
                player.incorrect(guessingPlayer.getUsername(), word);
            }
        }
    }

    public List<String> getPlayerNames() {
        List<String> players = new ArrayList<>();
        for (Player pp : this.players)
        {
            players.add(pp.getUsername());
        }
        for (Player pp : newPlayers)
        {
            players.add(pp.getUsername());
        }
        return players;
    }
}