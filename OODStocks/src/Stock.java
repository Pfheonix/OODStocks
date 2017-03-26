/**
 * Created by Pfheonix on 3/26/2017.
 */
public class Stock {
    private String name, symbol;
    private float sixMoHi, sixMoLo, price, marketCap;
    private int number;

    protected Stock(){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i < 4; ++i){
            temp.append((Math.random() * 26) + 65);
        }
        this.symbol = temp.toString();
        this.price = (float)((Math.random() * 10) + 0.01);
        this.sixMoHi = this.sixMoLo = this.price;
        this.number = (int)(((Math.random() * 50) + 50) * Math.pow(10, 5));
        this.marketCap = number * price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public float getSixMoHi() {
        return sixMoHi;
    }

    public float getSixMoLo() {
        return sixMoLo;
    }

    public float getPrice() {
        return price;
    }

    public float getMarketCap() {
        return marketCap;
    }

    public int getNumber() {
        return number;
    }
}
