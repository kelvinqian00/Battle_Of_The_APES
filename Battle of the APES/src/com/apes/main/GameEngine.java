package com.apes.main;

import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;
import com.apes.main.enums.PanelType;
import com.apes.main.data.*;
import com.apes.main.gui.GamePanel;
import com.apes.misc.io.*;
import com.apes.main.enums.*;

public class GameEngine {

	private GameManager gameManager;
	private JFrame gameFrame;
	
	private GamePanel gamePanel;
	
	private String name;
	private Enemy enemy;
	private QuestionList questionList;
	
	private int hitPoints;
	private int maxHitPoints;
	private int attackStrength;
	private int healStrength;
	
	private int hitPointsIncrease;
	private int attackStrengthIncrease;
	private int healStrengthIncrease;
	
	private ConfigData configData;
	private TurnStage currentStage;
	private PlayerDecision currentDecision;
	private boolean isCurrentAnsCorrect;
	private int currentTurn;
	//private int questionIndex;
	//private boolean canSave = false;
	
	private SaveGame save;
	
	public GameEngine(boolean isNewGame, GameManager gameManager) {
		this.gameManager = gameManager;
		gameFrame = gameManager.getGameFrame();
		
		//Load game data
		if (isNewGame) {
			startNewGame();
		} else {
			loadGame();
		}
		
		if (name == null) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else if ((enemy == null) || (questionList == null) || (this == null)) {
			JOptionPane.showMessageDialog(gameFrame, "An error has occured and crashed the game.", "Error!", JOptionPane.WARNING_MESSAGE);
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {
			//Prepare GUI
			gamePanel = new GamePanel(gameManager, this);
			gameFrame.setContentPane(gamePanel);
			showPlayerDecide(true);
			
			gameFrame.pack();
			gameFrame.revalidate();
			//canSave = true;
		}
	}
	
	//***Start New Game Section***
	private void startNewGame() {
		
		//NewGameLoad newLoad = null;
		try {
			//newLoad = new NewGameLoad();
			enemy = new EnemyLoader().readFromFile();
			questionList = new QuestionLoader().readFromFile();
			configData = new ConfigLoader().readFromFile();
		} catch (IOException | ParseException e) {
			JOptionPane.showMessageDialog(gameFrame, "An error occured. The game cannot be loaded.", "Error!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		enemy.initIncreases();
		
		hitPoints = configData.getMaxHitPoints();
		maxHitPoints = configData.getMaxHitPoints();
		attackStrength = configData.getAttackStrength();
		healStrength = configData.getHealStrength();
		hitPointsIncrease = configData.getHitPointsIncrease();
		attackStrengthIncrease = configData.getAttackStrengthIncrease();
		healStrengthIncrease = configData.getHealStrengthIncrease();
		
		currentTurn = 1;
		currentStage = TurnStage.PLAYER_DECIDE;
		name = askForNewName();
	}
	
	private String askForNewName() {
		String nameMessage = "Please select a new name.";
		String newName = null;
		newName = (String)JOptionPane.showInputDialog(gameManager.getGameFrame(), nameMessage, "Select New Name", JOptionPane.PLAIN_MESSAGE);
		if ((newName != null) && (newName.length() > 0)) {
			String opening = "A wild " + enemy.getName() + " has appeared! You, " + newName +
				", have been selected in order to stop this menance and save the environment!";
			JOptionPane.showMessageDialog(gameManager.getGameFrame(), opening, "Start Game", JOptionPane.WARNING_MESSAGE);
			return newName;
		} else {
			return null;
		}
	}
	
	private void loadGame() {
		SaveGame saveGame = null; 
		try {
			saveGame = new GameIOSystem(gameFrame).showOpenChooser();
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(gameFrame, "The save game cannot be loaded.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		if (saveGame == null) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {
			name = saveGame.getName();
			enemy = saveGame.getEnemy();
			System.out.println(enemy.toString());
			questionList = saveGame.getQuestionList();
			
			hitPoints = saveGame.getHitPoints();
			maxHitPoints = saveGame.getMaxHitPoints();
			attackStrength = saveGame.getAttackStrength();
			healStrength = saveGame.getHealStrength();
			
			hitPointsIncrease = saveGame.getHealStrengthIncrease();
			attackStrengthIncrease = saveGame.getAttackStrengthIncrease();
			healStrengthIncrease = saveGame.getHealStrengthIncrease();
			
			//currentStage = saveGame.getCurrentStage();
			//currentDecision = saveGame.getCurrentDecision();
			currentTurn = saveGame.getCurrentTurn();
			currentStage = TurnStage.PLAYER_DECIDE;
		}
		//questionIndex = saveGame.getQuestionIndex();
	}
	//***End New Game Section***
	
	//NOTE: All transition actions (i.e. all methods) occur during the previous turn stage!
	//The only exception is when the game first starts, when we set up the GUI during PLAYER_DECIDE.
	public void respondToMainButton() {
		//canSave = false;
		assert (currentStage != TurnStage.PLAYER_DECIDE);
		switch(currentStage) {
		case PLAYER_ACT:
			if ((currentDecision == PlayerDecision.ATTACK) || (currentDecision == PlayerDecision.HEAL) || 
					(currentDecision == PlayerDecision.HAMMER)) {
				enemyDecide();
			} else {
				System.out.println("Uh oh!");
			}
			break;
		case PLAYER_QUESTIONED:
			answerQuestion();
			break;
		case PLAYER_ANSWERS:
			respondToAnsweredQuestion();
			break;
		case PLAYER_ACT_ADVANCED:
			enemyDecide();
			break;
		case ENEMY_ATTACK:
			showPlayerDecide(false);
			break;
		default:
			break;
		}
		incrementTurnStage();
		//canSave = true;
	}
	
	public void respondToDecisionButtons(PlayerDecision playerDecision) {
		//canSave = false;
		assert (currentStage == TurnStage.PLAYER_DECIDE);
		currentDecision = playerDecision;
		switch(currentDecision) {
		case ATTACK:
			playerAttack();
			break;
		case HEAL:
			playerHeal();
			break;
		case HAMMER:
			playerHammer();
			break;
		default:
			askQuestion();
			break;
		}
		gamePanel.setNextButtonEnabled(true);
		gamePanel.setDecisionButtonsEnabled(false);
		incrementTurnStage();
		//canSave = true;
	}
	
	private void showPlayerDecide(boolean isStart) {
		checkIfPlayerLost();
		//System.out.println(currentStage);
		if ((isStart == true) && (currentStage == TurnStage.PLAYER_DECIDE)) {
			gamePanel.setNotificationExternally("Welcome, " + name + ". Please select an action: ");
			//System.out.println(enemy.getHitPoints());
			gamePanel.setEnemyHealth(Integer.toString(enemy.getHitPoints()));
			gamePanel.setPlayerHealth(hitPoints + "/" + maxHitPoints);
			gamePanel.setAttackStrength(Integer.toString(attackStrength));
			gamePanel.setHealStrength(Integer.toString(healStrength));
		} else if ((isStart == false) && (currentStage == TurnStage.ENEMY_ATTACK)){
			currentTurn++;
			gamePanel.setNotificationExternally("Please select your next action, " + name + ": ");
		}
		gamePanel.setTurnCounter(Integer.toString(currentTurn));
		autoSave();
		gamePanel.setNextButtonEnabled(false);
		gamePanel.setDecisionButtonsEnabled(true);
	}
	
	private void playerAttack() {
		assert(currentStage == TurnStage.PLAYER_DECIDE);
		enemy.beDamaged(attackStrength);
		if (enemy.getHitPoints() <= 0){
			gamePanel.setEnemyHealth("0");
		} else {
			gamePanel.setEnemyHealth(Integer.toString(enemy.getHitPoints()));
		}
		gamePanel.setNotificationExternally(name + " has used their attack! "
				+ "You have dealt " + attackStrength + " damage!" );
	}
	
	private void playerHeal() {
		assert(currentStage == TurnStage.PLAYER_DECIDE);
		hitPoints = hitPoints + healStrength;
		if (hitPoints > maxHitPoints) {
			hitPoints = maxHitPoints;
			gamePanel.setNotificationExternally(name + " has healed by " + healStrength + "! "
			+ "Now you are at full health, at " + hitPoints + " hit points!");
		} else {
			gamePanel.setNotificationExternally(name + " has healed by " + healStrength + "! "
			+ "Now you are at " + hitPoints + " hit points!");
		}
		gamePanel.setPlayerHealth(hitPoints + "/" + maxHitPoints);
	}
	
	private void playerHammer() {
		assert(currentStage == TurnStage.PLAYER_DECIDE);
		Random ran = new Random();
		int ranAttackStrength = ran.nextInt(attackStrength*2);
		enemy.beDamaged(ranAttackStrength);
		gamePanel.setNotificationExternally(name + " has used their random attack! "
				+ "You have dealt " + ranAttackStrength + " damage!");
		if (enemy.getHitPoints() <= 0){
			gamePanel.setEnemyHealth("0");
		} else {
			gamePanel.setEnemyHealth(Integer.toString(enemy.getHitPoints()));
		}
	}
	
	private void askQuestion() {
		assert(currentStage == TurnStage.PLAYER_DECIDE);
		Question question = questionList.getNewQuestion();
		gamePanel.setQuestionPanel(question);
	}
	
	private void answerQuestion() {
		assert(currentStage == TurnStage.PLAYER_QUESTIONED);
		isCurrentAnsCorrect = gamePanel.getPlayerAnswer();
		//System.out.println(isCurrentAnsCorrect);
		//gamePanel.setAnswerPanel();
	}
	
	private void respondToAnsweredQuestion() {
		assert(currentStage == TurnStage.PLAYER_ANSWERS);
		if (isCurrentAnsCorrect) {
			assert((currentDecision != PlayerDecision.ATTACK) || (currentDecision != PlayerDecision.HEAL) ||
					(currentDecision != PlayerDecision.HAMMER));
			switch(currentDecision) {
			case INCREASE_ATTACK:
				increaseAttack();
				break;
			case INCREASE_HP:
				increaseMaxHP();
				break;
			case INCREASE_HEAL:
				increaseHealStrength();
				break;
			default:
				break;
			}
		} else {
			enemyDecide();
		}
	}
	
	private void increaseAttack() { //Requires correct answer
		assert(currentStage == TurnStage.PLAYER_ANSWERS);
		attackStrength = attackStrength + attackStrengthIncrease;
		String notification = "<html>As you have answered the question correctly, "
				+ name + " has increased their attack strength "
				+ "by " + attackStrengthIncrease + " to " + attackStrength + ".</html>";
		//gamePanel.setNotification(notification);
		gamePanel.setNotificationExternally(notification);
		gamePanel.setAttackStrength(Integer.toString(attackStrength));
	}
	
	private void increaseMaxHP() { //Requires correct answer
		assert(currentStage == TurnStage.PLAYER_ANSWERS);
		maxHitPoints = maxHitPoints + hitPointsIncrease;
		String notification = "<html>As you have answered the question correctly, "
				+ name + " has <br>increased their max health "
				+ "by " + hitPointsIncrease + " to " + maxHitPoints + ".</html>";
		gamePanel.setNotificationExternally(notification);
		gamePanel.setPlayerHealth(hitPoints + "/" + maxHitPoints);
	}
	
	private void increaseHealStrength() { //Requires correct answer
		assert(currentStage == TurnStage.PLAYER_ANSWERS);
		healStrength = healStrength + healStrengthIncrease;
		String notification = "<html>As you have answered the question correctly, "
				+ name + " has <br>increased their heal amount "
				+ "by " + healStrengthIncrease + " to " + healStrength + ".</html>";
		gamePanel.setNotificationExternally(notification);
		gamePanel.setHealStrength(Integer.toString(healStrength));
	}
	
	private void enemyDecide() {
		assert((currentStage == TurnStage.PLAYER_ACT) || (currentStage == TurnStage.PLAYER_ACT_ADVANCED) ||
				(currentStage == TurnStage.PLAYER_ANSWERS));
		//System.out.println("Again?");
		checkIfPlayerWon();
		gamePanel.setActionPanel();
		EnemyDecision action = enemy.decide(currentTurn, hitPoints, attackStrength, healStrength);
		switch(action) {
		case ATTACK:
			enemyAttack();
			break;
		case HEAL:
			enemyHeal();
			break;
		case SUPER_ATTACK:
			enemySuperAttack();
			break;
		case INCREASE_ATTACK:
			enemyIncreaseAttack();
			break;
		case INCREASE_HEAL:
			enemyIncreaseHeal();
		default:
			break;
		}
		/*if (hitPoints < 0) {
			gamePanel.setPlayerHealth("0");
		}*/
		//checkIfPlayerLost();
	}
	
	private void enemyAttack() {
		int damage = 0;
		damage = enemy.attack();
		gamePanel.setNotificationExternally("The " + enemy.getName() + " has attacked! "
				+ "It has dealt " + damage + " damage!");
		assert (damage >= 0);
		hitPoints = hitPoints - damage;
		if (hitPoints >= 0) {
			gamePanel.setPlayerHealth(hitPoints + "/" + maxHitPoints);
		} else {
			gamePanel.setPlayerHealth("0/" + maxHitPoints);
		}
	}
	
	private void enemyHeal() {
		int enemyHeal = 0;
		enemyHeal = enemy.healUp();
		gamePanel.setNotificationExternally("The " + enemy.getName() + " healed up by "
				+ enemyHeal + " hit points!");
		gamePanel.setEnemyHealth(Integer.toString(enemy.getHitPoints()));
	}
	
	private void enemySuperAttack() {
		int damage = 0;
		damage = enemy.superAttack();
		gamePanel.setNotificationExternally("The " + enemy.getName() + " has used " + enemy.getSuperAttackName() + "! "
				+ "It has dealt " + damage + " damage!");
		assert (damage >= 0);
		hitPoints = hitPoints - damage;
		if (hitPoints >= 0) {
			gamePanel.setPlayerHealth(hitPoints + "/" + maxHitPoints);
		} else {
			gamePanel.setPlayerHealth("0/" + maxHitPoints);
		}
	}
	
	private void enemyIncreaseAttack() {
		int[] strengths = enemy.doubleAttack();
		gamePanel.setNotificationExternally("<html>The " + enemy.getName() + " has increased their attack strength from "
				+ strengths[0]+ " to " + strengths[2] + "! <br>"
				+ "In addition, it has increased the strength of " + enemy.getSuperAttackName() + " from "
				+ strengths[1] + " to " + strengths[3] + "!</html>");
	}
	
	private void enemyIncreaseHeal() {
		int[] healStrengths = enemy.doubleHeal();
		gamePanel.setNotificationExternally("The " + enemy.getName() + " has increased the amount they heal by "
				+ "from " + healStrengths[0] + " to " + healStrengths[1] + "!");
	}
	
	private void incrementTurnStage() {
		//System.out.println("Previous Stage: " + currentStage);
		switch(currentStage) {
		case PLAYER_DECIDE:
			if ((currentDecision == PlayerDecision.ATTACK) || (currentDecision == PlayerDecision.HEAL) || 
					(currentDecision == PlayerDecision.HAMMER)) {
				currentStage = TurnStage.PLAYER_ACT;
			} else if ((currentDecision == PlayerDecision.INCREASE_ATTACK) || (currentDecision == PlayerDecision.INCREASE_HP) || 
					(currentDecision == PlayerDecision.INCREASE_HEAL)) {
				currentStage = TurnStage.PLAYER_QUESTIONED;
			}
			break;
		case PLAYER_ACT:
			currentStage = TurnStage.ENEMY_ATTACK;
			break;
		case PLAYER_QUESTIONED:
			currentStage = TurnStage.PLAYER_ANSWERS;
			break;
		case PLAYER_ANSWERS:
			if (isCurrentAnsCorrect) {
				currentStage = TurnStage.PLAYER_ACT_ADVANCED;
			} else {
				currentStage = TurnStage.ENEMY_ATTACK;
			}
			break;
		case PLAYER_ACT_ADVANCED:
			currentStage = TurnStage.ENEMY_ATTACK;
			break;
		case ENEMY_ATTACK:
			currentStage = TurnStage.PLAYER_DECIDE;
			//currentTurn++;
			break;
		default:
			break;
		}
		//System.out.println("Current Stage: " + currentStage);
	}
	
	private void checkIfPlayerLost() {
		if (hitPoints <= 0) {
			String loseMessage = "<html>You have lost the fight against the " + enemy.getName() + ". Now that you have been vanquished, "
					+ "<br>the " + enemy.getName() + " will have free reign to destroy the planet, to unleash the Apocalypse "
					+ "<br>while you languish in the annals of history.</html>";
			JOptionPane.showMessageDialog(gameFrame, loseMessage, "Game Over", JOptionPane.NO_OPTION);
			gameManager.setPanel(PanelType.MAIN_PANEL);
		}
	}
	
	private void checkIfPlayerWon() {
		if (enemy.getHitPoints() <= 0) {
			String winMessage = "<html>You, " + name + ", have defeated the wild " + enemy.getName() + ". You are officially the greatest "
					+ "<br>environmental savior the world has ever known!</html>";
			JOptionPane.showMessageDialog(gameFrame, winMessage, "You Won!", JOptionPane.CLOSED_OPTION);
			//TODO: High score mechanism
			gameManager.setPanel(PanelType.MAIN_PANEL);
		}
	}
	
	public void autoSave() {
		save = new SaveGame();
		save.setGamePanel(gamePanel);
		save.setName(name);
		save.setEnemy(enemy);
		save.setQuestionList(questionList);
		
		save.setHitPoints(hitPoints);
		save.setMaxHitPoints(maxHitPoints);
		save.setAttackStrength(attackStrength);
		save.setHealStrength(healStrength);
		save.setHitPointsIncrease(hitPointsIncrease);
		save.setAttackStrengthIncrease(attackStrengthIncrease);
		save.setHealStrengthIncrease(healStrengthIncrease);
		
		//save.setCurrentStage(currentStage);
		//save.setCurrentDecision(currentDecision);
		//save.setCurrentAnsCorrect(isCurrentAnsCorrect);
		save.setCurrentTurn(currentTurn);
		//System.out.println(save.getCurrentTurn());
	}

	public void saveGame() {
		GameIOSystem fileSystem = gameManager.getIOSystem();
			
		try {
			fileSystem.showSaveChooser(save);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gameFrame, "An error occured while saving the game", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	@Override
	public String toString() {
		String str = "--All values are for player--\n"
				+ "Name: " + name + "\n"
				+ "Enemy: " + enemy.getName() + "\n"
				+ "Hit Points: " + hitPoints + "\n"
				+ "Max Hit Points: " + maxHitPoints + "\n"
				+ "Attack Strength: " + attackStrength + "\n"
				+ "Heal Strength: " + healStrength + "\n"
				+ "Hit Points Increase: " + hitPointsIncrease + "\n"
				+ "Attack Strength Increase: " + attackStrengthIncrease + "\n"
				+ "Heal Strength Increase: " + healStrengthIncrease + "\n"
				+ "---------\n"
				+ "Current Turn Stage: " + currentStage + "\n"
				+ "Current Player Decision: " + currentDecision + "\n"
				+ "Current Answer Correct: " + isCurrentAnsCorrect + "\n"
				+ "Turns Passed: " + currentTurn + "\n"
				+ "Current Question: " + questionList.getQuestionIndex() ;
				//+ "Saving Allowed: " + canSave;
		return str;
	}
}
