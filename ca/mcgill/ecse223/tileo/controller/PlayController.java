package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import java.util.List;

public class PlayController extends Controller {

    public boolean startGame(Game game) throws InvalidInputException {
        //alex
    }
    public boolean getTopCard(){
        //alex
    }
    public public List<Tile> roll() {
        aGame=getCurrentGame();
    }
    public boolean land(Tile tile) throws InvalidInputException{
        //karine
    }
    public List<Tile> playRollDieActionCard() {
        //sadhvi
    }
    public boolean playAddConnectionActionCard(Tile tile1, Tile tile2) throws InvalidInputException{
        //sadhvi
    }
    public boolean removeConnectionAction(Connection connection) throws InvalidInputException{
        //andre
    }
    public boolean teleport(Tile tile) throws InvalidPositionException {
        //christos
    }
}
