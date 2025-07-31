package gui;



import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class FXFileChooserHelper {
	
	/* 
	 * the class FXFileChooserHelper creates a FileChooser with the framework JavaFX
	 * the method openFileChoser open the FileChooser and passes a callback method as parameter to switch from FX-Thread back to Swing-Thread,
	 * when the file is selected:
	 * 
	 * 		Platform.runLater(() 
	 * 		...
	 * 		SwingUtilities.invokeLater(() -> callback.onFileSelected(file));
	 */
	
	
	//callback method
    public interface FileCallback {
        void onFileSelected(File file);
    }

    
    public static void openFileChooser(String suggestedFileName, FileCallback callback) {
        
        new JFXPanel();

        Platform.runLater(() -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save as");
            fileChooser.setInitialFileName(suggestedFileName);
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel workbook (*.xlsx)", "*.xlsx")
            );

            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {

                SwingUtilities.invokeLater(() -> callback.onFileSelected(file));
            }
        });
    }
}
