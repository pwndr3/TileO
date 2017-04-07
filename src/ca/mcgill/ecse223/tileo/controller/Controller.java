package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;

public class Controller {

	public Controller(MainPage theUI) {
		ui = theUI;
	}

	public void play(String gameName) {
		Game game = null;
		
		if(gameName != null)
			game = TileOApplication.getTileO().getGameByName(gameName);
		
		if(game.getMode() != Game.Mode.DESIGN) {
			if(game.hasStarted) {
				new TileOPlayUI(game).setVisible(true);
				ui.dispose();
			}
			else {
				cloneGame(game); //and play if everything's all right
			}
		}
		
		else {
			new PopUpManager(ui).acknowledgeMessage("Cannot play, game is not yet playable.");
		}
	}
	
	public void cloneGame(Game game) {
	    game = game.clone();
	    TileOApplication.getTileO().addGame(game);
	    TileOApplication.getTileO().setCurrentGame(game);
	    
	    boolean wasProcessed = false;
		
		while(!wasProcessed) {
			String newName = null;
			while((newName = new PopUpManager(ui).askSaveName(game.getGameName())) == "");
				
			if(newName != null) {
				boolean nameExists = false;
				
				for(Game gameFromTileO : TileOApplication.getTileO().getGames()) {
					if(gameFromTileO != null && newName.equals(gameFromTileO.getGameName()))
						nameExists = true;
				}
				
				if(!nameExists) {
					game.setGameName(newName);
					wasProcessed = true;
				} else {
					new PopUpManager(ui).errorMessage("Game name already exists");
				}
			} else 
				return;
				
		}
		
		new TileOPlayUI(game).setVisible(true);
		ui.dispose();
	}

	public void design(String gameName) {
		Game game = null;
		
		if(gameName != null)
			game = TileOApplication.getTileO().getGameByName(gameName);
		
		if(game == null) {
			new TileODesignUI(null).setVisible(true);
			ui.dispose();
		}
		
		else if(!game.hasStarted) {
			//Can design
			
			new TileODesignUI(game).setVisible(true);
			ui.dispose();
		} else {
			new PopUpManager(ui).acknowledgeMessage("Cannot design, game has already begun.");
		}
	}

	private MainPage ui;
}
