/*
 * Market class for OOD. It uses a singleton construction, allowing
 */

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Market {
    //Creating an ArrayList<Stock> to allow the market to keep the Stocks in itself.
    //A HashMap<String, Integer[]> allows the Market to know two things: the stock with the String key as its symbol's
    //Location in the ArrayList, and then the number of available shares.
    private ArrayList<Stock> stocks;
    private HashMap<String, Integer[]> shareAvailability;
    private StockFactory issuer;
    private static Market currentMarket;
    private Investor investor;
    private ArrayList<Double> totalValue;

    private Market(){
        //Initializing a temporary Integer array, to hold the index in the stocks ArrayList and the number of shares
        //available for each stock.
        Integer[] temp = new Integer[2];

        //Initializing each field, creating a stock, and filling each field with a value
        investor = new Investor();
        issuer = new StockFactory();
        shareAvailability = new HashMap<String, Integer[]>();
        stocks = new ArrayList<>();
        totalValue = new ArrayList<>();
        temp[0] = stocks.size();
        stocks.add(issuer.initialPublicOffering());
        temp[1] = (int)(stocks.get(temp[0]).getShareCount() * 0.05);
        shareAvailability.put(stocks.get(temp[0]).getSymbol(), temp);

        double sum = 0;

        //Foreach stock, get the price, add it, then set it as a data point in totalValue.
        for(Stock stock : stocks){
            sum += stock.getPrice();
        }
        totalValue.add(sum);
    }

    //Start Iterator Pattern
    //MarketStocks function will be used to display the list of all of the items in the market
    public Iterator getStocks() { return new MarketStocks(); }

    private class MarketStocks implements Iterator {
        int index;

        //This will return the stocks one at a time which should make
        // it easier to put the stocks into the GUI's array list
        @Override
        public Object next() {
            if (this.hasNext()) {
                return stocks.get(index++);
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            if (index < stocks.size()) {
                return true;
            }
            return false;
        }
    }
    //End Iterator Pattern
    
    //Getting a Market, which acts as a singleton.
    static Market getMarket(){
        if(currentMarket != null){
            return currentMarket;
        }
        currentMarket = new Market();
        return currentMarket;
    }

    //Creating a stock. Primarily used before the UpdateMarket method.
    void createStock(){
        Integer[] temp = new Integer[2];

        temp[0] = this.stocks.size();
        this.stocks.add(this.issuer.initialPublicOffering());
        temp[1] = (int)(this.stocks.get(temp[0]).getShareCount() * 0.05);
        this.shareAvailability.put(this.stocks.get(temp[0]).getSymbol(), temp);
    }

    //Buying shares for the investor's portfolio.
    public void buyShares(String symbol, int count){
        Stock temp = stocks.get(shareAvailability.get(symbol)[0]);
        Scanner input = new Scanner(System.in);

        if(temp == null){
            System.out.printf("Stock does not exist.\n");
            return;
        }

        //Checking to make sure you're not purchasing more shares than exist. That'd be bad.
        if(shareAvailability.get(symbol)[1] < count){
            System.out.printf("You are attempting to purchase more stock than is available.\nWould you like to purchase" +
                    "all available stock?\nY or N.\n");
            if(input.next().equals("N")){
                System.out.printf("Returning to previous state.\n");
                input.close();
                return;
            }
            investor.purchaseShares(temp, shareAvailability.get(symbol)[1], temp.getPrice() * shareAvailability.get(symbol)[1]);
            input.close();
            return;
        }

        investor.purchaseShares(temp, count, temp.getPrice() * count);

        input.close();
    }

    public HashMap getShareAvailability(){
        return this.shareAvailability;
    }

    Investor getInvestor(){
        return this.investor;
    }

    //Update the value of the market, the individual stocks, et cetera.
    void updateMarket(){
        double sum = 0;

        //For all stocks, update the price, remove them if they're 0 or less, and then add them to a sum.
        //Said sum will then be added as a data point to the totalValue.
        for(int i = 0; i < stocks.size(); ++i){
            stocks.get(i).updateSharePrice();
            if(stocks.get(i).getPrice() <= 0){
                stocks.remove(i);
            } else {
                sum += stocks.get(i).getPrice();
            }
        }

        totalValue.add(sum);

        sum = 0;

        //Update the investor's holdings.
        for(Stock stock : stocks){
            if(investor.getOwnedShares(stock.getSymbol()) != 0){
                sum += investor.getOwnedShares(stock.getSymbol()) * stock.getPrice();
            }
        }

        investor.updateHoldings(sum);
    }

    //Duh?
    ArrayList getTotalValue(){
        return this.totalValue;
    }
}
