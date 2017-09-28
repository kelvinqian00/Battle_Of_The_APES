package com.apes.misc.io;

import org.json.simple.JSONObject;

import com.apes.main.data.Enemy;

public class EnemyWriter extends GameJSONWriter{
	
	private Enemy enemy;
	private JSONObject enemyJSON;
	private String path /*= "Resources/Text/Enemies.json"*/;
	
	public EnemyWriter (Enemy enemy) {
		this.enemy = enemy;
		this.path = GameJSONWriter.path + "Enemies.json";
		enemyJSON = setupJSON();
		super.writeToFile(enemyJSON, path);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject setupJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", enemy.getName());
		jsonObject.put("super attack name", enemy.getSuperAttackName());
		jsonObject.put("hit points", enemy.getHitPoints());
		jsonObject.put("attack strength", enemy.getAttackStrength());
		jsonObject.put("heal strength", enemy.getHealStrength());
		jsonObject.put("super attack strength", enemy.getSuperAttackStrength());
		
		return jsonObject;
	}
	
}
