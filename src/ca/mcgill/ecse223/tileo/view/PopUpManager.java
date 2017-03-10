package ca.mcgill.ecse223.tileo.view;

import java.awt.FlowLayout;
import java.awt.Image;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
