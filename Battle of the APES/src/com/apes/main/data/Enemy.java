package com.apes.main.data;

import java.io.Serializable;
import java.util.Random;
import com.apes.main.enums.EnemyDecision;
import com.apes.misc.AboutInfo;

public class Enemy implements GameData, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private String name;
	private String superAttackName;
	
	private int hitPoints;
	private int attackStrength;
	private int healStrength;
	private int superAttackStrength;
	
	private int attackStrengthIncrease;
	private int healStrengthIncrease;
	private int superAttackStrIncrease;
	
	public Enemy() {
		
	}
	
	public void beDamaged(int damage) {
		hitPoints = hitPoints - damage;
	}
	
	public EnemyDecision decide(int turnsPassed, int playerHealth, int playerAttackStr, int playerHealStr) {
		//System.out.println(toString());
		Random rng = new Random();
		int ranNum = rng.nextInt(100);
		
		int healProb = 0;
		int superAttackProb = 0;
		int increaseAttackProb = 0;
		int increaseHealProb = 0;
		
		if (hitPoints <= 2*playerAttackStr) {
			healProb = 20;
		} else if (hitPoints <= 5*playerAttackStr) {
			healProb = 12;
		} else if (hitPoints <= 10*playerAttackStr) {
			healProb = 8;
		} else {
			healProb = 4;
		}
		
		if (turnsPassed <= 90) {
			superAttackProb = turnsPassed/6;
			//System.out.println("Super Attack Prob: " + superAttackProb + "%");
		} else {
			superAttackProb = 15;
		}
		
		if ((playerAttackStr/attackStrength) <= 1) {
			increaseAttackProb = 0;
		} else if ((playerAttackStr/attackStrength) <= 5) {
			increaseAttackProb = playerAttackStr/attackStrength;
		} else {
			increaseAttackProb = 2*(playerAttackStr/attackStrength);
		}
		/*if (playerAttackStr >= 2*attackStrength) {
			increaseAttackProb = 20;
		} else if (playerAttackStr >= attackStrength) {
			increaseAttackProb = 10;
		} else {
			increaseAttackProb = 2;
		}*/
		
		if ((playerHealStr/healStrength) <= 1) {
			increaseHealProb = 0;
		} else {
			increaseHealProb = (playerHealStr/healStrength);
		}
		/*if (playerHealStr >= 2*healStrength) {
			increaseHealProb = 20;
		} else if (playerHealStr >= healStrength) {
			increaseHealProb = 10;
		} else {
			increaseHealProb = 2;
		}*/
		
		int healNum = healProb;
		int superAttackNum = healNum + superAttackProb;
		int increaseAttackNum = superAttackNum + increaseAttackProb;
		int increaseHealNum = increaseAttackNum + increaseHealProb;
		
		//assert(increaseHealNum <= 100);
		//System.out.print("Random Num: " + ranNum + ", ");
		if (playerHealth < attackStrength) {
			return EnemyDecision.ATTACK;
		} else if (ranNum < healNum) {
			//System.out.println("Heal Prob: " + healProb + ", Heal Num: " + healNum);
			return EnemyDecision.HEAL;
		} else if (ranNum < superAttackNum) {
			//System.out.println("Super Attack Num: " + superAttackProb + ", Super Attack Num: " + superAttackNum);
			return EnemyDecision.SUPER_ATTACK;
		} else if (ranNum < increaseAttackNum) {
			//System.out.println("Increase Attack Prob: " + increaseAttackProb + ", Heal Num: " + increaseAttackNum);
			return EnemyDecision.INCREASE_ATTACK;
		} else if (ranNum < increaseHealNum) {
			//System.out.println("Increase Heal Prob: " + increaseHealProb + ", Heal Num: " + increaseHealNum);
			return EnemyDecision.INCREASE_HEAL;
		} else {
			return EnemyDecision.ATTACK;
		}
	}
	
	public int attack() {
		int actualAttackStrength = attackStrength;
		int modifier = getStrengthModifier(EnemyDecision.ATTACK);
		actualAttackStrength = actualAttackStrength + modifier;
		return actualAttackStrength;
	}
	
	public int healUp() {
		int healthRestored = healStrength;
		int modifier = getStrengthModifier(EnemyDecision.HEAL);
		healthRestored = healthRestored + modifier;
		assert (healthRestored > 0);
		hitPoints = hitPoints + healthRestored;
		return healthRestored;
	}
	
	public int superAttack() {
		int actualSuperAttackStr = superAttackStrength;
		int modifier = getStrengthModifier(EnemyDecision.SUPER_ATTACK);
		actualSuperAttackStr = actualSuperAttackStr + modifier;
		return actualSuperAttackStr;
	}
	
	public int[] doubleAttack() {
		int[] strengths = new int[4];
		
		int prevAttack = attackStrength;
		int prevSuperAttack = superAttackStrength;
		strengths[0] = prevAttack;
		strengths[1] = prevSuperAttack;
		
		//attackStrength = 2*attackStrength;
		//superAttackStrength = 2*superAttackStrength;
		attackStrength = attackStrength + attackStrengthIncrease;
		superAttackStrength = superAttackStrength + superAttackStrIncrease;
		strengths[2] = attackStrength;
		strengths[3] = superAttackStrength;
		
		return strengths;
	}
	
	public int[] doubleHeal() {
		int[] healStrengths = new int[2];
		
		int prevStrength = healStrength;
		healStrengths[0] = prevStrength;
		
		//healStrength = 2*healStrength;
		healStrength = healStrength + healStrengthIncrease;
		healStrengths[1] = healStrength;
		
		return healStrengths;
	}
	
	private int getStrengthModifier(EnemyDecision strengthType) {
		int attackStrengthRange = 0;
		int rangeExtent = 10;
		int modifier = 0;
		Random modifierRNG = new Random();
		Random isModNegRNG = new Random();
		boolean modIsNeg;
		if (strengthType == EnemyDecision.ATTACK) {
			attackStrengthRange = attackStrength/rangeExtent;
		} else if (strengthType == EnemyDecision.HEAL) {
			attackStrengthRange = healStrength/rangeExtent;
		} else if (strengthType == EnemyDecision.SUPER_ATTACK) {
			attackStrengthRange = superAttackStrength/rangeExtent;
		}
		modifier = modifierRNG.nextInt(attackStrengthRange);
		modIsNeg = isModNegRNG.nextBoolean();
		if (modIsNeg) {
			modifier = -1*modifier;
			return modifier;
		} else {
			return modifier;
		}
	}

	public String getName() {
		return name;
	}

	public String getSuperAttackName() {
		return superAttackName;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getAttackStrength() {
		return attackStrength;
	}

	public int getHealStrength() {
		return healStrength;
	}

	public int getSuperAttackStrength() {
		return superAttackStrength;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSuperAttackName(String superAttackName) {
		this.superAttackName = superAttackName;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public void setAttackStrength(int attackStrength) {
		this.attackStrength = attackStrength;
	}

	public void setHealStrength(int healStrength) {
		this.healStrength = healStrength;
	}

	public void setSuperAttackStrength(int superAttackStrength) {
		this.superAttackStrength = superAttackStrength;
	}
	
	public void initIncreases() {
		attackStrengthIncrease = attackStrength;
		healStrengthIncrease = healStrength;
		superAttackStrIncrease = superAttackStrength;
	}
	
	@Override
	public String toString(){
		String enemyInfo = "Name: " + name + "\n"
				+ "Super Attack: " + superAttackName + "\n"
				+ "Hit Points: " + Integer.toString(hitPoints) + "\n"
				+ "Attack Strength: " + Integer.toString(attackStrength) + "\n" 
				+ "Heal Strength: " + Integer.toString(healStrength) + "\n"
				+ "Super Attack Strength: " + Integer.toString(superAttackStrength);
		return enemyInfo;
	}
}
