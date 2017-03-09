package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;

public class Controller {

	public Controller(MainPage theUI) {
		ui = theUI;
	}

	public void play() {
		//TODO : Choose game
		Game game = TileOApplication.getTileO().getCurrentGame();
		
		if(game.getMode() == Game.Mode.GAME) {
			new TileOPlayUI(game).setVisible(true);
			ui.dispose();
		} else {
			//TODO : MessageBox - Not playable
		}
	}

	public void design() {
		//TODO : Choose game
		Game game = TileOApplication.getTileO().getCurrentGame();
		
		if(game == null) {
			game = new Game(32, TileOApplication.getTileO());
			game.setMode(Game.Mode.DESIGN);
			TileOApplication.getTileO().addGame(game);
			TileOApplication.getTileO().setCurrentGame(game);
			
			//Create 2 players
			Player.clearPlayers();
			Player player = new Player(1, game);
			player.setColor(Player.Color.RED);
			game.addPlayer(player);
			player = new Player(2, game);
			player.setColor(Player.Color.BLUE);
			game.addPlayer(player);
		}
		
		if(!game.hasStarted) {
			//Can design
			
			new TileODesignUI(game).setVisible(true);
			ui.dispose();
		} else {
			//TODO : MessageBox : Cannot design - game has started
		}
	}

	private MainPage ui;
}
