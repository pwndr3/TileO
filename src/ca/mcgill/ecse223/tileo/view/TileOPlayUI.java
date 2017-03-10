package ca.mcgill.ecse223.tileo.view;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.UIManager;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

public class TileOPlayUI extends javax.swing.JFrame {
	
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
	
	private int numberOfRows;
	private int numberOfCols;
	
    private JPanel tilesPanel;
    LinkedList<TileUI> tilesButtons;
    LinkedList<ConnectionUI> connectionButtons;
	
	private void setupBoardFromGame() {
		//Setup variables
		tilesButtons = new LinkedList<TileUI>();
		connectionButtons = new LinkedList<ConnectionUI>();
		
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
		List<TileUI> tiles = tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST).collect(Collectors.toList());
			tiles.forEach(s -> {
			int connX = s.getUIX()*2;
			int connY = s.getUIY()*2;
			
			//Hide nearest connections
			connectionButtons.parallelStream().filter(t -> (t.getUIX()==connX && Math.abs(t.getUIY()-connY)==1) ||
					(t.getUIY()==connY && Math.abs(t.getUIX()-connX)==1)).forEach(t -> {
						t.setState(ConnectionUI.State.HIDE);
						t.setLifeState(ConnectionUI.LifeState.NOTEXIST);
					});
		});
			
		//Set connections
		game.getConnections().parallelStream().forEach(s -> {
			Tile tile1 = s.getTile(0);
			Tile tile2 = s.getTile(1);
			
			connectionButtons.parallelStream().filter(t -> t.getState() == ConnectionUI.State.SHOW).forEach(t -> {
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
			
		//Starting positions
		for(int i = 0; i < game.numberOfPlayers(); i++) {
			if(!game.getPlayer(i).hasStartingTile())
				continue;
			
			Tile tile = game.getPlayer(i).getStartingTile();
			
			TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.getUIX()==tile.getX() && s.getUIY()==tile.getY()).findAny().orElse(null);
			if(tileGUI != null) {
				tileGUI.resetUI();
				try {
					tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/players/"+(i+1)+".png"))));
				} catch (IOException e) {
					
				}
			}
		}
		
		//Action & win tiles
		for(Tile tile : game.getTiles()) {
			if(tile instanceof ActionTile) {
				TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.getUIX()==tile.getX() && s.getUIY()==tile.getY()).findAny().orElse(null);
				tileGUI.resetUI();
				try {
					tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/action_tile.png"))));
				} catch (IOException e) {
					
				}
				
			} else if(tile instanceof WinTile) {
				TileUI tileGUI = tilesButtons.parallelStream().filter(s -> s.getUIX()==tile.getX() && s.getUIY()==tile.getY()).findAny().orElse(null);
				tileGUI.resetUI();
				try {
					tileGUI.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/win.png"))));
				} catch (IOException e) {
					
				}
			}
 		}
		
		//Hide disabled tiles
		tilesButtons.parallelStream().filter(s -> s.getLifeState() == TileUI.LifeState.NOTEXIST)
		.forEach(s -> {
			s.hideUI();
			s.setSelected(false);
			});
		
		update();
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
        jProgressBar1 = new javax.swing.JProgressBar();
        tilesPanel = new javax.swing.JPanel();
        tilesPanel.setPreferredSize(new java.awt.Dimension(1130, 680));
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
        jProgressBar1.setToolTipText("32");
        jProgressBar1.setValue(32);
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jProgressBar1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jProgressBar1.setString(jProgressBar1.getToolTipText());
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
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setFont(new java.awt.Font("Lucida Grande", 3, 13)); // NOI18N
        jButton1.setText("Back");
        jButton1.addActionListener(e -> {
			if(new PopUpManager(this).askYesOrNo("Any unsaved changes will be lost. Continue?") == 0) {
				new MainPage().setVisible(true);
				dispose();
			}
		});
        
        //
        setupBoardFromGame();
        //

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
                        .addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(tilesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        setUndecorated(true);
        
        pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        setLocationRelativeTo(null);
        setLocation(new Double(width/2).intValue()-getWidth()/2, new Double(height/2).intValue()-getHeight()/2);
    }
    
    private void update() {
    	// Change colors for connections
		connectionButtons.parallelStream().forEach(s -> {
			if (s.getLifeState() == ConnectionUI.LifeState.NOTEXIST) 
				s.setBackground(null); 
			else
				s.setBackground(new java.awt.Color(0, 0, 0));
		});

		// Reset states
		hideDisabledConnections();
		
		tilesButtons.parallelStream().filter(s -> s.isVisible()).forEach(s -> {
			s.setSelected(false);
			s.setFocusPainted(false);
			s.setBorderPainted(false);
		});
		
		repaint();
		revalidate();
    }

	private void showDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST && s.getState() == ConnectionUI.State.SHOW)
				.forEach(s -> s.showUI());
	}

	private void hideDisabledConnections() {
		connectionButtons.parallelStream().filter(s -> s.getLifeState() == ConnectionUI.LifeState.NOTEXIST)
				.forEach(s -> s.hideUI());
	}
	
    private void tileActionPerformed(java.awt.event.ActionEvent evt) {
    	
    }
		
    private void connectionActionPerformed(java.awt.event.ActionEvent evt) {
    	
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
