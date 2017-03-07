package ca.mcgill.ecse223.tileo.application;

import ca.mcgill.ecse223.tileo.view.MainPage;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.persistence.*;

public class TileOApplication {

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainPage().setVisible(true);
			}
		});
	}

	public static TileO getTileO() {
		if (tileo == null) {
			tileo = new TileO();
		}

		return tileo;
	}

	public static void load() {
		tileo = (TileO) PersistenceObjectStream.deserialize();
		//hola
	}

	public static void save() {
		PersistenceObjectStream.serialize(tileo);
	}

	private static TileO tileo;
}