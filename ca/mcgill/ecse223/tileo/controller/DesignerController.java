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
        //if there is a win tile on the board, delete it before continuing
        if(game.hasWinTile()) {
            WinTile oldWinTile = game.getWinTile();
            oldWinTile.delete();
        }
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

    public boolean createNormalTile(int x, int y) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileO.getCurrentGame();
        List<Tile> tiles= game.getTiles();
//iterate through list of tiles and see if a tile is already located at the location in the parameters
        for(int i=0;i<tiles.size() ;i++) {
            spectile = tiles.get(i);
            if(spectile.getX() == x && spectile.getY() == y) throw new InvalidInputException("there is already a tile located there");
            break;
            else game.addTile(new NormalTile(x, y, game));
        }
        return true;

    }

    public boolean deleteTile(Tile tile) throws InvalidInputException{

        if(tile==null|| ){
            throws new InvalidInputExceptions("there is no tile to delete");
        }
        /*delete the tile*/
        tile.delete();
        return true;
    }

    public boolean identifyActionTile(Tile tile) throws InvalidInputException{
        /*get current game and application*/
        TileO tileOApp = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();
        /* the initial coordinates of x and y, and connections are saved*/
        int firstx = tile.getX();
        int firsty = tile.getY();
        List<Connection> connections = tile.getConnections();
        /*delete the tile*/
        tile.delete();
        /* an action tile created at the previous location*/
        ActionTile actionTile = new ActionTile(firstx,firsty,game, 1); //inactivity period 1 but will be implemented otherwise for deliverable 4
        for(int i = connections.size()-1; i>=0; i--){
            actionTile.addConnection(connections.get(i));
        }
        game.addTile(actionTile);
        return true;

    }

    public boolean removeConnection(Connection aConnection) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();

        if(theConnection ==  null || this.game.indexOfConnection(aConnection) == -1){
                throw new InvalidInputException("There is no connection to remove");
            }
            theConnection.delete();


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
        //if there is a win tile already on the board delete it first before continuing
        if(game.hasWinTile())
        {
            oldWinTile = game.getWinTile();
            oldWinTile.delete();
        }
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
    public boolean createNormalTile(int x, int y) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileO.getCurrentGame();
        List<Tile> tiles= game.getTiles();
//iterate through list of tiles and see if a tile is already located at the location in the parameters
        for(int i=0;i<tiles.size() ;i++) {
            spectile = tiles.get(i);
            if(spectile.getX() == x && spectile.getY() == y) throw new InvalidInputException("there is already a tile located there");
            break;
            else game.addTile(new NormalTile(x, y, game));
        }
        return true;

    }

    public boolean deleteTile(Tile tile) throws InvalidInputException{

        if(tile==null){
            throws new InvalidInputExceptions("there is no tile to delete");
        }
        /*delete the tile*/
        tile.delete();
    }

    public boolean identifyActionTile(Tile tile) throws InvalidInputException{
        /*get current game and application*/
        TileO tileOApp = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();
        /* the initial coordinates of x and y, and connections are saved*/
        int firstx = tile.getX();
        int firsty = tile.getY();
        List<Connection> connections = tile.getConnections();
        /*delete the tile*/
        tile.delete();
        /* an action tile created at the previous location*/
        ActionTile actionTile = new ActionTile(firstx,firsty,game, 1); //inactivity period 1 but will be implemented otherwise for deliverable 4
        for(int i = connections.size()-1; i>=0; i--){
            actionTile.addConnection(connections.get(i));
        }
        game.addTile(actionTile);
        return true;

    }

    public boolean removeConnection(Connection aConnection) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();

        if(theConnection ==  null || this.game.indexOfConnection(aConnection) == -1){
            throw new InvalidInputException("There is no connection to remove");
        }
        theConnection.delete();


    }}
    public boolean connectTiles(Tile tile1, Tile tile2) throws InvalidInputException{
        //sadhvi
    }
    public boolean createDeck(int connectTiles, int loseTurn, int removeConnection, int rollDie, int teleport) throws InvalidInputException{
        //andre
    }
    public boolean createGame(int numOfPlayersInGame){
        TileO tileo = new TileO();
        Game aGame = new Game(0,tileo);
        tileo.setCurrentGame(aGame);
        aGame.setMode(Mode.DESIGN);
        Die die = new Die(aGame);
        Deck deck = new Deck(aGame);
        for(n=1;n<=numOfPlayersInGame;n++){
            Player player = new Player(n,aGame);
            player.setColor(Color.RED);
            aGame.addPlayer(player);
        }

    }
}


    public boolean removeConnection(Connection aConnection) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();

        if(theConnection ==  null || this.game.indexOfConnection(aConnection) == -1){
            throw new InvalidInputException("There is no connection to remove");
        }
        theConnection.delete();


    }