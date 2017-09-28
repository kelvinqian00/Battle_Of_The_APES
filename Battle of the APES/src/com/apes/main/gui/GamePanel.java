package com.apes.main.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.apes.main.GameEngine;
import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.main.data.Question;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class GamePanel extends JLayeredPane implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private GameManager gameManager;
	private JFrame gameFrame;
	private GameEngine gameEngine;
	
	private JPanel statPanel;
	private JPanel notifyPanel;
	private JPanel bodyPanel;
	
	private JPanel spacerPanel1;
	private JPanel spacerPanel2;
	private JPanel spacerPanel3;
	
	private JLabel turnCounter;
	private JLabel playerHP;
	private JLabel enemyHP;
	private JLabel attackStrength;
	private JLabel healStrength;
	private JLabel notification;
	
	private ActionPanel actionPanel;
	private QuestionPanel questionPanel;
	private int currentBodyPanel = -1;
	
	private JButton nextButton;
	
	public transient static final int ACTION_PANEL = 0;
	public transient static final int QUESTION_PANEL = 1; 
	
	public GamePanel(GameManager gameManager, GameEngine gameEngine){
		this.gameManager = gameManager;
		this.gameEngine = gameEngine;
		this.gameFrame = gameManager.getGameFrame();
		setLayout(new MigLayout("wrap 1", "", "[][shrinkprio 110, 0:15:30][]"
				+ "[shrinkprio 110, 0:15:30][][shrinkprio 110, 0:15:30]push[]"));
		
		statPanel = new JPanel();
		notifyPanel = new JPanel();
		
		turnCounter = new JLabel();
		playerHP = new JLabel();
		enemyHP = new JLabel();
		attackStrength = new JLabel();
		healStrength = new JLabel();
		notification = new JLabel();
		
		statPanel.setLayout(new MigLayout("", "[]10[]", "[][][]"));
		notifyPanel.setLayout(new MigLayout("", "[350]", ""));
		
		statPanel.add(turnCounter, "growx, span 2, wrap");
		statPanel.add(playerHP);
		statPanel.add(enemyHP, "wrap");
		statPanel.add(attackStrength);
		statPanel.add(healStrength, "wrap");
		notifyPanel.add(notification);
		
		spacerPanel1 = new JPanel();
		spacerPanel2 = new JPanel();
		spacerPanel3 = new JPanel();
		
		bodyPanel = new JPanel();
		actionPanel = new ActionPanel(this, gameEngine);
		setActionPanel();
		
		nextButton = new JButton("Continue");
		nextButton.addActionListener(this);
		nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "continue");
		nextButton.getActionMap().put("continue", new EnterAction());
		
		//optionsPanel = new JLayeredPane();
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "options");
		this.getActionMap().put("options", new EnterAction());
		
		add(statPanel, "growx");
		add(spacerPanel1);
		add(notifyPanel, "growx");
		add(spacerPanel2);
		add(bodyPanel, "growx");
		add(spacerPanel3);
		add(nextButton, "growx, south");
		
		//gameFrame.setMinimumSize(new Dimension(300, 450));
		//setMinimumSize(new Dimension(300, 600));
		//System.out.println("Created successfully");
	}
	
	@SuppressWarnings("serial")
	private class EnterAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getSource() == nextButton) {
				if (nextButton.isEnabled())respondToButtonPress();
			} else {
				gameManager.setPanel(PanelType.OPTIONS_PANEL);
			}
		}
	}
	
	public void setActionPanel() {
		if (currentBodyPanel == QUESTION_PANEL) {
			bodyPanel.remove(questionPanel);
			bodyPanel.add(actionPanel);
			currentBodyPanel = ACTION_PANEL;
		} else if (currentBodyPanel == -1) {
			bodyPanel.add(actionPanel);
			currentBodyPanel = ACTION_PANEL;
		} else if (currentBodyPanel == ACTION_PANEL) {
			//System.out.println("Action Panel Error");
			//Do nothing
		}
		bodyPanel.revalidate();
		resetFrame();
	}
	
	public void setQuestionPanel(Question question) {
		if (currentBodyPanel == ACTION_PANEL) {
			questionPanel = new QuestionPanel(this, gameEngine, question);
			bodyPanel.remove(actionPanel);
			bodyPanel.add(questionPanel);
			setNotification(questionPanel.getModifiedQuesText());
			currentBodyPanel = QUESTION_PANEL;
		} else if (currentBodyPanel == QUESTION_PANEL) {
			//System.out.println("Question Panel Error");
			//Do nothing
		}
		bodyPanel.revalidate();
		resetFrame();
	}
	
	public boolean getPlayerAnswer() {
		boolean isCorrect = questionPanel.checkAnswer();
		setNotification(questionPanel.getQuestionAnsText());
		return isCorrect;
	}
	
	public void setNotificationExternally(String str) {
		setNotification(str);
	}
	
	private void setNotification(String notificationText) {
		notification.setText(notificationText);
		resetFrame();
	}
	
	public void setTurnCounter(String turn) {
		turnCounter.setText("Current Turn: Turn " + turn);
		resetFrame();
	}
	
	public void setPlayerHealth(String playerHealth) {
		playerHP.setText("Player Health: " + playerHealth);
		resetFrame();
	}
	
	public void setEnemyHealth(String enemyHealth) {
		enemyHP.setText("Enemy Health: " + enemyHealth);
		resetFrame();
	}
	
	public void setAttackStrength(String attackStrength) {
		this.attackStrength.setText("Attack Strength: " + attackStrength);
		resetFrame();
	}
	
	public void setHealStrength(String healStrength) {
		this.healStrength.setText("Heal Amount: " + healStrength);
		resetFrame();
	}
	
	
	public void setDecisionButtonsEnabled(boolean isActivated) {
		actionPanel.setAllButtonsEnabled(isActivated);
		resetFrame();
	}
	
	public void setNextButtonEnabled(boolean isActivated) {
		nextButton.setEnabled(isActivated);
		resetFrame();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == nextButton) {
			respondToButtonPress();
		}
	}
	
	private void respondToButtonPress() {
		gameEngine.respondToMainButton();
	}
	
	private void resetFrame() {
		revalidate();
		gameFrame.pack();
		//gameFrame.setMinimumSize(new Dimension(gameFrame.getWidth(), statPanel.getHeight() + 
		//		notifyPanel.getHeight() + spacerPanel.getHeight() + bodyPanel.getHeight()+
		//		nextButton.getHeight()));
		//System.out.println(gameFrame.getHeight());
		revalidate();
	}

}
