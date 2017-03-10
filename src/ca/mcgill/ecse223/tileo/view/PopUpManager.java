package ca.mcgill.ecse223.tileo.view;

import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.popup.*;

/*
 * General pop up creator
 */
public class PopUpManager {
	PopUpManager(JFrame window) {
		parentWindow = window;
	}
	
	public int askInactivityPeriod() {
		TurnsUnactive window = new TurnsUnactive(parentWindow);
		return window.ask();
	}
	
	public void showActionTile(ActionCard card) {
		Image img = null;
		
		try {
			//Roll die again
			if(card instanceof RollDieActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/dice.png"));
			}
			
			//Add connection
			if(card instanceof ConnectTilesActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/plus.png"));
			}
			
			//Remove connection
			if(card instanceof RemoveConnectionActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/minus.png"));
			}
			
			//Teleport
			if(card instanceof TeleportActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/teleport.png"));
			}
			
			//Lose turn
			if(card instanceof LoseTurnActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/loseTurn.png"));
			}
			
			if(img != null) {
				new ActionCardPopUp(parentWindow, card.getInstructions(), img);
			} else {
				//Couldn't get image
			}
		} catch(Exception e) {
			
		}
	}
	
	JFrame parentWindow;
}
