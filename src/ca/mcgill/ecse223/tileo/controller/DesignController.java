package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DesignController {

	public DesignController(Game aGame, TileODesignUI aUI) {
		game = aGame;
		ui = aUI;
		
		rn = new Random();
	}

	// Player [0..3]*
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
		if (game.hasWinTile()) {
			if (tile == game.getWinTile()) {
				throw new InvalidInputException("Cannot place a player on the win tile.");
			}
		} else if (tile instanceof ActionTile) {
			throw new InvalidInputException("Cannot place a player on an action tile.");
		} else {
			for (int i = 0; i < game.numberOfPlayers(); i++) {
				Player otherPlayer = allPlayers.get(i);
				if (otherPlayer.hasStartingTile()) {
					if (otherPlayer.getStartingTile() == tile  && otherPlayer != player) { // this one too
						throw new InvalidInputException("This tile is already assigned to another player");
					} else if (otherPlayer.getStartingTile() == tile && otherPlayer == player) {
						throw new InvalidInputException("The player's already there no worries");
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
			int prevX = prevWin.getX();
			int prevY = prevWin.getY();

			List<Tile> tilesToConnectTo = new ArrayList<Tile>();

			for (Connection conn : prevWin.getConnections()) {
				if (conn.getTile(0) != prevWin)
					tilesToConnectTo.add(conn.getTile(0));
				if (conn.getTile(1) != prevWin)
					tilesToConnectTo.add(conn.getTile(1));
			}

			try {
				deleteTile(prevWin);
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}

			// Replace the deleted with a win Tile
			NormalTile normalTile = new NormalTile(prevX, prevY, game);

			for (Tile tileToConnectTo : tilesToConnectTo) {
				try {
					connectTiles(normalTile, tileToConnectTo);
				} catch (InvalidInputException e) {
					e.printStackTrace();
				}
			}
		}
		// Get the X and Y coordinates
		int winX = winTile.getX();
		int winY = winTile.getY();

		Tile tileToBeDeleted = game.getTileFromXY(winX, winY);

		List<Tile> tilesToConnectTo = new ArrayList<Tile>();

		for (Connection conn : tileToBeDeleted.getConnections()) {
			if (conn.getTile(0) != tileToBeDeleted)
				tilesToConnectTo.add(conn.getTile(0));
			if (conn.getTile(1) != tileToBeDeleted)
				tilesToConnectTo.add(conn.getTile(1));
		}

		// delete the tile
		tileToBeDeleted.delete();

		// Replace the tile with a win tile
		WinTile newWinTile = new WinTile(winX, winY, game);

		for (Tile tileToConnectTo : tilesToConnectTo) {
			try {
				connectTiles(newWinTile, tileToConnectTo);
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
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

		if (tile instanceof WinTile) {
			game.setWinTile(null);
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

		Tile tileToBeDeleted = game.getTileFromXY(x, y);

		List<Tile> tilesToConnectTo = new ArrayList<Tile>();

		for (Connection conn : tileToBeDeleted.getConnections()) {
			if (conn.getTile(0) != tileToBeDeleted)
				tilesToConnectTo.add(conn.getTile(0));
			if (conn.getTile(1) != tileToBeDeleted)
				tilesToConnectTo.add(conn.getTile(1));
		}

		// delete the tile
		tileToBeDeleted.delete();

		// an action tile created at the previous location
		ActionTile actionTile = new ActionTile(x, y, game, disableTurn); // inactivity
		// period 1 but
		// will be
		// implemented
		// otherwise for
		// deliverable 4

		for (Tile tileToConnectTo : tilesToConnectTo) {
			try {
				connectTiles(actionTile, tileToConnectTo);
			} catch (InvalidInputException e) {
				e.printStackTrace();
			}
		}

		game.addTile(actionTile);

		return true;
	}

	public void removeConnection(Tile tile1, Tile tile2) throws InvalidInputException {
		// Check if we can delete connection

		List<Connection> conns = tile1.getConnections();

		for (Connection conn : conns) {
			List<Tile> tiles = conn.getTiles();
			for (Tile tile : tiles) {
				if (tile == tile2) {
					conn.delete();
					return;
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

		// If not already connected
		if (!tile1.getConnections().parallelStream().filter(s -> s != null)
				.anyMatch(s -> s.getTile(0) == tile2 || s.getTile(1) == tile2)) {
			// Check if tiles are adjacent to one another
			if (x1 == x2 && Math.abs((y1 - y2)) == 1) {
				game.placeConnection(tile1, tile2);
			}

			if (y1 == y2 && Math.abs((x1 - x2)) == 1) {
				game.placeConnection(tile1, tile2);
			}
		}
	}
	
	public boolean createDeck(int rollDie, int removeConnection, int teleport, int loseTurn, int connectTiles, int sendBack,
			int additionalMove, int nextRollsOne, int showActionTiles, int moveWinTile, int movePlayer, int inactivityPeriod)
			throws InvalidInputException {
		// Exception if there are too many cards added
		if (connectTiles + loseTurn + removeConnection + rollDie + teleport + sendBack + additionalMove
				+ nextRollsOne + showActionTiles + moveWinTile + movePlayer + inactivityPeriod > 32) {
			throw new InvalidInputException("Cannot have more than 32 cards in a deck.");
		}

		Deck deck = game.getDeck();
		deck.clearDeck();

		// add cards to the deck
		for (int i = 0; i < connectTiles; i++) {
			ConnectTilesActionCard card = new ConnectTilesActionCard("Connect two tiles", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < loseTurn; i++) {
			LoseTurnActionCard card = new LoseTurnActionCard("You lose a turn", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < removeConnection; i++) {
			RemoveConnectionActionCard card = new RemoveConnectionActionCard("Remove a connection", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < rollDie; i++) {
			RollDieActionCard card = new RollDieActionCard("Roll die again", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < teleport; i++) {
			TeleportActionCard card = new TeleportActionCard("Teleport anywhere on the board", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < nextRollsOne; i++) {
			NextRollsOneActionCard card = new NextRollsOneActionCard("Next player rolls one", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < showActionTiles; i++) {
			ShowActionTilesActionCard card = new ShowActionTilesActionCard("Display all the action tiles", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < moveWinTile; i++) {
			MoveWinTileActionCard card = new MoveWinTileActionCard("Move win tile", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < movePlayer; i++) {
			MovePlayerActionCard card = new MovePlayerActionCard("Select player to move", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < sendBack; i++) {
			SendBackToStartActionCard card = new SendBackToStartActionCard("Select player to send back to start", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < additionalMove; i++) {
			AdditionalMoveActionCard card = new AdditionalMoveActionCard("Choose die's destiny", deck);
			deck.addCard(card);
		}
		for (int i = 0; i < inactivityPeriod; i++) {
			InactivityPeriodActionCard card = new InactivityPeriodActionCard("Inactivity period randomly selected", deck);
			deck.addCard(card);
		}
		return deck.hasCards();
	}

	// done
	public Game initGame(int numOfPlayersInGame) {
		if (game != null)
			TileOApplication.getTileO().removeGame(game);

		game = new Game(32, TileOApplication.getTileO());
		game.setMode(Game.Mode.DESIGN);
		TileOApplication.getTileO().addGame(game);
		TileOApplication.getTileO().setCurrentGame(game);

		Player.clearPlayers();
		for (int n = 0; n < numOfPlayersInGame; n++) {
			Player player = new Player(n, game);
			switch (n) {
			case 0:
				player.setColor(Player.Color.RED);
				break;
			case 1:
				player.setColor(Player.Color.BLUE);
				break;
			case 2:
				player.setColor(Player.Color.GREEN);
				break;
			case 3:
				player.setColor(Player.Color.YELLOW);
				break;
			}
			game.addPlayer(player);
		}

		return game;
	}

	public void saveGame(String gameName) {
		boolean modeGame = true;

		game.setGameName(gameName);

		if (!game.hasWinTile())
			modeGame = false;

		for (Player player : game.getPlayers()) {
			if (!player.hasStartingTile())
				modeGame = false;
		}

		if (modeGame)
			game.setMode(Game.Mode.GAME);
		else
			game.setMode(Game.Mode.DESIGN);

		TileOApplication.save();
	}
	
	private int randomInt(int minimum, int maximum) {
		if(maximum - minimum < 1)
			return 0;
		
		return minimum + rn.nextInt(maximum - minimum + 1);
	}
	
	public void generateRandomBoard() {
		//Size
		ui.setupBoard(true);
		game = ui.getGame();
		
		//Players
		for(int i = 0; i < game.numberOfPlayers(); i++) {
			boolean taken;
			
			do {
				int randomIndexOfTile = randomInt(0, game.numberOfTiles()-1);
				taken = false;
				Tile tile = game.getTile(randomIndexOfTile);
				
				for(Player player : game.getPlayers()) {
					if(player.getStartingTile() == tile)
						taken = true;
				}
				
				if(!taken) {
					game.getPlayer(i).setStartingTile(tile);
				}
			} while(taken);
		}
		
		//Win tile
		boolean processDone = false;
		
		while(!processDone) {
			int randomIndexOfTile = randomInt(0, game.numberOfTiles()-1);
			boolean taken = false;
			Tile tile = game.getTile(randomIndexOfTile);
			
			for(Player player : game.getPlayers()) {
				if(player.getStartingTile() == tile)
					taken = true;
			}
			
			if(!taken) {
				createWinTile(tile);
				processDone = true;
				ui.getTileUIByXY(tile.getX(), tile.getY()).setState(TileUI.State.WIN);
			}
		}
		
		//Action tiles
		int lowerBound = (int)((game.numberOfTiles() - game.numberOfPlayers() - 2) * 0.15);
		int upperBound = (int)((game.numberOfTiles() - game.numberOfPlayers() - 2) * 0.25);
		int numberOfActionTiles = randomInt(lowerBound, upperBound);
		
		if(numberOfActionTiles == 0)
			numberOfActionTiles = 2;
		
		for(int i = 0; i < numberOfActionTiles; i++) {
			int inactivityPeriod = randomInt(1,9);
			processDone = false;
			
			while(!processDone) {
				int randomIndexOfTile = randomInt(0, game.numberOfTiles()-1);
				boolean taken = false;
				Tile tile = game.getTile(randomIndexOfTile);
				
				for(Player player : game.getPlayers()) {
					if(player.getStartingTile() == tile)
						taken = true;
				}
				
				if(tile instanceof ActionTile)
					taken = true;
				
				if(tile instanceof WinTile)
					taken = true;
				
				if(!taken) {
					createActionTile(tile, inactivityPeriod);
					ui.getTileUIByXY(tile.getX(), tile.getY()).setState(TileUI.State.ACTION);
					processDone = true;
				}
			}
		}
		
		//Action cards
		int numberOfActionCards = randomInt(10, 32);
		
		//nbRollDieCard
		//nbRemoveConnectionCard
		//nbTeleportCard
		//nbLoseTurnCard
		//nbConnectTilesCard
		int[] cardsCounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		
		for(int i = 0; i < numberOfActionCards; i++) {
			int index = randomInt(0,11);
			cardsCounts[index]++;
		}
		
		try {
			//rollDie, removeConnection, teleport, loseTurn, connectTiles, sendBack, additionalMove, next rolls one,
			//show action tiles, move win tile, move player, inactivity period
			createDeck(cardsCounts[0], cardsCounts[1], cardsCounts[2], cardsCounts[3], cardsCounts[4], cardsCounts[5],
					cardsCounts[6], cardsCounts[7], cardsCounts[8], cardsCounts[9], cardsCounts[10], cardsCounts[11]);
		} catch (InvalidInputException e) {
			
		}	
		
		//Remove tiles
		lowerBound = (int)((game.numberOfTiles() - game.numberOfPlayers() - numberOfActionCards - 2) * 0.15);
		upperBound = (int)((game.numberOfTiles() - game.numberOfPlayers() - numberOfActionCards - 2) * 0.25);
		int numberOfRemovedTiles = randomInt(lowerBound, upperBound);
		
		for(int i = 0; i < numberOfRemovedTiles; i++) {
			processDone = false;
			
			while(!processDone) {
				int randomIndexOfTile = randomInt(0, game.numberOfTiles()-1);
				boolean taken = false;
				Tile tile = game.getTile(randomIndexOfTile);
				
				for(Player player : game.getPlayers()) {
					if(player.getStartingTile() == tile)
						taken = true;
				}
				
				if(tile instanceof ActionTile)
					taken = true;
				
				if(tile instanceof WinTile)
					taken = true;
				
				if(!taken) {
					try {
						deleteTile(game.getTile(randomIndexOfTile));
					} catch (InvalidInputException e) {
						e.printStackTrace();
					}
					processDone = true;
				}
			}
		}
		
		//Connections
		lowerBound = (int)(ui.getConnectionsUI().size()*(0.3));
		upperBound = (int)(ui.getConnectionsUI().size()*(0.5));
		int numberOfConnections = randomInt(lowerBound, upperBound);
		
		for(int i = 0; i < numberOfConnections; i++) {
			processDone = false;
			
			while(!processDone) {
				int randomX;
				int randomY;
				boolean isVertical = randomInt(0,1) == 0;
			
				//VERTICAL
				if(isVertical) {
					randomX = randomInt(0, ui.numberOfRows-2)*2+1;
					randomY = randomInt(0, ui.numberOfCols-2)*2;
				}
				//HORIZONTAL
				else {
					randomX = randomInt(0, ui.numberOfRows-2)*2;
					randomY = randomInt(0, ui.numberOfCols-2)*2+1;
				}
				
				ConnectionUI connection = ui.getConnectionUIByXY(randomX, randomY);
				
				if(connection.getLifeState() == ConnectionUI.LifeState.EXIST || connection.getState() == ConnectionUI.State.HIDE)
					continue;
				
				connection.setLifeState(ConnectionUI.LifeState.EXIST);
				
				//VERTICAL
				if(isVertical) {
					Tile tile1 = game.getTileFromXY((randomX-1)/2, randomY/2);
					Tile tile2 = game.getTileFromXY((randomX+1)/2, randomY/2);
					
					if(tile1 == null || tile2 == null)
						continue;
					
					try {
						connectTiles(tile1, tile2);
						processDone = true;
					} catch (InvalidInputException e) {
						// Already connection
					}
				}
				//HORIZONTAL
				else  {
					Tile tile1 = game.getTileFromXY(randomX/2, (randomY-1)/2);
					Tile tile2 = game.getTileFromXY(randomX/2, (randomY+1)/2);
					
					if(tile1 == null || tile2 == null)
						continue;
					
					try {
						connectTiles(tile1, tile2);
						processDone = true;
					} catch (InvalidInputException e) {
						// Already connection
					}
				}
			}
		}
		
		//Make sure starting positions are connected
		for(int i = 0; i < game.numberOfPlayers(); i++) {
			Tile tile = game.getPlayer(i).getStartingTile();
			
			boolean isEnoughConnections = false;
			
			//Check if enough connections
			isEnoughConnections = tile.longestPathFromTile(null, new ArrayList<Tile>()) > (((ui.numberOfRows < ui.numberOfCols) ? ui.numberOfRows : ui.numberOfCols) / 2);
			
			if(isEnoughConnections)
				continue;
			
			//Create 4 connections
			Tile tileUp = game.getTileFromXY(tile.getX()-1, tile.getY());
			Tile tileDown = game.getTileFromXY(tile.getX()+1, tile.getY());
			Tile tileRight = game.getTileFromXY(tile.getX(), tile.getY()+1);
			Tile tileLeft = game.getTileFromXY(tile.getX(), tile.getY()-1);
			
			try {
				if(tileUp != null) {
					ui.getConnectionUIByXY(tile.getX()*2-1, tile.getY()*2).setLifeState(ConnectionUI.LifeState.EXIST);
					
					connectTiles(tile, tileUp);
				}
				
				if(tileDown != null) {
					ui.getConnectionUIByXY(tile.getX()*2+1, tile.getY()*2).setLifeState(ConnectionUI.LifeState.EXIST);
					
					connectTiles(tile, tileDown);
				}
				
				if(tileRight != null) {
					ui.getConnectionUIByXY(tile.getX()*2, tile.getY()*2+1).setLifeState(ConnectionUI.LifeState.EXIST);
					
					connectTiles(tile, tileRight);
				}
				
				if(tileLeft != null) {
					ui.getConnectionUIByXY(tile.getX()*2, tile.getY()*2-1).setLifeState(ConnectionUI.LifeState.EXIST);
					
					connectTiles(tile, tileLeft);
				}
					
			} catch(InvalidInputException e) {
				
			}
			
		}
		
		//Show
		ui.setGame(game);
		ui.setupBoard(false);
		ui.update();
	}

	//
	private Game game;
	private TileODesignUI ui;
	Random rn;
}