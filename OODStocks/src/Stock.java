import java.util.ArrayList;

/*
 * Stock class, which contains information about a stock.
 */
public class Stock {
    private String symbol;
    private double sixMoHi, sixMoLo, price, marketCap;
    private int shareCount;
    private ArrayList<Double> priceHistory;

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

    //Update the prices, as well as the priceHistory.
    void updateSharePrice() {
        this.price = this.price + (Math.random() - .5);
        if (this.price > this.sixMoHi)
            this.sixMoHi = this.price;
        if (this.price < this.sixMoLo)
            this.sixMoLo = this.price;

        this.priceHistory.add(this.price);
    }
}
