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

	// **Do players start from 0 or 1, need to fix that for the for loop**
	public boolean startingPosition(Tile tile, int playerNumber) throws InvalidInputException {

		// Get info about current game
		List<Player> allPlayers = game.getPlayers();

		// Validation check
		if (!(game.hasPlayers())) {
			throw new InvalidInputException("There are no players in the current game.");
		}

		else if (playerNumber >= game.numberOfPlayers()) {
			throw new InvalidInputException("Player selected is not in the current game.");
		}
		// Get the specific player
		Player player = allPlayers.get(playerNumber);
		// Exceptions
		if (tile == game.getWinTile()) {
			throw new InvalidInputException("Cannot place a player on the win tile.");
		} else if (tile instanceof ActionTile) {
			throw new InvalidInputException("Cannot place a player on an action tile.");
		} else {
			for (int i = 1; i <= game.numberOfPlayers(); i++) {
				Player otherPlayer = allPlayers.get(i);
				if (otherPlayer.hasStartingTile()) {
					if (otherPlayer.getStartingTile() == tile) { // this one too
						throw new InvalidInputException("This tile is already a start tile for another player");
					}
				}
			}
		}
		// Set player's start tile
		return player.setStartingTile(tile);
	}

	public boolean createWinTile(Tile winTile) {
		// Check if the game already has a win tile
		if (game.hasWinTile()) {
			WinTile prevWin = game.getWinTile();
			List<Connection> tileConnections = prevWin.getConnections();
			int prevX = prevWin.getX();
			int prevY = prevWin.getY();

			try {
				deleteTile(prevWin);
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Replace the delelted with a win Tile
			// **Check if necessary to send game**
			NormalTile normalTile = new NormalTile(prevX, prevY, game);

			for (Connection conn : tileConnections) {
				// normalTile.addConnection(tileConnections.get(i));
				normalTile.addConnection(conn);
			}

		}
		// Get the X and Y cordinates
		int winX = winTile.getX();
		int winY = winTile.getY();

		// Get all info about the tile that will be replaced
		List<Connection> tileConnections = game.getTileFromXY(winX, winY).getConnections();
		game.getTileFromXY(winX, winY).delete();
		// game.deleteTile(win)

		// Replace the tile with a win tile
		WinTile newWinTile = new WinTile(winX, winY, game);

		// Add previous connections to winTile
		for (Connection conn : tileConnections) {
			newWinTile.addConnection(conn);
		}

		// Set win tile to the game
		return (game.setWinTile(newWinTile));
	}

	public boolean createNormalTile(int x, int y) {
		game.addTile(new NormalTile(x, y, game));

		return true;
	}

	public boolean deleteTile(Tile tile) throws InvalidInputException {
		// delete the tile
		if (tile == null) {
			throw new InvalidInputException("There is no tile to delete");
		}

		int x = tile.getX();
		int y = tile.getY();
		game.getTileFromXY(x, y).delete();
		return true;
	}

	public boolean createActionTile(Tile tile, int disableTurn) {
		// the initial coordinates of x and y, and connections are saved
		int x = tile.getX();
		int y = tile.getY();

		List<Connection> tileConnections = game.getTileFromXY(x, y).getConnections();

		// delete the tile
		game.getTileFromXY(x, y).delete();

		// an action tile created at the previous location
		ActionTile actionTile = new ActionTile(x, y, game, disableTurn); // inactivity
		// period 1 but
		// will be
		// implemented
		// otherwise for
		// deliverable 4

		for (Connection conn : tileConnections) {
			actionTile.addConnection(conn);
		}

		game.addTile(actionTile);
		return true;
	}

	public void removeConnection(Tile tile1, Tile tile2) throws InvalidInputException {
		// Check if we can delete connection

		List<Connection> conns = tile1.getConnections();

		for (Connection conn : conns) {
			List<Tile> tiles = conn.getTiles();
			{
				for (Tile tile : tiles) {
					if (tile == tile2) {
						conn.delete();
						return;
					}
				}
			}
		}
		// Throw exception
		throw new InvalidInputException("No connection to be deleted");
	}

	public void connectTiles(Tile tile1, Tile tile2) throws InvalidInputException {

		int x1 = tile1.getX();
		int y1 = tile1.getY();

		int x2 = tile2.getX();
		int y2 = tile2.getY();

		// Check if tiles are adjancent to one another
		if (x1 == x2 && Math.abs((y1 - y2)) == 1) {
			// You made connetion
			game.placeConnection(tile1, tile2);
		}

		if (y1 == y2 && Math.abs((x1 - x2)) == 1) {
			// You made connection
			game.placeConnection(tile1, tile2);
		}

		throw new InvalidInputException("No connection to be deleted");

	}

	public boolean createDeck(int connectTiles, int loseTurn, int removeConnection, int rollDie, int teleport)
			throws InvalidInputException {
		// Get info about current game
		// TileO tileo = TileOApplication.getTileO();
		// Game game = tileo.getCurrentGame();
		Deck deck = new Deck(game);
		// clear any previous cards added
		// deck.clearDeck();
		// Exception if there are too many cards added
		if (connectTiles + loseTurn + removeConnection + rollDie + teleport > 32) {
			throw new InvalidInputException("Cannot have more than 32 cards in a deck.");
		}
		// add cards to the deck
		for (int i = 0; i <= connectTiles; i++) {
			ConnectTilesActionCard card = new ConnectTilesActionCard("Connect two tiles", deck);
			deck.addCard(card);
		}
		for (int j = 0; j <= loseTurn; j++) {
			LoseTurnActionCard card = new LoseTurnActionCard("You lose a turn", deck);
			deck.addCard(card);
		}
		for (int k = 0; k <= removeConnection; k++) {
			RemoveConnectionActionCard card = new RemoveConnectionActionCard("Remove Connection", deck);
			deck.addCard(card);
		}
		for (int x = 0; x <= rollDie; x++) {
			RollDieActionCard card = new RollDieActionCard("Roll die again", deck);
			deck.addCard(card);
		}
		for (int y = 0; y <= teleport; y++) {
			TeleportActionCard card = new TeleportActionCard("Teleport anywhere on the board", deck);
			deck.addCard(card);
		}
		return deck.hasCards();
	}
	//done 
	public void initGame(int numOfPlayersInGame) {
		
		TileOApplication.getTileO().removeGame(game);
		
		Game newGame = new Game(32, TileOApplication.getTileO());
		newGame.setMode(Game.Mode.DESIGN);
		TileOApplication.getTileO().addGame(newGame);
		TileOApplication.getTileO().setCurrentGame(newGame);
		
		for (int n = 1; n <= numOfPlayersInGame; n++) {
			Player player = new Player(n, newGame);
			switch (n) {
			case 1:
				player.setColor(Player.Color.RED);
				break;
			case 2:
				player.setColor(Player.Color.BLUE);
				break;
			case 3:
				player.setColor(Player.Color.GREEN);
				break;
			case 4:
				player.setColor(Player.Color.YELLOW);
				break;
			}
			game.addPlayer(player);
		}
	}

	public void changeNumberOfPlayers(int numOfPlayers) {
		if (!(numOfPlayers == game.numberOfPlayers())) {
			// if different, you will reset the same completely
			return;
		}

	}

	//
	private Game game;
}
