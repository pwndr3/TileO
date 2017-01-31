/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 19 "../../../../../main.ump"
public class Position
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Position Attributes
  private integer x;
  private integer y;

  //Position Associations
  private Tile tile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Position(integer aX, integer aY, Tile aTile)
  {
    x = aX;
    y = aY;
    if (aTile == null || aTile.getPosition() != null)
    {
      throw new RuntimeException("Unable to create Position due to aTile");
    }
    tile = aTile;
  }

  public Position(integer aX, integer aY, Connection aConnectionForTile, Game aGameForTile, Player aPlayerForTile)
  {
    x = aX;
    y = aY;
    tile = new Tile(aConnectionForTile, aGameForTile, this, aPlayerForTile);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(integer aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(integer aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public integer getX()
  {
    return x;
  }

  public integer getY()
  {
    return y;
  }

  public Tile getTile()
  {
    return tile;
  }

  public void delete()
  {
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.delete();
    }
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "x" + "=" + (getX() != null ? !getX().equals(this)  ? getX().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "y" + "=" + (getY() != null ? !getY().equals(this)  ? getY().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null")
     + outputString;
  }
}