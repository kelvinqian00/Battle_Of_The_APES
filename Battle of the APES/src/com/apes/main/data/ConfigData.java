package com.apes.main.data;

public class ConfigData implements GameData{

	private int maxHitPoints;
	private int attackStrength;
	private int healStrength;
	
	private int hitPointsIncrease;
	private int attackStrengthIncrease;
	private int healStrengthIncrease;
	
	public ConfigData() {
		//Nothing goes here
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
}
