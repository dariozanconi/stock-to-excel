package gui;


import javax.swing.*;

import core.Data;
import core.ExcelCreator;
import core.Parser;

	/* 
	 *  
	 */

public class GuiActions {
			
    private final GuiInterface gui;
    private String range = "1y";
    private String interval = "1d";
    private Data data;

    public GuiActions(GuiInterface gui) {
        this.gui = gui;
    }

    public void onSearch(String symbol) {
    	    	
        Parser parser = new Parser();
        data = parser.getData(symbol, range, interval);
        if (data == null) {
            gui.showError("Enter a valid ticker symbol");
        } else {
            gui.showData(data);
        }
    }

    public void onRangeSelected(JComboBox<String> box) {
        range = (String) box.getSelectedItem();
    }

    public void onIntervalSelected(JComboBox<String> box) {
        interval = (String) box.getSelectedItem();
    }

    public void onCreateExcel( Boolean[] settings) {
        FXFileChooserHelper.openFileChooser(data.getName() + "_" + range + ".xlsx", file -> {
            ExcelCreator creator = new ExcelCreator(data, settings);
            try (var out = new java.io.FileOutputStream(file)) {
                creator.createWorkbook().write(out);
                gui.showSaved();
            } catch (Exception e) {
                gui.showError("File being used");
            }
        });
    }


}
