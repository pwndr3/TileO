/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;

import java.io.Serializable;

public class Player implements Serializable {

	// ------------------------
	// STATIC VARIABLES
	// ------------------------

	private static Map<Integer, Player> playersByNumber = new HashMap<Integer, Player>();

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Player Attributes
	private int number;
	private int turnsUntilActive;

	// Player State Machines
	public enum Color {
		RED, BLUE, GREEN, YELLOW
	}

	private Color color;

	// Player Associations
	private Tile startingTile;
	private Tile currentTile;
	private Game game;

	private static final long serialVersionUID = -9127381273012L;
	
	// Player State Machines
	public enum PlayerStatus {
		Active, Inactive
	}

	private PlayerStatus playerStatus;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Player(int aNumber, Game aGame) {
		number = aNumber;
		turnsUntilActive = 0;
		
		boolean didAddGame = setGame(aGame);
		if (!didAddGame) {
			throw new RuntimeException("Unable to create player due to game");
		}
		setColor(Color.RED);
		setPlayerStatus(PlayerStatus.Active);
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	
	public String getPlayerStatusFullName() {
		String answer = playerStatus.toString();
		return answer;
	}

	public PlayerStatus getPlayerStatus() {
		return playerStatus;
	}

	public boolean loseTurns(int n) {
		boolean wasEventProcessed = false;

		PlayerStatus aPlayerStatus = playerStatus;
		switch (aPlayerStatus) {
		case Active:
			if (n > 0) {
				setTurnsUntilActive(getTurnsUntilActive() + n);
				setPlayerStatus(PlayerStatus.Inactive);
				wasEventProcessed = true;
				break;
			}
			break;
		case Inactive:
			if (n > 0) {
				setTurnsUntilActive(getTurnsUntilActive() + n);
				setPlayerStatus(PlayerStatus.Inactive);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	public boolean takeTurn() {
		boolean wasEventProcessed = false;

		PlayerStatus aPlayerStatus = playerStatus;
		switch (aPlayerStatus) {
		case Inactive:
			if (getTurnsUntilActive() > 1) {
				setTurnsUntilActive(getTurnsUntilActive() - 1);
				setPlayerStatus(PlayerStatus.Inactive);
				wasEventProcessed = true;
				break;
			}
			if (getTurnsUntilActive() <= 1) {
				setTurnsUntilActive(0);
				setPlayerStatus(PlayerStatus.Active);
				wasEventProcessed = true;
				break;
			}
			break;
		default:
			// Other states do respond to this event
		}

		return wasEventProcessed;
	}

	private void setPlayerStatus(PlayerStatus aPlayerStatus) {
		playerStatus = aPlayerStatus;
	}

	public boolean setNumber(int aNumber) {
		boolean wasSet = false;
		number = aNumber;
		wasSet = true;
		return wasSet;
	}

	public boolean setTurnsUntilActive(int aTurnsUntilActive) {
		boolean wasSet = false;
		turnsUntilActive = aTurnsUntilActive;
		wasSet = true;
		return wasSet;
	}

	public int getNumber() {
		return number;
	}

	public int getTurnsUntilActive() {
		return turnsUntilActive;
	}

	public String getColorFullName() {
		String answer = color.toString();
		return answer;
	}

	public Color getColor() {
		return color;
	}

	public boolean setColor(Color aColor) {
		color = aColor;
		return true;
	}

	public Tile getStartingTile() {
		return startingTile;
	}

	public boolean hasStartingTile() {
		boolean has = startingTile != null;
		return has;
	}

	public Tile getCurrentTile() {
		return currentTile;
	}

	public boolean hasCurrentTile() {
		boolean has = currentTile != null;
		return has;
	}

	public Game getGame() {
		return game;
	}

	public boolean setStartingTile(Tile aNewStartingTile) {
		boolean wasSet = false;
		startingTile = aNewStartingTile;
		wasSet = true;
		return wasSet;
	}

	public boolean setCurrentTile(Tile aNewCurrentTile) {
		boolean wasSet = false;
		currentTile = aNewCurrentTile;
		wasSet = true;
		return wasSet;
	}

	public boolean setGame(Game aGame) {
		boolean wasSet = false;
		// Must provide game to player
		if (aGame == null) {
			return wasSet;
		}

		// game already at maximum (4)
		if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers()) {
			return wasSet;
		}

		Game existingGame = game;
		game = aGame;
		if (existingGame != null && !existingGame.equals(aGame)) {
			boolean didRemove = existingGame.removePlayer(this);
			if (!didRemove) {
				game = existingGame;
				return wasSet;
			}
		}
		game.addPlayer(this);
		wasSet = true;
		return wasSet;
	}

	public void delete() {
		startingTile = null;
		currentTile = null;
		Game placeholderGame = game;
		this.game = null;
		placeholderGame.removePlayer(this);
	}

	public String toString() {
		String outputString = "";
		return super.toString() + "[" + "number" + ":" + getNumber() + "," + "turnsUntilActive" + ":"
				+ getTurnsUntilActive() + "]" + System.getProperties().getProperty("line.separator") + "  "
				+ "startingTile = "
				+ (getStartingTile() != null ? Integer.toHexString(System.identityHashCode(getStartingTile())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "currentTile = "
				+ (getCurrentTile() != null ? Integer.toHexString(System.identityHashCode(getCurrentTile())) : "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "game = "
				+ (getGame() != null ? Integer.toHexString(System.identityHashCode(getGame())) : "null") + outputString;
	}

	public static void clearPlayers() {
		playersByNumber.clear();
	}

	public List<Tile> getPossibleMoves(int aRolledNumber) {
		List<Tile> possibleMoves = currentTile.getNeighbours(aRolledNumber);
		return possibleMoves;
	}
}
