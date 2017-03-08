package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.*;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class TileODesignUI extends javax.swing.JFrame {
	private static final long serialVersionUID = -4784304605398643427L;

	private DesignController currentController;

	private LinkedList<TileUI> tilesButtons;
	private LinkedList<ConnectionUI> connectionButtons;
	private JPanel tilesPanel;

	private enum DesignState {
		BOARD_SIZE, CHANGE_NUMBER_OF_PLAYERS, SELECT_STARTING_POSITION, ADD_TILE, REMOVE_TILE, ADD_CONNECTION, REMOVE_CONNECTION, NONE
	}

	private DesignState designState = DesignState.NONE;

	private int numberOfRows = 0;
	private int numberOfCols = 0;

	/**
	 * Creates new form TileOUGUI
	 */
	public TileODesignUI(Game aGame) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			//Too bad
		}
		
		game = aGame;
		currentController = new DesignController(game);

		//Init layout
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
	private static final int ALLBTN = 4095;
	
	private void maskButtons(int param) {
		verticalLength.setEnabled((param & BOARDSIZE) == BOARDSIZE);
		horizontalLength.setEnabled((param & BOARDSIZE) == BOARDSIZE);
		//
		chosenPlayer.setEnabled((param & PLAYERS) == PLAYERS);
		nbOfPlayers.setEnabled((param & PLAYERS) == PLAYERS);
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
		//Forbid negative numbers
		if (Integer.valueOf(nbRollDieCard.getText()) < 0)
			nbRollDieCard.setText("0");
		if (Integer.valueOf(nbRemoveConnectionCard.getText()) < 0)
			nbRemoveConnectionCard.setText("0");
		if (Integer.valueOf(nbTeleportCard.getText()) < 0)
			nbTeleportCard.setText("0");
		if (Integer.valueOf(nbLoseTurnCard.getText()) < 0)
			nbLoseTurnCard.setText("0");
		if (Integer.valueOf(nbConnectTilesCard.getText()) < 0)
			nbConnectTilesCard.setText("0");
		
		int nbOfCardsLeft = 32 - (Integer.valueOf(nbRollDieCard.getText()) + 
									Integer.valueOf(nbRemoveConnectionCard.getText()) + 
									Integer.valueOf(nbTeleportCard.getText()) + 
									Integer.valueOf(nbLoseTurnCard.getText()) + 
									Integer.valueOf(nbConnectTilesCard.getText()));

		if (nbOfCardsLeft < 0) {
			cardsLeft.setForeground(new java.awt.Color(255, 0, 0));
			applyChangesButton.setEnabled(false);
		} else {
			cardsLeft.setForeground(new java.awt.Color(0, 0, 0));
			applyChangesButton.setEnabled(true);
		}

		cardsLeft.setText(String.valueOf(nbOfCardsLeft));
	}
	
	private void changeBoardSize(int m, int n) {
		if (numberOfRows == m && numberOfCols == n)
			return;

		numberOfRows = m;
		numberOfCols = n;

		// Clear
		tilesButtons.clear();
		connectionButtons.clear();
		tilesPanel.removeAll();

		// Create buttons and put in linked list
		for (int i = 0; i < m + (m - 1); i++) {
			for (int j = 0; j < n + (n - 1); j++) {
				// Tile
				if (i % 2 == 0 && j % 2 == 0) {
					TileUI tile = new TileUI();
					tile.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							tileActionPerformed(evt);
						}
					});
					tilesButtons.add(tile);
					//Tile
					//currentController.createNormalTile(i/2, j/2);
				}

				// Horizontal connection
				else if (i % 2 == 1 && j % 2 == 0) {
					ConnectionUI conn = new ConnectionUI(ConnectionUI.Type.HORIZONTAL);
					conn.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionActionPerformed(evt);
						}
					});
					connectionButtons.add(conn);
				}

				// Vertical connection
				else if (i % 2 == 0 && j % 2 == 1) {
					ConnectionUI conn = new ConnectionUI(ConnectionUI.Type.VERTICAL);
					conn.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							connectionActionPerformed(evt);
						}
					});
					connectionButtons.add(conn);
				}

			}
		}

		// Create grids
		tilesPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		ListIterator<TileUI> tiles_it = tilesButtons.listIterator();
		ListIterator<ConnectionUI> conn_it = connectionButtons.listIterator();

		for (int row = 0; row < m + (m - 1); row++) {
			for (int col = 0; col < n + (n - 1); col++) {
				c.fill = GridBagConstraints.BOTH;
				c.gridx = row;
				c.gridy = col;

				// Tile
				if (row % 2 == 0 && col % 2 == 0) {
					TileUI next = tiles_it.next();
					tilesPanel.add(next, c);
					next.setPosition(row/2, col/2);
					//TODO : Create tile in game
				}

				// Horizontal connection
				else if (row % 2 == 1 && col % 2 == 0) {
					ConnectionUI next = conn_it.next();
					c.fill = GridBagConstraints.HORIZONTAL;
					tilesPanel.add(next, c);
					next.setPosition(row,col);
				}

				// Vertical connection
				else if (row % 2 == 0 && col % 2 == 1) {
					ConnectionUI next = conn_it.next();
					c.fill = GridBagConstraints.VERTICAL;
					tilesPanel.add(next, c);
					next.setPosition(row,col);
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

		applyChangesButtonActionPerformed(new java.awt.event.ActionEvent(new Object(), 0, ""));
	}

	//Setup new board
	private void setupBoard() {
		tilesButtons = new LinkedList<TileUI>();
		connectionButtons = new LinkedList<ConnectionUI>();

		tilesPanel = new javax.swing.JPanel();
		tilesPanel.setPreferredSize(new java.awt.Dimension(1130, 680));

		changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()), Integer.valueOf(verticalLength.getSelectedItem().toString()));
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
		applyChangesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				applyChangesButtonActionPerformed(evt);
				designState = DesignState.NONE;
			}
		});

		chosenPlayer.setBackground(new java.awt.Color(204, 204, 255));
		chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));

		removeTileButton.setBackground(new java.awt.Color(153, 153, 255));
		removeTileButton.setText("Remove Tile");
		removeTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(removeTileButton.isSelected()) {
					designState = DesignState.REMOVE_TILE;
					tileType.setEnabled(false);
					
					backupLists();
					maskButtons(REMOVETILEBTN);
				} else {
					tileType.setEnabled(true);
					resetUI();
					designState = DesignState.NONE;
				}
			}
		});

		tileType.setModel(
				new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Tile", "Action Tile", "Win Tile" }));

		addTileButton.setBackground(new java.awt.Color(153, 153, 255));
		addTileButton.setText("Add Tile");
		addTileButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(addTileButton.isSelected()) {
					designState = DesignState.ADD_TILE;
					
					backupLists();
					maskButtons(ADDTILEBTN);
					
					//Show disabled tiles when "Regular Tile" is selected 
					if (tileType.getSelectedItem().toString().equals("Regular Tile")) {
						showDisabledTiles();
					}
				} else {
					resetUI();
					designState = DesignState.NONE;
				}
			}
		});

		selectPositionButton.setBackground(new java.awt.Color(153, 153, 255));
		selectPositionButton.setText("Select Start Position");
		selectPositionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(selectPositionButton.isSelected()) {
					designState = DesignState.SELECT_STARTING_POSITION;
					
					backupLists();
					maskButtons(STARTINGBTN);
				} else {
					resetUI();
					designState = DesignState.NONE;
				}
			}
		});

		removeConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
		removeConnectionButton.setText("Remove Connection");
		removeConnectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(removeConnectionButton.isSelected()) {
					designState = DesignState.REMOVE_CONNECTION;
					
					backupLists();
					maskButtons(REMOVECONNBTN);
					
					// Change colors for connections
					for (ConnectionUI conn : connectionButtons) {
						conn.setBackground(null);
					}
				} else {
					resetUI();
					designState = DesignState.NONE;
				}
			}
		});

		addConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
		addConnectionButton.setText("Add Connection");
		addConnectionButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(addConnectionButton.isSelected()) {
					designState = DesignState.ADD_CONNECTION;
					
					backupLists();
					maskButtons(ADDCONNBTN);
					
					showDisabledConnections();
				} else {
					resetUI();
					designState = DesignState.NONE;
				}
			}
		});

		jLabel11.setText("Cards left :");

		jLabel12.setText("Roll Die");

		jLabel13.setText("Remove Connection");

		jLabel14.setText("Teleport");

		jLabel15.setText("Lose Turn");

		jLabel16.setText("Connect Tiles");

		nbRollDieCard.setText("0");
		nbRollDieCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeNumberOfCardsLeft();
			}
		});
		nbRollDieCard.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
				 
			 }
		      public void focusLost(FocusEvent e) {
		    	  changeNumberOfCardsLeft();
		      }
		});

		nbRemoveConnectionCard.setText("0");
		nbRemoveConnectionCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeNumberOfCardsLeft();
			}
		});
		nbRemoveConnectionCard.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
				 
			 }
		      public void focusLost(FocusEvent e) {
		    	  changeNumberOfCardsLeft();
		      }
		});

		nbTeleportCard.setText("0");
		nbTeleportCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeNumberOfCardsLeft();
			}
		});
		nbTeleportCard.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
				 
			 }
		      public void focusLost(FocusEvent e) {
		    	  changeNumberOfCardsLeft();
		      }
		});

		nbLoseTurnCard.setText("0");
		nbLoseTurnCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeNumberOfCardsLeft();
			}
		});
		nbLoseTurnCard.addFocusListener(new FocusListener() {
			 public void focusGained(FocusEvent e) {
				 
			 }
		      public void focusLost(FocusEvent e) {
		    	  changeNumberOfCardsLeft();
		      }
		});

		nbConnectTilesCard.setText("0");
		nbConnectTilesCard.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				changeNumberOfCardsLeft();
			}
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
		nbOfPlayers.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())) != game.numberOfPlayers()) {
					designState = DesignState.CHANGE_NUMBER_OF_PLAYERS;
					nbOfPlayersChanged();
				} else {
					resetUI();
					designState = DesignState.NONE;
				}
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
		backButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				backButtonActionPerformed(evt);
			}
		});

		cardsLeft.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
		cardsLeft.setText("32");

		horizontalLength.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
		horizontalLength.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				horizontalLengthActionPerformed(evt);
			}
		});
		horizontalLength.setSelectedIndex(8 - 2);

		verticalLength.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
		verticalLength.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				verticalLengthActionPerformed(evt);
			}
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

	private void applyChangesButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO : Rewrite according to classes
		
		// Change board size
		if (designState == DesignState.BOARD_SIZE) {
			changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()),
					Integer.valueOf(verticalLength.getSelectedItem().toString()));
		}

		// Select Player
		if (designState == DesignState.SELECT_STARTING_POSITION) {
			int playerNumber = Integer.valueOf(chosenPlayer.getSelectedItem().toString());

			if (playerNumber == 1) {
				for (JToggleButton button : tilesButtons) {
					if (button.getBackground().equals(new java.awt.Color(240, 10, 10))) {
						button.setBackground(null);
					}
					if (button.isSelected()) {
						button.setBackground(new java.awt.Color(240, 10, 10));
						button.setText("");
						button.setSelected(false);
					}
				}
			}

			if (playerNumber == 2) {
				for (JToggleButton button : tilesButtons) {
					if (button.getBackground().equals(new java.awt.Color(10, 10, 240))) {
						button.setBackground(null);
					}
					if (button.isSelected()) {
						button.setBackground(new java.awt.Color(10, 10, 240));
						button.setText("");
						button.setSelected(false);

					}
				}
			}

			if (playerNumber == 3) {
				for (JToggleButton button : tilesButtons) {
					if (button.getBackground().equals(new java.awt.Color(10, 240, 10))) {
						button.setBackground(null);
					}
					if (button.isSelected()) {
						button.setBackground(new java.awt.Color(10, 240, 10));
						button.setText("");
						button.setSelected(false);
					}
				}
			}

			if (playerNumber == 4) {
				for (JToggleButton button : tilesButtons) {
					if (button.getBackground().equals(new java.awt.Color(240, 240, 10))) {
						button.setBackground(null);
					}
					if (button.isSelected()) {
						button.setBackground(new java.awt.Color(240, 240, 10));
						button.setText("");
						button.setSelected(false);
					}
				}
			}
		}

		// Remove Tiles
		if (designState == DesignState.REMOVE_TILE) {
			int i = 0;
			for (JToggleButton button : tilesButtons) {
				if (button.isSelected()) {
					button.setVisible(false);
					button.setText("");
					button.setBackground(null);
					//Tile
					//currentController.deleteTile((i / numberOfRows) * 2, (i % numberOfRows) * 2);
				}
				i++;
			}
		}

		// Remove Connections
		if (designState == DesignState.REMOVE_CONNECTION) {
			int i = 0;
			for (ConnectionUI button : connectionButtons) {
				if (button.isSelected()) {
					button.setVisible(false);

					//Tile 1 and 2
					//currentController.removeConnection(button.getUIX(), button.getUIY());
				}
				i++;
			}

		}

		if (designState == DesignState.ADD_TILE) {
			int i = 0;
			for (JToggleButton tile : tilesButtons) {
				// Add Win Tile
				if (tileType.getSelectedItem().toString().equals("Win Tile")) {
					if (tile.getBackground().equals(Color.pink)) {
						tile.setBackground(null);
						tile.setText("");
					}
					if (tile.isSelected() && tile.isVisible()) {
						tile.setText("W");
						tile.setBackground(Color.pink);
						//Tile
						//currentController.createWinTile((i / numberOfRows) * 2, (i % numberOfRows) * 2);
					}
				}

				// Add Action Tile
				if (tileType.getSelectedItem().toString().equals("Action Tile")) {
					if (tile.isSelected() && tile.isVisible()) {
						tile.setText("A");
						tile.setBackground(Color.magenta);
						//Tile
						//currentController.createActionTile((i / numberOfRows) * 2, (i % numberOfRows) * 2);
					}
				}
				i++;
			}

			// Reset Tiles
			// Normal Tile
			if (tileType.getSelectedItem().toString().equals("Regular Tile")) {
				hideDisabledTiles();
			}

		}
		

		// Reset Connections
		if (designState == DesignState.ADD_CONNECTION) {
			hideDisabledConnections();

			int i = 0;
			for (ConnectionUI conn : connectionButtons) {
				if (conn.isVisible()) {
					if ((conn.getUIY() % 2) == 0) {
						//Tiles
						//currentController.connectTiles(conn.getUIX(), conn.getUIY(), true);
					}
					else {
						//Tiles
						//currentController.connectTiles(conn.getUIX(), conn.getUIY(), false);
					}
				}
				i++;
			}
		}
		
		if(designState == DesignState.CHANGE_NUMBER_OF_PLAYERS) {
			currentController.changeNumberOfPlayers(Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())));
		}

		// Change colors for connections
		/*for (ConnectionUI conn : connectionButtons) {
			if (conn.getLifeState() == ConnectionUI.LifeState.NOTEXIST)
				conn.setBackground(null);
			else
				conn.setBackground(new java.awt.Color(0, 0, 0));
		}*/

		if (designState == DesignState.ADD_TILE) {
			// Add Action Tile
			if (tileType.getSelectedItem().toString().equals("Action Tile")
					|| tileType.getSelectedItem().toString().equals("Win Tile")) {
				for (JToggleButton tile : tilesButtons) {
					if (tile.isVisible() && tile.isSelected()) {
						tile.setSelected(false);
					}
				}
			}
		}

		// Reset states
		disableChanges();
		maskButtons(ALLBTN);
		designState = DesignState.NONE;
		
		// Unselect all buttons
		selectPositionButton.setSelected(false);
		addTileButton.setSelected(false);
		removeTileButton.setSelected(false);
		removeConnectionButton.setSelected(false);
		addConnectionButton.setSelected(false);
	}
	
	//Tile clicked
	private void tileActionPerformed(java.awt.event.ActionEvent evt) {
		TileUI tile = (TileUI) evt.getSource();

		//Cannot select 2 at a time
		if (designState == DesignState.SELECT_STARTING_POSITION ||
				designState == DesignState.ADD_TILE && (tileType.getSelectedItem().toString().equals("Win Tile"))) {
			tilesButtons.parallelStream().filter(s -> s.isSelected() && s != tile).forEach(s -> s.setSelected(false));
		}
		
		//TODO : Action tile - MessageBox
		
		//If cannot click tile
		else {
			tile.setSelected(false);
			tile.setBorderPainted(false);
			tile.setFocusPainted(false);
		}
	}

	//Connection clicked
	private void connectionActionPerformed(java.awt.event.ActionEvent evt) {
		ConnectionUI conn = (ConnectionUI) evt.getSource();
		 
		if (designState == DesignState.ADD_CONNECTION) {
			//If already exists - do nothing
			if (conn.getLifeState() == ConnectionUI.LifeState.EXIST) {
				conn.setSelected(false);
				conn.setBorderPainted(false);
				conn.setFocusPainted(false);
			}
			//Enable changes
			else {
				enableChanges();
			}
		}
		else if (designState == DesignState.REMOVE_CONNECTION) {
			enableChanges();
		}
		//Does not allow connections to be clicked
		else {
			conn.setSelected(false);
			conn.setBorderPainted(false);
			conn.setFocusPainted(false);
		}
	}

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO : Check if game is playable to change game state
		TileOApplication.save();
	}

	private void nbOfPlayersChanged() {
		//TODO : MessageBox - game will be reset
		String nbOfPlayersChosen = String.valueOf(nbOfPlayers.getSelectedItem());
		if(game.numberOfPlayers() != Integer.valueOf(nbOfPlayersChosen))
			enableChanges();
		else
			disableChanges();

		//Only when apply changes
		/*if (nbOfPlayers.getSelectedItem() == "4") { // Setting value of combo
													// box for choosing a player
													// to defined number of
													// players
			chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
		} else if (nbOfPlayers.getSelectedItem() == "3") {
			chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
		} else {
			chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
		}*/
	}

	private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
		//TODO : MessageBox - any unsaved changes will be lost
		//then show available games OR create new board
		
		TileOApplication.load();
	}

	private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
		new MainPage().setVisible(true);
		dispose();
	}

	private void horizontalLengthActionPerformed(java.awt.event.ActionEvent evt) {
		designState = DesignState.BOARD_SIZE;
		
		if(Integer.valueOf(String.valueOf(horizontalLength.getSelectedItem())) != numberOfRows)
			enableChanges();
		else
			disableChanges();
	}

	private void verticalLengthActionPerformed(java.awt.event.ActionEvent evt) {
		designState = DesignState.BOARD_SIZE;
		
		if(Integer.valueOf(String.valueOf(verticalLength.getSelectedItem())) != numberOfCols)
			enableChanges();
		else
			disableChanges();
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
		tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST).forEach(s -> s.showUI());
	}

	private void hideDisabledTiles() {
		tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST).forEach(s -> s.hideUI());
	}

	private void showDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST).forEach(s -> s.showUI());
	}

	private void hideDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST).forEach(s -> s.hideUI());
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