package ca.mcgill.ecse223.tileo.application;

import ca.mcgill.ecse223.tileo.view.*;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.persistence.*;

public class TileOApplication{

 public static void main(String[] args) {
   //create the object
   loadGame("");
   designUI = new TileODesignUI();
   mainUI = new MainPage();
   playUI = new TileOPlayUI();

   //as always, everything we open, we must always close
   java.awt.EventQueue.invokeLater(new Runnable() {
                 public void run() {
                     mainUI.setVisible(true);
              } }  );
  }
 
 public static TileO getTileO() {
        if (tileo == null) {
            tileo = new TileO();
        }
        return tileo;
    }
 
 public static void loadGame(String filename) {
   Game game = (Game) PersistenceObjectStream.deserialize(filename);
   
   if (game == null) {
         game = new Game(32, getTileO());
   }
   
   getTileO().addGame(game);
   getTileO().setCurrentGame(game);
 }
 
 public static Game getCurrentGame() {
  return getTileO().getCurrentGame(); 
 }
 
 public static void saveGame(String filename) {
   Game game = getTileO().getCurrentGame();

   PersistenceObjectStream.serialize(filename, game);
 }
 
 public static void loadDesignWindow() {
   designUI = new TileODesignUI();
   
   mainUI.setVisible(false);
   designUI.setVisible(true);
   playUI.setVisible(false);
 }
 
 public static void loadPlayWindow() {
 playUI = new TileOPlayUI();
   
   mainUI.setVisible(false);
   designUI.setVisible(false);
   playUI.setVisible(true);
 }
 
 public static void loadMenuWindow() {
 mainUI = new MainPage();
   
   mainUI.setVisible(true);
   designUI.setVisible(false);
   playUI.setVisible(false);
 }
 
 private static TileO tileo;
 private static TileODesignUI designUI;
 private static MainPage mainUI;
 private static TileOPlayUI playUI;

 }