package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.apes.main.data.Enemy;

public class EnemyLoader extends GameJSONReader{

	public EnemyLoader() {
		cl = getClass().getClassLoader();
		input = cl.getResourceAsStream("com/apes/resources/Enemies.json");
	}
	
	@Override
	public Enemy readFromFile() throws FileNotFoundException, IOException, ParseException {
		ArrayList<JSONObject> enemyListJSON = new ArrayList<JSONObject>();
		
		Scanner fileScanner = new Scanner(/*new File("Resources/Text/Enemies.json")*/input);
		while (fileScanner.hasNext()){
			JSONObject obj = (JSONObject) new JSONParser().parse(fileScanner.nextLine());
			//System.out.println(obj.toJSONString());
			enemyListJSON.add(obj);
		}
		fileScanner.close();
		//System.out.println("Success!");
		Collections.shuffle(enemyListJSON);
		Enemy enemy = convertData(enemyListJSON.get(0));
		return enemy;
	}
	
	//@Override
	private Enemy convertData(JSONObject obj) {
		Enemy enemy = new Enemy();
		
		enemy.setName((String)obj.get("name"));
		enemy.setSuperAttackName((String)obj.get("super attack name"));
		enemy.setHitPoints((int)(long)obj.get("hit points"));
		enemy.setAttackStrength((int)(long)obj.get("attack strength"));
		enemy.setHealStrength((int)(long)obj.get("heal strength"));
		enemy.setSuperAttackStrength((int)(long)obj.get("super attack strength"));
		
		return enemy;
	}
}
