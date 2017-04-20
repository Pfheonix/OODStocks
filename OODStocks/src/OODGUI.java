//Russell Smith, 90698657, Object Oriented Design Final Project
//http://zetcode.com/gui/javafx/ <- big help for basics

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.tools.Tool;
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

		NumberFormat format = new DecimalFormat("#0.00");
		Market market = Market.getMarket();
		market.createStock();
		market.updateMarket();
		Iterator stocks = market.getStocks();
		ArrayList<Stock> stockListing = new ArrayList<>();
		while (stocks.hasNext()) {
			Stock tempStock = (Stock) stocks.next();
			stockListing.add(tempStock);
		}
		int balanceVar = 10000;
		int holdingVar = 0;

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
		marketchart.setTitle("Total Market Value Over Time");
		marketchart.setCreateSymbols(false);

		//XYChart is the actual stored number info that is fed into the linechart
		XYChart.Series<Number,Number> marketchartdata = new XYChart.Series<Number,Number>();
		marketchartdata.setName("Market Value");

		//The area for setting the (x,y) (Day,Value) values for our linechart
		//Any method that returns an integer is able to utilized in place of the placeholder #'s (1-10)


		//This actually adds the data from above to the linechart.
		marketchart.getData().add(marketchartdata);
		
		// manipulate the below to allow listview and linechart to expand with window
		ColumnConstraints Ccons1 = new ColumnConstraints();
		Ccons1.setHgrow(Priority.NEVER);
		ColumnConstraints Ccons2 = new ColumnConstraints();
		Ccons2.setHgrow(Priority.ALWAYS);
		
		root.getColumnConstraints().addAll(Ccons1, Ccons1, Ccons1, Ccons1, Ccons2);
		
		RowConstraints Rcons1 = new RowConstraints();
		Rcons1.setVgrow(Priority.NEVER);
		RowConstraints Rcons2 = new RowConstraints();
		Rcons2.setVgrow(Priority.ALWAYS);
		
		root.getRowConstraints().addAll(Rcons1, Rcons2);
		// manipulate the zbove to allow listview and linechart to expand with window
		
		//The labels for balance and holdings that sit on the bottom of GUI
		Label dayCounter = new Label("Day: 0");
		dayCounter.setFont(Font.font("TImes New Roman", FontWeight.NORMAL, 20));
		Label balancelbl = new Label("Balance:");
		balancelbl.setFont(Font.font("TImes New Roman", FontWeight.NORMAL, 20));
		Label holdinglbl = new Label("Holdings:");
		holdinglbl.setFont(Font.font("TImes New Roman", FontWeight.NORMAL, 20));

		//The fields that take variables and represent balance and holdings of the GUI user.
		Text balance = new Text("" + balanceVar);
		Text holdings = new Text("" + holdingVar);

		//'stockinput' is a placeholder below, though it might be used as a copy
		//from another method if that is easier to translate/transfer.
		//observablelist is used so that as it is changed, so is the listview output.
		ListView<String> marketview = new ListView<String>();
			//possibly put this in OODFinal and just use 'class.method' for setItems()
		ObservableList<String> stockinput = FXCollections.observableArrayList();
		ObservableList<String> top5input = FXCollections.observableArrayList();
		ObservableList<String> bot10input = FXCollections.observableArrayList();
		ObservableList<String> yourinput = FXCollections.observableArrayList();

		//these for loops make calls to external methods that add single lines at a time.
		stockinput.add("Name    \tGrowth\tNow    \tHigh    \tLow");
		 //sets list to have items before a button is pressed.

		//A button is created and mnemonic parsing activated so '_' doesn't show.
		// '_' means the next character+alt activates btn.
		//The text on btn is set then its event->action assigned.
		//A tooltip is created, then install(x) assigns it to x button.
		Button marketbtn = new Button();
		marketbtn.setMnemonicParsing(true);
		marketbtn.setText("_Market");
		marketbtn.setOnAction((ActionEvent event) -> {marketview.setItems(stockinput);});
		Tooltip tooltip0 = new Tooltip("Shows the current market selection of stocks.");
		Tooltip.install(marketbtn, tooltip0);
		
		Button top5btn = new Button();
		top5btn.setMnemonicParsing(true);
		top5btn.setText("Top _5 Climbing");
			//replace 'stockinput' with the top 5 stocks list from exterior method
		top5btn.setOnAction((ActionEvent event) -> {marketview.setItems(top5input);});
		Tooltip tooltip1 = new Tooltip("Selects the currently fasting climbing 5 stocks.");
		Tooltip.install(top5btn, tooltip1);
		
		Button bot10btn = new Button();
		bot10btn.setMnemonicParsing(true);
		bot10btn.setText("Bottom _10 Falling");
			//replace 'stockinput' with the bot 10 stocks list from exterior method
		bot10btn.setOnAction((ActionEvent event) -> {marketview.setItems(bot10input);});
		Tooltip tooltip2 = new Tooltip("Selects the currently fastest falling 10 stocks.");
		Tooltip.install(bot10btn, tooltip2);
		
		Button yourbtn = new Button();
		yourbtn.setMnemonicParsing(true);
		yourbtn.setText("Your _Stocks");
			//replace 'stockinput' with your stocks list from exterior method
		yourbtn.setOnAction((ActionEvent event) -> {marketview.setItems(yourinput);});
		Tooltip tooltip3 = new Tooltip("Selects the list of stocks that you own.");
		Tooltip.install(yourbtn, tooltip3);
		
		Button buybtn = new Button();
		buybtn.setMnemonicParsing(true);
		buybtn.setText("_Buy");
			//need to change this action to actually buying stocks
		buybtn.setOnAction((ActionEvent event) -> {
			String temp = marketview.getSelectionModel().getSelectedItem();
			yourinput.add(temp);
			String[] tokens = temp.split("\\s\\s\\s\\s\t");
			double tempBalanceVar = Double.parseDouble(balance.getText());
			tempBalanceVar -= (Double.parseDouble(tokens[2]));

			balance.setText("" + format.format(tempBalanceVar));
		}

		);
		Tooltip tooltip4 = new Tooltip("Click to purchase selected stock.");
		Tooltip.install(buybtn, tooltip4);

		Button daybtn = new Button();
		daybtn.setMnemonicParsing(true);
		daybtn.setText("Next Day");
		daybtn.setOnAction((ActionEvent) -> {
			//anything the next day button wants to do goes here.
			String temp = dayCounter.getText();
			String[] tokens = temp.split(":\\s");

			int day = Integer.parseInt(tokens[1]);
			stockinput.clear();
			top5input.clear();
			bot10input.clear();
			market.createStock();
			market.updateMarket();
			Iterator updateStocks = market.getStocks();
			ArrayList<Stock> newStockListing = new ArrayList<>();
			ArrayList<Stock> growthSort = new ArrayList<>();
			marketchartdata.getData().add(new XYChart.Data<Number, Number>(day, (double) market.getTotalValue().get(market.getTotalValue().size()-1)));

			stockinput.add("Name    \tGrowth\tNow    \tHigh    \tLow");
			while (updateStocks.hasNext()) {
				newStockListing.add((Stock) updateStocks.next());
				growthSort.add((Stock) updateStocks.next());
			}
			day++;
			dayCounter.setText("Day: " + day);
			//need to find a way to sort the stocks by growth so that they can be displayed in the top5 and bottom 10
			Collections.sort(growthSort, new Comparator<Stock>() {
				@Override
				public int compare(Stock o1, Stock o2) {
					return 0;
				}
			});

			for(int i=0; i<newStockListing.size(); i++){ stockinput.add(newStockListing.get(i).forList()); }
			marketview.setItems(stockinput);
			for(int i=0; i<5; i++){top5input.add(OODFinal.getTop5(i));}
			for(int i=0; i<10; i++){bot10input.add(OODFinal.getbot10(i));}

		});
		Tooltip tooltip5 = new Tooltip("Click to iterate to next day.");
		Tooltip.install(daybtn, tooltip5);

		//Adding to scene.(child, column start, row start, column width, row length)
		root2.getChildren().add(marketbtn);
		root2.getChildren().add(top5btn);
		root2.getChildren().add(bot10btn);
		root2.getChildren().add(yourbtn);
		root3.getChildren().add(balancelbl);
		root3.getChildren().add(balance);
		root3.getChildren().add(holdinglbl);
		root3.getChildren().add(holdings);
		root3.getChildren().add(buybtn);
		root.add(root2, 0, 0, 5, 1);
		root.add(root3, 0, 4, 4, 1);
		root.add(marketview, 0, 1, 4, 3);
		root.add(marketchart, 4, 1, 2, 3);
		root.add(daybtn, 6,4, 1,1);
		root.add(dayCounter, 6,1,1,1);
		
		
		//setTitle sets a title for the main window.
		stage.setTitle("Stateboro Stock Exchange");

		//This adds the scene to the stage.
		stage.setScene(scene);
		stage.sizeToScene();

		//This is what puts the window on the screen.
		stage.show();
	}
}
