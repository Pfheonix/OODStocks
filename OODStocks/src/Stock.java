/*
 * Stock class, which contains information about a stock.
 */
public class Stock {
    private String symbol;
    private float sixMoHi, sixMoLo, price, marketCap;
    private int shareCount;

    Stock(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < 4; ++i){
            temp.append((char)((Math.random() * 26) + 65));
        }
        this.symbol = temp.toString();
        this.price = (float)((Math.random() * 10) + 0.01);
        this.sixMoHi = this.sixMoLo = this.price;
        this.shareCount = (int)(((Math.random() * 50) + 50) * Math.pow(10, 5));
        this.marketCap = shareCount * price;
    }

    String getSymbol() {
        return symbol;
    }

    float getSixMoHi() {
        return sixMoHi;
    }

    float getSixMoLo() {
        return sixMoLo;
    }

    float getPrice() {
        return price;
    }

    float getMarketCap() {
        return marketCap;
    }

    int getShareCount() {
        return shareCount;
    }
}
