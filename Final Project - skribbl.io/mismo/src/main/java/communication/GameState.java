package main.java.communication;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable
{
    public final List<String> connectedPlayers;

    public final int gameStatus;

    public GameState(List<String> connectedPlayers, int gameStatus)
    {
        this.connectedPlayers = connectedPlayers;
        this.gameStatus = gameStatus;
    }
}
