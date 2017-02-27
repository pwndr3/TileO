package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import java.util.List;

public class DesignerController extends Controller {

    public boolean startingPosition(Tile tile, int playerNumber) throws InvalidInputException{
        //christos
    }
    public boolean createWinTile(Tile tile) throws InvalidInputException{
        //christos
    }
    public boolean placeTile(int x, int y) throws InvalidInputException{
        //abe
    }
    public boolean deleteTile(Tile tile) throws InvalidInputException{
        //abe
    }
    public boolean identifyActionTile(Tile tile) throws InvalidInputException{
        //abe
    }
    public boolean removeConnection(Connection aConnection) throws InvalidInputException{
        //alex
    }
    public boolean connectTiles(Tile tile1, Tile tile2) throws InvalidInputException{
        //sadhvi
    }
    public boolean createDeck(int connectTiles, int loseTurn, int removeConnection, int rollDie, int teleport) throws InvalidInputException{
        //andre
    }
    public boolean createGame(int numOfPlayersInGame) throws InvalidInputException{
        //karine
    }
}
