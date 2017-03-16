package ca.mcgill.ecse223.tileo.view.popup;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
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

        Button9.setText("9");
        Button9.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        Button9.setContentAreaFilled(false);
        Button9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button9ActionPerformed(evt);
            }
        });
        Container.add(Button9);
        Button9.setBounds(210, 340, 80, 35);

        Button3.setText("3");
        Button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button3ActionPerformed(evt);
            }
        });
        Container.add(Button3);
        Button3.setBounds(210, 160, 80, 35);

        Button4.setText("4");
        Button4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button4ActionPerformed(evt);
            }
        });
        Container.add(Button4);
        Button4.setBounds(10, 250, 80, 35);

        Button7.setText("7");
        Button7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button7ActionPerformed(evt);
            }
        });
        Container.add(Button7);
        Button7.setBounds(10, 340, 80, 35);

        Button8.setText("8");
        Button8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button8ActionPerformed(evt);
            }
        });
        Container.add(Button8);
        Button8.setBounds(110, 340, 80, 35);

        Button5.setText("5");
        Button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button5ActionPerformed(evt);
            }
        });
        Container.add(Button5);
        Button5.setBounds(110, 250, 80, 35);

        Button6.setText("6");
        Button6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button6ActionPerformed(evt);
            }
        });
        Container.add(Button6);
        Button6.setBounds(210, 250, 80, 35);

        Button1.setText("1");
        Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button1ActionPerformed(evt);
            }
        });
        Container.add(Button1);
        Button1.setBounds(10, 160, 80, 35);

        Button2.setText("2");
        Button2.setActionCommand("2");
        Button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button2ActionPerformed(evt);
            }
        });
        Container.add(Button2);
        Button2.setBounds(110, 160, 80, 35);

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

