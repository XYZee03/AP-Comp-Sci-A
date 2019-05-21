package main.java.client;

import java.util.List;
import java.awt.Point;

public interface Actions
{
    void serverMessage(String message);

    void clientMessage(String client, String message);

    void connectClient(String client);

    void disconnectClient(String client);

    void startClientDraw(String client);

    void startDraw(String wordToDraw, int timeLimit);

    void stopDraw();

    void getDrawing(Point point);

    void startGuess(String word);

    void stopGuess();

    void joined(List<String> players, int gameStatus);

    void connectedToServer();

    void disconnectedFromServer();

    void correctClientMessage(String player, String guess);

    void incorrectClientMessage(String player, String guess);

    void correctMessage();

    String getUsername();

    void error(String error);
}
