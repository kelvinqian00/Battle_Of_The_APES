package com.apes.misc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import org.json.simple.parser.ParseException;

import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.main.data.ConfigData;
import com.apes.misc.AboutInfo;
import com.apes.misc.io.ConfigLoader;
import com.apes.misc.io.ConfigWriter;

import net.miginfocom.swing.MigLayout;

public class PlayerConfigPanel extends CheatPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private GameManager gameManager;
	private ConfigData prevConfig;
	
	private JLabel titleLabel;
	private JLabel[] labels;
	private JTextField[] textFields;
	
	private JLabel createConfirm;
	private JButton create;
	private JButton close;
	
	public PlayerConfigPanel (GameManager gameManager) {
		this.gameManager = gameManager;
		setLayout(new MigLayout());
		try {
			prevConfig = new ConfigLoader().readFromFile();
		} catch (IOException | ParseException e) {
			JOptionPane.showMessageDialog(gameManager.getGameFrame(), "An error occured while reading from ConfigData.json.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		/*path = super.showFileChooser();
		if (path == null) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {*/
			
		createLabels();
		createFields();
		createButtons();
		addToPane();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == create) {
			try {
				loadObject();
			} catch (NumberFormatException | NullPointerException e) {
				System.out.println("Error!");
				e.printStackTrace();
			}
		} else if (event.getSource() == close) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		}
	}

	@Override
	protected void createLabels() {
		titleLabel = new JLabel("Here you can set the initial values of the player's stats.");
		labels = new JLabel[6];
		labels[0] = new JLabel("Max Health: ");
		labels[1] = new JLabel("Attack Strength: ");
		labels[2] = new JLabel("Heal Stength: ");
		labels[3] = new JLabel("Health Increase: ");
		labels[4] = new JLabel("Attack Strength Increase: ");
		labels[5] = new JLabel("Heal Amount Increase: ");
	}

	@Override
	protected void createFields() {
		textFields = new JTextField[6];
		for (int i=0; i < textFields.length; i++) {
			textFields[i] = new JTextField();
		}
		textFields[0].setText(Integer.toString(prevConfig.getMaxHitPoints()));
		textFields[1].setText(Integer.toString(prevConfig.getAttackStrength()));
		textFields[2].setText(Integer.toString(prevConfig.getHealStrength()));
		textFields[3].setText(Integer.toString(prevConfig.getHitPointsIncrease()));
		textFields[4].setText(Integer.toString(prevConfig.getAttackStrengthIncrease()));
		textFields[5].setText(Integer.toString(prevConfig.getHealStrengthIncrease()));
	}

	@Override
	protected void createButtons() {
		createConfirm = new JLabel("Player properties have not been reset");
		create = new JButton("Apply Changes");
		close = new JButton("Done");
		
		create.addActionListener(this);
		close.addActionListener(this);
	}

	@Override
	protected void addToPane() {
		add(titleLabel, "growx, span 2, wrap");
		
		assert (labels.length == textFields.length);
		for (int i=0; i < textFields.length; i++) {
			add(labels[i]);
			add(textFields[i], "growx, wrap");
		}
		
		add(createConfirm, "growx, span 2, wrap");
		add(create, "growx");
		add(close, "growx");
	}

	@Override
	protected void loadObject() throws NumberFormatException, NullPointerException {
		String[] playerStats = new String[6];
		for (int i=0; i < textFields.length; i++) {
			playerStats[i] = textFields[i].getText();
			if (playerStats[i].equals("")) {
				createConfirm.setText("You left a field blank!");
				return;
			} if (Integer.parseInt(playerStats[i]) < 0) {
				createConfirm.setText("You cannot have a negative number for a stat!");
				return;
			}
		}
		int maxHP = Integer.parseInt(playerStats[0]);
		int attackStrength = Integer.parseInt(playerStats[1]);
		int healStrength = Integer.parseInt(playerStats[2]);
		int hpIncrease = Integer.parseInt(playerStats[3]);
		int attackStrIncrease = Integer.parseInt(playerStats[4]);
		int healStrIncrease = Integer.parseInt(playerStats[5]);
		
		ConfigData nextConfig = new ConfigData();
		nextConfig.setMaxHitPoints(maxHP);
		nextConfig.setAttackStrength(attackStrength);
		nextConfig.setHealStrength(healStrength);
		nextConfig.setHitPointsIncrease(hpIncrease);
		nextConfig.setAttackStrengthIncrease(attackStrIncrease);
		nextConfig.setHealStrengthIncrease(healStrIncrease);
		
		createConfirm.setText("Your changes have been applied.");
		new ConfigWriter(nextConfig);
	}
}
