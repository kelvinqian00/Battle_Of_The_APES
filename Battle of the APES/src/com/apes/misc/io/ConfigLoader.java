package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.apes.main.data.ConfigData;

public class ConfigLoader extends GameJSONReader{

	public ConfigLoader() {
		cl = getClass().getClassLoader();
		input = cl.getResourceAsStream("com/apes/resources/ConfigData.json");
	}
	
	@Override
	public ConfigData readFromFile() throws FileNotFoundException, IOException, ParseException {
		Scanner fileScanner = new Scanner(input);
		//Scanner fileScanner = new Scanner(new File("Resources/Text/ConfigData.json"));
		JSONObject obj = (JSONObject) new JSONParser().parse(fileScanner.nextLine());
		fileScanner.close();
		//if (obj != null) System.out.println("Successfully did file");
		ConfigData configData = convertData(obj);
		return configData;
	}
	
	//@Override
	private ConfigData convertData(JSONObject obj) {
		ConfigData configData = new ConfigData();
		configData.setMaxHitPoints((int)(long)obj.get("max hit points"));
		configData.setAttackStrength((int)(long)obj.get("attack strength"));
		configData.setHealStrength((int)(long)obj.get("heal strength"));
		configData.setAttackStrengthIncrease((int)(long)obj.get("attack strength increase"));
		configData.setHitPointsIncrease((int)(long)obj.get("hit points increase"));
		configData.setHealStrengthIncrease((int)(long)obj.get("heal strength increase"));
		return configData;
	}
}
