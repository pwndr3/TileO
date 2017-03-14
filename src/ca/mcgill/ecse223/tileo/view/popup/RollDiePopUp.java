package ca.mcgill.ecse223.tileo.view.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

public class RollDiePopUp extends JFrame {
	public RollDiePopUp (JFrame window, String resPath) {
		Picture = new javax.swing.JLabel();

        setPreferredSize(new Dimension(112, 112)); 
        setSize(new Dimension(112, 112)); 
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        Dimension screenSize = window.getSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);

        getContentPane().add(Picture);
        Point newLocation = new Point(middle.x - (getWidth() / 2), 
                middle.y - (getHeight() / 2));
        setLocation(newLocation);

        Picture.setText("<html><img src=\""+resPath+"\"></html>");
        Picture.setBounds(0, 0, 112, 112);
        
        setCursor(null);
        setFocusable(false);
        
        setResizable(false);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setLocationRelativeTo(window);

        setVisible(true);
        pack();
        
        Timer timer = new Timer(4200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
	}
	
	private javax.swing.JLabel ActionTilePicture;
    private javax.swing.JLabel Picture;

}
