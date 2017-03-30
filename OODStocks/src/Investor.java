import java.util.HashMap;

/*
 * Investor class, which contains the user's profile as an investor.
 */
public class Investor {

    private double acctBalance;
    private HashMap<String, Integer> ownedStocks;

    Investor(){
        this.acctBalance = 10000;
        this.ownedStocks = new HashMap<String, Integer>();
    }

    public double getBalance(){
        return this.acctBalance;
    }

    public int getOwnedShares(String key){
        return ownedStocks.getOrDefault(key, 0);
    }

    void purchaseShares(Stock stock, int count, double cost){
        if(cost > this.acctBalance){
            System.out.printf("Your account balance is not sufficient.\nPlease deposit more, or reduce the number of shares.\n");
            return;
        }
        this.acctBalance -= cost;
        ownedStocks.put(stock.getSymbol(), count);
    }
}
