package ca.mcgill.ecse223.tileo.application;

import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.TileO;
import ca.mcgill.ecse223.tileo.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.tileo.view.TileOPage;


public static TileO getTileO(){
        if (tileO == null)
        {
        tileO = new TileO();
        }
        return tileO;
        }