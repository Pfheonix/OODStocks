/**
 * Allowing us to test the entire project.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class testMarket {
    public static void main(String[] args){
        Market stockMarket = Market.getMarket();

        printMarket(stockMarket);
    }

    static void printMarket(Market market){
        ArrayList<Stock> temp = market.getStocks();
        if(temp == null){
            System.out.printf("ArrayList not initialized.\n");
            return;
        }

        System.out.printf("Index\tSymbol\tPrice\t6MoHi\t6MoLo\n");

        for(int i = 0; i < temp.size(); ++i){
            System.out.printf("%d\t\t%s\t%.2f\t%.2f\t%.2f\n", i, temp.get(i).getSymbol(), temp.get(i).getPrice(), temp.get(i).getSixMoHi(), temp.get(i).getSixMoLo());
        }
        System.out.printf("Current Balance: %.2f.\n", market.getInvestor().getBalance());
    }
}
