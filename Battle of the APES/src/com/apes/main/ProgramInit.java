package com.apes.main;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ProgramInit {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("nimbusBase", new Color(41, 137, 52));
			UIManager.put("nimbusBlueGrey", new Color(150, 190, 150));
			UIManager.put("control", new Color(218, 232, 215));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "An error setting up the Look and Feel occured.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		new GameManager(true);
	}

}
