import java.util.ArrayList;
import java.util.Arrays;
public class Hand{
    private ArrayList<Card> hand;

    Hand(Card... c){
        hand = new ArrayList<Card>(Arrays.asList(c));
    }

    int getValue(){
        int val = 0;
        for(Card c: hand)
            val += c.getCardValue();
        if (val > 21)
            val = aceCheck(val);
        return val;
    }

    int aceCheck(int val){
        for (Card c: hand){
            if (c.isAce()){
                if(val > 21){
                    c.setAceToOne(true);
                    val = 0;
                }
                for(Card c1: hand)
                    val += c1.getCardValue();
                if (val <= 21)
                    break;
            }
        }
        return val;
    }

    ArrayList<Card> getHand(){
        return hand;
    }

    Card getCard(int in){
        return hand.get(in);
    }

    void addCard(Card c){
        hand.add(c);
    }

    boolean isBust(){
        boolean bust = false;
        if (getValue() > 21){
            bust = true;
            for (Card c: hand){
                if (bust && c.getCardValue() == 11){
                    c.setAceToOne(true);
                    bust = isBust();
                }
            }
        }
        return bust;
    }

    public String toString(){
        StringBuilder ret = new StringBuilder(hand.get(0).toString());
        for (int i = 1; i < hand.size(); i++){
            ret.append(", ").append(hand.get(i).toString());
        }
        return ret.toString();
    }
}