package ca.mcgill.ecse223.tileo.view;

import java.awt.FlowLayout;

import java.awt.Image;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.view.popup.*;

/*
 * General pop up creator
 */
public class PopUpManager {
	public PopUpManager(JFrame window) {
		parentWindow = window;
	}
	
	public void acknowledgeMessage(String message) {
		JOptionPane.showMessageDialog(parentWindow,message);
	}
	
	public void errorMessage(String message) {
		JOptionPane.showMessageDialog(parentWindow,message,"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	public int askInactivityPeriod() {
		TurnsUnactive window = new TurnsUnactive(parentWindow);
		return window.ask();
	}
	
	public int chooseDieRoll() {
		ChooseDieRoll window = new ChooseDieRoll(parentWindow);
		return window.ask();
	}
	
	public int askYesOrNo(String message) {
		Object[] options = {"Yes",
                "No"};
		return JOptionPane.showOptionDialog(parentWindow,message,"Are you sure?", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null,
				options,
				options[1]);
	}
	
	private class JTextFieldLimit extends PlainDocument {
		  private int limit;

		  JTextFieldLimit(int limit) {
		   super();
		   this.limit = limit;
		   }

		  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
		    if (str == null) return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
	
	public String askSaveName(String existingName) {
		JPanel panel = new JPanel();
		JTextField text = new JTextField(25);
		text.setSize(new java.awt.Dimension(200,25));
		text.setPreferredSize(new java.awt.Dimension(200,25));
		text.setDocument(new JTextFieldLimit(25));
		if(existingName == null) {
			Random rn = new Random();
			existingName = "Game";
			
			for(int i = 0; i < 5; i++) {
				existingName += rn.nextInt(10);
			}
		}
		
		text.setText(existingName);
		panel.setLayout(new FlowLayout());
		panel.add(text);
		
		if(JOptionPane.showConfirmDialog(parentWindow, panel, "Enter game name : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			String fieldText = text.getText().trim();
			
			if(fieldText.equals(""))
				return "";
				
			
			return fieldText;
		}
		
		return null;
	}
	
	public void showActionTile(ActionCard card) {
		Image img = null;
		
		if(card == null) {
			new ActionCardPopUp(parentWindow);
			return;
		}
		
		try {
			//Roll die again
			if(card instanceof RollDieActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/dice.png"));
			}
			
			//Add connection
			if(card instanceof ConnectTilesActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/plus.png"));
			}
			
			//Remove connection
			if(card instanceof RemoveConnectionActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/minus.png"));
			}
			
			//Teleport
			if(card instanceof TeleportActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/teleport.png"));
			}
			
			//Lose turn
			if(card instanceof LoseTurnActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/loseTurn.png"));
			}
			
			//Send back to start
			if(card instanceof SendBackToStartActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/sendBack.png"));
			}
			
			//Additional move
			if(card instanceof AdditionalMoveActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/additionalMove.png"));
			}
			
			//Inactivity period
			if(card instanceof InactivityPeriodActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/inactivityPeriod.png"));
			}
			
			//Move player
			if(card instanceof MovePlayerActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/movePlayer.png"));
			}
			
			//Move win tile
			if(card instanceof MoveWinTileActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/moveWinTile.png"));
			}
			
			//Next rolls one
			if(card instanceof NextRollsOneActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/nextRollsOne.png"));
			}
			
			//Show action tiles
			if(card instanceof ShowActionTilesActionCard) {
				img = ImageIO.read(getClass().getResource("/icons/cards/showActionTiles.png"));
			}
			
			if(img != null) {
				new ActionCardPopUp(parentWindow, card.getInstructions(), img);
			} else {
				//Couldn't get image
			}
		} catch(Exception e) {
			
		}
	}
	
	public void rollDie(int number) {
		String img = null;
		
		try {
			switch(number) {
			case 1:
				img = getClass().getResource("/icons/dice/1.gif").toString();
				break;
			case 2:
				img = getClass().getResource("/icons/dice/2.gif").toString();
				break;
			case 3:
				img = getClass().getResource("/icons/dice/3.gif").toString();
				break;
			case 4:
				img = getClass().getResource("/icons/dice/4.gif").toString();
				break;
			case 5:
				img = getClass().getResource("/icons/dice/5.gif").toString();
				break;
			case 6:
				img = getClass().getResource("/icons/dice/6.gif").toString();
				break;
			}
			
			if(img != null) {
				new RollDiePopUp(parentWindow, img);
			} else {
				//Couldn't get image
			}
		} catch(Exception e) {
			
		}
	}
	
	JFrame parentWindow;
}
