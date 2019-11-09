package main.java.game;

import java.util.List;

public abstract class Player
{

    private int score;

    private int status;

    final static int GUESSING = 0;

    final static int DRAWING = 1;

    public Player(int score, int status)
    {
        this.score = score;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Player))
        {
            return false;
        }
        Player pp = (Player) obj;
        return this.getUsername().equals(pp.getUsername());
    }

    void setScore(int score)
    {
        this.score = score;
    }

    void setStatus(int status)
    {
        this.status = status;
    }

    int getScore()
    {
        return score;
    }

    int getStatus()
    {
        return status;
    }

    public abstract void stopDrawClient(String player);

    public abstract void stopDraw();

    public abstract void startDrawClient(String player);

    public abstract void startDraw(String wordToDraw);

    public abstract void startGuess(String word);

    public abstract void startGame();

    public abstract void correct(String guess);

    public abstract void correctMessage(String guessingPlayer, String word);

    public abstract String getUsername();

    public abstract void scoreMessage(List<String> players, List<Integer> scores);

    public abstract void incorrect(String player, String guess);

    public abstract void revealWord(String currentWord);
}