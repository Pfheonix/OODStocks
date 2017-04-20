import java.util.ArrayList;
import java.util.HashMap;

/*
 * Investor class, which contains the user's profile as an investor.
 * The HashMap ownedStocks contains the Stock's symbol as a string, and then an integer value, used to determine the
 * number of shares owned. AcctBalance is the available capital to purchase stocks, while holdings is the overall value
 * of the held investments.
 */
public class Investor {

    private double acctBalance, holdings;
    private HashMap<String, Integer> ownedStocks;

    Investor(){
        this.acctBalance = 10000;
        this.ownedStocks = new HashMap<String, Integer>();
        this.holdings = 0;
    }

    double getBalance(){
        return this.acctBalance;
    }

    int getOwnedShares(String key){
        return ownedStocks.getOrDefault(key, 0);
    }

    public HashMap<String, Integer> getOwnedStocks(){ return this.ownedStocks; }

    //Purchasing
    void purchaseShares(Stock stock, int count, double cost){
        if(cost > this.acctBalance){
            System.out.printf("Your account balance is not sufficient.\nPlease deposit more, or reduce the number of shares.\n");
            return;
        }
        if(this.ownedStocks.containsKey(stock.getSymbol())){
            this.acctBalance -= cost;
            ownedStocks.replace(stock.getSymbol(), ownedStocks.get(stock.getSymbol()) + count);
            this.holdings += cost;
        } else {
            this.acctBalance -= cost;
            ownedStocks.put(stock.getSymbol(), count);
            this.holdings += cost;
        }
    }

    public double getHoldings(){
        return this.holdings;
    }

    void updateHoldings(double updatedHoldings){
        this.holdings = updatedHoldings;
    }

    public void updateBalance(double deposit){
        this.acctBalance += deposit;
    }
}
