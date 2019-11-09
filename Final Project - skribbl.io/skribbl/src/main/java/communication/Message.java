package main.java.communication;

import java.awt.Point;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Hashtable;


public class Message implements Serializable
{
    public enum MessageType
    {
        SERVER_DISCONNECT,

        SERVER_MESSAGE,

        CLIENT_MESSAGE,

        START_GUESS,

        STOP_DRAW_CLIENT,

        START_DRAW,

        STOP_DRAW,

        STOP_GUESS,

        CORRECT,

        JOIN_REQUEST,

        GET_USERNAME,

        CONNECTED_TO_SERVER,

        RETURN_USERNAME,

        CORRECT_CLIENT,

        INCORRECT_CLIENT,

        DRAWING,

        DISCONNECT,

        DISCONNECT_CLIENT,

        START_DRAW_CLIENT,

        CONNECT_CLIENT,

        JOIN,

        GUESS
    }

    private static final Hashtable<Class<?>, EnumSet<MessageType>> legalCombinations = new Hashtable<>();

    private static final EnumSet<MessageType> stringData = EnumSet.of(MessageType.SERVER_MESSAGE, MessageType.CLIENT_MESSAGE, MessageType.INCORRECT_CLIENT, MessageType.CORRECT_CLIENT, MessageType.GUESS, MessageType.CORRECT);

    private static final EnumSet<MessageType> DrawStateData = EnumSet.of(MessageType.START_DRAW);

    private static final EnumSet<MessageType> StateData = EnumSet.of(MessageType.CONNECTED_TO_SERVER);

    private static final EnumSet<MessageType> GameStateData = EnumSet.of(MessageType.JOIN, MessageType.CONNECTED_TO_SERVER);

    private static final EnumSet<MessageType> DrawingData = EnumSet.of(MessageType.DRAWING);

    static {
        legalCombinations.put(String.class, stringData);
        legalCombinations.put(Draw.class, DrawStateData);
        legalCombinations.put(State.class, StateData);
        legalCombinations.put(GameState.class, GameStateData);
        legalCombinations.put(Point.class, DrawingData);
    }

    public final MessageType messageType;

    public final String client;

    public final Data<?> data;

    public Message(MessageType messageType)
    {
        this.messageType = messageType;
        this.client = null;
        this.data = null;
    }

    public Message(MessageType messageType, String client) throws IllegalArgumentException
    {
        this.messageType = messageType;
        this.client = client;
        this.data = null;
        if(shouldContainData(messageType))
        {
            throw new IllegalArgumentException(messageType.toString() + " needs data.");
        }
    }

    private Message(MessageType messageType, String client, Data<?> data) throws IllegalArgumentException
    {
        if (data.data != null && !legalCombination(messageType, data.data.getClass()))
        {
            throw new IllegalArgumentException("Illegal combination: " + messageType + data.data.getClass());
        }
        this.messageType = messageType;
        this.client = client;
        this.data = data;
    }

    public static <T> Message CreateDataMessage(MessageType messageType, String client, T data)
    {
        Message message = null;
        try
        {
            message =  new Message(messageType, client, new Data<>(data));
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        return message;
    }

    public static <T> Message CreateDataMessage(MessageType messageType, T data)
    {
        return CreateDataMessage(messageType, null, data);
    }

    private static boolean legalCombination(MessageType messageType, Class<?> in)
    {
        if (!legalCombinations.containsKey(in))
        {
            return false;
        }
        EnumSet<MessageType> messageTypeEnumSet = legalCombinations.get(in);
        return messageTypeEnumSet.contains(messageType);
    }

    private static boolean shouldContainData(MessageType messageType)
    {
        for(EnumSet<MessageType> enumSet : legalCombinations.values())
        {
            if(enumSet.contains(messageType))
            {
                return true;
            }
        }
        return false;
    }
}
