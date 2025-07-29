
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Parser {
	/*
	 * 
	 * 
	 * The Class Parser provides extracting data making a query into Yahoo Finance database.
	 * The extraction of Json data is done with Gson framework.  
	 * Data are stored as a set of arrays of values (dates, quotes etc.) and meta-datas (name, currency, etc.)
	 * -> see the class Data
	 * 
	 */
	private String urlStr;
	
	@SuppressWarnings("deprecation")
	public Data getData(String symbol, String range, String interval) {
		
		// the intervals permitted are "1y","2y","5y","10y","ytd","max", however the user can pass the interval "1mo*"
		// "1mo*" will set all the last days of each month.
		// to get all the last days it will be needed 1d as interval, then the "last day check" will occur later in the class
                            
		if ("1mo*".equals(interval)) {
			urlStr = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol + "?range=" + range + "&interval=1d";
		} else {
			urlStr = "https://query1.finance.yahoo.com/v8/finance/chart/" + symbol + "?range=" + range + "&interval=" + interval;
		}
		
		try {
			URL url = new URL(urlStr);
			
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//acting as a real user
			connection.setRequestProperty("User-Agent", 
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)");
			
			// the Json message is read with the Buffered reader and stored as a String
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder json = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
			
			//Extracting all necessary data from the Json "tree" using Gson library. 
			//A Json will look like this:
			/*
			 {
  				"chart": {
    				"result": [
      					{
        				"meta": {
          				"currency": "USD",
          				"symbol": "AAPL",
          				"fullExchangeName": "NasdaqGS",
          				...
          				 "indicators": {
          				"quote": [
            					{
              					"high": [215.240005493164],
              					"low": [213.399993896484],
              					"open": [214.699996948242],
              					"volume": [40219700],
              					"close": [213.880004882813]
            					}
          					],
          				"adjclose": [
            			{
              					"adjclose": [213.880004882813]
						...
			 */
			
			Gson gson = new Gson();
			JsonObject root = gson.fromJson(json.toString(), JsonObject.class);
			JsonObject chart = root.getAsJsonObject("chart");
			JsonObject result = chart.getAsJsonArray("result").get(0).getAsJsonObject();
			
			JsonObject meta = result.getAsJsonObject("meta");
			
			String currency = meta.get("currency").getAsString();
			String name = meta.get("shortName").getAsString();
			String exchangeName = meta.get("fullExchangeName").getAsString(); 
			String instrumentType = meta.get("instrumentType").getAsString();
			Double marketPrice = meta.get("regularMarketPrice").getAsDouble();

			JsonArray timestamp = result.getAsJsonArray("timestamp");
			JsonObject indicators = result.getAsJsonObject("indicators");
			JsonArray JsonadjClose = indicators.getAsJsonArray("adjclose")
                    .get(0).getAsJsonObject()
                    .getAsJsonArray("adjclose");
			JsonArray JsonOpen = indicators.getAsJsonArray("quote").get(0).getAsJsonObject().getAsJsonArray("open");
			JsonArray JsonHigh = indicators.getAsJsonArray("quote").get(0).getAsJsonObject().getAsJsonArray("high");
			JsonArray JsonLow = indicators.getAsJsonArray("quote").get(0).getAsJsonObject().getAsJsonArray("low");
			JsonArray JsonClose = indicators.getAsJsonArray("quote").get(0).getAsJsonObject().getAsJsonArray("close");
	        
			//All necessary Arrays to store the data
	        ArrayList<Date> dates = new ArrayList<>();	       
	        ArrayList<Double> open = new ArrayList<>();
	    	ArrayList<Double> high = new ArrayList<>();
	    	ArrayList<Double> low = new ArrayList<>(); 
	    	ArrayList<Double> close = new ArrayList<>();
	    	ArrayList<Double> adjClose = new ArrayList<>();
	    	
	        ArrayList<Date> dateEnd = new ArrayList<>();
	        ArrayList<Double> openEnd = new ArrayList<>();
	        ArrayList<Double> highEnd = new ArrayList<>();
	        ArrayList<Double> lowEnd = new ArrayList<>();
	        ArrayList<Double> closeEnd = new ArrayList<>();
	        ArrayList<Double> adjCloseEnd = new ArrayList<>();
	        
	        for (int i = 0; i<timestamp.size(); i++) {	   
	        	long ts = timestamp.get(i).getAsLong();   //converting the dates in ms into type "Date"
	        	Date date = new Date(ts * 1000L);
	        		        		        	
	        	if (JsonadjClose.size() > i && !JsonadjClose.get(i).isJsonNull()
	        			 && JsonOpen.size() > i && !JsonOpen.get(i).isJsonNull()
	        			 && JsonHigh.size() > i && !JsonHigh.get(i).isJsonNull()
	        			 && JsonLow.size() > i && !JsonLow.get(i).isJsonNull()
	        			 && JsonClose.size() > i && !JsonClose.get(i).isJsonNull()) {
	        	   
	        	    dates.add(date);
	        	    open.add(JsonOpen.get(i).getAsDouble());
	        	    high.add(JsonHigh.get(i).getAsDouble());
	        	    low.add(JsonLow.get(i).getAsDouble());
	        	    close.add(JsonClose.get(i).getAsDouble());
	        	    adjClose.add(JsonadjClose.get(i).getAsDouble());
	        	}
	        	// Last-Day-of-Month Check if the selected interval is "1mo*"
	        	// datesEnd and closeEnd collects Dates and Values pairs referred to each last day of months
	        	if ("1mo*".equals(interval)) {
	        		if (dates.size() > 1) {
	        			Calendar cal = Calendar.getInstance();
	        			cal.setTime(dates.get(dates.size() - 2));
	        			int prevMonth = cal.get(Calendar.MONTH);

	        			cal.setTime(dates.get(dates.size() - 1));
	        			int currMonth = cal.get(Calendar.MONTH);
		                if ((prevMonth < currMonth) || (prevMonth == 11 && currMonth == 0)) {
		                    dateEnd.add(dates.get(dates.size() - 2));
		                    openEnd.add(open.get(open.size() - 2));
		                    highEnd.add(high.get(high.size() - 2));
		                    lowEnd.add(low.get(low.size() - 2));
		                    closeEnd.add(close.get(close.size() - 2));
		                    adjCloseEnd.add(adjClose.get(adjClose.size() - 2));
		                }
		            }
	        	}
	        	
	        }
	    //returning the special set of data, if the selected interval was 1mo*   
	    if ("1mo*".equals(interval)) {   
	    	return new Data(dateEnd, openEnd, highEnd, lowEnd, closeEnd, adjCloseEnd, name, currency, exchangeName, instrumentType, marketPrice);
	    }
	    //data with the other selected intervals and ranges
	    return new Data(dates, open, high, low, close, adjClose, name, currency, exchangeName,  instrumentType, marketPrice);
	        
		} catch (IOException e) {
			
			System.err.println("Error loading datas: " + e.getMessage());
			return null;
			
		} catch (java.lang.UnsupportedOperationException e) {		
			
			System.err.println("a Json data is null: " + e.getMessage());
			return null;	
			
		}
		
		
		
	}
}
