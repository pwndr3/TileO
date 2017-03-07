package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import java.util.ArrayList;
import java.util.List;

public class DesignController {

	public DesignController(Game aGame) {
		game = aGame;
	}

	/*
	 * public boolean startingPosition(Tile tile, int playerNumber) throws
	 * InvalidInputException{ //Get info about current game TileO tileo =
	 * TileOApplication.getTileO(); Game game = tileo.getCurrentGame(); List
	 * <Player> allPlayers = game.getPlayers();
	 * 
	 * //Validation check if(!(game.hasPlayers())){ throw new
	 * InvalidInputException("There are no players in the current game."); }
	 * else if(playerNumber >= game.numberOfPlayers()){ throw new
	 * InvalidInputException("Player selected is not in the current game."); }
	 * 
	 * //Get the specific player Player player = allPlayers.get(playerNumber);
	 * 
	 * //Exceptions if(tile==game.getWinTile()) { throw new
	 * InvalidInputException("Cannot place a player on the win tile."); } else
	 * if(tile instanceof ActionTile ) { throw new
	 * InvalidInputException("Cannot place a player on an action tile."); } else
	 * { for(int i=0; i<game.numberOfPlayers(); i++) { Player otherPlayer =
	 * allPlayers.get(i); if(otherPlayer.hasStartingTile()) {
	 * if(otherPlayer.getStartingTile() == tile) { //this one too throw new
	 * InvalidInputException("This tile is already a start tile for another player"
	 * ); } } } }
	 * 
	 * //Set players start tile return player.setStartingTile(tile); }
	 */

	public boolean createWinTile(int x, int y) {
		// Check if the game already has a win tile
		if (game.hasWinTile()) {
			WinTile prevWin = game.getWinTile();
			List<Connection> tileConnections = prevWin.getConnections();
			int prevX = prevWin.getX();
			int prevY = prevWin.getY();

			deleteTile(prevX, prevY);

			// Replace the tile with a win tile
			NormalTile normalTile = new NormalTile(x, y, game);

			for (int i = tileConnections.size() - 1; i > 0; i--) {
				normalTile.addConnection(tileConnections.get(i));
			}

		}

		// Get all info about the tile that will be replaced
		List<Connection> tileConnections = game.getTileFromXY(x, y).getConnections();
		game.getTileFromXY(x, y).delete();

		// Replace the tile with a win tile
		WinTile winTile = new WinTile(x, y, game);

		for (int i = tileConnections.size() - 1; i > 0; i--) {
			winTile.addConnection(tileConnections.get(i));
		}

		// Set win tile to the game
		return (game.setWinTile(winTile));
	}

	public boolean createNormalTile(int x, int y) {
		game.addTile(new NormalTile(x, y, game));

		return true;
	}

	public boolean deleteTile(int x, int y) {
		// delete the tile
		if (game.getTileFromXY(x, y) != null)
			game.getTileFromXY(x, y).delete();
		return true;
	}

	public boolean createActionTile(int x, int y) {
		// the initial coordinates of x and y, and connections are saved
		List<Connection> connections = game.getTileFromXY(x, y).getConnections();
		// delete the tile
		game.getTileFromXY(x, y).delete();

		// an action tile created at the previous location
		ActionTile actionTile = new ActionTile(x, y, game, 1); // inactivity
																// period 1 but
																// will be
																// implemented
																// otherwise for
																// deliverable 4
		for (int i = connections.size() - 1; i >= 0; i--) {
			actionTile.addConnection(connections.get(i));
		}

		game.addTile(actionTile);
		return true;
	}

	public void removeConnection(int x, int y) {
		// Below is method in game class that removes connection.
		// game.getConnectionFromXY(x,y).delete();
	}

	public void connectTiles(int x, int y, boolean isHorizontal) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();

		for (Tile tile : game.getTiles()) {
			if (isHorizontal) {
				if (tile.getX() == x - 1 && tile.getY() == y) {
					tiles.add(tile);
				}

				if (tile.getX() == x + 1 && tile.getY() == y) {
					tiles.add(tile);
				}
			} else {

				if (tile.getX() == x && tile.getY() == y - 1) {
					tiles.add(tile);
				}

				if (tile.getX() == x && tile.getY() == y + 1) {
					tiles.add(tile);
				}
			}
		}

		// Created "placeConnection" method in game class that is used by
		// ConnectTiles Action Card as well.
		game.placeConnection(tiles.get(0), tiles.get(1));
	}

	/*
	 * public boolean createDeck(int connectTiles, int loseTurn, int
	 * removeConnection, int rollDie, int teleport) throws
	 * InvalidInputException{ //Get info about current game TileO tileo =
	 * TileOApplication.getTileO(); Game game = tileo.getCurrentGame(); Deck
	 * deck = game.getDeck(); //clear any previous cards added deck.clearDeck();
	 * //Exception if there are too many cards added if(connectTiles + loseTurn
	 * + removeConnection + rollDie + teleport > 32){ throw new
	 * InvalidInputException("Cannot have more than 32 cards in a deck."); }
	 * //add cards to the deck for(int i=0; i<=connectTiles; i++){
	 * ConnectTilesActionCard card = new
	 * ConnectTilesActionCard("Connect two tiles", deck); deck.add(card); }
	 * for(int j=0; j<=loseturn; j++){ LoseTurnActionCard card = new
	 * LoseTurnActionCard("You lose a turn", deck); deck.add(card); } for(int
	 * k=0; k<=removeConnection; k++){ RemoveConnectionActionCard card = new
	 * RemoveConnectionActionCard("Remove Connection", deck); deck.add(card); }
	 * for(int x=0; x<=rollDie; x++){ RollDieActionCard card = new
	 * RollDieActionCard("Roll die again", deck); deck.add(card); } for(int y=0;
	 * y<=teleport; y++){ TeleportActionCard card = new
	 * TeleportActionCard("Teleport anywhere on the board", deck);
	 * deck.add(card); } return deck.hasCards(); }
	 */

	public void initGame(int numOfPlayersInGame) {
		/*
		 * Game aGame = TileOApplication.getCurrentGame();
		 * 
		 * for(int n = 1; n <= numOfPlayersInGame; n++){ Player player = new
		 * Player(n,aGame); switch(n) { case 1:
		 * player.setColor(Player.Color.RED); break; case 2:
		 * player.setColor(Player.Color.BLUE); break; case 3:
		 * player.setColor(Player.Color.GREEN); break; case 4:
		 * player.setColor(Player.Color.YELLOW); break; }
		 * aGame.addPlayer(player); }
		 */
	}

	public void changeNumberOfPlayers(int numOfPlayers) {
		/*
		 * Game aGame = TileOApplication.getCurrentGame();
		 * 
		 * for(Player player : aGame.getPlayers()) { aGame.removePlayer(player);
		 * }
		 * 
		 * for(int n = 1; n <= numOfPlayers; n++){ Player player = new
		 * Player(n,aGame); switch(n) { case 1:
		 * player.setColor(Player.Color.RED); break; case 2:
		 * player.setColor(Player.Color.BLUE); break; case 3:
		 * player.setColor(Player.Color.GREEN); break; case 4:
		 * player.setColor(Player.Color.YELLOW); break; }
		 * 
		 * aGame.addPlayer(player); }
		 */
	}

	//
	private Game game;
}
