package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.view.*;

public class Controller {
    
<<<<<<< HEAD
  public Controller(MainPage theUI) {
    currentUI = theUI;
  }
  
    public void play(){
        TileOApplication.loadPlayWindow();
=======
    public boolean saveGame(){

>>>>>>> 93a073978c3199331ee3458326fa9d10049797d6
    }
    
    public void load() {
        TileOApplication.loadDesignWindow();
    }
    
    private MainPage currentUI;
}

