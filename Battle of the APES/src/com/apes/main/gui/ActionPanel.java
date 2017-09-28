package com.apes.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.apes.main.GameEngine;
import com.apes.main.enums.PlayerDecision;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class ActionPanel extends JLayeredPane implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();

	//private GamePanel gamePanel;
	private transient GameEngine gameEngine;
	
	private JButton attackButton;
	private JButton healButton;
	private JButton hammerButton;
	private JButton increaseAttackButton;
	private JButton increaseHPButton;
	private JButton increaseHealButton;
	
	public ActionPanel(GamePanel gamePanel, GameEngine gameEngine) {
		//this.gamePanel = gamePanel;
		this.gameEngine = gameEngine;
		setLayout(new MigLayout());
		
		attackButton = new JButton("Attack!");
		healButton = new JButton("Heal Up");
		hammerButton = new JButton("Deal Random Attack");
		increaseAttackButton = new JButton("Increase Attack Strength");
		increaseHPButton = new JButton("Increase Max Health");
		increaseHealButton = new JButton("Increase Heal Amount");
		
		attackButton.addActionListener(this);
		healButton.addActionListener(this);
		hammerButton.addActionListener(this);
		increaseAttackButton.addActionListener(this);
		increaseHPButton.addActionListener(this);
		increaseHealButton.addActionListener(this);
		
		add(attackButton, "growx, wrap");
		add(healButton, "growx, wrap");
		add(hammerButton, "growx, wrap");
		add(increaseAttackButton, "growx, wrap");
		add(increaseHPButton, "growx, wrap");
		add(increaseHealButton, "growx");
	}

	protected void setAllButtonsEnabled(boolean isEnabled) {
		attackButton.setEnabled(isEnabled);
		healButton.setEnabled(isEnabled);
		hammerButton.setEnabled(isEnabled);
		increaseAttackButton.setEnabled(isEnabled);
		increaseHPButton.setEnabled(isEnabled);
		increaseHealButton.setEnabled(isEnabled);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == attackButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.ATTACK);
		} else if (event.getSource() == healButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.HEAL);
		} else if (event.getSource() == hammerButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.HAMMER);
		} else if (event.getSource() == increaseAttackButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.INCREASE_ATTACK);
		} else if (event.getSource() == increaseHPButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.INCREASE_HP);
		} else if (event.getSource() == increaseHealButton) {
			gameEngine.respondToDecisionButtons(PlayerDecision.INCREASE_HEAL);
		}
		
	}
	
	public void setGameEngine(GameEngine gameEngine) {
		if (this.gameEngine == null) {
			this.gameEngine = gameEngine;
		}
	}

}
