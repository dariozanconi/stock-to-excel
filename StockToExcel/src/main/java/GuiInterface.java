import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartPanel;

public class GuiInterface extends JFrame{
    /**
	 * 
	 */
	
	private static final long serialVersionUID = 3593571954132289316L;
	private JTextField field;
    private JComboBox<String> rangeBox, intervalBox;
    private JButton searchButton, createButton;
    private JPanel mainPanel;
    private JLayeredPane layeredLinePane;
    private JLabel errorLabel, stockDetail, stockName, background, heading, signature, 
    				ticker, interval, range, info;
    private ChartPanel chartPanel;
    private SettingsPanel settings;


    private final GuiBuilder builder = new GuiBuilder();
    private final GuiActions actions = new GuiActions(this);

    public GuiInterface() {
    	
    	new SplashScreen();
    	
    	SwingUtilities.invokeLater(() -> {
    		initFrame();
    		initComponents();
    		setVisible(true);   		
    	});
        
    }
       
    
    private void initFrame() {
        setTitle("Stock to Excel");
        setSize(700, 550);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(builder.loadIcon(GuiConstants.ICON_APP, 64, 64).getImage());
        getContentPane().setBackground(GuiConstants.BACKGROUND_COLOR);
                 

    }

    @SuppressWarnings("unused")
	private void initComponents() {
    	
        mainPanel = builder.createPanel(30, 55, 620, 430);
        add(mainPanel);                
		
        heading = builder.createLabel("operated with", 450,-20, 600,100, GuiConstants.FONT_HEADING);
        heading.setHorizontalTextPosition(JLabel.LEFT);
		heading.setVerticalTextPosition(JLabel.CENTER);
        heading.setIcon(builder.loadIcon(GuiConstants.ICON_YAHOO, 115, 42));
        
        signature = builder.createLabel("created by Dario Zanconi", 550, 490, 200, 20, GuiConstants.FONT_SIGNATURE);
                
        background = builder.createLabel("", 210, 130, 200,200, GuiConstants.FONT_LABEL);
        background.setIcon(builder.loadIcon(GuiConstants.ICON_BACKGROUND, 200, 200));
        
     //******** INPUT PANEL ************
        ticker = builder.createLabel("  Ticker symbol: ", 24, 30, 210, 50, GuiConstants.FONT_LABEL);
        ticker.setIcon(builder.loadIcon(GuiConstants.ICON_STOCK, 48, 43));
        ticker.setHorizontalTextPosition(JLabel.RIGHT);
		ticker.setVerticalTextPosition(JLabel.CENTER);		
		        
        field = builder.createTextField(195, 42, 120, 30, e -> actions.onSearch(field.getText()));
        searchButton = builder.createButton("🔍", 283, 45, 25, 25, e -> actions.onSearch(field.getText()));
        
        //Combination of field text and search button
        JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(30, 55, 520, 380);
		layeredPane.add(field, Integer.valueOf(1));
		layeredPane.add(searchButton, Integer.valueOf(2)); 
		this.add(layeredPane);
		
		interval = builder.createLabel("Interval", 480, 20, 70, 20, GuiConstants.FONT_DEFAULT);
		range = builder.createLabel("Range", 400, 20, 70, 20, GuiConstants.FONT_DEFAULT);
		info = builder.createLabel(" ⓘ ", 519, 20, 30, 20, GuiConstants.FONT_LABEL);
        rangeBox = builder.createComboBox(new String[]{"6mo", "1y", "2y", "5y", "10y", "ytd", "max"}, 400, 44, 70, 28, e -> actions.onRangeSelected(rangeBox));
        rangeBox.setSelectedIndex(1);
        intervalBox = builder.createComboBox(new String[]{"1d", "5d", "1wk", "1mo", "1mo*"}, 480, 44, 70, 28, e -> actions.onIntervalSelected(intervalBox));
        
        //TOOL TIP TEXT 
        UIManager.put("ToolTip.background", new Color(255,255,198));
		UIManager.put("ToolTip.font", new Font("Open Sans", Font.PLAIN, 12));
		UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		ticker.setToolTipText("search for the ticker to be loaded, ex. AAPL");
		info.setToolTipText("1mo* sets the last day of each month");
		
        add(signature);
        add(heading);
        mainPanel.add(background);
        mainPanel.add(ticker);
        mainPanel.add(interval);
        mainPanel.add(range);
        mainPanel.add(info);
        mainPanel.add(rangeBox);
        mainPanel.add(intervalBox);  
        repaint();
       
    }

    @SuppressWarnings("unused")
	public void showData(Data data) {
    	
        if (stockDetail != null) {         // If data are already shown, remove everything
        	
        	if (errorLabel!=null) {
				mainPanel.remove(errorLabel);
        	}
        	
        	mainPanel.remove(chartPanel);
        	mainPanel.remove(stockDetail);
			mainPanel.remove(stockName);
			settings.remove();
        	repaint();
        	
        } else {                           // First data shown -> remove the background
        	mainPanel.remove(background);       	
        }
        
    //Separator line		
		JPanel linePanel = builder.createLine(30, 157, 620);  			
		layeredLinePane = new JLayeredPane();
		layeredLinePane.setBounds(0, 0, 670, 495);			
		layeredLinePane.add(mainPanel, Integer.valueOf(1));
		layeredLinePane.add(linePanel, Integer.valueOf(2));		
		this.add(layeredLinePane);
		
	//****** OUTPUT PANEL **********
		
		//Setting panel	
		settings = new SettingsPanel(mainPanel);              
		settings.init(495, 210);	
		
		//Chart
        chartPanel = new StockChart(data.getName(), data).getChart();  
        chartPanel.setBounds(2, 186, 430, 237);        	 
		
        //Stock Name & Details label
		stockDetail = builder.createLabel(" " + data.getExchangeName() + " - " +data.getCurrency(), 24, 125, 200,50, GuiConstants.FONT_HEADING);
		stockDetail.setIcon(builder.loadIcon(GuiConstants.ICON_DETAILS, 43,43));
		stockDetail.setHorizontalTextPosition(JLabel.RIGHT);
		stockDetail.setVerticalTextPosition(JLabel.TOP);
		stockDetail.setToolTipText("<html> "
				+ "Name: " +data.getName() + 
				"<br>Exchange name: " + data.getExchangeName() +
				"<br>Currency: " + data.getCurrency()+
				"<br>Instrument Type: " + data.getInstrumentType()+
				"<br>Market Price: " + data.getMarketPrice()+
		   "</html>");
		stockName = builder.createLabel(data.getName(), 71, 135, 400, 50, GuiConstants.FONT_NAME);
		
		//EXCEL Button
		createButton = builder.createIconButton(GuiConstants.ICON_EXCEL, 484, 127, 74, 74,
	            e -> actions.onCreateExcel(data, settings.getSettings()));
        builder.addIconHoverEffect(createButton, GuiConstants.ICON_EXCEL, GuiConstants.ICON_ENTERED, GuiConstants.ICON_EXCEL, 54, 77, 45);
        
        mainPanel.add(stockDetail);
		mainPanel.add(stockName);
        mainPanel.add(createButton);		
		mainPanel.add(chartPanel);  
		repaint();  
		
		
        }
        
    public void showSaved() {
    	//confirmation of successfully saved
    	JLabel saved = builder.createLabel("", 560, 149, 60, 60, GuiConstants.FONT_LABEL);
    	saved.setIcon(builder.loadIcon(GuiConstants.ICON_SAVED, 25, 25));
    	mainPanel.add(saved);
    	repaint();
    }

    public void showError(String message) {
    	
    	//showing two different error messages
    	
        if (errorLabel != null) mainPanel.remove(errorLabel);
        
        //TICKER ERROR
        if (message.equals("Enter a valid ticker symbol")) {
        	errorLabel = builder.createLabel(message, 194, 74, 200, 20, GuiConstants.FONT_ERROR);
        	errorLabel.setForeground(Color.RED);
        	mainPanel.add(errorLabel);
        	repaint();
        }
        //FILE CREATION ERROR
        if (message.equals("File being used")) {
        	errorLabel = builder.createLabel(message, 499, 187, 150,20, GuiConstants.FONT_ERROR);
        	errorLabel.setForeground(Color.RED);
        	mainPanel.add(errorLabel);
        	repaint();
        }
        
    }
    
    
}