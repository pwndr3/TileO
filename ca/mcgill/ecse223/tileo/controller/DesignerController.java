package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import java.util.List;

public class DesignerController extends Controller {

    public boolean startingPosition(Tile tile, int playerNumber) throws InvalidInputException{
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
    //EXCEPTIONS 
        Player player= getWithNumber(playerNumber);
        return player.setStartingTile(tile);
    }
    public boolean createWinTile(Tile tile) throws InvalidInputException{
        //Get the current application and game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
    //IF THERE IS A WINTILE ON THE BOARD FIND IT AND DELETE IT BEFORE DOING REST

        //Save the tile's connections, x and y coordinates. The tile is then deleted.
        List<Connection> tileConnections = tile.getConnections();
        int x = tile.getX();
        int y = tile.getY();
        tile.delete();

        //A winTile is created, passing it the x-y coordinates and current game.
        WinTile winTile = new WinTile(x,y,game);

        //The connections are added back onto the tile.
        for(int i = tileConnections.size()-1; i>0; i--){
            winTile.addConnection(tileConnections.get(i));
        }

        //The newly created hiddenTile is added to the game.
        currentGame.addTile(winTile);

        return (wintile==game.getWinTile());
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

    }
}
