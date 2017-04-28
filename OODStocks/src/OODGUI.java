//Russell Smith, 90698657, Object Oriented Design Final Project
//http://zetcode.com/gui/javafx/ <- big help for basics

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

//the class that uses Application is the main class of an FX program.
public class OODGUI extends Application {

	public static void main(String[] args){
		OODGUI.launch(args);
	}

	public void start(Stage stage){
		TheGUI(stage);
	}

	//The user interface is actually built in this method.
	private void TheGUI(Stage stage){

		NumberFormat format = new DecimalFormat("#00.00");
		Market market = Market.getMarket();
		market.createStock();
		market.updateMarket();
		Iterator stocks = market.getStocks();
		ArrayList<Stock> stockListing = new ArrayList<>();
		while (stocks.hasNext()) {
			Stock tempStock = (Stock) stocks.next();
			stockListing.add(tempStock);
		}

		//This is where everything is positioned for the GUI
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		root.setPadding(new Insets(5));

		//This is the container for all the content in a scene graph. the frame everything rests on
		Scene scene = new Scene(root, 1024, 768);

		//this HBox holds the 4 buttons that alternate views of listview and linechart.
		HBox root2 = new HBox();
		root2.setPadding(new Insets(10));
		root2.setAlignment(Pos.CENTER);
		root2.setSpacing(15);

		//This HBox holds the 2 labels and text objects for 'balance' and 'holdings'
		HBox root3 = new HBox();
		root3.setPadding(new Insets(10));
		root3.setAlignment(Pos.BASELINE_LEFT);
		root3.setSpacing(15);

		//The axis' and the chart that show the span of the markets rise and fall
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel("Day");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Value ($)");
		LineChart<Number,Number> marketchart = new LineChart<Number,Number>(xAxis, yAxis);
		marketchart.setTitle("Current Market Value");
		marketchart.setCreateSymbols(false);

		//XYChart is the actual stored number info that is fed into the lineChart
		XYChart.Series<Number,Number> marketchartdata = new XYChart.Series<Number,Number>();
		marketchartdata.setName("Market Value");

		//The area for setting the (x,y) (Day,Value) values for our lineChart
		//Any method that returns an integer is able to utilized in place of the placeholder #'s (1-10)


		//This actually adds the data from above to the lineChart.
		marketchart.getData().add(marketchartdata);
		
		// manipulate the below to allow listView and lineChart to expand with window
		ColumnConstraints cCons1 = new ColumnConstraints();
		cCons1.setHgrow(Priority.NEVER);
		ColumnConstraints cCons2 = new ColumnConstraints();
		cCons2.setHgrow(Priority.ALWAYS);
		
		root.getColumnConstraints().addAll(cCons1, cCons1, cCons1, cCons1, cCons2);
		
		RowConstraints rCons1 = new RowConstraints();
		rCons1.setVgrow(Priority.NEVER);
		RowConstraints rCons2 = new RowConstraints();
		rCons2.setVgrow(Priority.ALWAYS);
		
		root.getRowConstraints().addAll(rCons1, rCons2);
		// manipulate the zbove to allow listview and linechart to expand with window
		
		//The labels for balance and holdings that sit on the bottom of GUI
		Label dayCounter = new Label("Day: 0");
		dayCounter.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		Label balanceLbl = new Label("Balance:");
		balanceLbl.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		Label holdingsLbl = new Label("Holdings:");
		holdingsLbl.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		//The fields that take variables and represent balance and holdings of the GUI user.
		Text balance = new Text("" + market.getInvestor().getBalance());
		Text holdings = new Text("" + market.getInvestor().getHoldings());

		//'stockinput' is a placeholder below, though it might be used as a copy
		//from another method if that is easier to translate/transfer.
		//observablelist is used so that as it is changed, so is the listview output.
		ListView<String> marketview = new ListView<String>();
        //possibly put this in OODFinal and just use 'class.method' for setItems()
		ObservableList<String> stockInput = FXCollections.observableArrayList();
		ObservableList<String> top5Input = FXCollections.observableArrayList();
		ObservableList<String> bot10Input = FXCollections.observableArrayList();
		ObservableList<String> yourInput = FXCollections.observableArrayList();

		stockInput.add(stockListing.get(0).forList());

		//these for loops make calls to external methods that add single lines at a time.
		stockInput.add("Name    \tGrowth\tNow    \tHigh    \tLow");
		 //sets list to have items before a button is pressed.

		//A button is created and mnemonic parsing activated so '_' doesn't show.
		// '_' means the next character+alt activates btn.
		//The text on btn is set then its event->action assigned.
		//A tooltip is created, then install(x) assigns it to x button.
		Button marketBtn = new Button();
		marketBtn.setMnemonicParsing(true);
		marketBtn.setText("_Market");
		marketBtn.setOnAction((ActionEvent event) -> {marketview.setItems(stockInput);});
		Tooltip marketTip = new Tooltip("Shows the current market selection of stocks.");
		Tooltip.install(marketBtn, marketTip);
		
		Button top5Btn = new Button();
		top5Btn.setMnemonicParsing(true);
		top5Btn.setText("Top _5 Climbing");

        //replace 'stockInput' with the top 5 stocks list from exterior method
		top5Btn.setOnAction((ActionEvent event) -> {marketview.setItems(top5Input);});
		Tooltip topFiveTip = new Tooltip("Selects the currently fasting climbing 5 stocks.");
		Tooltip.install(top5Btn, topFiveTip);
		
		Button bot10Btn = new Button();
		bot10Btn.setMnemonicParsing(true);
		bot10Btn.setText("Bottom _10 Falling");

		//replace 'stockInput' with the bot 10 stocks list from exterior method
		bot10Btn.setOnAction((ActionEvent event) -> {marketview.setItems(bot10Input);});
		Tooltip botTenTip = new Tooltip("Selects the currently fastest falling 10 stocks.");
		Tooltip.install(bot10Btn, botTenTip);
		
		Button yourBtn = new Button();
		yourBtn.setMnemonicParsing(true);
		yourBtn.setText("Your _Stocks");

		//replace 'stockInput' with your stocks list from exterior method
		yourBtn.setOnAction((ActionEvent event) -> {marketview.setItems(yourInput);});
		Tooltip yourTip = new Tooltip("Selects the list of stocks that you own.");
		Tooltip.install(yourBtn, yourTip);
		
		Button buyBtn = new Button();
		buyBtn.setMnemonicParsing(true);
		buyBtn.setText("_Buy");

		//Purchases stocks to the user.
		buyBtn.setOnAction((ActionEvent event) -> {
			String temp = marketview.getSelectionModel().getSelectedItem();
			String[] tokens = temp.split("\\s\\s\\s\\s\t");

			double tempBalanceVar = Double.parseDouble(balance.getText());
			tempBalanceVar -= (Double.parseDouble(tokens[2]));

			TextInputDialog dialog = new TextInputDialog("0000");
			dialog.setTitle("Purchasing" + tokens[0]);
			dialog.setContentText("How many shares of " + tokens[0] + " would you like to purchase?");

			Optional<String> result = dialog.showAndWait();

			result.ifPresent(count -> {market.buyShares(tokens[0], Integer.parseInt(count));
			    yourInput.add(temp);
			});

			balance.setText("" + format.format(market.getInvestor().getBalance()));
			holdings.setText("" + format.format(market.getInvestor().getHoldings()));
		});

		Tooltip buyTip = new Tooltip("Click to purchase selected stock.");
		Tooltip.install(buyBtn, buyTip);

		//The next day tells the market to update itself, then requests the updated information, and displays it.
		Button dayBtn = new Button();
		dayBtn.setMnemonicParsing(true);
		dayBtn.setText("_Next Day");
		dayBtn.setOnAction((ActionEvent) -> {
			//anything the next day button wants to do goes here.
			String temp = dayCounter.getText();
			String[] tokens = temp.split(":\\s");

			int day = Integer.parseInt(tokens[1]);
			stockInput.clear();
			top5Input.clear();
			bot10Input.clear();
			yourInput.clear();
			market.updateMarket();
			Iterator updateStocks = market.getStocks();
			ArrayList<Stock> newStockListing = new ArrayList<>();
			ArrayList<Stock> growthSort = new ArrayList<>();
			marketchartdata.getData().add(new XYChart.Data<Number, Number>(day, (double) market.getTotalValue().get(market.getTotalValue().size()-1)));

			stockInput.add("Name    \tGrowth\tNow    \tHigh    \tLow");
            top5Input.add("Name    \tGrowth\tNow    \tHigh    \tLow");
            bot10Input.add("Name    \tGrowth\tNow    \tHigh    \tLow");
            yourInput.add("Name    \tGrowth\tNow    \tHigh    \tLow    \tOwned");

			//When using an iterator, it is generally understand that you use one .next per loop.
            //Otherwise, you skip things.
            //And wind up sorting a disjoint set from your market.
			while (updateStocks.hasNext()) {
				newStockListing.add((Stock) updateStocks.next());
			}
			growthSort.addAll(newStockListing);
			day++;
			dayCounter.setText("Day: " + day);

			//Sorting an ArrayList designed to reflect Growth rate.
            growthSort = stockSort(growthSort, new ArrayList<>());

            //Magical for loop goodness which adds to all of the views.
			for(int i = 0; i < newStockListing.size(); i++){ stockInput.add(newStockListing.get(i).forList()); }
			marketview.setItems(stockInput);
			for(int i = 0; i < growthSort.size() && i < 10; i++){bot10Input.add(growthSort.get(i).forTopFive());}
			for(int i = growthSort.size() - 1; i > 0 && i > growthSort.size() - 6; --i){top5Input.add(growthSort.get(i).forLowTen());}
			for(int i = 0; i < newStockListing.size(); ++i){
			    if(market.getInvestor().getOwnedStocks().containsKey(newStockListing.get(i).getSymbol())){
			        yourInput.add(newStockListing.get(i).forList() + "     \t" + market.getInvestor().getOwnedStocks().get(newStockListing.get(i).getSymbol()));
                }
            }

            holdings.setText("" + format.format(market.getInvestor().getHoldings()));

		});

		Button addBtn = new Button();
		addBtn.setMnemonicParsing(true);
		addBtn.setText("_Add Stock");
		addBtn.setOnAction((ActionEvent) -> {
			//anything the add stock button wants to do goes here.
			String temp = dayCounter.getText();
			String[] tokens = temp.split(":\\s");

			int day = Integer.parseInt(tokens[1]);
			stockInput.clear();
			top5Input.clear();
			bot10Input.clear();
			market.createStock();
			Iterator updateStocks = market.getStocks();
			ArrayList<Stock> newStockListing = new ArrayList<>();
			ArrayList<Stock> growthSort = new ArrayList<>();
			marketchartdata.getData().add(new XYChart.Data<Number, Number>(day, (double) market.getTotalValue().get(market.getTotalValue().size()-1)));

			stockInput.add("Name    \tGrowth\tNow    \tHigh    \tLow");

			//When using an iterator, it is generally understood that you use one .next per loop.
			//Otherwise, you skip things and wind up sorting a disjoint set from your market.
			while (updateStocks.hasNext()) {
				newStockListing.add((Stock) updateStocks.next());
			}
			growthSort.addAll(newStockListing);
			dayCounter.setText("Day: " + day);

			//Once again, sorts the stocks.
            //Seems unnecessary, but should a stock that, technically, has no movement still act as one of the top 5...
            //You don't want to miss out, do you?
			growthSort = stockSort(growthSort, new ArrayList<>());

			//Magical for loop goodness.
			for(int i = 0; i < newStockListing.size(); i++){ stockInput.add(newStockListing.get(i).forList()); }
			marketview.setItems(stockInput);
			for(int i = 0; i < growthSort.size() && i < 10; i++){bot10Input.add(growthSort.get(i).forTopFive());}
			for(int i = growthSort.size() - 1; i > 0 && i > growthSort.size() - 6; --i){top5Input.add(growthSort.get(i).forLowTen());}

		});

		Tooltip addTip = new Tooltip("Click to iterate to add a stock to the market.");
		Tooltip.install(addBtn, addTip);

		//Adding to scene.(child, column start, row start, column width, row length)
		root2.getChildren().add(marketBtn);
		root2.getChildren().add(top5Btn);
		root2.getChildren().add(bot10Btn);
		root2.getChildren().add(yourBtn);
		root3.getChildren().add(balanceLbl);
		root3.getChildren().add(balance);
		root3.getChildren().add(holdingsLbl);
		root3.getChildren().add(holdings);
		root3.getChildren().add(buyBtn);
		root.add(root2, 0, 0, 5, 1);
		root.add(root3, 0, 4, 4, 1);
		root.add(marketview, 0, 1, 4, 3);
		root.add(marketchart, 4, 1, 2, 3);
		root.add(addBtn, 5,4, 1,1);
		root.add(dayBtn, 6,4, 1,1);
		root.add(dayCounter, 6,1,1,1);
		
		
		//setTitle sets a title for the main window.
		stage.setTitle("The Boro Stock Exchange");

		//This adds the scene to the stage.
		stage.setScene(scene);
		stage.sizeToScene();

		//This is what puts the window on the screen.
		stage.show();
	}

	//A mergesort to allow for growth rate discrimination - i.e. Top 5, Bottom 10.
    public static ArrayList<Stock> stockSort(ArrayList<Stock> toSort, ArrayList<Stock> sortHelp){
        int toSortSize = toSort.size();
        Stock[] convStock = new Stock[toSortSize];
        convStock = toSort.toArray(convStock);

        if(toSortSize == 0){
            return toSort;
        }

        for(int w = 1; w < toSortSize; w *= 2){
            for(int i = 0; i < toSortSize; i += 2 * w){
                sortHelp.addAll(stockSortMerge(convStock, i, Math.min(i + w, toSortSize), Math.min(i + (2 * w), toSortSize), new Stock[toSortSize]));
            }

            toSort.clear();
            toSort.addAll(sortHelp);
            convStock = toSort.toArray(convStock);
            sortHelp.clear();
        }

        return toSort;
    }

    //The actual merge method. To change key value, change .getPercentGrowth() to new value getter.
    public static ArrayList<Stock> stockSortMerge(Stock[] toSort, int iLeft, int iRight, int iEnd, Stock[] sortHelp){
        int i = iLeft, j = iRight;

        for(int k = iLeft; k < iEnd; ++k){
            if(j >= iEnd){
                sortHelp[k] = toSort[i];
                i += 1;
            } else if(i < iRight && toSort[j].getPercentGrowth() < toSort[i].getPercentGrowth()){
                sortHelp[k] = toSort[j];
                j += 1;
            } else if (i < iRight && toSort[i].getPercentGrowth() <= toSort[j].getPercentGrowth()){
                sortHelp[k] = toSort[i];
                i += 1;
            } else {
                sortHelp[k] = toSort[j];
                j += 1;
            }

        }

        ArrayList<Stock> sortList = new ArrayList<Stock>();
        for(int l = 0; l < sortHelp.length; ++l){
            if(sortHelp[l] != null)
                sortList.add(sortHelp[l]);
        }

        return sortList;
    }
}
