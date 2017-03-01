package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.view.*;

public class Controller {
    
  public Controller(MainPage theUI) {
    currentUI = theUI;
  }
  
    public void play(){
        TileOApplication.loadPlayWindow();
    }
    
    public void load() {
        TileOApplication.loadDesignWindow();
    }
    
    private MainPage currentUI;
}

