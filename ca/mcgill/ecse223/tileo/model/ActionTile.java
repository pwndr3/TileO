/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.util.*;

// line 29 "../../../../../main.ump"
public class ActionTile extends Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ActionTile Attributes
  private integer turnsDisabled;
  private integer turnsUntilActive;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ActionTile(integer aX, integer aY, Game aGame, integer aTurnsDisabled)
  {
    super(aX, aY, aGame);
    turnsDisabled = aTurnsDisabled;
    turnsUntilActive = '0';
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTurnsUntilActive(integer aTurnsUntilActive)
  {
    boolean wasSet = false;
    turnsUntilActive = aTurnsUntilActive;
    wasSet = true;
    return wasSet;
  }

  public integer getTurnsDisabled()
  {
    return turnsDisabled;
  }

  public integer getTurnsUntilActive()
  {
    return turnsUntilActive;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "turnsDisabled" + "=" + (getTurnsDisabled() != null ? !getTurnsDisabled().equals(this)  ? getTurnsDisabled().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "turnsUntilActive" + "=" + (getTurnsUntilActive() != null ? !getTurnsUntilActive().equals(this)  ? getTurnsUntilActive().toString().replaceAll("  ","    ") : "this" : "null")
     + outputString;
  }
}