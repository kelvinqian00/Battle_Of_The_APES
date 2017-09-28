package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;

public class GameJSONWriter {
	
	public static String path = "src/com/apes/resources/";
	public GameJSONWriter() {}
	
	/*protected InputStream setupInput(String path) {
		ClassLoader cl = getClass().getClassLoader();
		InputStream input = cl.getResourceAsStream(path);
		return input;
	}*/
	
	protected JSONObject setupJSON() {
		JSONObject jsonObject = new JSONObject();
		return jsonObject;
	}
	
	protected void writeToFile(JSONObject jsonObject, String path) {
		assert(!jsonObject.isEmpty());
		try {
			FileWriter mainWriter = new FileWriter(path, true);
			mainWriter.append(jsonObject.toJSONString() + "\n");
			mainWriter.flush();
			mainWriter.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The specified file cannot be found.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error in writing to the specified file occured.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
