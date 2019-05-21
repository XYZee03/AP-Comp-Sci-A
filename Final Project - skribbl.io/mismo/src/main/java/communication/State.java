package main.java.communication;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable
{
    public final List<String> connectedClients;

    public State(List<String> connectedClients)
    {
        this.connectedClients = connectedClients;
    }
}
