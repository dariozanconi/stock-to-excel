package gui;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {
	
	private static final long serialVersionUID = 2837765466724476131L;

		public SplashScreen() {
   	        
			JLabel splashLabel = new JLabel(new ImageIcon(getClass().getResource("/splash.jpg")));
			getContentPane().add(splashLabel, BorderLayout.CENTER);
      
			pack();
			setLocationRelativeTo(null);        	 
			setVisible(true);

			try {
				Thread.sleep(2700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
      
			setVisible(false);
			dispose();
    }
}
