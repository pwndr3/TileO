public class TileOApplication {

 public static void main(String[] args) {
   //create the object
   TileODesignUI designUI = new TileODesignUI();

   //call the pack function to make sure everything is displayed well
   designUI.pack();
   //calll the setLocationRelativeTo null so the screen just appears in the middle of the screen
   designUI.setLocationRelativeTo(null);

   //as always, everything we open, we must always close
   java.awt.EventQueue.invokeLater(new Runnable() {
                 public void run() {
                     designUI.setVisible(true);
              } }  );
  }

 }