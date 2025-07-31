package gui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class SettingsPanel {
    private final JPanel parentPanel;
    private final JCheckBox[] boxes = new JCheckBox[5];
    private final Boolean[] settings = {false, false, false, false, true};
    private final String[] labels = {"Open", "High", "Low", "Close", "Adj. Close"};
    private final GuiBuilder builder = new GuiBuilder();

    public SettingsPanel(JPanel panel) {
    	//Passing mainPanel of GuiInterface as the parentPanel
        this.parentPanel = panel;
    }

    public void init(int x, int y) {
    	
    	// Create buttons of the corresponding setting values:
    	// OPEN, HIGH, LOW, CLOSE etc.    	
        for (int i = 0; i < labels.length; i++) {       	
            JCheckBox box = new JCheckBox(labels[i]);
            box.setBounds(x, y + i * 30, 100, 20);
            box.setSelected(settings[i]);
            box.setBackground(Color.WHITE);
            box.setFont(new Font("Open Sans", Font.PLAIN, 12));
            box.setIcon(builder.loadIcon(GuiConstants.ICON_TOGGLE_OFF, 32,32));
			box.setSelectedIcon(builder.loadIcon(GuiConstants.ICON_TOGGLE_ON, 32,32));
            box.setFocusable(false);
            box.setToolTipText("spreadsheet settings");
            boxes[i]=box;
            parentPanel.add(boxes[i]);
            parentPanel.repaint();
        }
    }
    
    public void remove() {  
    	
    	for (int i = 0; i < labels.length; i++) {
             parentPanel.remove(boxes[i]);
         }      	
    }

    public Boolean[] getSettings() {
    	//return the settings, when creating the spreadsheet
        for (int i = 0; i < boxes.length; i++) {
            settings[i] = boxes[i].isSelected();
        }
        return settings;
    }

}
