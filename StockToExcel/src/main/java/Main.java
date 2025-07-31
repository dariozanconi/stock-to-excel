
import gui.GuiInterface;

public class Main {
	
	/*
	 * STOCK TO EXCEL
	 * Stock viewer / saving stock values into excel spreadsheet
	 * 
	 * This software provides searching for products (equity, ETF, index etc.) of the stock market
	 * The user insert the ticker symbol, interval and range, and will get a representation of the product (name, details and a chart)
	 * The user can save the price values at the selected interval and range into an excel spreadsheet (.xslx). It is 
	 * given also the possibility to select which values add (open, high, low, close, adj. close)
	 * 
	 * 
	 * 
	 */

	public static void main(String[] args) {
		
		new GuiInterface();

	}

}
