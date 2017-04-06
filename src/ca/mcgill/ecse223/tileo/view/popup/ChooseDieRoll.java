package ca.mcgill.ecse223.tileo.view.popup;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ChooseDieRoll extends JDialog {

    /**
     * Creates new form TurnsUnactive
     */
    public ChooseDieRoll(JFrame window) {
    	parentWindow = window;
    }
    
    public int ask() {
    	jPanel1 = new javax.swing.JPanel();
        But3 = new javax.swing.JButton();
        But2 = new javax.swing.JButton();
        But6 = new javax.swing.JButton();
        But5 = new javax.swing.JButton();
        But1 = new javax.swing.JButton();
        But4 = new javax.swing.JButton();

        setPreferredSize(new Dimension(470, 320)); 
        setSize(new Dimension(470, 320)); 
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(null);
        jPanel1.setLayout(null);
        
        Dimension screenSize = parentWindow.getSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
        
        setBackground(new java.awt.Color(0, 0, 0));        

        But3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But3ActionPerformed(evt);
            }
        });
        jPanel1.add(But3);
        But3.setBounds(320, 0, 150, 150);

        But2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But2ActionPerformed(evt);
            }
        });
        jPanel1.add(But2);
        But2.setBounds(162, 0, 150, 150);

        But6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But6ActionPerformed(evt);
            }
        });
        jPanel1.add(But6);
        But6.setBounds(320, 160, 150, 150);

        But5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But5ActionPerformed(evt);
            }
        });
        jPanel1.add(But5);
        But5.setBounds(160, 160, 150, 150);

        But1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But1ActionPerformed(evt);
            }
        });
        jPanel1.add(But1);
        But1.setBounds(0, 0, 150, 150);

        But4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                But4ActionPerformed(evt);
            }
        });
        jPanel1.add(But4);
        But4.setBounds(0, 160, 150, 150);
        
        try {
			But6.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/6.png"))));
			But6.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/6_h.png"))));
			But5.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/5.png"))));
			But5.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/5_h.png"))));
			But4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/4.png"))));
			But4.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/4_h.png"))));
			But3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/3.png"))));
			But3.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/3_h.png"))));
			But2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/2.png"))));
			But2.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/2_h.png"))));
			But1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/1.png"))));
			But1.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/popup/dice/1_h.png"))));
		} catch (IOException e) {
			
		}
        
        But1.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But1.setContentAreaFilled(false);
        But2.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But2.setContentAreaFilled(false);
        But3.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But3.setContentAreaFilled(false);
        But4.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But4.setContentAreaFilled(false);
        But5.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But5.setContentAreaFilled(false);
        But6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        But6.setContentAreaFilled(false);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 470, 320);
        
        Point newLocation = new Point(middle.x - (getWidth() / 2), 
                middle.y - (getHeight() / 2));
        setLocation(newLocation);

        setModal(true);
        
        setLocationRelativeTo(parentWindow);
        setResizable(false);
        //setUndecorated(true);
        
        setVisible(true);
        pack();
        
        return answer;
    }

    private void But3ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 3;
        dispose();
    }

    private void But4ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 4;
        dispose();
    }

    private void But5ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 5;
        dispose();
    }

    private void But6ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 6;
        dispose();
    }

    private void But1ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 1;
        dispose();
    }

    private void But2ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 2;
        dispose();
    }
    
    private JFrame parentWindow;
    private int answer = 0;

    private javax.swing.JButton But1;
    private javax.swing.JButton But2;
    private javax.swing.JButton But3;
    private javax.swing.JButton But4;
    private javax.swing.JButton But5;
    private javax.swing.JButton But6;
    private javax.swing.JPanel jPanel1;
}

