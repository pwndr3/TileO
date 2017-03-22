package ca.mcgill.ecse223.tileo.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LookAndFeel;
import javax.swing.Painter;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayController;

public class TileOPlayUI extends javax.swing.JFrame {
	public TileOPlayUI(Game aGame) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			// Too bad
		}

		game = aGame;
		currentController = new PlayController(this, game);

		initComponents();

		if (!game.hasStarted) {
			try {
				currentController.startGame();
			} catch (Exception e) {
				new PopUpManager(this).acknowledgeMessage(e.getMessage());
			}
		} else {
			currentController.loadGame();
		}

		setupBoardFromGame();
	}

	private Game game;

	private int numberOfRows;
	private int numberOfCols;

	private JPanel tilesPanel;
	LinkedList<TileUI> tilesButtons;
	LinkedList<ConnectionUI> connectionButtons;

	private void setupBoardFromGame() {
		// Setup variables
		tilesButtons = new LinkedList<TileUI>();
		connectionButtons = new LinkedList<ConnectionUI>();

		// Find number of rows and columns
		int maxRow = 0;
		int maxCol = 0;

		for (Tile tile : game.getTiles()) {
			if (tile.getX() > maxRow)
				maxRow = tile.getX();
			if (tile.getY() > maxCol)
				maxCol = tile.getY();
		}

		numberOfRows = ++maxRow;
		numberOfCols = ++maxCol;

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

					tile.setMargin(new Insets(0, 0, 0, 0));
					tile.setBorder(null);

					if (game.getTileFromXY(row / 2, col / 2) != null) {
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

		// Remove connections
		List<TileUI> tiles = tilesButtons.stream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST)
				.collect(Collectors.toList());
		tiles.forEach(s -> {
			int connX = s.getUIX() * 2;
			int connY = s.getUIY() * 2;

			// Hide nearest connections
			connectionButtons.stream().filter(t -> (t.getUIX() == connX && Math.abs(t.getUIY() - connY) == 1)
					|| (t.getUIY() == connY && Math.abs(t.getUIX() - connX) == 1)).forEach(t -> {
						t.setState(ConnectionUI.State.HIDE);
						t.setLifeState(ConnectionUI.LifeState.NOTEXIST);
					});
		});

		// Set connections
		game.getConnections().stream().forEach(s -> {
			if(s != null) {
				Tile tile1 = s.getTile(0);
				Tile tile2 = s.getTile(1);
					
				connectionButtons.stream().filter(t -> t.getState() == ConnectionUI.State.SHOW).forEach(t -> {
					// Horizontal
					if (tile1.getX() == tile2.getX()) {
						if (t.getUIX() == tile1.getX() * 2) {
							int farRight = (tile1.getY() > tile2.getY()) ? tile1.getY() * 2 : tile2.getY() * 2;
	
							if ((farRight - t.getUIY()) == 1) {
								t.setState(ConnectionUI.State.SHOW);
								t.setLifeState(ConnectionUI.LifeState.EXIST);
								t.setVisible(true);
							}
						}
					}
	
					// Vertical
					if (tile1.getY() == tile2.getY()) {
						if (t.getUIY() == tile1.getY() * 2) {
							int bottom = (tile1.getX() > tile2.getX()) ? tile1.getX() * 2 : tile2.getX() * 2;
	
							if ((bottom - t.getUIX()) == 1) {
								t.setState(ConnectionUI.State.SHOW);
								t.setLifeState(ConnectionUI.LifeState.EXIST);
								t.setVisible(true);
							}
						}
					}
				});
			} else {
				System.out.println("null");
			}
		});

		// Starting positions
		for (int i = 0; i < game.numberOfPlayers(); i++) {
			if (!game.getPlayer(i).hasStartingTile())
				continue;

			Tile tile = game.getPlayer(i).getStartingTile();

			TileUI tileGUI = tilesButtons.stream()
					.filter(s -> s.getUIX() == tile.getX() && s.getUIY() == tile.getY()).findAny().orElse(null);
			if (tileGUI != null) {
				tileGUI.resetUI();
				
				tileGUI.setPlayerIcon(String.valueOf(i+1));
				tileGUI.setVisited(true);
			}
		}

		// Hide disabled tiles
		tilesButtons.stream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST).forEach(s -> {
			s.hideUI();
			s.setSelected(false);
		});

		update();
		maskButtons(ROLLDIE);
		
		if(currentController.getState() == PlayController.State.GameWon) {
			maskButtons(0);
			saveButton.setEnabled(false);
			
			Timer timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	winGame();
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
		}
	}

	public int PICKCARD = 1;
	public int ROLLDIE = 2;

	public void maskButtons(int mask) {
		pickCardButton.setEnabled((mask & PICKCARD) == PICKCARD);
		rollDieButton.setEnabled((mask & ROLLDIE) == ROLLDIE);
	}

	private void initComponents() {
		playerTurnLabel = new javax.swing.JLabel();
		playerColor = new javax.swing.JLabel();
		pickCardButton = new javax.swing.JButton();
		rollDieButton = new javax.swing.JButton();
		addConnectionButton = new javax.swing.JButton();
		removeConnectionButton = new javax.swing.JButton();
		teleportButton = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jProgressBar1 = new JProgressBar();
		tilesPanel = new javax.swing.JPanel();
		tilesPanel.setPreferredSize(new java.awt.Dimension(1130, 680));
		saveButton = new javax.swing.JButton();
		loadButton = new javax.swing.JButton();
		jButton1 = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		playerTurnLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
		playerTurnLabel.setText("Player Turn:");

		playerColor.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
		playerColor.setForeground(new java.awt.Color(240, 10, 10));
		playerColor.setText("RED");

		pickCardButton.setBackground(new java.awt.Color(51, 102, 255));
		pickCardButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
		pickCardButton.setForeground(new java.awt.Color(255, 255, 255));
		pickCardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/cards.png"))); // NOI18N
		pickCardButton.setText("Pick a Card");
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
		addConnectionButton.setVisible(false);

		removeConnectionButton.setBackground(new java.awt.Color(51, 102, 255));
		removeConnectionButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
		removeConnectionButton.setForeground(new java.awt.Color(255, 255, 255));
		removeConnectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/minus.png"))); // NOI18N
		removeConnectionButton.setText("Remove Connection");
		removeConnectionButton.setMaximumSize(new java.awt.Dimension(100, 100));
		removeConnectionButton.setMinimumSize(new java.awt.Dimension(100, 100));
		removeConnectionButton.setPreferredSize(new java.awt.Dimension(50, 50));
		removeConnectionButton.setSize(new java.awt.Dimension(50, 25));
		removeConnectionButton.setVisible(false);

		teleportButton.setBackground(new java.awt.Color(51, 102, 255));
		teleportButton.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
		teleportButton.setForeground(new java.awt.Color(255, 255, 255));
		teleportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/teleport.png"))); // NOI18N
		teleportButton.setText("     Teleport        ");
		teleportButton.setMaximumSize(new java.awt.Dimension(100, 100));
		teleportButton.setMinimumSize(new java.awt.Dimension(100, 100));
		teleportButton.setPreferredSize(new java.awt.Dimension(50, 50));
		teleportButton.setSize(new java.awt.Dimension(50, 25));
		teleportButton.setVisible(false);

		jLabel1.setFont(new java.awt.Font("Malayalam MN", 1, 20)); // NOI18N
		jLabel1.setText("Connection Pieces Left");

		jProgressBar1.setForeground(new java.awt.Color(255, 255, 255));
		jProgressBar1.setBackground(new java.awt.Color(255, 204, 0));
		jProgressBar1.setFont(new java.awt.Font("Malayalam MN", 1, 24)); // NOI18N
		jProgressBar1.setMaximum(32);
		jProgressBar1.setValue(game.getCurrentConnectionPieces());
		jProgressBar1.setBorderPainted(false);
		jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		jProgressBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
		jProgressBar1.setString(String.valueOf(jProgressBar1.getValue()));
		jProgressBar1.setStringPainted(true);

		saveButton.setBackground(new java.awt.Color(255, 204, 0));
		saveButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
		saveButton.setText("Save");
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
		loadButton.setMaximumSize(new java.awt.Dimension(100, 100));
		loadButton.setMinimumSize(new java.awt.Dimension(100, 100));
		loadButton.setPreferredSize(new java.awt.Dimension(50, 50));
		loadButton.setSize(new java.awt.Dimension(50, 25));
		loadButton.setVisible(false);

		jButton1.setBackground(new java.awt.Color(255, 0, 0));
		jButton1.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
		jButton1.setText("Back");
		jButton1.addActionListener(e -> {
			if(!saved) {
				if (new PopUpManager(this).askYesOrNo("Any unsaved changes will be lost. Continue?") == 0) {
					new MainPage().setVisible(true);
					dispose();
				}
			} else {
				new MainPage().setVisible(true);
				dispose();
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addComponent(jButton1)
										.addGap(474, 474, 474).addComponent(playerTurnLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(playerColor))
								.addGroup(layout.createSequentialGroup().addContainerGap()
										.addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addComponent(jLabel1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(rollDieButton,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(pickCardButton,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(addConnectionButton,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(removeConnectionButton,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(teleportButton,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(jProgressBar1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
														.addComponent(saveButton,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.PREFERRED_SIZE, 236,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 236,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
										.createSequentialGroup().addContainerGap().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(playerTurnLabel).addComponent(playerColor)))
								.addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addComponent(jButton1)))
						.addGap(74, 74, 74)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
								.createSequentialGroup().addComponent(jLabel1)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(27, 27, 27)
								.addComponent(rollDieButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(pickCardButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(addConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(removeConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(teleportButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(52, 52, 52)
								.addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(78, Short.MAX_VALUE)));

		setUndecorated(true);

		pack();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		setLocationRelativeTo(null);
		setLocation(new Double(width / 2).intValue() - getWidth() / 2,
				new Double(height / 2).intValue() - getHeight() / 2);
	}

	public void update() {
		// Change colors for connections
		connectionButtons.stream().forEach(s -> {
			if (s.getLifeState() == ConnectionUI.LifeState.NOTEXIST)
				s.setBackground(null);
			else
				s.setBackground(new java.awt.Color(0, 0, 0));
			
			s.setSelected(false);
		});

		// Reset states
		hideDisabledConnections();

		tilesButtons.stream().filter(s -> s.isVisible()).forEach(s -> {
			s.setSelected(false);
			s.setFocusPainted(false);
			s.setBorderPainted(false);
			s.resetUI();
		});
		
		//Set player icons
		HashMap<Integer, String> playerArray = new HashMap<Integer, String>();
		
		for (int i = 0; i < game.numberOfPlayers(); i++) {
			Tile tile = game.getPlayer(i).getCurrentTile();
			Integer index = game.getTiles().indexOf(tile);
			
			if(playerArray.containsKey(index))
				playerArray.replace(index, playerArray.get(index)+String.valueOf(i+1));
			else
				playerArray.put(index, String.valueOf(i+1));
		}
		
		for(HashMap.Entry<Integer, String> entry : playerArray.entrySet()) {
			Integer index = entry.getKey();
			
			Tile tile = game.getTiles().get(index);
			
			TileUI tileUI = getTileUIByXY(tile.getX(), tile.getY());
			tileUI.setPlayerIcon(entry.getValue());
			tileUI.setVisited(true);
		}
		
		//Set visited
		tilesButtons.stream().forEach(s -> {
			if(s.isVisited())
				s.setBackground(Color.decode("#ffc4c4"));
			else
				s.setBackground(null);
		});

		updateConnectionPieces();
		updatePlayerNameAndColor();
		
		if(currentController.getState() == PlayController.State.GameWon) {
			maskButtons(0);
			saveButton.setEnabled(false);
			
			Timer timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	winGame();
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
		}

		repaint();
		revalidate();
	}

	private void updatePlayerNameAndColor() {
		switch (game.getCurrentPlayer().getNumber()) {
		case 0:
			playerColor.setText("RED");
			playerColor.setForeground(new java.awt.Color(240, 10, 10));
			break;
		case 1:
			playerColor.setText("BLUE");
			playerColor.setForeground(new java.awt.Color(10, 10, 240));
			break;
		case 2:
			playerColor.setText("GREEN");
			playerColor.setForeground(new java.awt.Color(10, 240, 10));
			break;
		case 3:
			playerColor.setText("YELLOW");
			playerColor.setForeground(new java.awt.Color(240, 240, 10));
			break;
		}
	}

	private void updateConnectionPieces() {
		Color color = null;
		int n = game.getCurrentConnectionPieces();

		if (n == 0)
			color = Color.decode("#E50005");
		if (n == 1)
			color = Color.decode("#E30900");
		if (n == 2)
			color = Color.decode("#E21900");
		if (n == 3)
			color = Color.decode("#E12800");
		if (n == 4)
			color = Color.decode("#E03700");
		if (n == 5)
			color = Color.decode("#DF4600");
		if (n == 6)
			color = Color.decode("#DD5500");
		if (n == 7)
			color = Color.decode("#DC6400");
		if (n == 8)
			color = Color.decode("#DB7200");
		if (n == 9)
			color = Color.decode("#DA8000");
		if (n == 10)
			color = Color.decode("#D98F00");
		if (n == 11)
			color = Color.decode("#D79D00");
		if (n == 12)
			color = Color.decode("#D6AB00");
		if (n == 13)
			color = Color.decode("#D5B800");
		if (n == 14)
			color = Color.decode("#D4C600");
		if (n == 15)
			color = Color.decode("#D2D300");
		if (n == 16)
			color = Color.decode("#C3D200");
		if (n == 17)
			color = Color.decode("#B3D000");
		if (n == 18)
			color = Color.decode("#A4CF00");
		if (n == 19)
			color = Color.decode("#95CE00");
		if (n == 20)
			color = Color.decode("#86CD00");
		if (n == 21)
			color = Color.decode("#77CC00");
		if (n == 22)
			color = Color.decode("#69CA00");
		if (n == 23)
			color = Color.decode("#5AC900");
		if (n == 24)
			color = Color.decode("#4CC800");
		if (n == 25)
			color = Color.decode("#3EC700");
		if (n == 26)
			color = Color.decode("#30C600");
		if (n == 27)
			color = Color.decode("#22C400");
		if (n == 28)
			color = Color.decode("#14C300");
		if (n == 29)
			color = Color.decode("#07C200");
		if (n == 30)
			color = Color.decode("#00C105");
		if (n == 31)
			color = Color.decode("#00C013");
		if (n == 32)
			color = Color.decode("#00BF1F");

		try {
			LookAndFeel lnf = UIManager.getLookAndFeel().getClass().newInstance();
			// UIDefaults uiDefaults = lnf.getDefaults();
			UIManager.put("nimbusOrange", color);
			UIManager.getLookAndFeel().uninitialize();
			UIManager.setLookAndFeel(lnf);
		} catch (Exception e) {
			e.printStackTrace();
		}

		jProgressBar1.setValue(n);
		jProgressBar1.setString(String.valueOf(n));

		jProgressBar1.updateUI();
		jProgressBar1.repaint();
	}

	private void showDisabledConnections() {
		boolean allEnabled = true;
		
		for(ConnectionUI s : connectionButtons) {
			if(s.getLifeState() == ConnectionUI.LifeState.NOTEXIST && s.getState() == ConnectionUI.State.SHOW) {
				if(!s.isVisible()) {
					allEnabled = false;
					s.showUI();
				}
			}
		}
		
		if(allEnabled) {
			new PopUpManager(this).acknowledgeMessage("No connection to add");
			currentController.nextTurn();
		}
	}

	private void hideDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST)
				.forEach(s -> s.hideUI());
	}

	private void tileActionPerformed(java.awt.event.ActionEvent evt) {
		TileUI tileUI = (TileUI) evt.getSource();
		Tile tile = null;

		switch (currentController.getState()) {
		case Roll:
			if(!tileUI.getBackground().equals(new java.awt.Color(30, 30, 220)) && !tileUI.getBackground().equals(new java.awt.Color(132,105,209))) {
				tileUI.setSelected(false);
				tileUI.setBorderPainted(false);
				tileUI.setFocusPainted(false);
				
				return;
			}
			
			currentController.setState(PlayController.State.Move);
			tile = game.getTileFromXY(tileUI.getUIX(), tileUI.getUIY());
			currentController.land(tile);
			
			if(tile instanceof ActionTile) {
				landOnActionTile(tileUI, tile);
			}
			
			else if(tile instanceof WinTile) {
				landOnWinTile(tileUI, tile);
				
			}
			
			else {
				tileUI.setVisited(true);
				currentController.nextTurn();
			}
			
			break;

		case ActionCard:
			if(game.getMode() == Game.Mode.GAME_TELEPORTACTIONCARD) {
				tile = game.getTileFromXY(tileUI.getUIX(), tileUI.getUIY());
				tileUI.setVisited(true);
				currentController.playTeleportActionCard(tile);
				
				switch(currentController.getState()) {
				case ActionCard:
					landOnActionTile(tileUI, tile);
					break;
				case GameWon:
					landOnWinTile(tileUI, tile);
					break;
				default:
					tileUI.setVisited(true);
					currentController.nextTurn();
					break;
				}
			}
			break;

		default:
			tileUI.setSelected(false);
			tileUI.setBorderPainted(false);
			tileUI.setFocusPainted(false);
		}
	}
	
	private void landOnActionTile(TileUI tileUI, Tile tile) {
		tileUI.setVisited(true);
		if(((ActionTile)game.getCurrentPlayer().getCurrentTile()).getActionTileStatus() == ActionTile.ActionTileStatus.Active) {
			new PopUpManager(this).showActionTile(null);
			update();
			currentController.setState(PlayController.State.ActionCard);
			maskButtons(PICKCARD);
			((ActionTile)game.getCurrentPlayer().getCurrentTile()).deactivate();
		} else {
			currentController.nextTurn();
		}
	}
	
	private void landOnWinTile(TileUI tileUI, Tile tile) {
		tileUI.setVisited(true);
		update();
	}
	
	private void winGame() {
		currentController.saveGame();
		new PopUpManager(this).acknowledgeMessage("Player "+(game.getCurrentPlayer().getColor())+" won the game!");
		Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new MainPage().setVisible(true);
				dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
	}

	private void connectionActionPerformed(java.awt.event.ActionEvent evt) {
		ConnectionUI connUI = (ConnectionUI) evt.getSource();

		Tile tile1 = null;
		Tile tile2 = null;
		
		connUI.setBorderPainted(false);
		connUI.setFocusPainted(false);
		
		switch (game.getMode()) {
		case GAME_CONNECTTILESACTIONCARD:
			if (connUI.getLifeState() == ConnectionUI.LifeState.NOTEXIST) {
				connUI.setLifeState(ConnectionUI.LifeState.EXIST);

				if (connUI.getType() == ConnectionUI.Type.HORIZONTAL) {
					tile1 = game.getTileFromXY(connUI.getUIX() / 2, (connUI.getUIY() - 1) / 2);
					tile2 = game.getTileFromXY(connUI.getUIX() / 2, (connUI.getUIY() + 1) / 2);
				} else if (connUI.getType() == ConnectionUI.Type.VERTICAL) {
					tile1 = game.getTileFromXY((connUI.getUIX() - 1) / 2, connUI.getUIY() / 2);
					tile2 = game.getTileFromXY((connUI.getUIX() + 1) / 2, connUI.getUIY() / 2);
				}

				currentController.playAddConnectionActionCard(tile1, tile2);
				currentController.nextTurn();
			} else {
				connUI.setSelected(false);
				connUI.setBorderPainted(false);
				connUI.setFocusPainted(false);
			}
			break;

		case GAME_REMOVECONNECTIONACTIONCARD:
			if (connUI.getLifeState() == ConnectionUI.LifeState.EXIST) {
				connUI.setLifeState(ConnectionUI.LifeState.NOTEXIST);

				if(connUI.getType() == ConnectionUI.Type.HORIZONTAL) {
					tile1 = game.getTileFromXY(connUI.getUIX()/2, (connUI.getUIY()-1)/2);
					tile2 = game.getTileFromXY(connUI.getUIX()/2, (connUI.getUIY()+1)/2);
				} else if(connUI.getType() == ConnectionUI.Type.VERTICAL) {
					tile1 = game.getTileFromXY((connUI.getUIX()-1)/2, connUI.getUIY()/2);
					tile2 = game.getTileFromXY((connUI.getUIX()+1)/2, connUI.getUIY()/2);
				}

				currentController.playRemoveConnectionActionCard(tile1, tile2);
				currentController.nextTurn();
			} else {
				connUI.setSelected(false);
				connUI.setBorderPainted(false);
				connUI.setFocusPainted(false);
			}
			break;

		default:
			connUI.setSelected(false);
			connUI.setBorderPainted(false);
			connUI.setFocusPainted(false);
		}
	}

	private void pickCardButtonActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			ActionCard actionCard = currentController.getTopCard();
			new PopUpManager(this).showActionTile(actionCard);
			JFrame window = this;
			
			Timer timer = new Timer(3000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (actionCard instanceof RollDieActionCard) {
	    				game.setMode(Game.Mode.GAME_ROLLDIEACTIONCARD);
	    				currentController.setState(PlayController.State.Roll);
	    				maskButtons(ROLLDIE);
	    			}

	    			else if (actionCard instanceof ConnectTilesActionCard) {
	    				game.setMode(Game.Mode.GAME_CONNECTTILESACTIONCARD);
	    				currentController.setState(PlayController.State.ActionCard);
	    				maskButtons(0);
	    				showDisabledConnections();
	    			}

	    			else if (actionCard instanceof RemoveConnectionActionCard) {
	    				game.setMode(Game.Mode.GAME_REMOVECONNECTIONACTIONCARD);
	    				currentController.setState(PlayController.State.ActionCard);
	    				maskButtons(0);
	    				
	    				boolean allDisabled = true;
	    				
	    				for(ConnectionUI s : connectionButtons) {
	    					// Change colors for connections
	    					
	    					if(s.isVisible()) {
	    						allDisabled = false;
	    						s.setBackground(null);
	    					}
	    				}
	    				
	    				if(allDisabled) {
	    					new PopUpManager(window).acknowledgeMessage("No connection to remove");
	    					currentController.nextTurn();
	    				}
	    			}

	    			else if (actionCard instanceof LoseTurnActionCard) {
	    				//Make lose next turn for currentPlayer
	    				game.setMode(Game.Mode.GAME_LOSETURNACTIONCARD);
	    				currentController.setState(PlayController.State.ActionCard);
	    				currentController.playLoseTurnActionCard();
	    			}

	    			else if (actionCard instanceof TeleportActionCard) {
	    				game.setMode(Game.Mode.GAME_TELEPORTACTIONCARD);
	    				currentController.setState(PlayController.State.ActionCard);
	    				maskButtons(0);
	    			}
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
		} catch (Exception e) {
			new PopUpManager(this).acknowledgeMessage(e.getMessage());
			currentController.nextTurn();
		}
	}

	private void rollDieButtonActionPerformed(java.awt.event.ActionEvent evt) {
		saved = false;
		
		List<Tile> possiblePlayerMoves = currentController.rollDie();
		JFrame window = this;
		
		Timer timer = new Timer(4200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (possiblePlayerMoves.isEmpty()) {
        			new PopUpManager(window).acknowledgeMessage("No possible moves");
        			currentController.nextTurn();
        		} else {
        			possiblePlayerMoves.parallelStream().forEach(s -> {
        				if(!s.getHasBeenVisited())
        					getTileUIByXY(s.getX(), s.getY()).setBackground(new java.awt.Color(30, 30, 220));
        				else
        					getTileUIByXY(s.getX(), s.getY()).setBackground(new java.awt.Color(132,105,209));
        			});
        			currentController.setState(PlayController.State.Roll);
        		}
            }
        });
        timer.setRepeats(false);
        timer.start();
        maskButtons(0);
	}

	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		saved = true;
		currentController.saveGame();
		new PopUpManager(this).acknowledgeMessage("Game saved.");
	}

	private TileUI getTileUIByXY(int x, int y) {
		return tilesButtons.parallelStream().filter(s -> s.getUIX() == x && s.getUIY() == y).findAny().orElse(null);
	}

	private boolean saved = true;
	private javax.swing.JButton addConnectionButton;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private JProgressBar jProgressBar1;
	private javax.swing.JButton loadButton;
	private javax.swing.JButton pickCardButton;
	private javax.swing.JLabel playerColor;
	private javax.swing.JLabel playerTurnLabel;
	private javax.swing.JButton removeConnectionButton;
	private javax.swing.JButton rollDieButton;
	private javax.swing.JButton saveButton;
	private javax.swing.JButton teleportButton;
	PlayController currentController;
}
