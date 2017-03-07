package ca.mcgill.ecse223.tileo.view;

import java.awt.*;

import javax.swing.UIManager;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

public class TileOPlayUI extends javax.swing.JFrame {

	/**
	 * Creates new form TileOPlayMode
	 */
	public TileOPlayUI(Game aGame) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			//Too bad
		}
		
		game = aGame;
		
		initComponents();
	}

	private int currentPlayer = 1;
	private Game game;

	private void initComponents() {
		pickCardButton = new javax.swing.JToggleButton();
		rollDieButton = new javax.swing.JToggleButton();
		addButton = new javax.swing.JToggleButton();
		removeCard = new javax.swing.JToggleButton();
		placeButton = new javax.swing.JToggleButton();
		saveButton = new javax.swing.JButton();
		loadButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jProgressBar1 = new javax.swing.JProgressBar();
		playerTurnJLabel = new javax.swing.JLabel();
		colorOfCurrentPlayer = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		pickCardButton.setBackground(new java.awt.Color(153, 153, 255));
		pickCardButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		pickCardButton.setEnabled(false);
		pickCardButton.setText("Pick a Card in Deck");

		rollDieButton.setBackground(new java.awt.Color(153, 153, 255));
		rollDieButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		rollDieButton.setText("Roll Die");

		addButton.setBackground(new java.awt.Color(153, 153, 255));
		addButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		addButton.setEnabled(false);
		addButton.setText("Add Connection");

		removeCard.setBackground(new java.awt.Color(153, 153, 255));
		removeCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		removeCard.setEnabled(false);
		removeCard.setText("Remove Connection");

		placeButton.setBackground(new java.awt.Color(153, 153, 255));
		placeButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		placeButton.setText("Teleport");
		placeButton.setEnabled(false);
		placeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				placeButtonActionPerformed(evt);
			}
		});

		saveButton.setBackground(new java.awt.Color(153, 153, 255));
		saveButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		saveButton.setText("Save");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		loadButton.setBackground(new java.awt.Color(153, 153, 255));
		loadButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		loadButton.setText("Load");

		jLabel1.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
		jLabel1.setText("Connection Pieces Available");

		jProgressBar1.setMaximum(32);
		jProgressBar1.setToolTipText("");
		jProgressBar1.setValue(32);
		jProgressBar1.setString(String.valueOf(jProgressBar1.getValue()));
		jProgressBar1.setStringPainted(true);

		playerTurnJLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
		playerTurnJLabel.setText("Player Turn : ");

		colorOfCurrentPlayer.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
		if (currentPlayer == 1) {
			colorOfCurrentPlayer.setText("RED");
			colorOfCurrentPlayer.setForeground(Color.RED);
		} else if (currentPlayer == 2) {
			colorOfCurrentPlayer.setText("BLUE");
			colorOfCurrentPlayer.setForeground(Color.BLUE);
		} else if (currentPlayer == 3) {
			colorOfCurrentPlayer.setText("GREEN");
			colorOfCurrentPlayer.setForeground(Color.GREEN);
		} else {
			colorOfCurrentPlayer.setText("YELLOW");
			colorOfCurrentPlayer.setForeground(Color.YELLOW);
		}

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(placeButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(removeCard, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(rollDieButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(pickCardButton))
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(saveButton)))
										.addGap(55, 55, 55))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup()
												.addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(29, 29, 29))
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addComponent(jLabel1).addContainerGap())))
				.addGroup(layout.createSequentialGroup().addGap(257, 257, 257).addComponent(playerTurnJLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(colorOfCurrentPlayer).addGap(0, 282, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(playerTurnJLabel).addComponent(colorOfCurrentPlayer))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(pickCardButton)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(rollDieButton))
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(addButton)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(removeCard)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(placeButton)
				.addGap(32, 32, 32).addComponent(saveButton)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(loadButton)
				.addGap(26, 26, 26)));

		pack();
	}// </editor-fold>

	private void placeButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}
	
	// Variables declaration - do not modify
	private javax.swing.JToggleButton addButton;
	private javax.swing.JLabel colorOfCurrentPlayer;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JProgressBar jProgressBar1;
	private javax.swing.JButton loadButton;
	private javax.swing.JToggleButton pickCardButton;
	private javax.swing.JToggleButton placeButton;
	private javax.swing.JLabel playerTurnJLabel;
	private javax.swing.JToggleButton removeCard;
	private javax.swing.JToggleButton rollDieButton;
	private javax.swing.JButton saveButton;
	// End of variables declaration
}