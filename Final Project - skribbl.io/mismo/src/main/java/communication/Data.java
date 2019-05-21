package main.java.communication;

import java.io.Serializable;

public class Data<T> implements Serializable
{
    public final T data;

    Data(T data)
    {
        this.data = data;
    }
}