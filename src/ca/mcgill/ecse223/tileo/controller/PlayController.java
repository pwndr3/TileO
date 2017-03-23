package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.PopUpManager;
import ca.mcgill.ecse223.tileo.view.TileOPlayUI;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayController {

	// PlayController State Machines
	public enum State {
		Ready, Roll, Move, ActionCard, GameWon
	}

	private State state;

	// PlayController Associations
	private List<Tile> possibleMoves;

	private Game game;
	private TileOPlayUI ui;

	public PlayController(TileOPlayUI aUi, Game aGame) {
		game = aGame;
		ui = aUi;
		setState(State.Ready);
	}

	public String getStateFullName() {
		String answer = state.toString();
		return answer;
	}

	public State getState() {
		return state;
	}

	public void startGame() throws Exception {
		Deck deck = game.getDeck();
		List<Player> allPlayers = game.getPlayers();

		if (!game.hasWinTile()) {
			throw new InvalidInputException("No win tile in the game");
		}
		for (int k = 0; k < game.numberOfPlayers(); k++) {
			Player player = allPlayers.get(k);
			if (!player.hasStartingTile()) {
				throw new InvalidInputException("One or more players do not have a starting position.");
			}
		}

		TileOApplication.getTileO().setCurrentGame(game);

		List<Tile> tiles = game.getTiles();
		for (int i = 0; i < tiles.size(); i++) {
			Tile checkTile = tiles.get(i);
			checkTile.setHasBeenVisited(false);
		}
		for (int j = 0; j < game.numberOfPlayers(); j++) {
			Player thisPlayer = allPlayers.get(j);
			Tile startingTile = thisPlayer.getStartingTile();
			thisPlayer.setCurrentTile(startingTile);
			startingTile.setHasBeenVisited(true);
		}
		game.setCurrentPlayer(allPlayers.get(0));

		deck.shuffle();

		setState(State.Roll);
	}

	public ActionCard getTopCard() throws Exception {
		Deck deck = game.getDeck();
		ActionCard actionCard = deck.getCurrentCard();

		int index = deck.indexOfCard(actionCard);
		// Return to the beginning of the deck when the counter reaches the end
		// of the deck
		ActionCard nextTopCard;
		int numOfCards = deck.numberOfCards();

		if (index + 1 < numOfCards) {
			nextTopCard = deck.getCard(index + 1);
		} else {
			deck.shuffle();
			nextTopCard = deck.getCard(0);
		}

		deck.setCurrentCard(nextTopCard);

		return nextTopCard;
	}

	public List<Tile> rollDie() {
		int rolledNumber = game.rollDie();

		new PopUpManager(ui).rollDie(rolledNumber);

		Player currentPlayer = game.getCurrentPlayer();
		List<Tile> possibleMoves = currentPlayer.getPossibleMoves(rolledNumber);
		Set<Tile> uniqueSet = new HashSet<>();
		uniqueSet.addAll(possibleMoves);
		possibleMoves.clear();
		possibleMoves.addAll(uniqueSet);
		Tile currTile = currentPlayer.getCurrentTile();
		possibleMoves.remove(currTile);

		setState(State.Move);

		return possibleMoves;
	}

	public boolean land(Tile tile) {
		boolean wasEventProcessed = false;

		State aState = state;
		switch (aState) {
		case Move:
			if (tile instanceof NormalTile) {
				tile.land();
				setState(State.Roll);
				wasEventProcessed = true;
				break;
			}
			if (tile instanceof WinTile) {
				tile.land();
				game.setMode(Game.Mode.GAME_WON);
				setState(State.GameWon);
				wasEventProcessed = true;
				break;
			}
			if (tile instanceof ActionTile) {
				tile.land();
				setState(State.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
		}

		return wasEventProcessed;
	}
	
	public boolean playSendBackToStartActionCard(Player playerChosen)
	{
		boolean wasEventProcessed = false;

		State aState = state;
		switch (aState)
		{
			case ActionCard:
				if (game.getDeck().getCurrentCard() instanceof SendBackToStartActionCard)
				{
					// line 42 "model.ump"
					doPlaySendBackToStartActionCard(playerChosen);
					setState(State.Roll);
					wasEventProcessed = true;
					break;
				}
				break;
			default:
				// Other states do respond to this event
		}

		return wasEventProcessed;
	}
	
	public boolean doPlaySendBackToStartActionCard(Player playerChosen){
		playerChosen.setCurrentTile(playerChosen.getStartingTile())
		return true;
	}

	public List<Tile> playRollDieActionCard() {
		// Roll The Die method returns possible tiles for player to move to.
		List<Tile> tiles = rollDie();
		setState(State.Roll);

		return tiles;
	}

	public void playAddConnectionActionCard(Tile tile1, Tile tile2) {
		game.placeConnection(tile1, tile2);

		if (game.getCurrentConnectionPieces() > 0)
			game.setCurrentConnectionPieces(game.getCurrentConnectionPieces() - 1);

		setState(State.Roll);
	}

	public void playRemoveConnectionActionCard(Tile tile1, Tile tile2) {
		List<Connection> conns = tile1.getConnections();

		for (Connection conn : conns) {
			List<Tile> tiles = conn.getTiles();
			{
				for (Tile tile : tiles) {
					if (tile == tile2) {
						conn.delete();

						if (game.getCurrentConnectionPieces() < 32)
							game.setCurrentConnectionPieces(game.getCurrentConnectionPieces() + 1);

						return;
					}
				}
			}
		}

		setState(State.Roll);
	}

	public boolean playTeleportActionCard(Tile tile) {
		TeleportActionCard playedCard = (TeleportActionCard) game.getDeck().getCurrentCard();
		boolean wasEventProcessed = false;

		State aState = state;
		switch (aState) {
		case ActionCard:
			if (tile instanceof NormalTile) {
				// Play the card
				playedCard.play(tile);
				setState(State.Roll);
				wasEventProcessed = true;
				break;
			}
			if (tile instanceof WinTile) {
				// Play the card
				playedCard.play(tile);
				setState(State.GameWon);
				game.setMode(Game.Mode.GAME_WON);
				wasEventProcessed = true;
				break;
			}
			if (tile instanceof ActionTile) {
				// Play the card
				playedCard.play(tile);
				setState(State.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean playLoseTurnActionCard() {
		boolean wasEventProcessed = false;

		State aState = state;
		switch (aState) {
		case ActionCard:
			if (game.getDeck().getCurrentCard() instanceof LoseTurnActionCard) {
				Player player = game.getCurrentPlayer();
				player.loseTurns(1);
				nextTurn();
				setState(State.Roll);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
		}

		return wasEventProcessed;
	}

	public void nextTurn() {
		ui.update();
		game.determineNextPlayer(ui);
		ui.update();
		setState(PlayController.State.Roll);
		game.setMode(Game.Mode.GAME);
		ui.maskButtons(ui.ROLLDIE);
	}

	public void saveGame() {
		TileOApplication.save();
	}

	public boolean loadGame() {
		boolean wasEventProcessed = false;

		State aState = state;
		switch (aState) {
		case Ready:
			if (game.getMode() == Game.Mode.GAME) {
				setState(State.Roll);
				wasEventProcessed = true;
				break;
			}
			if (game.getMode() == Game.Mode.GAME_WON) {
				setState(State.GameWon);
				wasEventProcessed = true;
				break;
			}
			if (game.getMode() != Game.Mode.GAME && game.getMode() != Game.Mode.GAME_WON
					&& game.getMode() != Game.Mode.DESIGN) {
				setState(State.ActionCard);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}
	
	public void setState(State gameState) {
		state = gameState;
	}

	public Tile getPossibleMove(int index) {
		Tile aPossibleMove = possibleMoves.get(index);
		return aPossibleMove;
	}

	public List<Tile> getPossibleMoves() {
		List<Tile> newPossibleMoves = Collections.unmodifiableList(possibleMoves);
		return newPossibleMoves;
	}

	public int numberOfPossibleMoves() {
		int number = possibleMoves.size();
		return number;
	}

	public boolean hasPossibleMoves() {
		boolean has = possibleMoves.size() > 0;
		return has;
	}

	public int indexOfPossibleMove(Tile aPossibleMove) {
		int index = possibleMoves.indexOf(aPossibleMove);
		return index;
	}

	public static int minimumNumberOfPossibleMoves() {
		return 0;
	}

	public boolean addPossibleMove(Tile aPossibleMove) {
		boolean wasAdded = false;
		if (possibleMoves.contains(aPossibleMove)) {
			return false;
		}
		possibleMoves.add(aPossibleMove);
		wasAdded = true;
		return wasAdded;
	}

	public boolean removePossibleMove(Tile aPossibleMove) {
		boolean wasRemoved = false;
		if (possibleMoves.contains(aPossibleMove)) {
			possibleMoves.remove(aPossibleMove);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	public boolean addPossibleMoveAt(Tile aPossibleMove, int index) {
		boolean wasAdded = false;
		if (addPossibleMove(aPossibleMove)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfPossibleMoves()) {
				index = numberOfPossibleMoves() - 1;
			}
			possibleMoves.remove(aPossibleMove);
			possibleMoves.add(index, aPossibleMove);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMovePossibleMoveAt(Tile aPossibleMove, int index) {
		boolean wasAdded = false;
		if (possibleMoves.contains(aPossibleMove)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfPossibleMoves()) {
				index = numberOfPossibleMoves() - 1;
			}
			possibleMoves.remove(aPossibleMove);
			possibleMoves.add(index, aPossibleMove);
			wasAdded = true;
		} else {
			wasAdded = addPossibleMoveAt(aPossibleMove, index);
		}
		return wasAdded;
	}

	public void delete() {
		possibleMoves.clear();
	}
}
