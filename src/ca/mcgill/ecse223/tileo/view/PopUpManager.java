package ca.mcgill.ecse223.tileo.view;

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
	
	public void showActionTile(ActionCard card) {
		ActionCardPopUp popup = new ActionCardPopUp(parentWindow, "");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
		}
		
		popup.dispose();
	}
	
	JFrame parentWindow;
}
