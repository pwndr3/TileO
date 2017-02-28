/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.util.*;
import java.awt.*;

public class TileODesignUI extends javax.swing.JFrame {
    private String hLength = "";
    private String vLength = "";
    private String numOfPlayersInGame = "";
    
    private LinkedList<JToggleButton> tilesButtons;
    private LinkedList<JToggleButton> connectionButtons;
    private LinkedList<Integer> tilesButtonsBackup;
    private LinkedList<Integer> connectionButtonsBackup;
    private JPanel tilesPanel;
    
    private enum ButtonSelection { SELECT, ADDTILE, REMOVETILE, ADDCONN, REMOVECONN, NONE }
    private ButtonSelection buttonSelected = ButtonSelection.NONE;
    
    private double WINDOW_WIDTH = getContentPane().getSize().getWidth();
    private int WINDOW_HEIGHT = (int)getContentPane().getSize().getHeight();
    
    private int numberOfRows = 0;
    private int numberOfCols = 0;

    /**
     * Creates new form TileOUGUI
     */
    public TileODesignUI() {
        initComponents();
    }
    
    private int getNumberOfCardsLeft() {
      return 32 - (Integer.valueOf(nbRollDieCard.getText()) + 
                                  Integer.valueOf(nbRemoveConnectionCard.getText()) + 
                                  Integer.valueOf(nbTeleportCard.getText()) + 
                                  Integer.valueOf(nbLoseTurnCard.getText()) + 
                                  Integer.valueOf(nbConnectTilesCard.getText()));
    }
    
    private void showDisabledTiles() {
      for(JToggleButton button : tilesButtons) {
        if(!button.isVisible()) {
           button.setVisible(true);
        }
      }
    }
    
    private void hideDisabledTiles() {
      for(JToggleButton button : tilesButtons) {
        if(button.isSelected()) {
           button.setVisible(false);
        }
      }
    }
    
    private void showDisabledConnections() {
      for(JToggleButton button : connectionButtons) {
        if(!button.isVisible()) {
           button.setVisible(true);
        }
      }
    }
    
    private void hideDisabledConnections() {
      for(JToggleButton button : connectionButtons) {
        if(button.isSelected()) {
           button.setVisible(false);
        }
      }
    }
    
    private void changeNumberOfCardsLeft() {
      int nbOfCardsLeft = getNumberOfCardsLeft();
      
      if(nbOfCardsLeft < 0) {
        cardsLeft.setForeground(new java.awt.Color(255, 0, 0));
        applyChangesButton.setEnabled(false);
      }
      else {
        cardsLeft.setForeground(new java.awt.Color(0, 0, 0));
        applyChangesButton.setEnabled(true);
      }
      
      cardsLeft.setText(String.valueOf(nbOfCardsLeft));
    }
    
    private void changeBoardSize(int m, int n) {
      if(numberOfRows == m && numberOfCols == n)
        return;
      
      numberOfRows = m;
      numberOfCols = n;

      //Clear
      tilesButtons.clear();
      connectionButtons.clear();
      tilesPanel.removeAll();
      
      //Create buttons and put in linked list
      for(int i = 0; i < m+(m-1); i++) {
        for(int j = 0; j < n+(n-1); j++) {
          JToggleButton toggleButton = new JToggleButton();
          
          //Tile
          if(i%2 == 0 && j%2 == 0) {
             toggleButton.setPreferredSize(new java.awt.Dimension(30,30));
             toggleButton.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt) {
                 tileActionPerformed(evt);
             }
             });
             tilesButtons.add(toggleButton);
          }
            
            //Horizontal connection
            else if(i%2 == 1 && j%2 == 0) {
              toggleButton.setPreferredSize(new java.awt.Dimension(10,10));
              toggleButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                 connectionActionPerformed(evt);
              }
            });
              toggleButton.setVisible(false);
              toggleButton.setSelected(true);
              connectionButtons.add(toggleButton);
           }
                                     
            //Vertical connection
            else if(i%2 == 0 && j%2 == 1) {
              toggleButton.setPreferredSize(new java.awt.Dimension(10,10));
              toggleButton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                 connectionActionPerformed(evt);
              }});
              toggleButton.setVisible(false);
              toggleButton.setSelected(true);
              connectionButtons.add(toggleButton);
            }
            
        }}
       
        
       //Create grids      
      tilesPanel.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      
      ListIterator<JToggleButton> tiles_it = tilesButtons.listIterator();
      ListIterator<JToggleButton> conn_it = connectionButtons.listIterator();
      
      for(int row = 0; row < m+(m-1); row++) {
        for(int col = 0; col < n+(n-1); col++) {
          c.fill = GridBagConstraints.HORIZONTAL;
          c.gridx = row;
          c.gridy = col;
          
          //Tile
          if(row%2 == 0 && col%2 == 0) {
                tilesPanel.add(tiles_it.next(), c);
          }
          
          //Horizontal connection
            else if(row%2 == 1 && col%2 == 0) {
              c.fill = GridBagConstraints.HORIZONTAL;
              tilesPanel.add(conn_it.next(), c);
            }
            
            //Vertical connection
            else if(row%2 == 0 && col%2 == 1) {
              c.fill = GridBagConstraints.VERTICAL;
              tilesPanel.add(conn_it.next(), c);
            }
            
            //Gap
            else {
              JPanel gap = new JPanel();
              gap.setPreferredSize(new java.awt.Dimension(10,10));
              tilesPanel.add(gap, c);
            }
        }
      }
    }
    
    private void backupLists() {
      tilesButtonsBackup.clear();
      connectionButtonsBackup.clear();
      
      ListIterator<Integer> tiles_it = tilesButtonsBackup.listIterator();
      ListIterator<Integer> conn_it = connectionButtonsBackup.listIterator();
      
      for(JToggleButton button : tilesButtons) {
        tiles_it.add(button.isSelected() ? 1 : 0);
      }
      
      for(JToggleButton button : connectionButtons) {
        conn_it.add(((button.getBackground()==(new java.awt.Color(0,0,0)) ? 1 : 0) << 1) | (button.isSelected() ? 1 : 0));
      }
    }
    
    private void restoreLists() {   
      ListIterator<JToggleButton> tiles_it = tilesButtons.listIterator();
      ListIterator<JToggleButton> conn_it = connectionButtons.listIterator();
      
      for(int button : tilesButtonsBackup) {
           JToggleButton tile = tiles_it.next();
           tile.setSelected((button & 1) == 1);          
      }
      
      for(int button : connectionButtonsBackup) {
           JToggleButton tile = conn_it.next();
           tile.setSelected((button & 1) == 1);
           
           if((button & 2) == 1) {
               tile.setBackground(new java.awt.Color(0,0,0));
           } else {
               tile.setBackground(null); 
           }
      }
    }
    
    private void resetUI() {
      restoreLists();
      
      applyChangesButtonActionPerformed(new java.awt.event.ActionEvent(new Object(), 0, ""));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        applyChangesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyChangesButtonActionPerformed(evt);
            }
        });

        chosenPlayer.setBackground(new java.awt.Color(204, 204, 255));
        chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        chosenPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chosenPlayerActionPerformed(evt);
            }
        });

        removeTileButton.setBackground(new java.awt.Color(153, 153, 255));
        removeTileButton.setText("Remove Tile");
        removeTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTileButtonActionPerformed(evt);
            }
        });

        tileType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Tile", "Action Tile", "Win Tile" }));
        tileType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tileTypeActionPerformed(evt);
            }
        });

        addTileButton.setBackground(new java.awt.Color(153, 153, 255));
        addTileButton.setText("Add Tile");
        addTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTileButtonActionPerformed(evt);
            }
        });

        selectPositionButton.setBackground(new java.awt.Color(153, 153, 255));
        selectPositionButton.setText("Select Start Position");
        selectPositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectPositionButtonActionPerformed(evt);
            }
        });

        removeConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
        removeConnectionButton.setText("Remove Connection");
        removeConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeConnectionButtonActionPerformed(evt);
            }
        });

        addConnectionButton.setBackground(new java.awt.Color(153, 153, 255));
        addConnectionButton.setText("Add Connection");
        addConnectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addConnectionButtonActionPerformed(evt);
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
                nbRollDieCardActionPerformed(evt);
            }
        });

        nbRemoveConnectionCard.setText("0");
        nbRemoveConnectionCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbRemoveConnectionCardActionPerformed(evt);
            }
        });

        nbTeleportCard.setText("0");
        nbTeleportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbTeleportCardActionPerformed(evt);
            }
        });

        nbLoseTurnCard.setText("0");
        nbLoseTurnCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbLoseTurnCardActionPerformed(evt);
            }
        });

        nbConnectTilesCard.setText("0");
        nbConnectTilesCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nbConnectTilesCardActionPerformed(evt);
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
                nbOfPlayersActionPerformed(evt);
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

        horizontalLength.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
        horizontalLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalLengthActionPerformed(evt);
            }
        });
        horizontalLength.setSelectedIndex(8-2);

        verticalLength.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" }));
        verticalLength.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalLengthActionPerformed(evt);
            }
        });
        verticalLength.setSelectedIndex(8-2);
        
        /*
         * 
         * 
         * BEGIN OWN CODE
         * 
         * 
         */
        
        //Window
        
        setResizable(false);
        
        //Board
        
        tilesButtons = new LinkedList<JToggleButton>();
        connectionButtons = new LinkedList<JToggleButton>();
        tilesButtonsBackup = new LinkedList<Integer>();
        connectionButtonsBackup = new LinkedList<Integer>();
        
        tilesPanel = new javax.swing.JPanel();
        tilesPanel.setPreferredSize(new java.awt.Dimension(1080, 600));
          
        changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()), Integer.valueOf(verticalLength.getSelectedItem().toString()));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        
        /*
         * 
         * END OWN CODE
         * 
         * 
         */    

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(6, 6, 6)
                                .addComponent(cardsLeft)
                                .addGap(52, 52, 52)
                                .addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(31, 31, 31)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(nbRemoveConnectionCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(nbConnectTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel16)))
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(addConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(removeConnectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(chosenPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(selectPositionButton, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(addTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(removeTileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(61, 61, 61)
                                        .addComponent(horizontalLength, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(verticalLength, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(nbOfPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(applyChangesButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                //.addGap(376, 376, 376))
                        .addGap(120, 120, 120))
            .addComponent(tilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(backButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(horizontalLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verticalLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(chosenPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectPositionButton)
                    .addComponent(nbOfPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel9)
                            .addComponent(tileType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addTileButton)
                            .addComponent(removeTileButton))
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(removeConnectionButton)
                            .addComponent(addConnectionButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton)
                        .addGap(13, 13, 13)
                        .addComponent(saveButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(applyChangesButton)
                            .addComponent(cardsLeft)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nbRollDieCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbRemoveConnectionCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbTeleportCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbLoseTurnCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nbConnectTilesCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tilesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void applyChangesButtonActionPerformed(java.awt.event.ActionEvent evt) {
       //Change board size
       changeBoardSize(Integer.valueOf(horizontalLength.getSelectedItem().toString()), Integer.valueOf(verticalLength.getSelectedItem().toString()));
       
       //Select Player
       if(buttonSelected == ButtonSelection.SELECT) {
           int playerNumber = Integer.valueOf(chosenPlayer.getSelectedItem().toString());
           
           if(playerNumber == 1) {
             for(JToggleButton button : tilesButtons) {
               if(button.isSelected()) {
                 button.setBackground(new java.awt.Color(240,10,10));
                 button.setSelected(false);
               }
             }
           }
           
           if(playerNumber == 2) {
             for(JToggleButton button : tilesButtons) {
               if(button.isSelected()) {
                 button.setBackground(new java.awt.Color(10,10,240));
                 button.setSelected(false);
                 
               }
             }
           }
           
           if(playerNumber == 3) {
             for(JToggleButton button : tilesButtons) {
               if(button.isSelected()) {
                 button.setBackground(new java.awt.Color(10,240,10));
                 button.setSelected(false);
               }
             }
           }
           
           if(playerNumber == 4) {
             for(JToggleButton button : tilesButtons) {
               if(button.isSelected()) {
                 button.setBackground(new java.awt.Color(240,240,10));
                 button.setSelected(false);
               }
             }
           }
       }
       
       //Enable all buttons
       selectPositionButton.setEnabled(true);
       addTileButton.setEnabled(true);
       removeTileButton.setEnabled(true);
       removeConnectionButton.setEnabled(true);
       addConnectionButton.setEnabled(true);
       selectPositionButton.setSelected(false);
       addTileButton.setSelected(false);
       removeTileButton.setSelected(false);
       removeConnectionButton.setSelected(false);
       addConnectionButton.setSelected(false);
       
      //Remove Tiles
       if(buttonSelected == ButtonSelection.REMOVETILE) {
           for(JToggleButton button : tilesButtons) {
             if(button.isSelected()) {
               button.setVisible(false);
             }
            }
       }
       
       //Remove Connections
       if(buttonSelected == ButtonSelection.REMOVECONN) {
           for(JToggleButton button : connectionButtons) {
             if(button.isSelected()) {
               button.setVisible(false);
             }
            }
       }
       
       //Reset Tiles
       if(buttonSelected == ButtonSelection.ADDTILE) {
          hideDisabledTiles();
       }
       
       //Reset Connections
       if(buttonSelected == ButtonSelection.ADDCONN) {
          hideDisabledConnections();
       }
       
       //Change colors for connections
      for(JToggleButton button : connectionButtons) {
         if(button.isSelected())
           button.setBackground(null);
         else
           button.setBackground(new java.awt.Color(0, 0, 0));
       }
       
       //Reset button
       applyChangesButton.setSelected(false);
       buttonSelected = ButtonSelection.NONE;
       
       //Repaint GUI
       repaint();
       revalidate();
    }

    private void chosenPlayerActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void removeTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(removeTileButton.isSelected()) {
      buttonSelected = ButtonSelection.REMOVETILE;
      backupLists();
      
        //Change buttons
       selectPositionButton.setEnabled(false);
       addTileButton.setEnabled(false);
       removeTileButton.setEnabled(true);
       removeConnectionButton.setEnabled(false);
       addConnectionButton.setEnabled(false);
      } else {
        resetUI();
      }
      
      //Repaint GUI
       repaint();
       revalidate();
    }

    private void tileTypeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void addTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(addTileButton.isSelected()) {
      buttonSelected = ButtonSelection.ADDTILE;
      backupLists();
      
        //Change buttons
       selectPositionButton.setEnabled(false);
       addTileButton.setEnabled(true);
       removeTileButton.setEnabled(false);
       removeConnectionButton.setEnabled(false);
       addConnectionButton.setEnabled(false);
       
       showDisabledTiles();
       } else {
        resetUI();
      }
       
       //Repaint GUI
       repaint();
       revalidate();
    }

    private void selectPositionButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(selectPositionButton.isSelected()) {
      buttonSelected = ButtonSelection.SELECT;
      backupLists();
      
        //Change buttons
       selectPositionButton.setEnabled(true);
       addTileButton.setEnabled(false);
       removeTileButton.setEnabled(false);
       removeConnectionButton.setEnabled(false);
       addConnectionButton.setEnabled(false);
       } else {
        resetUI();
      }
       
       //Repaint GUI
       repaint();
       revalidate();
    }

    private void removeConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(removeConnectionButton.isSelected()) {
      buttonSelected = ButtonSelection.REMOVECONN;
      backupLists();
      
        //Change buttons
       selectPositionButton.setEnabled(false);
       addTileButton.setEnabled(false);
       removeTileButton.setEnabled(false);
       removeConnectionButton.setEnabled(true);
       addConnectionButton.setEnabled(false);
       
        //Change colors for connections
      for(JToggleButton button : connectionButtons) {
         button.setBackground(null);
       }
      } else {
        resetUI();
      }
       
       //Repaint GUI
       repaint();
       revalidate();
    }

    private void addConnectionButtonActionPerformed(java.awt.event.ActionEvent evt) {
      if(addConnectionButton.isSelected()) {
      buttonSelected = ButtonSelection.ADDCONN;
      
      backupLists();
      
        //Change buttons
       selectPositionButton.setEnabled(false);
       addTileButton.setEnabled(false);
       removeTileButton.setEnabled(false);
       removeConnectionButton.setEnabled(false);
       addConnectionButton.setEnabled(true);
       
      showDisabledConnections();
      } else {
        resetUI();
      }
       
       //Repaint GUI
       repaint();
       revalidate();
    }

    private void nbRollDieCardActionPerformed(java.awt.event.ActionEvent evt) {
      // Change cards left
      changeNumberOfCardsLeft();
    }

    private void nbRemoveConnectionCardActionPerformed(java.awt.event.ActionEvent evt) {
      // Change cards left
      changeNumberOfCardsLeft();
    }

    private void nbTeleportCardActionPerformed(java.awt.event.ActionEvent evt) {
      // Change cards left
      changeNumberOfCardsLeft();
    }

    private void nbLoseTurnCardActionPerformed(java.awt.event.ActionEvent evt) {
        // Change cards left
      changeNumberOfCardsLeft();
    }

    private void nbConnectTilesCardActionPerformed(java.awt.event.ActionEvent evt) {
        // Change cards left
      changeNumberOfCardsLeft();
    }
    
    private void tileActionPerformed(java.awt.event.ActionEvent evt) {
      if(buttonSelected == ButtonSelection.NONE) {
        JToggleButton button = (JToggleButton)evt.getSource();
        button.setSelected(false);
        button.setBorderPainted( false );
        button.setFocusPainted( false );
      }
      
      if(buttonSelected == ButtonSelection.ADDTILE || buttonSelected == ButtonSelection.REMOVECONN || buttonSelected == ButtonSelection.ADDCONN) {
        JToggleButton button = (JToggleButton)evt.getSource();
        if(button.isSelected()) {
          button.setSelected(false);
          button.setBorderPainted(false);
          button.setFocusPainted(false);
        }
      }
    }
    
    private void connectionActionPerformed(java.awt.event.ActionEvent evt) {
       if(buttonSelected == ButtonSelection.ADDTILE || buttonSelected == ButtonSelection.REMOVETILE || buttonSelected == ButtonSelection.ADDCONN) {
        JToggleButton button = (JToggleButton)evt.getSource();
        if(button.isSelected()) {
          button.setSelected(false);
          button.setBorderPainted(false);
          button.setFocusPainted(false);
        }
      }
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void nbOfPlayersActionPerformed(java.awt.event.ActionEvent evt) {
        String nbOfPlayersChosen = (String) nbOfPlayers.getSelectedItem();
        switch (nbOfPlayersChosen) { //Setting number of pkayers in game, to send to controller
            case "2":
                numOfPlayersInGame = "2";
                break;
            case "3":
                numOfPlayersInGame = "3";
                break;
            case "4":
                numOfPlayersInGame = "4";
                break;
        }
        if (nbOfPlayers.getSelectedItem()== "4") { //Setting value of combo box for choosing a player to defined number of players
            chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1", "2", "3", "4"}));}
        else if(nbOfPlayers.getSelectedItem()== "3"){
            chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1", "2", "3"}));}
        else{
            chosenPlayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        }
    }

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //changeBoardSize(10,10);
    }

    private void horizontalLengthActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void verticalLengthActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TileODesignUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TileODesignUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TileODesignUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TileODesignUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TileODesignUI().setVisible(true);
            }
        });
    }

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
