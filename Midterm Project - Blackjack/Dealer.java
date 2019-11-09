public class Dealer
{
    private Hand dealerHand;
    private Shoe shoe;
    public Dealer(Shoe s){
        shoe = s;
        dealerHand = new Hand();
    }

    public int dealerMove() throws InterruptedException{
        System.out.println(this);
        int value = dealerHand.getValue();
        Thread.sleep(500);
        System.out.println("Dealer's value: " + value);
        Thread.sleep(500);
        while (value <=16){
            System.out.println("Dealer hits.");
            dealerHand.addCard(deal());
            value = dealerHand.getValue();
            Thread.sleep(1000);
            System.out.println(this);
            Thread.sleep(500);
            System.out.println("Dealer's value: " + value);
            Thread.sleep(500);
        }
        return value;
    }

    public Card deal(){
        return shoe.dealCard();
    }
    
    public Hand getHand(){
        return dealerHand;
    }
    
    public String toString(){
        return "Dealer's hand: " + dealerHand;
    }
    
    public void reset(){
        dealerHand.getHand().subList(0, dealerHand.getHand().size()).clear();
    }
}
