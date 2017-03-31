/*
 * Stock class, which contains information about a stock.
 */
public class Stock {
    private String symbol;
    private double sixMoHi, sixMoLo, price, marketCap;
    private int shareCount;

    Stock(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < 4; ++i){
            temp.append((Math.random() * 26) + 65);
        }
        this.symbol = temp.toString();
        this.price = Math.random() * 10) + 0.01;
        this.sixMoHi = this.sixMoLo = this.price;
        this.shareCount = (int)(((Math.random() * 50) + 50) * Math.pow(10, 5));
        this.marketCap = shareCount * price;
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
    
    void updateSharePrice() {
        price = price + (Math.random() - .5)
        if price > sicMoHi
            sixMoHi = price;
        if price < sixMoLo
            sixMoLo = price;
    }
}
