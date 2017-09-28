package com.apes.main;

import java.io.Serializable;
//import java.util.ArrayList;

//import com.apes.main.data.ConfigData;
import com.apes.main.data.Enemy;
import com.apes.main.data.GameData;
//import com.apes.main.data.Question;
import com.apes.main.data.QuestionList;
//import com.apes.main.enums.PlayerDecision;
//import com.apes.main.enums.TurnStage;
import com.apes.main.gui.GamePanel;
import com.apes.misc.AboutInfo;

public class SaveGame implements GameData, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private transient GamePanel gamePanel;
	
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
	
	//private TurnStage currentStage;
	//private PlayerDecision currentDecision;
	//private boolean isCurrentAnsCorrect;
	private int currentTurn;
	
	public SaveGame() {
		//Constructor
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public String getName() {
		return name;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public QuestionList getQuestionList() {
		return questionList;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public int getAttackStrength() {
		return attackStrength;
	}

	public int getHealStrength() {
		return healStrength;
	}

	public int getHitPointsIncrease() {
		return hitPointsIncrease;
	}

	public int getAttackStrengthIncrease() {
		return attackStrengthIncrease;
	}

	public int getHealStrengthIncrease() {
		return healStrengthIncrease;
	}

	/*public ConfigData getConfigData() {
		return configData;
	}*/

	/*public TurnStage getCurrentStage() {
		return currentStage;
	}

	public PlayerDecision getCurrentDecision() {
		return currentDecision;
	}

	public boolean isCurrentAnsCorrect() {
		return isCurrentAnsCorrect;
	}*/

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public void setQuestionList(QuestionList questionList) {
		this.questionList = questionList;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public void setAttackStrength(int attackStrength) {
		this.attackStrength = attackStrength;
	}

	public void setHealStrength(int healStrength) {
		this.healStrength = healStrength;
	}

	public void setHitPointsIncrease(int hitPointsIncrease) {
		this.hitPointsIncrease = hitPointsIncrease;
	}

	public void setAttackStrengthIncrease(int attackStrengthIncrease) {
		this.attackStrengthIncrease = attackStrengthIncrease;
	}

	public void setHealStrengthIncrease(int healStrengthIncrease) {
		this.healStrengthIncrease = healStrengthIncrease;
	}

	/*public void setConfigData(ConfigData configData) {
		this.configData = configData;
	}*/

	/*public void setCurrentStage(TurnStage currentStage) {
		this.currentStage = currentStage;
	}

	public void setCurrentDecision(PlayerDecision currentDecision) {
		this.currentDecision = currentDecision;
	}

	public void setCurrentAnsCorrect(boolean isCurrentAnsCorrect) {
		this.isCurrentAnsCorrect = isCurrentAnsCorrect;
	}*/

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
}
