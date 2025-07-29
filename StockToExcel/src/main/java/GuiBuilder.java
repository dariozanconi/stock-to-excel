import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.*;
import java.awt.event.ActionListener;

public class GuiBuilder {
	
	/*
	 * Class to build different GUI Components with the same features
	 */
	
    public JLabel createLabel(String text, int x, int y, int w, int h, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, w, h);
        return label;
    }

    public JButton createButton(String text, int x, int y, int w, int h, ActionListener al) {
        JButton button = new JButton(text);
        button.setBounds(x, y, w, h);
        button.setFocusable(false);
        button.setBorder(null);
		button.setBackground(Color.WHITE);
		button.setContentAreaFilled(true);
        button.addActionListener(al);
        return button;
    }

    public JButton createIconButton(String iconPath, int x, int y, int w, int h, ActionListener al) {
        JButton button = new JButton();
        button.setBounds(x, y, w, h);
        button.setIcon(loadIcon(iconPath, w-20, h-20));
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.addActionListener(al);
        return button;
    }

    public JTextField createTextField(int x, int y, int w, int h, ActionListener al) {
        JTextField field = new JTextField();
        field.setFont(new Font("Open Sans", Font.PLAIN, 15));
        field.setBounds(x, y, w, h);
        field.setBorder(new RoundedBorder(15, false));
        if (al != null) field.addActionListener(al);
        return field;
    }

    public JComboBox<String> createComboBox(String[] items, int x, int y, int w, int h, ActionListener al) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(GuiConstants.FONT_DEFAULT);
        combo.setBounds(x, y, w, h);
        combo.setBorder(new RoundedBorder(10, false));
        combo.setFocusable(false);
        combo.setBackground(Color.WHITE);
        combo.setUI(new BasicComboBoxUI() {

	         @Override
	         protected JButton createArrowButton() {
	        	JButton button = new JButton(new ImageIcon(new ImageIcon
	            		(getClass().getResource("/arrow.jpg")).getImage().
	            		getScaledInstance(13, 13, Image.SCALE_SMOOTH)));
	            button.setBackground(Color.WHITE);
	            button.setBorder(null);
	            button.setIcon(new ImageIcon(new ImageIcon
	            		(getClass().getResource("/arrow.jpg")).getImage().
	            		getScaledInstance(13, 13, Image.SCALE_SMOOTH)));	            
	            button.setVisible(true);
	            return button;
	         }
	         @Override
	         public void installUI(JComponent c) {
	             super.installUI(c);
	             comboBox.setLayout(null); 
	             arrowButton.setBounds(comboBox.getWidth() - 22, 5, 18, comboBox.getHeight()-10);
	             comboBox.add(arrowButton);
	         }

	      });
        combo.addActionListener(al);
        return combo;
    }

    public JPanel createPanel(int x, int y, int w, int h) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, w, h);
        panel.setLayout(null);
        panel.setBorder(new RoundedBorder(25, true));
        panel.setBackground(GuiConstants.BACKGROUND_COLOR);
        return panel;
    }
    
    public JPanel createLine(int x, int y, int w) {
    	JPanel linePanel = new JPanel() {
			
			private static final long serialVersionUID = -672374986409444291L;

			@Override	
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(0, 0, w+30, 0);
				repaint();
			}		
		};
		linePanel.setBounds(x, y, w, 2);
		linePanel.setOpaque(false);
		return linePanel;
    }

    public ImageIcon loadIcon(String path, int w, int h) {
        return new ImageIcon(new ImageIcon(getClass().getResource(path))
                .getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
    }

    public void addIconHoverEffect(JButton button, String normal, String hover, String pressed, int size1, int size2, int size3) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setIcon(loadIcon(hover, size2, size2));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setIcon(loadIcon(normal, size1, size1));
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                button.setIcon(loadIcon(pressed, size3, size3));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                button.setIcon(loadIcon(normal, size1, size1));
            }
        });
    }

    
    public static class RoundedBorder implements Border {
        private int radius;
        private boolean fill;

        public RoundedBorder(int radius, boolean fill) {
            this.radius = radius;
            this.fill = fill;
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(1, 7, 1, 1); }

        @Override
        public boolean isBorderOpaque() { return true; }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (fill) {
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(x, y, width-1, height-1, radius, radius);
            }
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}

