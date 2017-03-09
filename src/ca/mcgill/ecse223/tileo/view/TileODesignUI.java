package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.*;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;

public class TileODesignUI extends javax.swing.JFrame {
	private static final long serialVersionUID = -4784304605398643427L;

	private DesignController currentController;

	private LinkedList<TileUI> tilesButtons;
	private LinkedList<ConnectionUI> connectionButtons;
	private JPanel tilesPanel;

	private enum DesignState {
		BOARD_SIZE, CHANGE_NUMBER_OF_PLAYERS, SELECT_STARTING_POSITION, ADD_TILE, REMOVE_TILE, ADD_CONNECTION, REMOVE_CONNECTION, CARDS, NONE
	}

	private DesignState designState = DesignState.NONE;

	private int numberOfRows = 0;
	private int numberOfCols = 0;

	private int nbOfCardsLeft = 32;
	int[] cardsCounts = { 0, 0, 0, 0, 0 };

	/**
	 * Creates new form TileOUGUI
	 */
	public TileODesignUI(Game aGame) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// Too bad
		}

		game = aGame;
		currentController = new DesignController(game);

		// Init layout
		initComponents();
		disableChanges();
	}

	private static final int BOARDSIZE = 1;
	private static final int PLAYERS = 2;
	private static final int STARTINGBTN = 4;
	private static final int TILETYPE = 8;
	private static final int ADDTILEBTN = 16;
	private static final int REMOVETILEBTN = 32;
	private static final int ADDCONNBTN = 64;
	private static final int REMOVECONNBTN = 128;
	private static final int CARDS = 256;
	private static final int SAVEBTN = 512;
	private static final int LOADBTN = 1024;
	private static final int CHOSENPLAYER = 2048;
	private static final int ALLBTN = 8191;

	private void maskButtons(int param) {
		verticalLength.setEnabled((param & BOARDSIZE) == BOARDSIZE);
		horizontalLength.setEnabled((param & BOARDSIZE) == BOARDSIZE);
		//
		nbOfPlayers.setEnabled((param & PLAYERS) == PLAYERS);
		chosenPlayer.setEnabled((param & CHOSENPLAYER) == CHOSENPLAYER);
		//
		selectPositionButton.setEnabled((param & STARTINGBTN) == STARTINGBTN);
		//
		tileType.setEnabled((param & TILETYPE) == TILETYPE);
		//
		addTileButton.setEnabled((param & ADDTILEBTN) == ADDTILEBTN);
		//
		removeTileButton.setEnabled((param & REMOVETILEBTN) == REMOVETILEBTN);
		//
		removeConnectionButton.setEnabled((param & REMOVECONNBTN) == REMOVECONNBTN);
		//
		addConnectionButton.setEnabled((param & ADDCONNBTN) == ADDCONNBTN);
		//
		nbRollDieCard.setEnabled((param & CARDS) == CARDS);
		nbRemoveConnectionCard.setEnabled((param & CARDS) == CARDS);
		nbTeleportCard.setEnabled((param & CARDS) == CARDS);
		nbLoseTurnCard.setEnabled((param & CARDS) == CARDS);
		nbConnectTilesCard.setEnabled((param & CARDS) == CARDS);
		//
		saveButton.setEnabled((param & SAVEBTN) == SAVEBTN);
		//
		loadButton.setEnabled((param & LOADBTN) == LOADBTN);
	}

	private void changeNumberOfCardsLeft() {
		// Forbid negative numbers			
		try {
			if (Integer.valueOf(nbRollDieCard.getText()) < 0)
				nbRollDieCard.setText(String.valueOf(cardsCounts[0]));
		} catch(NumberFormatException e) {
			nbRollDieCard.setText(String.valueOf(cardsCounts[0]));
		}
		
		try {
			if (Integer.valueOf(nbRemoveConnectionCard.getText()) < 0)
				nbRemoveConnectionCard.setText(String.valueOf(cardsCounts[1]));
		} catch(NumberFormatException e) {
			nbRemoveConnectionCard.setText(String.valueOf(cardsCounts[1]));
		}
		
		try {
			if (Integer.valueOf(nbTeleportCard.getText()) < 0)
				nbTeleportCard.setText(String.valueOf(cardsCounts[2]));
		} catch(NumberFormatException e) {
			nbTeleportCard.setText(String.valueOf(cardsCounts[2]));
		}
		
		try {
			if (Integer.valueOf(nbLoseTurnCard.getText()) < 0)
				nbLoseTurnCard.setText(String.valueOf(cardsCounts[3]));
		} catch(NumberFormatException e) {
			nbLoseTurnCard.setText(String.valueOf(cardsCounts[3]));
		}
		
		try {
			if (Integer.valueOf(nbConnectTilesCard.getText()) < 0)
				nbConnectTilesCard.setText(String.valueOf(cardsCounts[4]));
		} catch(NumberFormatException e) {
			nbConnectTilesCard.setText(String.valueOf(cardsCounts[4]));
		}

		cardsCounts[0] = Integer.valueOf(nbRollDieCard.getText());
		cardsCounts[1] = Integer.valueOf(nbRemoveConnectionCard.getText());
		cardsCounts[2] = Integer.valueOf(nbTeleportCard.getText());
		cardsCounts[3] = Integer.valueOf(nbLoseTurnCard.getText());
		cardsCounts[4] = Integer.valueOf(nbConnectTilesCard.getText());

		// If number has changed
		if (nbOfCardsLeft != (32 - cardsCounts[0] - cardsCounts[1] - cardsCounts[2] - cardsCounts[3]
				- cardsCounts[4])) {
			nbOfCardsLeft = (32 - cardsCounts[0] - cardsCounts[1] - cardsCounts[2] - cardsCounts[3] - cardsCounts[4]);

			if (nbOfCardsLeft < 0) {
				cardsLeft.setForeground(new java.awt.Color(255, 0, 0));
				disableChanges();
			} else {
				cardsLeft.setForeground(new java.awt.Color(0, 0, 0));
				enableChanges();
			}

			cardsLeft.setText(String.valueOf(nbOfCardsLeft));
		}
		
		designState = DesignState.CARDS;
	}

	private void changeBoardSize(int m, int n) {
		if (numberOfRows == m && numberOfCols == n)
			return;

		// Clear board and do a whole new one (with normal tiles)
		numberOfRows = m;
		numberOfCols = n;

		// Clear
		tilesButtons.clear();
		connectionButtons.clear();
		tilesPanel.removeAll();

		// Create grids
		tilesPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Create buttons and put in linked list
		for (int row = 0; row < m + (m - 1); row++) {
			for (int col = 0; col < n + (n - 1); col++) {
				c.fill = GridBagConstraints.BOTH;
				c.gridx = row; // row
				c.gridy = col; // column

				// Tile
				if (row % 2 == 0 && col % 2 == 0) {
					// UI
					TileUI tile = new TileUI();
					tile.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							tileActionPerformed(evt);
						}
					});
					tilesButtons.add(tile);

					tilesPanel.add(tile, c);
					tile.setPosition(row / 2, col / 2);

					// Game
					currentController.createNormalTile(row / 2, col / 2);
				}

				// Horizontal connection
				else if (row % 2 == 1 && col % 2 == 0) {
					ConnectionUI conn = new ConnectionUI(ConnectionUI.Type.HORIZONTAL);
					conn.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionActionPerformed(evt);
						}
					});
					connectionButtons.add(conn);

					c.fill = GridBagConstraints.HORIZONTAL;
					tilesPanel.add(conn, c);
					conn.setPosition(row, col);
				}

				// Vertical connection
				else if (row % 2 == 0 && col % 2 == 1) {
					ConnectionUI conn = new ConnectionUI(ConnectionUI.Type.VERTICAL);
					conn.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionActionPerformed(evt);
						}
					});
					connectionButtons.add(conn);

					c.fill = GridBagConstraints.VERTICAL;
					tilesPanel.add(conn, c);
					conn.setPosition(row, col);
				}

				// Gap
				else {
					JPanel gap = new JPanel();
					gap.setPreferredSize(new java.awt.Dimension(10, 10));
					tilesPanel.add(gap, c);
				}
			}
		}
	}

	private void backupLists() {
		tilesButtons.parallelStream().forEach(s -> s.saveUIState());
		connectionButtons.parallelStream().forEach(s -> s.saveUIState());
	}

	private void restoreLists() {
		tilesButtons.parallelStream().forEach(s -> s.restoreUIState());
		connectionButtons.parallelStream().forEach(s -> s.restoreUIState());
	}

	private void resetUI() {
		restoreLists();
		update();
	}

	// Setup new board
	private void setupBoard() {
		tilesButtons = new LinkedList<TileUI>();
		connectionButtons = new LinkedList<ConnectionUI>();

		tilesPanel = new javax.swing.JPanel();
		tilesPanel.setPreferredSize(new java.awt.Dimension(1130, 680));

		changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()),
				Integer.valueOf(verticalLength.getSelectedItem().toString()));

		if (game == null) {
			game = currentController.initGame(Integer.valueOf(String.valueOf(chosenPlayer.getSelectedItem())));
		}
	}

	private void initComponents() {
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		applyChangesButton = new javax.swing.JToggleButton();
		chosenPlayer = new javax.swing.JComboBox<>();
		removeTileButton = new javax.swing.JToggleButton();
		tileType = new javax.swing.JComboBox<>();
		addTileButton = new javax.swing.JToggleButton();
		selectPositionButton = new javax.swing.JToggleButton();
		removeConnectionButton = new javax.swing.JToggleButton();
		addConnectionButton = new javax.swing.JToggleButton();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		nbRollDieCard = new javax.swing.JTextField();
		nbRemoveConnectionCard = new javax.swing.JTextField();
		nbTeleportCard = new javax.swing.JTextField();
		nbLoseTurnCard = new javax.swing.JTextField();
		nbConnectTilesCard = new javax.swing.JTextField();
		saveButton = new javax.swing.JButton();
		nbOfPlayers = new javax.swing.JComboBox<>();
		loadButton = new javax.swing.JButton();
		jLabel17 = new javax.swing.JLabel();
		backButton = new javax.swing.JButton();
		cardsLeft = new javax.swing.JLabel();
		horizontalLength = new javax.swing.JComboBox<>();
		verticalLength = new javax.swing.JComboBox<>();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBackground(new java.awt.Color(204, 255, 255));

		jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel1.setText("Board:");

		jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel2.setText("Player:");

		jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel3.setText("Tile:");

		jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel4.setText("Connection:");

		jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel5.setText("Deck:");

		jLabel6.setText("Enter dimensions");

		jLabel7.setText("Enter number of players");

		jLabel8.setText("Select player");

		jLabel9.setText("Change tile type");

		jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		jLabel10.setText("x");

		applyChangesButton.setBackground(new java.awt.Color(0, 204, 0));
		applyChangesButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		applyChangesButton.setForeground(new java.awt.Color(255, 255, 255));
		applyChangesButton.setText("Apply changes");
		applyChangesButton.setEnabled(false);
		applyChangesButton.addActionListener(e -> {
			update();
			designState = DesignState.NONE;
		});

		chosenPlayer.setBackground(new java.awt.Color(204, 204, 255));
		chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

		removeTileButton.setBackground(new java.awt.Color(153, 153, 255));
		removeTileButton.setText("Remove Tile");
		removeTileButton.addActionListener(e -> {
			if (removeTileButton.isSelected()) {
				designState = DesignState.REMOVE_TILE;
				tileType.setEnabled(false);

				backupLists();
				maskButtons(REMOVETILEBTN);
			} else {
				tileType.setEnabled(true);
				resetUI();
				designState = DesignState.NONE;
			}
		});

		tileType.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Tile", "Action Tile", "Win Tile" }));

		addTileButton.setBackground(new java.awt.Color(153, 153, 255));
		addTileButton.setText("Add Tile");
		addTileButton.addActionListener(e -> {
			if (addTileButton.isSelected()) {
				designState = DesignState.ADD_TILE;

				backupLists();
				maskButtons(ADDTILEBTN);

				// Show disabled tiles when "Regular Tile" is selected
				if (tileType.getSelectedItem().toString().equals("Regular Tile")) {
					showDisabledTiles();
				}
			} else {
				resetUI();
				designState = DesignState.NONE;
			}
		});

		selectPositionButton.setBackground(new java.awt.Color(153, 153, 255));
		selectPositionButton.setText("Select Start Position");
		selectPositionButton.addActionListener(e -> {
			if (selectPositionButton.isSelected()) {
				designState = DesignState.SELECT_STARTING_POSITION;

				backupLists();
				maskButtons(STARTINGBTN);
			} else {
				resetUI();
				designState = DesignState.NONE;
			}
		});

		removeConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
		removeConnectionButton.setText("Remove Connection");
		removeConnectionButton.addActionListener(e -> {
			if (removeConnectionButton.isSelected()) {
				designState = DesignState.REMOVE_CONNECTION;

				backupLists();
				maskButtons(REMOVECONNBTN);

				// Change colors for connections
				connectionButtons.parallelStream().forEach(s -> s.setBackground(null));
			} else {
				resetUI();
				designState = DesignState.NONE;
			}
		});

		addConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
		addConnectionButton.setText("Add Connection");
		addConnectionButton.addActionListener(e -> {
			if (addConnectionButton.isSelected()) {
				designState = DesignState.ADD_CONNECTION;

				backupLists();
				maskButtons(ADDCONNBTN);

				showDisabledConnections();
			} else {
				resetUI();
				designState = DesignState.NONE;
			}
		});

		jLabel11.setText("Cards left :");

		jLabel12.setText("Roll Die");

		jLabel13.setText("Remove Connection");

		jLabel14.setText("Teleport");

		jLabel15.setText("Lose Turn");

		jLabel16.setText("Connect Tiles");

		nbRollDieCard.setText("0");
		nbRollDieCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
		nbRollDieCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

		nbRemoveConnectionCard.setText("0");
		nbRemoveConnectionCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
		nbRemoveConnectionCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

		nbTeleportCard.setText("0");
		nbTeleportCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
		nbTeleportCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

		nbLoseTurnCard.setText("0");
		nbLoseTurnCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
		nbLoseTurnCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

		nbConnectTilesCard.setText("0");
		nbConnectTilesCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
		nbConnectTilesCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

		saveButton.setBackground(new java.awt.Color(0, 0, 255));
		saveButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		saveButton.setForeground(new java.awt.Color(255, 255, 255));
		saveButton.setText("Load");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		nbOfPlayers.setBackground(new java.awt.Color(204, 204, 255));
		nbOfPlayers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4" }));
		nbOfPlayers.addActionListener(e -> {
			if (Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())) != game.numberOfPlayers()) {
				designState = DesignState.CHANGE_NUMBER_OF_PLAYERS;
				nbOfPlayersChanged();
			} else {
				resetUI();
				designState = DesignState.NONE;
			}
		});

		loadButton.setBackground(new java.awt.Color(0, 0, 255));
		loadButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		loadButton.setForeground(new java.awt.Color(255, 255, 255));
		loadButton.setText("Save");
		loadButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loadButtonActionPerformed(evt);
			}
		});

		jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
		jLabel17.setText("TileO Design Mode");

		backButton.setBackground(new java.awt.Color(255, 0, 0));
		backButton.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
		backButton.setForeground(new java.awt.Color(255, 255, 255));
		backButton.setText("Back");
		backButton.addActionListener(e -> {
			new MainPage().setVisible(true);
			dispose();
		});

		cardsLeft.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
		cardsLeft.setText("32");

		horizontalLength.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
		horizontalLength.addActionListener(e -> {
			designState = DesignState.BOARD_SIZE;

			if (Integer.valueOf(String.valueOf(horizontalLength.getSelectedItem())) != numberOfRows)
				enableChanges();
			else
				disableChanges();
		});
		horizontalLength.setSelectedIndex(8 - 2);

		verticalLength.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
		verticalLength.addActionListener(e -> {
			designState = DesignState.BOARD_SIZE;

			if (Integer.valueOf(String.valueOf(verticalLength.getSelectedItem())) != numberOfCols)
				enableChanges();
			else
				disableChanges();
		});
		verticalLength.setSelectedIndex(8 - 2);

		// Window

		setResizable(false);

		// Board
		setupBoard();
		//
		GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);

		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
								.createSequentialGroup().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
												.createSequentialGroup().addComponent(jLabel11).addGap(6, 6, 6)
												.addComponent(cardsLeft).addGap(52, 52, 52)
												.addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE,
														33, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39,
														Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup().addComponent(jLabel5)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jLabel12).addGap(31, 31, 31)))
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(18, 18, 18)
												.addComponent(jLabel13))
										.addGroup(layout.createSequentialGroup().addGap(57, 57, 57).addComponent(
												nbRemoveConnectionCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(36, 36, 36)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel14)
										.addGroup(layout.createSequentialGroup().addGap(6, 6, 6).addComponent(
												nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(50, 50, 50)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(6, 6, 6)
												.addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE,
														33, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(87, 87, 87).addComponent(nbConnectTilesCard,
														javax.swing.GroupLayout.PREFERRED_SIZE, 33,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addComponent(jLabel15)
												.addGap(43, 43, 43).addComponent(jLabel16)))
								.addGap(46, 46, 46))
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel3))
										.addGap(61, 61, 61)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addGap(100, 100, 100)
														.addComponent(addConnectionButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 153,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(layout.createSequentialGroup()
																		.addGap(36, 36, 36)
																		.addComponent(removeConnectionButton,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				153,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(0, 0, Short.MAX_VALUE))
																.addGroup(layout.createSequentialGroup()
																		.addGap(18, 18, 18).addComponent(jLabel8)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(chosenPlayer,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addGap(18, 18, 18)
																		.addComponent(selectPositionButton,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				191, Short.MAX_VALUE))))
												.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
														.createSequentialGroup().addComponent(jLabel9)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(51, 51, 51)
														.addComponent(addTileButton,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(removeTileButton,
																javax.swing.GroupLayout.PREFERRED_SIZE, 153,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup().addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout.createSequentialGroup().addComponent(jLabel6)
																.addGap(61, 61, 61)
																.addComponent(horizontalLength,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 60,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(jLabel10)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(verticalLength,
																		javax.swing.GroupLayout.PREFERRED_SIZE, 60,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(layout.createSequentialGroup().addComponent(jLabel7)
																.addGap(18, 18, 18).addComponent(nbOfPlayers,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGap(0, 0, Short.MAX_VALUE))))
								.addGroup(layout.createSequentialGroup().addComponent(jLabel4).addGap(0, 0,
										Short.MAX_VALUE)))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(applyChangesButton, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
								.addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(26, 26, 26))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(16, 16, 16).addComponent(backButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabel17).addGap(376, 376, 376))
						// .addGap(120, 120, 120))
						.addComponent(tilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(7, 7, 7)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel17).addComponent(backButton))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel6).addComponent(jLabel10).addComponent(
										horizontalLength, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(verticalLength, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(14, 14, 14)
						.addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
								.addComponent(jLabel7).addComponent(jLabel8)
								.addComponent(chosenPlayer, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(
										selectPositionButton)
								.addComponent(nbOfPlayers, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(13, 13, 13)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3).addComponent(jLabel9)
												.addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(addTileButton).addComponent(removeTileButton))
										.addGap(14, 14, 14)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel4).addComponent(removeConnectionButton)
												.addComponent(addConnectionButton)))
								.addGroup(layout.createSequentialGroup()
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(loadButton).addGap(13, 13, 13).addComponent(saveButton)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jLabel5)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(jLabel11).addComponent(applyChangesButton)
														.addComponent(cardsLeft)))
								.addGroup(layout
										.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel12).addComponent(jLabel13).addComponent(jLabel14)
												.addComponent(jLabel15).addComponent(jLabel16))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(nbRemoveConnectionCard,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(nbConnectTilesCard,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(tilesPanel,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	private void update() {
		switch (designState) {
		/*
		 * IF BOARD SIZE HAS CHANGED
		 */
		case BOARD_SIZE:
			changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()),
					Integer.valueOf(verticalLength.getSelectedItem().toString()));
			break;

		/*
		 * IF STARTING POSITION OF PLAYER
		 */
		case SELECT_STARTING_POSITION:
			TileUI tileGUI = null;
			int playerNumber = Integer.valueOf(chosenPlayer.getSelectedItem().toString());

			tileGUI = tilesButtons.parallelStream().filter(s -> s.isSelected()).findAny().orElse(null);

			try {
				// TODO : Check if player has already a tile, if so, replace
				// color with null
				
				switch (playerNumber) {
				case 1:
					tileGUI.setBackground(new java.awt.Color(240, 10, 10));
					break;

				case 2:
					tileGUI.setBackground(new java.awt.Color(10, 10, 240));
					break;

				case 3:
					tileGUI.setBackground(new java.awt.Color(10, 240, 10));
					break;

				case 4:
					tileGUI.setBackground(new java.awt.Color(240, 240, 10));
					break;
				}

				tileGUI.resetUI();

				Tile startingTile = game.getTileFromXY(tileGUI.getUIX(), tileGUI.getUIY());

				try {
					if (!currentController.startingPosition(startingTile, playerNumber)) {
						// TODO : Error - couldn't set starting position
					}
				} catch (InvalidInputException e) {

				}
			} catch (Exception e) {
				// TODO : tileGUI null
			}

			break;

		/*
		 * IF ADDING A TILE
		 */
		case ADD_TILE:
			//WinTile
			if (tileType.getSelectedItem().toString().equals("Win Tile")) {
				TileUI prevWinTileUI = tilesButtons.parallelStream().filter(s -> s.getState() == TileUI.State.WIN).findAny().orElse(null);
				TileUI nextWinTileUI = tilesButtons.parallelStream().filter(s -> s.isSelected() && 
						s.getLifeState() == TileUI.LifeState.EXIST &&
						s.getState() == TileUI.State.NORMAL).findAny().orElse(null);
				
				//Then there were a previous win tile
				if (prevWinTileUI != null) {
					prevWinTileUI.resetUI();
				}
				
				//WinTile is selected
				if (nextWinTileUI != null) {
					nextWinTileUI.setState(TileUI.State.WIN);
					nextWinTileUI.setText("W");
					nextWinTileUI.setBackground(Color.pink);
					
					Tile nextWinTile = game.getTileFromXY(nextWinTileUI.getUIX(), nextWinTileUI.getUIY());
					currentController.createWinTile(nextWinTile);
				}
			} 
			//ActionTile
			else if(tileType.getSelectedItem().toString().equals("Action Tile")) {
				//TODO : Popup inactivity period
				int disableTurns = 1;
				
				List<TileUI> tiles = tilesButtons.parallelStream().filter(s -> s.isSelected() && 
						s.getLifeState() == TileUI.LifeState.EXIST &&
						s.getState() == TileUI.State.NORMAL).collect(Collectors.toList());
				
				tiles.forEach(s -> {
					s.setText("A");
					s.setBackground(Color.magenta);
					
					Tile tileEquivalent = game.getTileFromXY(s.getUIX(), s.getUIY());
					
					currentController.createActionTile(tileEquivalent, disableTurns);
				});
			}
			//NormalTile
			else {
				List<TileUI> tiles = tilesButtons.parallelStream().filter(s -> !s.isSelected() && s.getLifeState() == TileUI.LifeState.NOTEXIST).collect(Collectors.toList());
				tiles.forEach(s -> {
					s.setLifeState(TileUI.LifeState.EXIST);
					s.resetUI();
					
					int connX = s.getUIX()*2;
					int connY = s.getUIY()*2;
					
					//Show nearest connections
					connectionButtons.parallelStream().filter(t -> (t.getUIX()==connX && Math.abs(t.getUIY()-connY)==1) ||
							(t.getUIY()==connY && Math.abs(t.getUIX()-connX)==1)).forEach(t -> {
								t.setState(ConnectionUI.State.SHOW);
								t.setLifeState(ConnectionUI.LifeState.EXIST);
							});		
				});
				
			}
			break;

		/*
		 * IF REMOVING A TILE
		 */
		case REMOVE_TILE:
			List<TileUI> tiles = tilesButtons.parallelStream().filter(s -> s.isSelected() && s.getLifeState() == TileUI.LifeState.EXIST).collect(Collectors.toList());
			tiles.forEach(s -> {
				s.setLifeState(TileUI.LifeState.NOTEXIST);
				s.resetUI();
				
				Tile tileEquivalent = game.getTileFromXY(s.getUIX(), s.getUIY());
				
				int connX = s.getUIX()*2;
				int connY = s.getUIY()*2;
				
				//Hide nearest connections
				connectionButtons.parallelStream().filter(t -> (t.getUIX()==connX && Math.abs(t.getUIY()-connY)==1) ||
						(t.getUIY()==connY && Math.abs(t.getUIX()-connX)==1)).forEach(t -> {
							t.setState(ConnectionUI.State.HIDE);
							t.setLifeState(ConnectionUI.LifeState.NOTEXIST);
						});
				
				try {
					currentController.deleteTile(tileEquivalent);
				} catch (InvalidInputException e) {
					//No tile to delete
				}
			});
			break;

		/*
		 * IF ADDING A CONNECTION
		 */
		case ADD_CONNECTION:
			connectionButtons.parallelStream()
				.filter(s -> s.isSelected() && s.isVisible() && s.getLifeState() == ConnectionUI.LifeState.NOTEXIST
				&& s.getState() == ConnectionUI.State.SHOW)
				.forEach(s -> {
					s.setLifeState(ConnectionUI.LifeState.EXIST);
					
					if(s.getType() == ConnectionUI.Type.HORIZONTAL) {
						Tile tile1 = game.getTileFromXY((s.getUIX()-1)/2, s.getUIY()/2);
						Tile tile2 = game.getTileFromXY((s.getUIX()+1)/2, s.getUIY()/2);
						
						try {
							currentController.connectTiles(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					} else if(s.getType() == ConnectionUI.Type.VERTICAL) {
						Tile tile1 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()-1)/2);
						Tile tile2 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()+1)/2);
						
						try {
							currentController.connectTiles(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					}
				});
			break;

		/*
		 * IF REMOVING A CONNECTION
		 */
		case REMOVE_CONNECTION:
			connectionButtons.parallelStream().filter(s -> !s.isSelected() && s.isVisible() && s.getLifeState() == ConnectionUI.LifeState.EXIST
				&& s.getState() == ConnectionUI.State.SHOW).forEach(s -> {
					s.setLifeState(ConnectionUI.LifeState.NOTEXIST);
					
					if(s.getType() == ConnectionUI.Type.HORIZONTAL) {
						Tile tile1 = game.getTileFromXY((s.getUIX()-1)/2, s.getUIY()/2);
						Tile tile2 = game.getTileFromXY((s.getUIX()+1)/2, s.getUIY()/2);
						
						try {
							currentController.removeConnection(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					} else if(s.getType() == ConnectionUI.Type.VERTICAL) {
						Tile tile1 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()-1)/2);
						Tile tile2 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()+1)/2);
						
						try {
							currentController.removeConnection(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					}	
				});
			break;

		/*
		 * IF NUMBER HAS CHANGED
		 */
		case CHANGE_NUMBER_OF_PLAYERS:
			setupBoard();
			game = currentController.initGame(Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())));
			
			 if (nbOfPlayers.getSelectedItem() == "4") {
				 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" })); 
			 } 
			 
			 else if (nbOfPlayers.getSelectedItem() == "3") { 
				 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
			 } 
			 
			 else { 
				 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" })); 
			 }
			 break;
			 
		/*
		 * IF CARDS HAVE CHANGED
		 */
		case CARDS:
			try {
				currentController.createDeck(cardsCounts[0], cardsCounts[1], cardsCounts[2], cardsCounts[3], cardsCounts[4]);
			} catch (InvalidInputException e) {
				// More than 32
			}
			break;
		}

		// Change colors for connections
		connectionButtons.parallelStream().forEach(s -> {
			if (s.getLifeState() == ConnectionUI.LifeState.NOTEXIST) 
				s.setBackground(null); 
			else
				s.setBackground(new java.awt.Color(0, 0, 0));
		});

		// Reset states
		hideDisabledTiles();
		hideDisabledConnections();
		
		disableChanges();
		maskButtons(ALLBTN);
		designState = DesignState.NONE;

		// Unselect all buttons
		selectPositionButton.setSelected(false);
		addTileButton.setSelected(false);
		removeTileButton.setSelected(false);
		removeConnectionButton.setSelected(false);
		addConnectionButton.setSelected(false);
		
		tilesButtons.parallelStream().filter(s -> s.isVisible()).forEach(s -> {
			s.setSelected(false);
			s.setFocusPainted(false);
			s.setBorderPainted(false);
		});
		
		revalidate();
		repaint();
	}

	// Tile clicked
	private void tileActionPerformed(java.awt.event.ActionEvent evt) {
		TileUI tile = (TileUI) evt.getSource();

		// Cannot select 2 at a time
		if (designState == DesignState.SELECT_STARTING_POSITION) {
			// If tile is normal
			if (tile.getState() == TileUI.State.NORMAL) {
				tilesButtons.parallelStream().filter(s -> s.isSelected() && s != tile)
						.forEach(s -> s.setSelected(false));
				enableChanges();
			} else {
				tile.setSelected(false);
				tile.setBorderPainted(false);
				tile.setFocusPainted(false);
				disableChanges();
			}
		}

		else if (designState == DesignState.ADD_TILE) {
			// WinTile - Cannot select 2 at a time
			if (tileType.getSelectedItem().toString().equals("Win Tile")) {
				// If tile is normal
				if (tile.getState() == TileUI.State.NORMAL) {
					tilesButtons.parallelStream().filter(s -> s.isSelected() && s != tile)
							.forEach(s -> s.setSelected(false));
					enableChanges();
				} else {
					tile.setSelected(false);
					tile.setBorderPainted(false);
					tile.setFocusPainted(false);
					disableChanges();
				}
			}

			else if (tileType.getSelectedItem().toString().equals("Action Tile")) {
				// TODO : Action tile - MessageBox

				// If tile is normal
				if (tile.getState() == TileUI.State.NORMAL) {
					enableChanges();
				} else {
					tile.setSelected(false);
					tile.setBorderPainted(false);
					tile.setFocusPainted(false);
					disableChanges();
				}
			}

			// Normal tile
			else {
				if (tile.getLifeState() == TileUI.LifeState.NOTEXIST) {
					// Let clicking
					enableChanges();
				}
			}
		}

		// Remove tile
		else if (designState == DesignState.REMOVE_TILE) {
			// Let clicking
			enableChanges();
		}

		// If cannot click tile
		else {
			tile.setSelected(false);
			tile.setBorderPainted(false);
			tile.setFocusPainted(false);
		}
	}

	// Connection clicked
	private void connectionActionPerformed(java.awt.event.ActionEvent evt) {
		ConnectionUI conn = (ConnectionUI) evt.getSource();

		if (designState == DesignState.ADD_CONNECTION) {
			// If already exists - do nothing
			if (conn.getLifeState() == ConnectionUI.LifeState.EXIST) {
				conn.setSelected(false);
				conn.setBorderPainted(false);
				conn.setFocusPainted(false);
			}
			// Enable changes
			else {
				enableChanges();
			}
		} else if (designState == DesignState.REMOVE_CONNECTION) {
			enableChanges();
		}
		// Does not allow connections to be clicked
		else {
			conn.setSelected(false);
			conn.setBorderPainted(false);
			conn.setFocusPainted(false);
		}
	}

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO : Check if game is playable to change game state
		TileOApplication.save();
	}

	private void nbOfPlayersChanged() {
		// TODO : MessageBox - game will be reset
		String nbOfPlayersChosen = String.valueOf(nbOfPlayers.getSelectedItem());
		if (game.numberOfPlayers() != Integer.valueOf(nbOfPlayersChosen)) {
			enableChanges();
			maskButtons(PLAYERS);
		}
		else {
			disableChanges();
			maskButtons(ALLBTN);
		}
	}

	private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO : MessageBox - any unsaved changes will be lost
		// then show available games OR create new board

		TileOApplication.load();
	}

	private void enableChanges() {
		applyChangesButton.setEnabled(true);
		applyChangesButton.setSelected(false);
	}

	private void disableChanges() {
		applyChangesButton.setEnabled(false);
		applyChangesButton.setSelected(false);
	}

	private void showDisabledTiles() {
		tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST)
				.forEach(s -> {
					s.showUI();
					s.setSelected(true);
					});
	}

	private void hideDisabledTiles() {
		tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST)
				.forEach(s -> {
					s.hideUI();
					s.setSelected(false);
					});
	}

	private void showDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST && s.getState() == ConnectionUI.State.SHOW)
				.forEach(s -> s.showUI());
	}

	private void hideDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST)
				.forEach(s -> s.hideUI());
	}

	//
	private Game game;

	// Variables declaration - do not modify
	private javax.swing.JToggleButton addConnectionButton;
	private javax.swing.JToggleButton addTileButton;
	private javax.swing.JToggleButton applyChangesButton;
	private javax.swing.JButton backButton;
	private javax.swing.JLabel cardsLeft;
	private javax.swing.JComboBox<String> chosenPlayer;
	private javax.swing.JComboBox<String> horizontalLength;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel13;
	private javax.swing.JLabel jLabel14;
	private javax.swing.JLabel jLabel15;
	private javax.swing.JLabel jLabel16;
	private javax.swing.JLabel jLabel17;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JButton loadButton;
	private javax.swing.JTextField nbConnectTilesCard;
	private javax.swing.JTextField nbLoseTurnCard;
	private javax.swing.JComboBox<String> nbOfPlayers;
	private javax.swing.JTextField nbRemoveConnectionCard;
	private javax.swing.JTextField nbRollDieCard;
	private javax.swing.JTextField nbTeleportCard;
	private javax.swing.JToggleButton removeConnectionButton;
	private javax.swing.JToggleButton removeTileButton;
	private javax.swing.JButton saveButton;
	private javax.swing.JToggleButton selectPositionButton;
	private javax.swing.JComboBox<String> tileType;
	private javax.swing.JComboBox<String> verticalLength;
	// End of variables declaration
}