package com.apes.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.*;

import com.apes.main.*;
import com.apes.main.enums.PanelType;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class OptionsPanel extends JLayeredPane implements ActionListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	GameManager gameManager;
	GameEngine gameEngine;
	
	JButton resumeButton;
	JButton saveButton;
	JButton exitButton;
	
	public OptionsPanel(GameManager gameManager, GameEngine gameEngine) {
		this.gameManager = gameManager;
		this.gameEngine = gameEngine;
		setLayout(new MigLayout("", "50[100]50", "40[]10[]10[]40"));
		
		resumeButton = new JButton("Resume");
		saveButton = new JButton("Save Turn");
		exitButton = new JButton("Exit Game");
		
		resumeButton.addActionListener(this);
		saveButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		add(resumeButton, "growx, wrap");
		add(saveButton, "growx, wrap");
		add(exitButton, "growx, wrap");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == resumeButton) {
			gameManager.setPanel(PanelType.RESUME_GAME_PANEL);
		} else if (event.getSource() == saveButton) {
			gameEngine.saveGame();
		} else if (event.getSource() == exitButton) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		}
	}
}
