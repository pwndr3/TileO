package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.*;
import ca.mcgill.ecse223.tileo.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class TileODesignUI extends JFrame {
	private DesignController currentController;

	private LinkedList<TileUI> tilesButtons;
	private LinkedList<ConnectionUI> connectionButtons;
	private JPanel tilesPanel;

	private enum DesignState {
		BOARD_SIZE, CHANGE_NUMBER_OF_PLAYERS, SELECT_STARTING_POSITION, ADD_TILE, REMOVE_TILE, ADD_CONNECTION, REMOVE_CONNECTION, CARDS, NONE
	}

	private DesignState designState = DesignState.NONE;

	public int numberOfRows = 0;
	public int numberOfCols = 0;

	private int nbOfCardsLeft = 32;
	int[] cardsCounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public TileODesignUI(Game aGame) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// Too bad
		}

		game = aGame;
		currentController = new DesignController(game, this);

		// Init layout
		initComponents();
		disableChanges();
		designState = DesignState.NONE;
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
	private static final int CHOSENPLAYER = 1024;
	private static final int GENERATEBTN = 2048;
	private static final int ALLBTN = 4095;

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
		if((param & GENERATEBTN) == GENERATEBTN) {
			generateButton.setEnabled(true);
			generateButton.setForeground(new java.awt.Color(255,255,255));
		} else {
			generateButton.setEnabled(false);
			generateButton.setForeground(new java.awt.Color(200,200,200));
		}
		
		//
		nbRollDieCard.setEnabled((param & CARDS) == CARDS);
		nbRemoveConnectionCard.setEnabled((param & CARDS) == CARDS);
		nbTeleportCard.setEnabled((param & CARDS) == CARDS);
		nbLoseTurnCard.setEnabled((param & CARDS) == CARDS);
		nbConnectTilesCard.setEnabled((param & CARDS) == CARDS);
		nbAdditionalMoveCard.setEnabled((param & CARDS) == CARDS);
	    nbSendBackCard.setEnabled((param & CARDS) == CARDS);
	    nbShowActionTilesCard.setEnabled((param & CARDS) == CARDS);
	    nbMovePlayerCard.setEnabled((param & CARDS) == CARDS);
	    nbMoveWinTileCard.setEnabled((param & CARDS) == CARDS);
	    nbNextRollsOneCard.setEnabled((param & CARDS) == CARDS);
	    nbInactivityPeriodCard.setEnabled((param & CARDS) == CARDS);
		//
		if((param & SAVEBTN) == SAVEBTN) {
			saveButton.setEnabled(true);
			saveButton.setForeground(new java.awt.Color(0,0,0));
		} else {
			saveButton.setEnabled(false);
			saveButton.setForeground(new java.awt.Color(200,200,200));
		}
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
		
		try {
			if (Integer.valueOf(nbSendBackCard.getText()) < 0)
				nbSendBackCard.setText(String.valueOf(cardsCounts[5]));
		} catch(NumberFormatException e) {
			nbSendBackCard.setText(String.valueOf(cardsCounts[5]));
		}
		
		try {
			if (Integer.valueOf(nbAdditionalMoveCard.getText()) < 0)
				nbAdditionalMoveCard.setText(String.valueOf(cardsCounts[6]));
		} catch(NumberFormatException e) {
			nbAdditionalMoveCard.setText(String.valueOf(cardsCounts[6]));
		}
		
		try {
			if (Integer.valueOf(nbNextRollsOneCard.getText()) < 0)
				nbNextRollsOneCard.setText(String.valueOf(cardsCounts[7]));
		} catch(NumberFormatException e) {
			nbNextRollsOneCard.setText(String.valueOf(cardsCounts[7]));
		}
		
		try {
			if (Integer.valueOf(nbShowActionTilesCard.getText()) < 0)
				nbShowActionTilesCard.setText(String.valueOf(cardsCounts[8]));
		} catch(NumberFormatException e) {
			nbShowActionTilesCard.setText(String.valueOf(cardsCounts[8]));
		}
		
		try {
			if (Integer.valueOf(nbMoveWinTileCard.getText()) < 0)
				nbMoveWinTileCard.setText(String.valueOf(cardsCounts[9]));
		} catch(NumberFormatException e) {
			nbMoveWinTileCard.setText(String.valueOf(cardsCounts[9]));
		}
		
		try {
			if (Integer.valueOf(nbMovePlayerCard.getText()) < 0)
				nbMovePlayerCard.setText(String.valueOf(cardsCounts[10]));
		} catch(NumberFormatException e) {
			nbMovePlayerCard.setText(String.valueOf(cardsCounts[10]));
		}
		
		try {
			if (Integer.valueOf(nbInactivityPeriodCard.getText()) < 0)
				nbInactivityPeriodCard.setText(String.valueOf(cardsCounts[11]));
		} catch(NumberFormatException e) {
			nbInactivityPeriodCard.setText(String.valueOf(cardsCounts[11]));
		}
		
		cardsCounts[0] = Integer.valueOf(nbRollDieCard.getText());
		cardsCounts[1] = Integer.valueOf(nbRemoveConnectionCard.getText());
		cardsCounts[2] = Integer.valueOf(nbTeleportCard.getText());
		cardsCounts[3] = Integer.valueOf(nbLoseTurnCard.getText());
		cardsCounts[4] = Integer.valueOf(nbConnectTilesCard.getText());
		cardsCounts[5] = Integer.valueOf(nbSendBackCard.getText());
		cardsCounts[6] = Integer.valueOf(nbAdditionalMoveCard.getText());
		cardsCounts[7] = Integer.valueOf(nbNextRollsOneCard.getText());
		cardsCounts[8] = Integer.valueOf(nbShowActionTilesCard.getText());
		cardsCounts[9] = Integer.valueOf(nbMoveWinTileCard.getText());
		cardsCounts[10] = Integer.valueOf(nbMovePlayerCard.getText());
		cardsCounts[11] = Integer.valueOf(nbInactivityPeriodCard.getText());

		// If number has changed
		if (nbOfCardsLeft != (32 - cardsCounts[0] - cardsCounts[1] - cardsCounts[2] - cardsCounts[3] - cardsCounts[4]
				- cardsCounts[5] - cardsCounts[6] - cardsCounts[7] - cardsCounts[8] - cardsCounts[9] - cardsCounts[10] - cardsCounts[11])) {
			nbOfCardsLeft = (32 - cardsCounts[0] - cardsCounts[1] - cardsCounts[2] - cardsCounts[3] - cardsCounts[4]
					- cardsCounts[5] - cardsCounts[6] - cardsCounts[7] - cardsCounts[8] - cardsCounts[9] - cardsCounts[10] - cardsCounts[11]);

			if (nbOfCardsLeft < 0) {
				cardsLeft.setForeground(new java.awt.Color(255, 0, 0));
				disableChanges();
				maskButtons(ALLBTN);
				//TODO : Ask game cards to see if it has changed
			} else {
				cardsLeft.setForeground(new java.awt.Color(0, 0, 0));
				enableChanges();
				maskButtons(CARDS);
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
				c.gridx = col;
				c.gridy = row; 

				// Tile
				if (row % 2 == 0 && col % 2 == 0) {
					// UI
					TileUI tile = new TileUI();
					tile.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							tileActionPerformed(evt);
						}
					});
					tile.setMargin(new Insets(0, 0, 0, 0));
					tile.setBorder(null);
					tilesButtons.add(tile);

					tilesPanel.add(tile, c);
					tile.setPosition(row / 2, col / 2);

					// Game
					currentController.createNormalTile(row / 2, col / 2);
				}

				// Vertical connection
				else if (row % 2 == 1 && col % 2 == 0) {
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

				// Horizontal connection
				else if (row % 2 == 0 && col % 2 == 1) {
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

				// Gap
				else {
					JPanel gap = new JPanel();
					gap.setPreferredSize(new java.awt.Dimension(10, 10));
					tilesPanel.add(gap, c);
				}
			}
		}
	}
	
	private void setWidgets() {
		//Number of players
		int nbPlayers = game.numberOfPlayers();
		nbOfPlayers.setSelectedIndex(nbPlayers-2);
		
		if (nbOfPlayers.getSelectedItem() == "4") {
			 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" })); 
		 } 
		 
		 else if (nbOfPlayers.getSelectedItem() == "3") { 
			 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
		 } 
		 
		 else { 
			 chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" })); 
		 }
		 
		//Cards
		cardsCounts = new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
		game.getDeck().getCards().parallelStream().forEach(s -> {
			if(s instanceof RollDieActionCard)
				cardsCounts[0]++;
			if(s instanceof RemoveConnectionActionCard)
				cardsCounts[1]++;
			if(s instanceof TeleportActionCard)
				cardsCounts[2]++;
			if(s instanceof LoseTurnActionCard)
				cardsCounts[3]++;
			if(s instanceof ConnectTilesActionCard)
				cardsCounts[4]++;
			if(s instanceof SendBackToStartActionCard)
				cardsCounts[5]++;
			if(s instanceof AdditionalMoveActionCard)
				cardsCounts[6]++;
			if(s instanceof NextRollsOneActionCard)
				cardsCounts[7]++;
			if(s instanceof ShowActionTilesActionCard)
				cardsCounts[8]++;
			if(s instanceof MoveWinTileActionCard)
				cardsCounts[9]++;
			if(s instanceof MovePlayerActionCard)
				cardsCounts[10]++;
			if(s instanceof InactivityPeriodActionCard)
				cardsCounts[11]++;
		});
		
		nbRollDieCard.setText(String.valueOf(cardsCounts[0]));
		nbRemoveConnectionCard.setText(String.valueOf(cardsCounts[1])); 
		nbTeleportCard.setText(String.valueOf(cardsCounts[2]));
		nbLoseTurnCard.setText(String.valueOf(cardsCounts[3]));
		nbConnectTilesCard.setText(String.valueOf(cardsCounts[4]));
		nbSendBackCard.setText(String.valueOf(cardsCounts[5])); 
		nbAdditionalMoveCard.setText(String.valueOf(cardsCounts[6]));
		nbNextRollsOneCard.setText(String.valueOf(cardsCounts[7]));
		nbShowActionTilesCard.setText(String.valueOf(cardsCounts[8]));
		nbMoveWinTileCard.setText(String.valueOf(cardsCounts[9])); 
		nbMovePlayerCard.setText(String.valueOf(cardsCounts[10]));
		nbInactivityPeriodCard.setText(String.valueOf(cardsCounts[11])); 
		
		cardsLeft.setText(String.valueOf(32 - cardsCounts[0] - cardsCounts[1] - cardsCounts[2] - cardsCounts[3] - cardsCounts[4]
				- cardsCounts[5] - cardsCounts[6] - cardsCounts[7] - cardsCounts[8] - cardsCounts[9] - cardsCounts[10] - cardsCounts[11]));
		
		//Board size
		horizontalLength.setSelectedIndex(numberOfCols - 3);
		verticalLength.setSelectedIndex(numberOfRows - 3);
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
	public void setupBoard(boolean forceNewGame) {
		numberOfRows = numberOfCols = 0;
		
		if(tilesButtons == null) {
			tilesButtons = new LinkedList<TileUI>();
		} else {
			tilesButtons.clear();
		}
		
		if(connectionButtons == null) {
			connectionButtons = new LinkedList<ConnectionUI>();
		} else {
			connectionButtons.clear();
		}

		if(tilesPanel == null) {
			tilesPanel = new javax.swing.JPanel();
			tilesPanel.setPreferredSize(new java.awt.Dimension(1130, 530));
			//TODO : MouseClick changeNumberOfCards
		} else {
			tilesPanel.removeAll();
			tilesPanel.revalidate();
		}

		//Delete current game then create new one
		if (game == null || forceNewGame) {
			game = currentController.initGame(Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())));
		}
		
		if(forceNewGame)
			changeBoardSize(Integer.valueOf(verticalLength.getSelectedItem().toString()),
				Integer.valueOf(horizontalLength.getSelectedItem().toString()));
		else
			setTiles();
		
		setWidgets();
		
		repaint();
		revalidate();
	}
	
	private void setTiles() {
		// Find number of rows and columns
		int maxRow = 0;
		int maxCol = 0;
		
		for(Tile tile : game.getTiles()) {
			if(tile.getX() > maxRow)
				maxRow = tile.getX();
			if(tile.getY() > maxCol)
				maxCol = tile.getY();
		}
		
		numberOfRows = ++maxRow;
		numberOfCols = ++maxCol;

		tilesButtons.clear();
		connectionButtons.clear();
		tilesPanel.removeAll();
		
		// Create grids
		tilesPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Create buttons and put in linked list
		for (int row = 0; row < numberOfRows + (numberOfRows - 1); row++) {
			for (int col = 0; col < numberOfCols + (numberOfCols - 1); col++) {
				c.fill = GridBagConstraints.BOTH;
				c.gridx = col; // column
				c.gridy = row; // row

				// Tile
				if (row % 2 == 0 && col % 2 == 0) {
					// UI
					TileUI tile = new TileUI();
					tile.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							tileActionPerformed(evt);
						}
					});
					tile.setMargin(new Insets(0, 0, 0, 0));
					tile.setBorder(null);
					
					if(game.getTileFromXY(row/2, col/2) != null) {
						tile.setLifeState(TileUI.LifeState.EXIST);
						tile.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(java.awt.event.ActionEvent evt) {
								tileActionPerformed(evt);
							}
						});
					} else {
						tile.setLifeState(TileUI.LifeState.NOTEXIST);
					}
					
					tilesButtons.add(tile);

					tilesPanel.add(tile, c);
					tile.setPosition(row / 2, col / 2);
				}

				// Vertical connection
				else if (row % 2 == 1 && col % 2 == 0) {
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

				// Horizontal connection
				else if (row % 2 == 0 && col % 2 == 1) {
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

				// Gap
				else {
					JPanel gap = new JPanel();
					gap.setPreferredSize(new java.awt.Dimension(10, 10));
					tilesPanel.add(gap, c);
				}
			}
		}
		
		//Remove connections
		List<TileUI> tiles = tilesButtons.stream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST).collect(Collectors.toList());
			tiles.forEach(s -> {
			int connX = s.getUIX()*2;
			int connY = s.getUIY()*2;
			
			//Hide nearest connections
			connectionButtons.stream().filter(t -> (t.getUIX()==connX && Math.abs(t.getUIY()-connY)==1) ||
					(t.getUIY()==connY && Math.abs(t.getUIX()-connX)==1)).forEach(t -> {
						t.setState(ConnectionUI.State.HIDE);
						t.setLifeState(ConnectionUI.LifeState.NOTEXIST);
					});
		});
			
		//Set connections
		game.getConnections().stream().forEach(s -> {
			Tile tile1 = s.getTile(0);
			Tile tile2 = s.getTile(1);
			
			connectionButtons.stream().filter(t -> t.getState() == ConnectionUI.State.SHOW).forEach(t -> {
				//Horizontal
				if(tile1.getX() == tile2.getX()) {
					if(t.getUIX() == tile1.getX()*2) {
						int farRight = (tile1.getY() > tile2.getY()) ? tile1.getY()*2 : tile2.getY()*2;
						
						if((farRight - t.getUIY()) == 1) {
							t.setState(ConnectionUI.State.SHOW);
							t.setLifeState(ConnectionUI.LifeState.EXIST);
							t.setVisible(true);
						}
					}
				}
			
				//Vertical
				if(tile1.getY() == tile2.getY()) {
					if(t.getUIY() == tile1.getY()*2) {
						int bottom = (tile1.getX() > tile2.getX()) ? tile1.getX()*2 : tile2.getX()*2;
						
						if((bottom - t.getUIX()) == 1) {
							t.setState(ConnectionUI.State.SHOW);
							t.setLifeState(ConnectionUI.LifeState.EXIST);
							t.setVisible(true);
						}
					}
				}
			});
		});
		
		//Action & win tiles
		for(Tile tile : game.getTiles()) {
			TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.getUIX()==tile.getX() && s.getUIY()==tile.getY()).findAny().orElse(null);
			if(tile instanceof ActionTile) {
				setActionTileIcon(tileGUI, ((ActionTile)tile).getInactivityPeriod());
			} else if(tile instanceof WinTile) {
				try {
					tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/win.png"))));
				} catch (IOException e) {
					
				}
			} else {
				tileGUI.resetUI();
			}
 		}
		
		//Starting positions
		for(int i = 0; i < game.numberOfPlayers(); i++) {
			if(!game.getPlayer(i).hasStartingTile())
				continue;
			
			Tile tile = game.getPlayer(i).getStartingTile();
			
			TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.getUIX()==tile.getX() && s.getUIY()==tile.getY()).findAny().orElse(null);
			if(tileGUI != null) {
				try {
					tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/"+(i+1)+".png"))));
				} catch (IOException e) {
					
				}
			}
		}
		
		update();
	}
	
	private void setActionTileIcon(TileUI tileUI, int inactivityPeriod) {
		try {
			tileUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/actiontiles/"+inactivityPeriod+".png"))));
			
		} catch (IOException e) {
		}
		
		tileUI.setState(TileUI.State.ACTION);
	}

	private void initComponents() {
		jLabel1 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        verticalLength = new javax.swing.JComboBox<>();
        horizontalLength = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nbOfPlayers = new javax.swing.JComboBox<>();
        chosenPlayer = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        removeTileButton = new javax.swing.JToggleButton();
        jLabel11 = new javax.swing.JLabel();
        tileType = new javax.swing.JComboBox<>();
        addTileButton = new javax.swing.JToggleButton();
        selectPositionButton = new javax.swing.JToggleButton();
        addConnectionButton = new javax.swing.JToggleButton();
        removeConnectionButton = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        nbRollDieCard = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        nbRemoveConnectionCard = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        nbTeleportCard = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        nbLoseTurnCard = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        nbConnectTilesCard = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        nbSendBackCard = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        nbAdditionalMoveCard = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        nbNextRollsOneCard = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        nbShowActionTilesCard = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        nbMoveWinTileCard = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        nbMovePlayerCard = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        nbInactivityPeriodCard = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        cardsLeft = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        applyChangesButton = new javax.swing.JToggleButton();
        generateButton = new javax.swing.JButton();

        //jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        //jLabel1.setText("Design Mode");
        String p = getClass().getResource("/icons/test.png").toString();
        jLabel1.setText("<html><img src=\""+p+"\"></html>");
        //jLabel1.setVisible(false);
        
        backButton.setBackground(new java.awt.Color(255, 0, 0));
        backButton.setFont(new java.awt.Font("Lucida Grande", 2, 13)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(e -> {
			if(designState != DesignState.NONE) {
				if(new PopUpManager(this).askYesOrNo("Any unsaved changes will be lost. Continue?") == 0) {
				new MainPage().setVisible(true);
				dispose();
				}
			} else {
				new MainPage().setVisible(true);
				dispose();
			}
		});

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 13)); 
        jLabel2.setText("Board :");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 13)); 
        jLabel3.setText("Player :");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel4.setText("Tile :");

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 13)); 
        jLabel5.setText("Connection :");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel6.setText("Deck :");

        jLabel7.setLabelFor(verticalLength);
        jLabel7.setText("Enter dimensions");

        verticalLength.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
        verticalLength.setSelectedIndex(8 - 3);
        verticalLength.addActionListener(e -> {
			if (Integer.valueOf(String.valueOf(verticalLength.getSelectedItem())) != numberOfRows) {
				if(designState == DesignState.NONE)
					new PopUpManager(this).acknowledgeMessage("If you apply changes, the whole board will be reset.");
				enableChanges();
				designState = DesignState.BOARD_SIZE;
				maskButtons(BOARDSIZE);
			}
			else {
				disableChanges();
				maskButtons(ALLBTN);
			}
		});

        horizontalLength.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
        horizontalLength.setSelectedIndex(8 - 3);
        horizontalLength.addActionListener(e -> {
			if (Integer.valueOf(String.valueOf(horizontalLength.getSelectedItem())) != numberOfCols) {
				if(designState == DesignState.NONE)
					new PopUpManager(this).acknowledgeMessage("If you apply changes, the whole board will be reset.");
				enableChanges();
				designState = DesignState.BOARD_SIZE;
				maskButtons(BOARDSIZE);
			}
			else {
				disableChanges();
				maskButtons(ALLBTN);
			}
		});

        jLabel8.setFont(new java.awt.Font("Lucida Sans Typewriter", 1, 13)); // NOI18N
        jLabel8.setText("x");

        jLabel9.setText("Enter number of players");

        nbOfPlayers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4" }));
        nbOfPlayers.addActionListener(e -> {
			if (Integer.valueOf(String.valueOf(nbOfPlayers.getSelectedItem())) != game.numberOfPlayers()) {
				nbOfPlayersChanged();
			} else {
				designState = DesignState.NONE;
				resetUI();
			}
		});
        
        chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));

        jLabel10.setText("Select player:");

        removeTileButton.setForeground(new java.awt.Color(255,255,255));
        removeTileButton.setBackground(new java.awt.Color(51, 102, 255));
        removeTileButton.setText("Remove Tile");
        removeTileButton.addActionListener(e -> {
			if (removeTileButton.isSelected()) {
				designState = DesignState.REMOVE_TILE;
				tileType.setEnabled(false);

				backupLists();
				maskButtons(REMOVETILEBTN);
			} else {
				tileType.setEnabled(true);
				designState = DesignState.NONE;
				resetUI();
			}
		});

        jLabel11.setText("Change tile type");

        tileType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Tile", "Action Tile", "Win Tile" }));

        addTileButton.setForeground(new java.awt.Color(255,255,255));
        addTileButton.setBackground(new java.awt.Color(51, 102, 255));
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
				designState = DesignState.NONE;
				resetUI();
			}
		});

		selectPositionButton.setForeground(new java.awt.Color(255,255,255));
        selectPositionButton.setBackground(new java.awt.Color(51, 102, 255));
        selectPositionButton.setText("Select Start Position");
        selectPositionButton.addActionListener(e -> {
			if (selectPositionButton.isSelected()) {
				designState = DesignState.SELECT_STARTING_POSITION;

				backupLists();
				maskButtons(STARTINGBTN);
			} else {
				designState = DesignState.NONE;
				resetUI();
			}
		});

        addConnectionButton.setForeground(new java.awt.Color(255,255,255));
        addConnectionButton.setBackground(new java.awt.Color(51, 102, 255));
        addConnectionButton.setText("Add Connection");
        addConnectionButton.addActionListener(e -> {
			if (addConnectionButton.isSelected()) {
				designState = DesignState.ADD_CONNECTION;

				backupLists();
				maskButtons(ADDCONNBTN);

				showDisabledConnections();
			} else {
				designState = DesignState.NONE;
				resetUI();
			}
		});

        removeConnectionButton.setForeground(new java.awt.Color(255,255,255));
        removeConnectionButton.setBackground(new java.awt.Color(51, 102, 255));
        removeConnectionButton.setText("Remove Connection");
        removeConnectionButton.addActionListener(e -> {
			if (removeConnectionButton.isSelected()) {
				designState = DesignState.REMOVE_CONNECTION;

				backupLists();
				maskButtons(REMOVECONNBTN);

				// Change colors for connections
				connectionButtons.parallelStream().forEach(s -> s.setBackground(null));
			} else {
				designState = DesignState.NONE;
				resetUI();
			}
		});

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel12.setLabelFor(nbRollDieCard);
        jLabel12.setText("Roll Die");
        jLabel12.setToolTipText("<html>\nThis card allows the player to  <br />roll the die a second time.");
        nbRollDieCard.setToolTipText("<html>\nThis card allows the player to  <br />roll the die a second time.");
        nbRollDieCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
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

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel13.setLabelFor(nbRemoveConnectionCard);
        jLabel13.setText("Remove Connection");
        jLabel13.setToolTipText("<html>\nThis card allows the player to <br />remove a connection currently on the board.");
        nbRemoveConnectionCard.setToolTipText("<html>\nThis card allows the player to <br />remove a connection currently on the board.");
        nbRemoveConnectionCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
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

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel14.setLabelFor(nbTeleportCard);
        jLabel14.setText("Teleport");
        jLabel14.setToolTipText("<html>\nThis card allows the player to teleport <br /> himself on any tile on the board.");
        nbTeleportCard.setToolTipText("<html>\nThis card allows the player to teleport <br /> himself on any tile on the board.");
        nbTeleportCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
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

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel15.setLabelFor(nbLoseTurnCard);
        jLabel15.setText("Lose Turn");
        jLabel15.setToolTipText("<html>\nThis card makes the player <br /> lose his next turn.");
        nbLoseTurnCard.setToolTipText("<html>\nThis card makes the player <br /> lose his next turn.");
        nbLoseTurnCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
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

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel16.setLabelFor(nbConnectTilesCard);
        jLabel16.setText("Connect Tiles");
        jLabel16.setToolTipText("<html>\nThis card allows the player to add <br />a connection anywhere on the board.");
        nbConnectTilesCard.setToolTipText("<html>\nThis card allows the player to add <br />a connection anywhere on the board.");
        nbConnectTilesCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
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

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel17.setLabelFor(nbSendBackCard);
        jLabel17.setText("Send Back To Start");
        jLabel17.setToolTipText("<html>\nThis card allows the player to send another <br />player of his choice to his starting position.");
        nbSendBackCard.setToolTipText("<html>\nThis card allows the player to send another <br />player of his choice to his starting position.");
        nbSendBackCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbSendBackCard.setText("0");
        nbSendBackCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbSendBackCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel18.setLabelFor(nbAdditionalMoveCard);
        jLabel18.setText("Additional Move");
        jLabel18.setToolTipText("<html>\nThis card allows the player to make an additional move. <br /> For this move, the player chooses a number between 1 and 6 <br /> and then moves exactly that number of tiles on the board.");
        nbAdditionalMoveCard.setToolTipText("<html>\nThis card allows the player to make an additional move. <br /> For this move, the player chooses a number between 1 and 6 <br /> and then moves exactly that number of tiles on the board.");
        nbAdditionalMoveCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbAdditionalMoveCard.setText("0");
        nbAdditionalMoveCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbAdditionalMoveCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel19.setLabelFor(nbNextRollsOneCard);
        jLabel19.setText("Next Rolls One");
        jLabel19.setToolTipText("<html>\nThis card allows the player to make <br /> the next player roll a one.");
        nbNextRollsOneCard.setToolTipText("<html>\nThis card allows the player to make <br /> the next player roll a one.");
        nbNextRollsOneCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbNextRollsOneCard.setText("0");
        nbNextRollsOneCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbNextRollsOneCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel20.setLabelFor(nbShowActionTilesCard);
        jLabel20.setText("Show Action Tiles");
        jLabel20.setToolTipText("<html>\nThis card allows the player to see all the action tiles <br />present on the board for a duration of 5 seconds.");
        nbShowActionTilesCard.setToolTipText("<html>\nThis card allows the player to see all the action tiles <br />present on the board for a duration of 5 seconds.");
        nbShowActionTilesCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbShowActionTilesCard.setText("0");
        nbShowActionTilesCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbShowActionTilesCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel21.setLabelFor(nbMoveWinTileCard);
        jLabel21.setText("Move Win Tile");
        jLabel21.setToolTipText("<html>\nThis card allos the player to move the <br />Win Tile anywhere on the board, except <br />for his current position.");
        nbMoveWinTileCard.setToolTipText("<html>\nThis card allos the player to move the <br />Win Tile anywhere on the board, except <br />for his current position.");
        nbMoveWinTileCard.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        nbMoveWinTileCard.setText("0");
        nbMoveWinTileCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbMoveWinTileCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        jLabel22.setLabelFor(nbMovePlayerCard);
        jLabel22.setText("Move Player");
        jLabel22.setToolTipText("<html>\nThis card allows the player to move <br />one of his opponent to an arbitrary <br />tile of his choice.");
        nbMovePlayerCard.setToolTipText("<html>\nThis card allows the player to move <br />one of his opponent to an arbitrary <br />tile of his choice.");
        nbMovePlayerCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbMovePlayerCard.setText("0");
        nbMovePlayerCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbMovePlayerCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});
        
        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 13)); 
        jLabel24.setLabelFor(nbInactivityPeriodCard);
        jLabel24.setText("Inactivity Period");
        jLabel24.setToolTipText("<html>\nThis card allows the player to assign new inactivity periods <br /> to all action tiles in the game. The new inactivity periods are randomly chosen from 0, 1, 2, or 3. <br />  The players are not informed about the new inactivity periods. ");

        nbInactivityPeriodCard.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        nbInactivityPeriodCard.setText("0");
        nbInactivityPeriodCard.addActionListener(e -> {
			changeNumberOfCardsLeft();
		});
        nbInactivityPeriodCard.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {

			}

			public void focusLost(FocusEvent e) {
				changeNumberOfCardsLeft();
			}
		});

        jLabel23.setText("Cards left : ");

        cardsLeft.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        cardsLeft.setText("32");

        applyChangesButton.setBackground(new java.awt.Color(0, 204, 0));
		applyChangesButton.setFont(new java.awt.Font("Lucida Grande", 1, 13));
		applyChangesButton.setForeground(new java.awt.Color(0, 0, 0));
        applyChangesButton.setText("Apply Changes");
        disableChanges();
        applyChangesButton.addActionListener(e -> {
 			update();
			designState = DesignState.NONE;
		});
        
        saveButton.setBackground(new java.awt.Color(255, 204, 0));
		saveButton.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		saveButton.setForeground(new java.awt.Color(0, 0, 0));
		saveButton.setText("Save");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

		generateButton.setBackground(Color.decode("#681072"));
		generateButton.setForeground(new java.awt.Color(255, 255, 255));
		generateButton.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        generateButton.setText("Generate");
        generateButton.addActionListener(e -> {
			if(new PopUpManager(this).askYesOrNo("This will generate a random board with size "+numberOfRows+"x"+numberOfCols+" and "+game.numberOfPlayers()+" players. Continue?") == 0)
        		currentController.generateRandomBoard();
		});
        
        // Window
 		setResizable(false);

 		// Board
 		setupBoard(game == null);
     		
 		maskButtons(ALLBTN);
     	//

 		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel23)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cardsLeft)))
                            .addGap(31, 31, 31)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(addConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(removeConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel7))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nbOfPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(92, 92, 92)
                                            .addComponent(jLabel10)
                                            .addGap(18, 18, 18)
                                            .addComponent(chosenPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(24, 24, 24)
                                            .addComponent(selectPositionButton))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(verticalLength, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(8, 8, 8)
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(horizontalLength, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(45, 45, 45)
                                            .addComponent(jLabel13))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(95, 95, 95)
                                            .addComponent(nbRemoveConnectionCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(43, 43, 43)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel14))
                                    .addGap(55, 55, 55)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(9, 9, 9)))
                                    .addGap(68, 68, 68)
                                    .addComponent(nbConnectTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(70, 70, 70)
                                    .addComponent(nbSendBackCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addComponent(nbAdditionalMoveCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addComponent(nbNextRollsOneCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(28, 28, 28)
                                                .addComponent(jLabel19)))
                                        .addGap(29, 29, 29)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(nbShowActionTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(38, 38, 38)))
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(nbMoveWinTileCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(26, 26, 26)))
                                        .addGap(30, 30, 30)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(nbMovePlayerCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(74, 74, 74)
                                                .addComponent(nbInactivityPeriodCard, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel24)))
                                        .addGap(7, 7, 7)))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(31, 31, 31)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(addTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(34, 34, 34)
                            .addComponent(removeTileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(100, 100, 100)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)))
                        .addComponent(applyChangesButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(27, 27, 27))
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(backButton)
                            .addGap(400, 400, 400)
                            .addComponent(jLabel1)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(tilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(backButton))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jLabel7)
                                .addComponent(verticalLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(horizontalLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8))
                            .addGap(35, 35, 35)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel9)
                                .addComponent(nbOfPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(chosenPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)
                                .addComponent(selectPositionButton))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(jLabel11)
                                .addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addTileButton)
                                .addComponent(removeTileButton))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(addConnectionButton)
                                .addComponent(removeConnectionButton))
                            .addGap(19, 19, 19)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel23)
                                        .addComponent(cardsLeft)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nbRemoveConnectionCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel15))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nbConnectTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nbSendBackCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nbNextRollsOneCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(nbAdditionalMoveCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel19))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel24)
                                                .addComponent(jLabel22))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nbMoveWinTileCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(nbMovePlayerCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(nbInactivityPeriodCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addComponent(nbShowActionTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(generateButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(saveButton)
                            .addGap(41, 41, 41)
                            .addComponent(applyChangesButton)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(5, Short.MAX_VALUE))
            );

	
        setUndecorated(true);
        pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        setLocationRelativeTo(null);
        setLocation(new Double(width/2).intValue()-getWidth()/2, new Double(height/2).intValue()-getHeight()/2);
        
        
	}

	public void update() {
		switch (designState) {
		/*
		 * IF BOARD SIZE HAS CHANGED
		 */
		case BOARD_SIZE:
			setupBoard(true);
			break;

		/*
		 * IF STARTING POSITION OF PLAYER
		 */
		case SELECT_STARTING_POSITION:
			int playerNumber = Integer.valueOf(chosenPlayer.getSelectedItem().toString());

			TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.isSelected()).findAny().orElse(null);
			Tile startingTile = game.getTileFromXY(tileGUI.getUIX(), tileGUI.getUIY());

			int oldX = -1, oldY = -1;
			
			//Check if player has already a tile ...
			if(game.getPlayer(playerNumber-1).hasStartingTile()) {
				Tile prevTile = game.getPlayer(playerNumber-1).getStartingTile();
				oldX = prevTile.getX();
				oldY = prevTile.getY();
			}
			
			try {
				if (!currentController.startingPosition(startingTile, playerNumber-1)) {
					// Error - couldn't set starting position
				} else {
					//...if so, replace color with null
					if(oldX != -1 && oldY != -1) {
						for(TileUI tile : tilesButtons) {
							if(tile.getUIX() == oldX && tile.getUIY() == oldY)
								tile.resetUI();
						}
					}
					
					tileGUI.resetUI();
					switch (playerNumber) {
					case 1:
						try {
							tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/1.png"))));
						} catch (IOException e) {
							
						}
						break;

					case 2:
						try {
							tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/2.png"))));
						} catch (IOException e) {
							
						}
						break;

					case 3:
						try {
							tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/3.png"))));
						} catch (IOException e) {
							
						}
						break;

					case 4:
						try {
							tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/4.png"))));
						} catch (IOException e) {
							
						}
						break;
					}
				}
			} catch (InvalidInputException e) {
				new PopUpManager(this).acknowledgeMessage(e.getMessage());
			}

			break;

		/*
		 * IF ADDING A TILE
		 */
		case ADD_TILE:
			//WinTile
			if (tileType.getSelectedItem().toString().equals("Win Tile")) {
				TileUI prevWinTileUI = tilesButtons.stream().filter(s -> s.getState() == TileUI.State.WIN).findAny().orElse(null);
				TileUI nextWinTileUI = tilesButtons.stream().filter(s -> s.isSelected() && 
						s.getLifeState() == TileUI.LifeState.EXIST &&
						s.getState() == TileUI.State.NORMAL).findAny().orElse(null);
				
				//Then there were a previous win tile
				if (prevWinTileUI != null) {
					prevWinTileUI.resetUI();
				}
				
				//WinTile is selected
				if (nextWinTileUI != null) {
					nextWinTileUI.setState(TileUI.State.WIN);
					try {
						nextWinTileUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/win.png"))));
					} catch (IOException e) {
						
					}
					
					Tile nextWinTile = game.getTileFromXY(nextWinTileUI.getUIX(), nextWinTileUI.getUIY());
					currentController.createWinTile(nextWinTile);
				}
			} 
			//ActionTile
			else if(tileType.getSelectedItem().toString().equals("Action Tile")) {
				int disableTurns = new PopUpManager(this).askInactivityPeriod();
				
				List<TileUI> tiles = tilesButtons.stream().filter(s -> s.isSelected() && 
						s.getLifeState() == TileUI.LifeState.EXIST &&
						s.getState() == TileUI.State.NORMAL).collect(Collectors.toList());
				
				tiles.forEach(s -> {
					setActionTileIcon(s, disableTurns);
					
					Tile tileEquivalent = game.getTileFromXY(s.getUIX(), s.getUIY());
					
					currentController.createActionTile(tileEquivalent, disableTurns);
					s.setState(TileUI.State.ACTION);
				});
			}
			//NormalTile
			else {
				List<TileUI> tiles = tilesButtons.stream().filter(s -> !s.isSelected() && s.getLifeState() == TileUI.LifeState.NOTEXIST).collect(Collectors.toList());
				tiles.forEach(s -> {
					s.setLifeState(TileUI.LifeState.EXIST);
					s.setState(TileUI.State.NORMAL);
					s.resetUI();
					
					int x = s.getUIX();
					int y = s.getUIY();
					
					currentController.createNormalTile(s.getUIX(), s.getUIY());
					
					//Show nearest connections
					connectionButtons.stream().filter(t -> t.getState() == ConnectionUI.State.HIDE).forEach(t -> {
						boolean show = false;
								
						//Top
						if(getTileUIByXY(x-1,y) != null && getTileUIByXY(x-1,y).getLifeState() == TileUI.LifeState.EXIST && y*2 == t.getUIY() && x*2-1 == t.getUIX())
							show = true;
						//Bottom
						else if(getTileUIByXY(x+1,y) != null && getTileUIByXY(x+1,y).getLifeState() == TileUI.LifeState.EXIST && y*2 == t.getUIY() && x*2+1 == t.getUIX())
							show = true;
						//Left
						else if(getTileUIByXY(x,y-1) != null && getTileUIByXY(x,y-1).getLifeState() == TileUI.LifeState.EXIST && y*2-1 == t.getUIY() && x*2 == t.getUIX())
							show = true;
						//Right
						else if(getTileUIByXY(x,y+1) != null && getTileUIByXY(x,y+1).getLifeState() == TileUI.LifeState.EXIST && y*2+1 == t.getUIY() && x*2 == t.getUIX())
							show = true;
						
						if(show)
							t.setState(ConnectionUI.State.SHOW);
					});
				});
				
			}
			break;

		/*
		 * IF REMOVING A TILE
		 */
		case REMOVE_TILE:
			List<TileUI> tiles = tilesButtons.stream().filter(s -> s.isSelected() && s.getLifeState() == TileUI.LifeState.EXIST).collect(Collectors.toList());
			tiles.forEach(s -> {
				s.setLifeState(TileUI.LifeState.NOTEXIST);
				s.setState(TileUI.State.NORMAL);
				s.resetUI();
				
				Tile tileEquivalent = game.getTileFromXY(s.getUIX(), s.getUIY());
				
				int connX = s.getUIX()*2;
				int connY = s.getUIY()*2;
				
				//Hide nearest connections
				connectionButtons.stream().filter(t -> (t.getUIX()==connX && Math.abs(t.getUIY()-connY)==1) ||
						(t.getUIY()==connY && Math.abs(t.getUIX()-connX)==1)).forEach(t -> {
							t.setState(ConnectionUI.State.HIDE);
							t.setLifeState(ConnectionUI.LifeState.NOTEXIST);
						});
				
				try {
					currentController.deleteTile(tileEquivalent);
				} catch (InvalidInputException e) {
					e.printStackTrace();
				}
			});
			break;

		/*
		 * IF ADDING A CONNECTION
		 */
		case ADD_CONNECTION:
			connectionButtons.stream()
				.filter(s -> s.isSelected() && s.isVisible() && s.getLifeState() == ConnectionUI.LifeState.NOTEXIST
				&& s.getState() == ConnectionUI.State.SHOW)
				.forEach(s -> {
					s.setLifeState(ConnectionUI.LifeState.EXIST);
					
					if(s.getType() == ConnectionUI.Type.HORIZONTAL) {
						Tile tile1 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()-1)/2);
						Tile tile2 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()+1)/2);
						
						try {
							currentController.connectTiles(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					} else if(s.getType() == ConnectionUI.Type.VERTICAL) {
						Tile tile1 = game.getTileFromXY((s.getUIX()-1)/2, s.getUIY()/2);
						Tile tile2 = game.getTileFromXY((s.getUIX()+1)/2, s.getUIY()/2);
						
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
			connectionButtons.stream().filter(s -> s.isSelected() && s.isVisible() && s.getLifeState() == ConnectionUI.LifeState.EXIST
				&& s.getState() == ConnectionUI.State.SHOW).forEach(s -> {
					s.setLifeState(ConnectionUI.LifeState.NOTEXIST);
					
					if(s.getType() == ConnectionUI.Type.HORIZONTAL) {
						Tile tile1 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()-1)/2);
						Tile tile2 = game.getTileFromXY(s.getUIX()/2, (s.getUIY()+1)/2);
						
						try {
							currentController.removeConnection(tile1, tile2);
						} catch (InvalidInputException e) {
							// Already connection
						}
					} else if(s.getType() == ConnectionUI.Type.VERTICAL) {
						Tile tile1 = game.getTileFromXY((s.getUIX()-1)/2, s.getUIY()/2);
						Tile tile2 = game.getTileFromXY((s.getUIX()+1)/2, s.getUIY()/2);
						
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
			setupBoard(true);
			
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
				//rollDie, removeConnection, teleport, loseTurn, connectTiles, sendBack, additionalMove, next rolls one,
				//show action tiles, move win tile, move player, inactivity period
				currentController.createDeck(cardsCounts[0], cardsCounts[1], cardsCounts[2], cardsCounts[3], cardsCounts[4], cardsCounts[5],
						cardsCounts[6], cardsCounts[7], cardsCounts[8], cardsCounts[9], cardsCounts[10], cardsCounts[11]);
			} catch (InvalidInputException e) {
				// More than 32
			}
			break;
			
		case NONE:
			break;
		default:
			break;
		}

		// Change colors for connections
		connectionButtons.parallelStream().forEach(s -> {
			if (s.getLifeState() == ConnectionUI.LifeState.NOTEXIST) {
				s.setBackground(null); 
				s.setColored(false);
			}
			else {
				s.setBackground(new java.awt.Color(0, 0, 0));
				s.setColored(true);
			}
			s.setSelected(false);
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
				tilesButtons.stream().filter(s -> s.isSelected() && s != tile)
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
				else {
					tile.setSelected(false);
					tile.setBorderPainted(false);
					tile.setFocusPainted(false);
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
				conn.toggleColor();
			}
		} else if (designState == DesignState.REMOVE_CONNECTION) {
			enableChanges();
			conn.toggleColor();
		}
		// Does not allow connections to be clicked
		else {
			conn.setSelected(false);
			conn.setBorderPainted(false);
			conn.setFocusPainted(false);
		}
	}

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		boolean wasProcessed = false;
		
		while(!wasProcessed) {
			String newName = null;
			while((newName = new PopUpManager(this).askSaveName(game.getGameName())) == "");
				
			if(newName != null) {
				boolean nameExists = false;
				
				for(Game gameFromTileO : TileOApplication.getTileO().getGames()) {
					if(gameFromTileO != null && newName.equals(gameFromTileO.getGameName()) && !newName.equals(game.getGameName()))
						nameExists = true;
				}
				
				if(!nameExists) {
					currentController.saveGame(newName);
					new PopUpManager(this).acknowledgeMessage("Game saved");
					wasProcessed = true;
				} else {
					new PopUpManager(this).errorMessage("Game name already exists");
				}
			}
			else
				wasProcessed = true;
		}
	}

	private void nbOfPlayersChanged() {
		if(designState == DesignState.NONE)
			new PopUpManager(this).acknowledgeMessage("If you apply changes, the whole board will be reset.");
		
		String nbOfPlayersChosen = String.valueOf(nbOfPlayers.getSelectedItem());
		if (game.numberOfPlayers() != Integer.valueOf(nbOfPlayersChosen)) {
			enableChanges();
			designState = DesignState.CHANGE_NUMBER_OF_PLAYERS;
			maskButtons(PLAYERS);
		}
		else {
			disableChanges();
			maskButtons(ALLBTN);
			designState = DesignState.NONE;
		}
	}

	private void enableChanges() {
		applyChangesButton.setEnabled(true);
		applyChangesButton.setSelected(false);
		applyChangesButton.setForeground(new java.awt.Color(0,0,0));
	}

	private void disableChanges() {
		applyChangesButton.setEnabled(false);
		applyChangesButton.setSelected(false);
		applyChangesButton.setForeground(new java.awt.Color(200,200,200));
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
	
	public TileUI getTileUIByXY(int x, int y) {
		return tilesButtons.parallelStream().filter(s -> s.getUIX() == x && s.getUIY() == y).findAny().orElse(null);
	}
	
	public ConnectionUI getConnectionUIByXY(int x, int y) {
		return connectionButtons.parallelStream().filter(s -> s.getUIX() == x && s.getUIY() == y).findAny().orElse(null);
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game aGame) {
		game = aGame;
	}
	
	public List<ConnectionUI> getConnectionsUI() {
		return connectionButtons;
	}
	
	public List<TileUI> getTilesUI() {
		return tilesButtons;
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
    private javax.swing.JButton generateButton;
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JTextField nbAdditionalMoveCard;
    private javax.swing.JTextField nbConnectTilesCard;
    private javax.swing.JTextField nbLoseTurnCard;
    private javax.swing.JTextField nbMovePlayerCard;
    private javax.swing.JTextField nbMoveWinTileCard;
    private javax.swing.JTextField nbNextRollsOneCard;
    private javax.swing.JTextField nbInactivityPeriodCard;
    private javax.swing.JComboBox<String> nbOfPlayers;
    private javax.swing.JTextField nbRemoveConnectionCard;
    private javax.swing.JTextField nbRollDieCard;
    private javax.swing.JTextField nbSendBackCard;
    private javax.swing.JTextField nbShowActionTilesCard;
    private javax.swing.JTextField nbTeleportCard;
    private javax.swing.JToggleButton removeConnectionButton;
    private javax.swing.JToggleButton removeTileButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JToggleButton selectPositionButton;
    private javax.swing.JComboBox<String> tileType;
    private javax.swing.JComboBox<String> verticalLength;
	// End of variables declaration
}