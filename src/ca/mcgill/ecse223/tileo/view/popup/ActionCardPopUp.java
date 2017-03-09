package ca.mcgill.ecse223.tileo.view.popup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ActionCardPopUp extends JFrame {
	private class ImagePanel extends JComponent {
	    private Image image;
	    public ImagePanel(Image image) {
	        this.image = image;
	    }
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, this);
	    }
	}
	
	public ActionCardPopUp(JFrame window, String message) {
		setPreferredSize(new Dimension(300, 400)); 
		setSize(new Dimension(300, 400)); 
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setBackground(new java.awt.Color(255,255,255));
		//setLayout(new FlowLayout());
		
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("/icons/popup/actionTile.png"));
			getContentPane().add(new ImagePanel(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setLayout(new FlowLayout());
		JLabel l1=new JLabel("Here will be action\ncard directives.");
		add(l1);
		
		
		/*setResizable(false);
		setAlwaysOnTop(true);
		setUndecorated(true);
		setLocationRelativeTo(window);
		
		Dimension screenSize = window.getSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (getWidth() / 2), 
		                              middle.y - (getHeight() / 2));
		setLocation(newLocation);
		 */
		
		setVisible(true);
		toFront();
		pack();
		
		revalidate();
		repaint();
	}
}
