package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.Controller;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.view.popup.ActionCardPopUp;

import java.util.List;
import java.util.stream.Collectors;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.*;

public class MainPage extends javax.swing.JFrame {

    /**
     * Creates new form MainPage
     */
    public MainPage() {
    	controller = new Controller(this);
		
		//Init window
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			//Too bad
		}
		
        initComponents();
        
        TileOApplication.load();
        
        designableGames = TileOApplication.getTileO().getGames().parallelStream().filter(s -> !s.hasStarted).collect(Collectors.toList());
        playableGames = TileOApplication.getTileO().getGames().parallelStream().filter(s -> s.hasStarted || s.getMode() != Game.Mode.DESIGN).collect(Collectors.toList());
        
        for(Game game : designableGames)
        	designModel.addElement(game.getGameName());
        	
        for(Game game : playableGames)
        	playModel.addElement(game.getGameName());
        
        if(designModel.getSize() == 0)
        	designModel.addElement("No designable game");
        if(playModel.getSize() == 0)
        	playModel.addElement("No playable game");
    }
    
    private void updateLists() {
    	designModel.removeAllElements();
    	playModel.removeAllElements();
    	
    	designableGames = TileOApplication.getTileO().getGames().parallelStream().filter(s -> !s.hasStarted).collect(Collectors.toList());
        playableGames = TileOApplication.getTileO().getGames().parallelStream().filter(s -> s.hasStarted || s.getMode() != Game.Mode.DESIGN).collect(Collectors.toList());
        
        for(Game game : designableGames)
        	designModel.addElement(game.getGameName());
        	
        for(Game game : playableGames)
        	playModel.addElement(game.getGameName());
        
        if(designModel.getSize() == 0)
        	designModel.addElement("No designable game");
        if(playModel.getSize() == 0)
        	playModel.addElement("No playable game");
    }

    private void initComponents() {
    	ImageIcon backgroundImage = new javax.swing.ImageIcon(getClass().getResource("/icons/tileomainpage.jpg"));
    	
    	designModel = new DefaultComboBoxModel<>();
    	playModel = new DefaultComboBoxModel<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        loadGameToDesignComboBox = new javax.swing.JComboBox<>();
        loadGameToPlayComboBox = new javax.swing.JComboBox<>();
        deleteDesignButton = new javax.swing.JButton();
        loadDesignButton = new javax.swing.JButton();
        loadPlayButton = new javax.swing.JButton();
        deletePlayButton = new javax.swing.JButton();
        designNewGameButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabel3.setFont(new java.awt.Font("Songti SC", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Design");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(180, 190, 120, 40);

        jLabel1.setFont(new java.awt.Font("Songti SC", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Play");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(700, 190, 120, 40);

        loadGameToDesignComboBox.setBackground(new java.awt.Color(197, 242, 250));
        loadGameToDesignComboBox.setModel(designModel);
        jPanel1.add(loadGameToDesignComboBox);
        loadGameToDesignComboBox.setBounds(140, 240, 180, 27);

        loadGameToPlayComboBox.setBackground(new java.awt.Color(197, 242, 250));
        loadGameToPlayComboBox.setModel(playModel);
        loadGameToPlayComboBox.setToolTipText("");
        jPanel1.add(loadGameToPlayComboBox);
        loadGameToPlayComboBox.setBounds(650, 240, 180, 27);

        deleteDesignButton.setBackground(new java.awt.Color(197, 242, 250));
        deleteDesignButton.setText("Delete");
        jPanel1.add(deleteDesignButton);
        deleteDesignButton.setBounds(230, 290, 90, 29);
        deleteDesignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDesignButtonActionPerformed(evt);
            }
        });

        loadDesignButton.setBackground(new java.awt.Color(197, 242, 250));
        loadDesignButton.setText("Load");
        loadDesignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadDesignButtonActionPerformed(evt);
            }
        });
        jPanel1.add(loadDesignButton);
        loadDesignButton.setBounds(140, 290, 90, 29);

        loadPlayButton.setBackground(new java.awt.Color(197, 242, 250));
        loadPlayButton.setText("Load");
        loadPlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadPlayButtonActionPerformed(evt);
            }
        });
        jPanel1.add(loadPlayButton);
        loadPlayButton.setBounds(650, 290, 90, 29);

        deletePlayButton.setBackground(new java.awt.Color(197, 242, 250));
        deletePlayButton.setText("Delete");
        deletePlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePlayButtonActionPerformed(evt);
            }
        });
        jPanel1.add(deletePlayButton);
        deletePlayButton.setBounds(740, 290, 90, 29);

        designNewGameButton.setBackground(new java.awt.Color(255, 207, 0));
        designNewGameButton.setFont(new java.awt.Font("Helvetica", 1, 24)); // NOI18N
        designNewGameButton.setForeground(new java.awt.Color(51, 51, 51));
        designNewGameButton.setText("Design New Game");
        designNewGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                designNewGameButtonActionPerformed(evt);
            }
        });
        jPanel1.add(designNewGameButton);
        

        jLabel2.setIcon(backgroundImage); // NOI18N
        jLabel2.setText("");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, -10, 980, 670);

        jLabel4.setText("");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(460, 260, 45, 16);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        
        setResizable(false);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        //setUndecorated(true);
        setLocationRelativeTo(null);
        setLocation(new Double(width/2).intValue()-980/2, new Double(height/2).intValue()-654/2);
        
        designNewGameButton.setMargin(new Insets(0,0,0,0));
        designNewGameButton.setBounds(330, 520, 980/4, 50);

        pack();
    }

    private void loadDesignButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if(!designableGames.isEmpty()) {
    		controller.design(String.valueOf(loadGameToDesignComboBox.getSelectedItem()));
    	} else {
    		new PopUpManager(this).acknowledgeMessage("No game selected, cannot design");
    	}
    }

    private void loadPlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if(!playableGames.isEmpty()) {
    		controller.play(String.valueOf(loadGameToPlayComboBox.getSelectedItem()));
    	} else {
    		new PopUpManager(this).acknowledgeMessage("No game selected, cannot play");
    	}
    }

    private void deleteDesignButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	if(!designableGames.isEmpty()) {
        	if(new PopUpManager(this).askYesOrNo("Are you sure you want to delete the game?") == 0) {
        		if(TileOApplication.getTileO().removeGame(TileOApplication.getTileO().getGameByName(String.valueOf(loadGameToDesignComboBox.getSelectedItem())))) {
        			new PopUpManager(this).acknowledgeMessage("Game deleted");
        			updateLists();
        			TileOApplication.save();
        		}
        		else
        			new PopUpManager(this).errorMessage("Game not deleted");
        		
        	}
    	} else {
    		new PopUpManager(this).acknowledgeMessage("No game selected to delete");
    	}
    }

    private void deletePlayButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	TileO tileo = TileOApplication.getTileO();
    	if(!playableGames.isEmpty()) {
        	if(new PopUpManager(this).askYesOrNo("Are you sure you want to delete the game?") == 0) {
        		if(TileOApplication.getTileO().removeGame(TileOApplication.getTileO().getGameByName(String.valueOf(loadGameToPlayComboBox.getSelectedItem())))) {
	        		new PopUpManager(this).acknowledgeMessage("Game deleted");
	        		updateLists(); 
	        		TileOApplication.save();
        		}
        		else
        			new PopUpManager(this).errorMessage("Game not deleted");
        	}
    	} else {
    		new PopUpManager(this).acknowledgeMessage("No game selected to delete");
    	}
    }

    private void designNewGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	controller.design(null);
    }

    private javax.swing.JButton deleteDesignButton;
    private javax.swing.JButton deletePlayButton;
    private javax.swing.JButton designNewGameButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loadDesignButton;
    private javax.swing.JComboBox<String> loadGameToDesignComboBox;
    private javax.swing.JComboBox<String> loadGameToPlayComboBox;
    private javax.swing.JButton loadPlayButton;
    DefaultComboBoxModel<String> designModel;
    DefaultComboBoxModel<String> playModel;
    private Controller controller;
    
    private List<Game> designableGames;
    private List<Game> playableGames;
}


