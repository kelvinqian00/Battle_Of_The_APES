package com.apes.main.gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JLayeredPane implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private ImageIcon icon;
	private URL imgURL;
	private JLabel iconLabel;
	private JLayeredPane centralPanel;
	
	private JButton newButton;
	private JButton loadButton;
	private JButton helpButton;
	private JButton cheatButton;
	private JButton creditsButton;
	private JButton exitButton;
	
	private GameManager gameManager;
	ActionListener reset;
	
	public MainPanel (GameManager gameManager) {
		
		this.gameManager = gameManager;
		assert (gameManager != null);
		
		setLayout(new MigLayout("", "[align center]", "15[]10[]25"));
		//icon = new ImageIcon("src/com/apes/resources/BattleOfTheAPESHeader.png");
		//System.out.println(getClass().getClassLoader().getResource("com/apes/resources/ConfigData.json").toString());
		//System.out.println(getClass().getClassLoader().getResource("com/apes/resources/Battle of the APES Header.png").toString());
		try {
			imgURL = getClass().getClassLoader().getResource("com/apes/resources/HeaderImage.png");
			if (imgURL != null) {
				icon = new ImageIcon(imgURL);
			} else {
				throw new IOException();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gameManager.getGameFrame(), "An error has occured; the banner cannot load.", "Error!", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		
		Image image = icon.getImage();
		Image image2 = image.getScaledInstance(300, 110, Image.SCALE_SMOOTH);
		iconLabel = new JLabel(new ImageIcon(image2));
		
		centralPanel = new JLayeredPane();
		centralPanel.setLayout(new MigLayout("", "[]10[]", "[]10[]10[]10[]"));
		
		
		newButton = new JButton("New Game");
		loadButton = new JButton("Load Game");
		helpButton = new JButton("Help");
		cheatButton = new JButton("Cheats");
		creditsButton = new JButton("About");
		exitButton = new JButton("Exit Game");
		
		newButton.addActionListener(this);
		loadButton.addActionListener(this);
		helpButton.addActionListener(this);
		cheatButton.addActionListener(this);
		creditsButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		centralPanel.add(newButton, "span 2, growx, wrap");
		centralPanel.add(loadButton, "span 2, growx, wrap");
		centralPanel.add(helpButton, "span 2, growx, wrap");
		centralPanel.add(cheatButton, "span 2, growx, wrap");
		centralPanel.add(creditsButton, "growx");
		centralPanel.add(exitButton, "growx");
		
		add(iconLabel, "wrap");
		add(centralPanel, "gapleft 50, gapright 50");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == newButton) {
			gameManager.startGame(true);
		} else if (event.getSource() == loadButton) {
			gameManager.startGame(false);
		} else if (event.getSource() == helpButton) {
			gameManager.setPanel(PanelType.HELP_PANEL);
		} else if (event.getSource() == cheatButton) {
			String cheatCode = JOptionPane.showInputDialog(gameManager.getGameFrame(), "Enter Cheat Code");
			if ((cheatCode != null) && (!AboutInfo.getIsReleased())) {
				switch (cheatCode){
				case "password":
					gameManager.setPanel(PanelType.ENEMY_CREATOR_PANEL);
					break;
				case "swordfish":
					gameManager.setPanel(PanelType.QUESTION_CREATOR_PANEL);
					break;
				case "bravesound":
					gameManager.setPanel(PanelType.CONFIG_PANEL);
				default: //do nothing
					break;
				}
			}
		} else if (event.getSource() == creditsButton) {
			gameManager.setPanel(PanelType.ABOUT_PANEL);
		} else if (event.getSource() == exitButton) {
			gameManager.getGameFrame().setVisible(false);
			gameManager.getGameFrame().dispose();
		}
		
	}

}
