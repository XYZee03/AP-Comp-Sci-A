public enum Suit{
    HEARTS, DIAMONDS, SPADES, CLUBS;

    @Override
    public String toString(){
        switch(this)
        {
            case HEARTS:    return"Hearts";
            case DIAMONDS:  return"Diamonds";
            case SPADES:    return"Spades";
            case CLUBS:     return"Clubs";
            default:        return"";
        }
    }
}
