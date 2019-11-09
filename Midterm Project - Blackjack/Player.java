public class Player
{
    private Hand playerHand;
    private int balance;
    private String name;
    private Shoe shoe;

    public Player(int b, String n, Shoe s)
    {
        this.shoe = s;
        balance = b;
        name = n;
        playerHand = new Hand();
    }

    public void betMoney(int bet){
        balance -= bet;
    }
    

    public void doubleDown(int bet){
        balance -= bet;
        hit();
    }

    public void doubleDown(int bet, Hand h){
        balance -= bet;
        hit(h);
    }
    
    public void addWin(int won){
        balance += won;
    }

    public void hit(){
        playerHand.addCard(shoe.dealCard());
    }

    public void hit(Hand h) {
        h.addCard(shoe.dealCard());
    }

    public Hand getHand(){
        return playerHand;
    }

    public int getBalance(){
        return balance;
    }

    public String toString(){
        return name + "'s hand: " + getHand();
    }

    public String toString(Hand h){
        return name + "'s new hand: " + h;
    }

    public String toStringS(){
        return name + "'s old hand: " + getHand();
    }

    public void reset(){
        playerHand.getHand().subList(0, playerHand.getHand().size()).clear();
    }
}