package com.apes.misc;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.apes.main.GameManager;

public class AboutInfo {
	
	private static String version = "Beta 1.1";
	private static boolean isReleased = true;
	
	private static String gameName = "Battle of the APES";
	private static String author = "Kelvin Qian";
	private static String schoolClass = "Mrs. Seeber's AP Environmental Science class";
	private static String license = "MIT License";
	private static String original = "Dan Destroyer";
	private static String originator = "Jeremy Thompson";
	private static String[] betaTesters = {"Daniel Adelman", "Jeremy Thompson", "Ken Margolin"};
	
	private String messageText;
	private JFrame gameFrame;
	
	public AboutInfo (GameManager gameManager) {
		gameFrame = gameManager.getGameFrame();
		
		String betaTestString = String.join("<li>", betaTesters);
		
		messageText =  "<html><u>" + gameName + "</u><br>"
				+ "Author: " + author + "<br>"
				+ "Created for: " + schoolClass + "<br>"
				+ "Current version: " + version + "<br>"
				+ "<br>"
				+ "This game is licensed under the " + license + ". <br>"
				+ "Please see License.txt for more details<br>"
				+ "<br>"
				+ "Based off of '" + original + "' by " + originator + ".<br>"
				+ "<br>"
				+ "Special thanks to our beta testers: <br><ul><li>" + betaTestString + "</ul></html>";
		displayAbout();
	}
	public void displayAbout() {
		JOptionPane.showMessageDialog(gameFrame, messageText, "About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static long getSerialUID() {
		long serialUID = (long) version.hashCode();
		return serialUID;
	}
	
	public static boolean getIsReleased() {
		return isReleased;
	}
	
}
