package ca.mcgill.ecse223.tileo.view;

import java.awt.*;

import javax.swing.UIManager;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

public class TileOPlayUI extends javax.swing.JFrame {
	
	public TileOPlayUI() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			//Too bad
		}
		
		//game = aGame;
		
		initComponents();
	}

	private int currentPlayer = 1;
	private Game game;
	
    private void initComponents() {

        playerTurnLabel = new javax.swing.JLabel();
        playerColor = new javax.swing.JLabel();
        pickCardButton = new javax.swing.JButton();
        rollDieButton = new javax.swing.JButton();
        addConnectionButton = new javax.swing.JButton();
        removeConnectionButton = new javax.swing.JButton();
        teleportButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        playerTurnLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        playerTurnLabel.setText("Player Turn:");

        playerColor.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        playerColor.setForeground(new java.awt.Color(0, 204, 0));
        playerColor.setText("GREEN");

        pickCardButton.setBackground(new java.awt.Color(51, 102, 255));
        pickCardButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        pickCardButton.setForeground(new java.awt.Color(255, 255, 255));
        pickCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cards.png"))); // NOI18N
        pickCardButton.setText("Pick a Card");
        pickCardButton.setToolTipText("");
        pickCardButton.setMaximumSize(new java.awt.Dimension(100, 100));
        pickCardButton.setMinimumSize(new java.awt.Dimension(100, 100));
        pickCardButton.setPreferredSize(new java.awt.Dimension(50, 50));
        pickCardButton.setSize(new java.awt.Dimension(50, 25));
        pickCardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pickCardButtonActionPerformed(evt);
            }
        });

        rollDieButton.setBackground(new java.awt.Color(51, 102, 255));
        rollDieButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        rollDieButton.setForeground(new java.awt.Color(255, 255, 255));
        rollDieButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/dice.png"))); // NOI18N
        rollDieButton.setText("Roll Die");
        rollDieButton.setToolTipText("");
        rollDieButton.setMaximumSize(new java.awt.Dimension(100, 100));
        rollDieButton.setMinimumSize(new java.awt.Dimension(100, 100));
        rollDieButton.setPreferredSize(new java.awt.Dimension(50, 50));
        rollDieButton.setSize(new java.awt.Dimension(50, 25));
        rollDieButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollDieButtonActionPerformed(evt);
            }
        });

        addConnectionButton.setBackground(new java.awt.Color(51, 102, 255));
        addConnectionButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        addConnectionButton.setForeground(new java.awt.Color(255, 255, 255));
        addConnectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/plus.png"))); // NOI18N
        addConnectionButton.setText("   Add  Connection  ");
        addConnectionButton.setToolTipText("");
        addConnectionButton.setMaximumSize(new java.awt.Dimension(100, 100));
        addConnectionButton.setMinimumSize(new java.awt.Dimension(100, 100));
        addConnectionButton.setPreferredSize(new java.awt.Dimension(50, 50));
        addConnectionButton.setSize(new java.awt.Dimension(50, 25));
        addConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addConnectionButtonActionPerformed(evt);
            }
        });

        removeConnectionButton.setBackground(new java.awt.Color(51, 102, 255));
        removeConnectionButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        removeConnectionButton.setForeground(new java.awt.Color(255, 255, 255));
        removeConnectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minus.png"))); // NOI18N
        removeConnectionButton.setText("Remove Connection");
        removeConnectionButton.setToolTipText("");
        removeConnectionButton.setMaximumSize(new java.awt.Dimension(100, 100));
        removeConnectionButton.setMinimumSize(new java.awt.Dimension(100, 100));
        removeConnectionButton.setPreferredSize(new java.awt.Dimension(50, 50));
        removeConnectionButton.setSize(new java.awt.Dimension(50, 25));
        removeConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeConnectionButtonActionPerformed(evt);
            }
        });

        teleportButton.setBackground(new java.awt.Color(51, 102, 255));
        teleportButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        teleportButton.setForeground(new java.awt.Color(255, 255, 255));
        teleportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/teleport.png"))); // NOI18N
        teleportButton.setText("     Teleport        ");
        teleportButton.setToolTipText("");
        teleportButton.setMaximumSize(new java.awt.Dimension(100, 100));
        teleportButton.setMinimumSize(new java.awt.Dimension(100, 100));
        teleportButton.setPreferredSize(new java.awt.Dimension(50, 50));
        teleportButton.setSize(new java.awt.Dimension(50, 25));
        teleportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleportButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Malayalam MN", 1, 20)); // NOI18N
        jLabel1.setText("Connection Pieces Left");

        jProgressBar1.setBackground(new java.awt.Color(255, 204, 0));
        jProgressBar1.setFont(new java.awt.Font("Malayalam MN", 1, 24)); // NOI18N
        jProgressBar1.setMaximum(32);
        jProgressBar1.setToolTipText("19");
        jProgressBar1.setValue(19);
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jProgressBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jProgressBar1.setString(jProgressBar1.getToolTipText());
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1138, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 692, Short.MAX_VALUE)
        );

        saveButton.setBackground(new java.awt.Color(255, 204, 0));
        saveButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        saveButton.setText("Save");
        saveButton.setToolTipText("");
        saveButton.setMaximumSize(new java.awt.Dimension(100, 100));
        saveButton.setMinimumSize(new java.awt.Dimension(100, 100));
        saveButton.setPreferredSize(new java.awt.Dimension(50, 50));
        saveButton.setSize(new java.awt.Dimension(50, 25));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setBackground(new java.awt.Color(255, 204, 0));
        loadButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        loadButton.setText("Load");
        loadButton.setToolTipText("");
        loadButton.setMaximumSize(new java.awt.Dimension(100, 100));
        loadButton.setMinimumSize(new java.awt.Dimension(100, 100));
        loadButton.setPreferredSize(new java.awt.Dimension(50, 50));
        loadButton.setSize(new java.awt.Dimension(50, 25));
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
        jButton1.setText("Back");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton1)
                        .addGap(474, 474, 474)
                        .addComponent(playerTurnLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(playerColor))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pickCardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rollDieButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(addConnectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(removeConnectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(teleportButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerTurnLabel)
                            .addComponent(playerColor)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton1)))
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(pickCardButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rollDieButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(teleportButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        teleportButton.getAccessibleContext().setAccessibleName("     Teleport      ");

        pack();
    }

    private void pickCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    }//GEN-LAST:event_pickCardButtonActionPerformed

    private void rollDieButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void addConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void removeConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void teleportButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private javax.swing.JButton addConnectionButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton pickCardButton;
    private javax.swing.JLabel playerColor;
    private javax.swing.JLabel playerTurnLabel;
    private javax.swing.JButton removeConnectionButton;
    private javax.swing.JButton rollDieButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton teleportButton;
}
