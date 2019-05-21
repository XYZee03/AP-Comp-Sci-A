package main.java.communication;

import java.io.Serializable;

public class Draw implements Serializable
{
    public final String word;

    public final int time;

    public Draw(String word, int timeLimit)
    {
        this.word = word;
        this.time = timeLimit;
    }
}
