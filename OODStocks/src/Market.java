/*
 * Market class for OOD.
 */

import java.util.HashMap;
import java.util.ArrayList;
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
        Integer[] temp = new Integer[2];

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

        for(int i = 0; i < stocks.size(); ++i){
            sum += stocks.get(i).getPrice();
        }
        totalValue.add(sum);
    }

    public static Market getMarket(){
        if(currentMarket != null){
            return currentMarket;
        }
        currentMarket = new Market();
        return currentMarket;
    }

    public void createStock(){
        Integer[] temp = new Integer[2];

        temp[0] = this.stocks.size();
        this.stocks.add(this.issuer.initialPublicOffering());
        temp[1] = (int)(this.stocks.get(temp[0]).getShareCount() * 0.05);
        this.shareAvailability.put(this.stocks.get(temp[0]).getSymbol(), temp);
    }

    public void sellShares(String symbol, int count){
        Stock temp = stocks.get(shareAvailability.get(symbol)[0]);
        Scanner input = new Scanner(System.in);

        if(temp == null){
            System.out.printf("Stock does not exist.\n");
            return;
        }
        if(shareAvailability.get(symbol)[1] < count){
            System.out.printf("You are attempting to purchase more stock than is available.\nWould you like to purchase" +
                    "all available stock?\nY or N.\n");
            if(input.next().equals("N")){
                System.out.printf("Returning to previous state.\n");
            }
        }

        investor.purchaseShares(temp, count, temp.getPrice() * count);

        input.close();
    }

    public ArrayList getStocks(){
        return this.stocks;
    }

    public HashMap getShareAvailability(){
        return this.shareAvailability;
    }

    public Investor getInvestor(){
        return this.investor;
    }

    public void updateMarket(){
        double sum = 0;
        for(int i = 0; i < stocks.size(); ++i){
            stocks.get(i).updateSharePrice();
            sum += stocks.get(i).getPrice();
        }

        totalValue.add(sum);

        sum = 0;

        for(int i = 0; i < stocks.size(); ++i){
            if(investor.getOwnedShares(stocks.get(i).getSymbol()) != 0){
                sum += investor.getOwnedShares(stocks.get(i).getSymbol()) * stocks.get(i).getPrice();
            }
        }

        investor.updateHoldings(sum);
    }

    public ArrayList getTotalValue(){
        return this.totalValue;
    }
}
