import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/*
 * Stock class, which contains information about a stock.
 * The symbol is used to refer to the stock in many different contexts, while the highs, lows, and marketcap help
 * describe the health of the company selling the stock. ShareCount is self explanatory, and the price history is used
 * for graphing.
 */
public class Stock {
    private String symbol;
    private double sixMoHi, sixMoLo, price, marketCap;
    private int shareCount;
    private ArrayList<Double> priceHistory;
    private double percentGrowth;

    Stock(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < 4; ++i){
            temp.append((char)((Math.random() * 26) + 65));
        }
        this.symbol = temp.toString();
        this.price = (Math.random() * 10) + 0.01;
        this.sixMoHi = this.sixMoLo = this.price;
        this.shareCount = (int)(((Math.random() * 50) + 50) * Math.pow(10, 5));
        this.marketCap = shareCount * price;
        this.priceHistory = new ArrayList<Double>();
        this.priceHistory.add(this.price);
        percentGrowth = 0;

    }

    String getSymbol() {
        return symbol;
    }

    double getSixMoHi() {
        return sixMoHi;
    }

    double getSixMoLo() {
        return sixMoLo;
    }

    double getPrice() {
        return price;
    }

    double getMarketCap() {
        return marketCap;
    }

    int getShareCount() {
        return shareCount;
    }

    double getPercentGrowth(){
        return this.percentGrowth;
    }

    //Update the prices, as well as the priceHistory.
    void updateSharePrice() {
        this.price = this.price + (Math.random() - .5);
        if (this.price <= 0){
            return;
        }
        if (this.price > this.sixMoHi) {
            this.sixMoHi = this.price;
        }
        if (this.price < this.sixMoLo) {
            this.sixMoLo = this.price;
        }

        this.priceHistory.add(this.price);
        if(priceHistory.size() < 6) {
            percentGrowth = (priceHistory.get(priceHistory.size()-1) - priceHistory.get(0))/priceHistory.size();
        } else if (priceHistory.size() > 5) {
            percentGrowth = (priceHistory.get(priceHistory.size()-1) - priceHistory.get(priceHistory.size()-6))/5;
        } else {
            percentGrowth = 0;
        }
    }

    public String forList() {
        NumberFormat format = new DecimalFormat("#0.00");
        return (symbol + "    \t" + format.format(percentGrowth) + "    \t" + format.format(price) + "    \t" + format.format(sixMoHi) + "    \t"  + format.format(sixMoLo));
    }

    public String forTopFive() {
        NumberFormat format = new DecimalFormat("#0.00");
        return (symbol + "    \t" + format.format(percentGrowth) + "    \t" + format.format(price) + "    \t" + format.format(sixMoHi) + "    \t"  + format.format(sixMoLo));
    }

    public String forLowTen() {
        NumberFormat format = new DecimalFormat("#0.00");
        return (symbol + "    \t" + format.format(percentGrowth) + "    \t" +format.format(price) + "    \t" + format.format(sixMoHi) + "    \t"  + format.format(sixMoLo));
    }

}
