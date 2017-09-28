package com.apes.misc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.main.data.Enemy;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class EnemyCreatorPanel extends CheatPanel implements ActionListener/*, CheatPane*/{

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private GameManager gameManager;
	
	private JLabel enemyTitleLabel;
	private JLabel[] labels;	
	private JTextField[] textFields;
	
	private JLabel createConfirm;
	private JButton create;
	private JButton close;
	
	public EnemyCreatorPanel(GameManager gameManager) {
		
		this.gameManager = gameManager;
		setLayout(new MigLayout());
		
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
			} catch (NumberFormatException e) {
				createConfirm.setText("You need to have numbers for the enemy's stats!");
			} catch (NullPointerException e) {
				createConfirm.setText("You left a field blank!");
			}
		} else if (event.getSource() == close) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {}
	}

	@Override
	protected void createLabels() {
		enemyTitleLabel = new JLabel("Here you can create a new enemy, which will be randomly chosen " +
				"to fight the player.");
		labels = new JLabel[6];
		labels[0] = new JLabel("Enemy Name:");
		labels[1] = new JLabel("Name of Super Attack:");
		labels[2] = new JLabel("Maximum Hit Points:");
		labels[3] = new JLabel("Regular Attack Strength:");
		labels[4] = new JLabel("Heal Amount per Turn:");
		labels[5] = new JLabel("Super Attack Strength:");
	}

	@Override
	protected void createFields() {
		textFields = new JTextField[6];
		for (int i=0; i < textFields.length; i++) {
			textFields[i] = new JTextField();
		}
	}
	
	@Override
	protected void createButtons() {
		createConfirm = new JLabel("You have not created any enemies yet. (Yeah!)");
		create = new JButton("Create Enemy");
		close = new JButton("Go Back");
		
		create.addActionListener(this);
		close.addActionListener(this);
	}

	@Override
	protected void addToPane() {
		add(enemyTitleLabel, "growx, span 2, wrap");
		
		for (int i=0; i < textFields.length; i++) {
			add(labels[i]);
			add(textFields[i], "growx, wrap");
		}
		
		add(createConfirm, "growx, span 2, wrap");
		add(create, "growx");
		add(close, "growx, wrap");
	}
	
	@Override
	protected void loadObject() throws NumberFormatException, NullPointerException {
		String[] enemyStatsText = new String[6];
		for (int i=0; i < textFields.length; i++) {
			enemyStatsText[i] = textFields[i].getText();
			if (enemyStatsText[i].equals("")) {
				createConfirm.setText("You left a field blank!");
				return;
			}
		}
		
		int[] numericalStats = new int[4];
		for (int i=0; i < (numericalStats.length); i++) {
			numericalStats[i] = Integer.parseInt(enemyStatsText[i+2]);
			System.out.println(i + ":" + numericalStats[i]);
			if(numericalStats[i] <= 0) {
				createConfirm.setText("You cannot have a negative number or zero as an enemy stat!");
				return;
			}
		}
		
		Enemy enemy = new Enemy();
		enemy.setName(enemyStatsText[0]);
		enemy.setSuperAttackName(enemyStatsText[1]);
		enemy.setHitPoints(numericalStats[0]);
		enemy.setAttackStrength(numericalStats[1]);
		enemy.setHealStrength(numericalStats[2]);
		enemy.setSuperAttackStrength(numericalStats[3]);
		createConfirm.setText("You have created a new " + enemyStatsText[0] + "!");
		
		System.out.println(enemy.toString());
		//new EnemyWriter(enemy);
	}


}
