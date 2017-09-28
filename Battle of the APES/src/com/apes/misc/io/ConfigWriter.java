package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import com.apes.main.data.ConfigData;

public class ConfigWriter extends GameJSONWriter{

	private ConfigData config;
	private JSONObject configJSON;
	private String path;
	
	public ConfigWriter(ConfigData config) {
		this.config = config;
		this.path = GameJSONWriter.path + "ConfigData.json";
		configJSON = setupJSON();
		writeToFile(configJSON, path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject setupJSON() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("max hit points", config.getMaxHitPoints());
		jsonObject.put("attack strength", config.getAttackStrength());
		jsonObject.put("heal strength", config.getHealStrength());
		jsonObject.put("attack strength increase", config.getAttackStrengthIncrease());
		jsonObject.put("hit points increase", config.getHitPointsIncrease());
		jsonObject.put("heal strength increase", config.getHealStrengthIncrease());
		
		return jsonObject;
	}
	
	@Override
	protected void writeToFile(JSONObject jsonObject, String path) {
		assert(!jsonObject.isEmpty());
		try {
			FileWriter mainWriter = new FileWriter(path, false);
			mainWriter.write(jsonObject.toJSONString() + "\n");
			mainWriter.flush();
			mainWriter.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ConfigData.json cannot be found.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error writing to ConfigData.json occured.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
