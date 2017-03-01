package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.*;
import java.util.List;

public class DesignController {
  
  public DesignController(TileODesignUI theUI) {
    currentUI = theUI;
  }

    /*public boolean startingPosition(Tile tile, int playerNumber) throws InvalidInputException{
        //Get info about current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        List <Player> allPlayers = game.getPlayers();

        //Validation check
        if(!(game.hasPlayers())){
            throw new InvalidInputException("There are no players in the current game.");
        }
        else if(playerNumber >= game.numberOfPlayers()){
            throw new InvalidInputException("Player selected is not in the current game.");
        }

        //Get the specific player
        Player player = allPlayers.get(playerNumber); //not sure if u implemented correctly

        //Exceptions
        if(tile==game.getWinTile()) {
            throw new InvalidInputException("Cannot place a player on the win tile.");
        }
        else if(tile instanceof ActionTile ) {
            throw new InvalidInputException("Cannot place a player on an action tile.");
        }
        else {
            for(int i=0; i<game.numberOfPlayers(); i++) {
                Player otherPlayer = allPlayers.get(i);
                if(otherPlayer.hasStartingTile()) {
                    if(otherPlayer.getStartingTile() == tile) {  //this one too
                        throw new InvalidInputException("This tile is already a start tile for another player");
                    }
                }
            }
        }

        //Set players start tile
        return player.setStartingTile(tile);
    }

    public boolean createWinTile(Tile tile) throws InvalidInputException{
        //Get info about the current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();

        //Check if the game already has a win tile
            if(game.hasWinTile()) {
                throw new InvalidInputException("Win tile already exists.");
            }

        //Get all info about the tile that will be replaced
        List<Connection> tileConnections = tile.getConnections();
        int x = tile.getX();
        int y = tile.getY();
        tile.delete();

        //Replace the tile with a win tile
        WinTile winTile = new WinTile(x,y,game);

        for(int i = tileConnections.size()-1; i>0; i--){
            winTile.addConnection(tileConnections.get(i));
        }

        //Set win tile to the game
        return (game.setWinTile(wintile));
    }

    public boolean createNormalTile(int x, int y) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game game = tileO.getCurrentGame();
        List<Tile> tiles= game.getTiles();
//iterate through list of tiles and see if a tile is already located at the location in the parameters
        for(int i=0;i<tiles.size() ;i++) {
            Tile spectile = tiles.get(i);
            if(spectile.getX() == x && spectile.getY() == y) {
              throw new InvalidInputException("there is already a tile located there");
              break;}
            
            else game.addTile(new NormalTile(x, y, game));
        }
        return true;
    }

    public boolean deleteTile(Tile tile) throws InvalidInputException{

        if(tile==null ){
            throw new InvalidInputExceptions("there is no tile to delete");
        }
        //delete the tile
        tile.delete();
        return true;
    }

    public boolean identifyActionTile(Tile tile) throws InvalidInputException{
        //get current game and application
        TileO tileOApp = TileOApplication.getTileO();
        Game game = tileOApp.getCurrentGame();
        // the initial coordinates of x and y, and connections are saved
        int firstx = tile.getX();
        int firsty = tile.getY();
        List<Connection> connections = tile.getConnections();
        //delete the tile
        tile.delete();
        // an action tile created at the previous location
        ActionTile actionTile = new ActionTile(firstx,firsty,game, 1); //inactivity period 1 but will be implemented otherwise for deliverable 4
        for(int i = connections.size()-1; i>=0; i--){
            actionTile.addConnection(connections.get(i));
        }
        game.addTile(actionTile);
        return true;
    }

    public void removeConnection(Connection aConnection) throws InvalidInputException{
        TileO tileO = TileOApplication.getTileO();
        Game currentGame = tileO.getCurrentGame();
        //Below is method in game class that removes connection.
        currentGame.eliminateConnection(aConnection);
    }

    public void connectTiles(Tile tile1, Tile tile2) throws InvalidInputException{
     Game currentGame = TileOApplication.getCurrentGame();
     //Created "placeConnection" method in game class that is used by ConnectTiles Action Card as well.
     currentGame.placeConnection(tile1, tile2);
    }
  
    public boolean createDeck(int connectTiles, int loseTurn, int removeConnection, int rollDie, int teleport) throws InvalidInputException{
        //Get info about current game
        TileO tileo = TileOApplication.getTileO();
        Game game = tileo.getCurrentGame();
        Deck deck = game.getDeck();
        //clear any previous cards added
        deck.clearDeck();
        //Exception if there are too many cards added
        if(connectTiles + loseTurn + removeConnection + rollDie + teleport > 32){
            throw new InvalidInputException("Cannot have more than 32 cards in a deck.");
        }
        //add cards to the deck
        for(int i=0; i<=connectTiles; i++){
            ConnectTilesActionCard card = new ConnectTilesActionCard("Connect two tiles", deck);
            deck.add(card);
        }
        for(int j=0; j<=loseturn; j++){
            LoseTurnActionCard card = new LoseTurnActionCard("You lose a turn", deck);
            deck.add(card);
        }
        for(int k=0; k<=removeConnection; k++){
            RemoveConnectionActionCard card = new RemoveConnectionActionCard("Remove Connection", deck);
            deck.add(card);
        }
        for(int x=0; x<=rollDie; x++){
            RollDieActionCard card = new RollDieActionCard("Roll die again", deck);
            deck.add(card);
        }
        for(int y=0; y<=teleport; y++){
            TeleportActionCard card = new TeleportActionCard("Teleport anywhere on the board", deck);
            deck.add(card);
        }
        return deck.hasCards();
    }

    public void createGame(int numOfPlayersInGame){
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
        TileOApplication.getTileO().addGame(aGame);
    }*/
    
    //Fields
    private TileODesignUI currentUI;
}


