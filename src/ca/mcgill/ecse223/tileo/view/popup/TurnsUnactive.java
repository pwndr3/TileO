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

public class TurnsUnactive extends JDialog {

    /**
     * Creates new form TurnsUnactive
     */
    public TurnsUnactive(JFrame window) {
    	parentWindow = window;
    }
    
    public int ask() {
    	Container = new javax.swing.JPanel();
        Button9 = new javax.swing.JButton();
        Button3 = new javax.swing.JButton();
        Button4 = new javax.swing.JButton();
        Button7 = new javax.swing.JButton();
        Button8 = new javax.swing.JButton();
        Button5 = new javax.swing.JButton();
        Button6 = new javax.swing.JButton();
        Button1 = new javax.swing.JButton();
        Button2 = new javax.swing.JButton();
        PictureBackground = new javax.swing.JLabel();

        setPreferredSize(new Dimension(300, 400)); 
        setSize(new Dimension(300, 400)); 
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        
        Container.setLayout(null);
        Dimension screenSize = parentWindow.getSize();
        Point middle = new Point(screenSize.width / 2, screenSize.height / 2);

        try {
			Button9.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/9.png"))));
			Button9.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/9_h.png"))));
			Button8.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/8.png"))));
			Button8.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/8_h.png"))));
			Button7.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/7.png"))));
			Button7.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/7_h.png"))));
			Button6.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/6.png"))));
			Button6.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/6_h.png"))));
			Button5.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/5.png"))));
			Button5.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/5_h.png"))));
			Button4.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/4.png"))));
			Button4.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/4_h.png"))));
			Button3.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/3.png"))));
			Button3.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/3_h.png"))));
			Button2.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/2.png"))));
			Button2.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/2_h.png"))));
			Button1.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/1.png"))));
			Button1.setRolloverIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/buttons/1_h.png"))));
		} catch (IOException e) {
			
		}
        Button9.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button9.setContentAreaFilled(false);
        Button8.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button8.setContentAreaFilled(false);
        Button7.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button7.setContentAreaFilled(false);
        Button6.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button6.setContentAreaFilled(false);
        Button5.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button5.setContentAreaFilled(false);
        Button4.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button4.setContentAreaFilled(false);
        Button3.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button3.setContentAreaFilled(false);
        Button2.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button2.setContentAreaFilled(false);
        Button1.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button1.setContentAreaFilled(false);
        
        Button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button9ActionPerformed(evt);
            }
        });
        Container.add(Button9);
        Button9.setBounds(220, 320, 50, 50);

        Button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button3ActionPerformed(evt);
            }
        });
        Container.add(Button3);
        Button3.setBounds(220, 140, 50, 50);

        Button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button4ActionPerformed(evt);
            }
        });
        Container.add(Button4);
        Button4.setBounds(30, 230, 50, 50);

        Button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button7ActionPerformed(evt);
            }
        });
        Container.add(Button7);
        Button7.setBounds(30, 320, 50, 50);

        Button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button8ActionPerformed(evt);
            }
        });
        Container.add(Button8);
        Button8.setBounds(125, 320, 50, 50);

        Button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button5ActionPerformed(evt);
            }
        });
        Container.add(Button5);
        Button5.setBounds(125, 230, 50, 50);

        Button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button6ActionPerformed(evt);
            }
        });
        Container.add(Button6);
        Button6.setBounds(220, 230, 50, 50);

        Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button1ActionPerformed(evt);
            }
        });
        Container.add(Button1);
        Button1.setBounds(30, 140, 50, 50);
        
        Button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button2ActionPerformed(evt);
            }
        });
        Container.add(Button2);
        Button2.setBounds(125, 140, 50, 50);

        String p = getClass().getResource("/icons/popup/inactivityPeriod.png").toString();
        PictureBackground.setText("<html><img src=\""+p+"\"></html>");
        Container.add(PictureBackground);
        PictureBackground.setBounds(0, 0, 300, 400);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        Point newLocation = new Point(middle.x - (getWidth() / 2), 
                middle.y - (getHeight() / 2));
        setLocation(newLocation);

        setModal(true);
        
        setLocationRelativeTo(parentWindow);
        setResizable(false);
        setUndecorated(true);
        
        setVisible(true);
        pack();
        
        return answer;
    }

    private void Button9ActionPerformed(java.awt.event.ActionEvent evt) {
        answer = 9;
        dispose();
    }

    private void Button3ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 3;
        dispose();
    }

    private void Button4ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 4;
        dispose();
    }

    private void Button7ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 7;
        dispose();
    }

    private void Button8ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 8;
        dispose();
    }

    private void Button5ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 5;
        dispose();
    }

    private void Button6ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 6;
        dispose();
    }

    private void Button1ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 1;
        dispose();
    }

    private void Button2ActionPerformed(java.awt.event.ActionEvent evt) {
    	answer = 2;
        dispose();
    }
    
    private JFrame parentWindow;
    private int answer = 0;

    private javax.swing.JButton Button1;
    private javax.swing.JButton Button2;
    private javax.swing.JButton Button3;
    private javax.swing.JButton Button4;
    private javax.swing.JButton Button5;
    private javax.swing.JButton Button6;
    private javax.swing.JButton Button7;
    private javax.swing.JButton Button8;
    private javax.swing.JButton Button9;
    private javax.swing.JPanel Container;
    private javax.swing.JLabel PictureBackground;
    // End of variables declaration//GEN-END:variables
}

