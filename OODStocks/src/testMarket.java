///**
// * Allowing us to test the entire project.
// */
//
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//import javafx.scene.control.Button;
//
//import javax.swing.text.html.HTMLDocument;
//
//public class testMarket extends Application{
//
//    public void start(Stage stage){
//        Market market = Market.getMarket();
//        for(int i = 0; i < 10; ++i){
//            market.createStock();
//            market.updateMarket();
//        }
//        Iterator stocks = market.getStocks();
//        ArrayList<Stock> temp = new ArrayList<>();
//        while (stocks.hasNext()) {
//            temp.add((Stock) stocks.next());
//        }
//
//        stage.setTitle("Statesboro Small Stocks!");
//        stage.setMinHeight(768);
//        stage.setMinWidth(1024);
//        GridPane root = new GridPane();
//        HBox tabBar = new HBox();
//        tabBar.getChildren().add(0, new Button("Market"));
//        tabBar.getChildren().add(1, new Button("Rising 5"));
//        tabBar.getChildren().add(2, new Button("Falling 10"));
//        tabBar.getChildren().add(3, new Button("My Stocks"));
//        tabBar.setSpacing(15);
//        root.add(tabBar, 0, 0);
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(15);
//
//
//        root.add(testMarket.populateText(temp), 0, 1);
//        root.add(testMarket.populateChart(market.getTotalValue()), 1, 1);
//
//
//
//        Scene graph = new Scene(root, 1024, 768);
//        stage.setScene(graph);
//        stage.sizeToScene();
//        stage.show();
//    }
//
//    public static void main(String[] args){
//        launch(args);
//    }
//
//    static void printMarket(Market market){
//        Iterator stocks = market.getStocks();
//        ArrayList<Stock> temp = new ArrayList<>();
//        while (stocks.hasNext()) {
//            temp.add((Stock) stocks.next());
//        }
//        if(temp == null){
//            System.out.printf("ArrayList not initialized.\n");
//            return;
//        }
//
//        System.out.printf("Index\tSymbol\tPrice\t6MoHi\t6MoLo\n");
//
//        for(int i = 0; i < temp.size(); ++i){
//            System.out.printf("%d\t\t%s\t%.2f\t%.2f\t%.2f\n", i, temp.get(i).getSymbol(), temp.get(i).getPrice(), temp.get(i).getSixMoHi(), temp.get(i).getSixMoLo());
//        }
//        System.out.printf("Current Balance: %.2f.\n", market.getInvestor().getBalance());
//    }
//
//    static GridPane populateText(ArrayList<Stock> inputList){
//
//        DecimalFormat doubleForm = new DecimalFormat(".##");
//        GridPane textRow = new GridPane();
//
//        textRow.setAlignment(Pos.CENTER_LEFT);
//        textRow.setHgap(8);
//        textRow.setVgap(2);
//        textRow.setPadding(new Insets(25, 25, 25, 25));
//
//
//
//        for(int i = 0; i < inputList.size(); ++i) {
//            Text[] tempText = new Text[5];
//            for(int j = 0; j < 5; ++j){
//                tempText[j] = new Text();
//            }
//            tempText[0].setText(Integer.toString((i + 1)));
//            textRow.add(tempText[0], 0, i);
//            tempText[1].setText(inputList.get(i).getSymbol());
//            textRow.add(tempText[1], 1, i);
//            tempText[2].setText(doubleForm.format((inputList.get(i).getPrice())));
//            textRow.add(tempText[2], 2, i);
//            tempText[3].setText(doubleForm.format((inputList.get(i).getPrice())));
//            textRow.add(tempText[3], 3, i);
//            tempText[4].setText(doubleForm.format((inputList.get(i).getPrice())));
//            textRow.add(tempText[4], 4, i);
//        }
//
//        return textRow;
//    }
//
//    static LineChart populateChart(ArrayList<Double> inputList){
//        final NumberAxis dayAxis = new NumberAxis();
//        final NumberAxis priceAxis = new NumberAxis();
//        dayAxis.setLabel("Day of 6 Month Cycle");
//        priceAxis.setLabel("Price");
//        LineChart<Number, Number> stockChart = new LineChart<Number, Number>(dayAxis, priceAxis);
//        stockChart.setTitle("Market Total Value");
//
//        XYChart.Series history = new XYChart.Series();
//        history.setName("Market History");
//
//        for(int i = 0; i < inputList.size(); ++i){
//            history.getData().add(new XYChart.Data(i + 1, inputList.get(i)));
//        }
//
//        stockChart.getData().add(history);
//
//        return stockChart;
//    }
//}
