package ca.mcgill.ecse223.tileo.view.popup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ActionCardPopUp extends JFrame {
	public ActionCardPopUp(JFrame window, String message, Image icon) {
		Picture = new javax.swing.JLabel();
        Button = new javax.swing.JLabel();
        ActionTilePicture = new javax.swing.JLabel();

        setPreferredSize(new Dimension(300, 400)); 
        setSize(new Dimension(300, 400)); 
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        Dimension screenSize = window.getSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);

        ImageIcon imgIcon = new ImageIcon(icon);
        Picture.setIcon(imgIcon);
        getContentPane().add(Picture);
        Point newLocation = new Point(middle.x - (getWidth() / 2), 
                middle.y - (getHeight() / 2));
        setLocation(newLocation);

        Picture.setBounds(getWidth()/2 - imgIcon.getIconWidth()/2, getHeight()/2 - 50, imgIcon.getIconWidth(), imgIcon.getIconHeight());

        getContentPane().add(Button);
        Button.setFont(new java.awt.Font("Lucida Grande", 3, 20));
        Button.setForeground(new java.awt.Color(223,225,0));
        Button.setBounds(getWidth()/2 - 100, getHeight() - 150, 200, 60);
        Button.setText("<html><div style=\"text-align:center; \">"+message+"</div></html>");

        String p = getClass().getResource("/icons/popup/actionTile.png").toString();
        ActionTilePicture.setText("<html><img src=\""+p+"\"></html>");
        getContentPane().add(ActionTilePicture);
        ActionTilePicture.setBounds(0, 0, 300, 400);
        
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(window);

        setVisible(true);
        pack();
        
        Timer timer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
	}
	
	private javax.swing.JLabel ActionTilePicture;
    private javax.swing.JLabel Button;
    private javax.swing.JLabel Picture;
    private ImageIcon img;

}
