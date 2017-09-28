package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

//import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.apes.main.data.GameData;

public abstract class GameJSONReader {

	public ClassLoader cl;
	public InputStream input;
	
	public abstract GameData readFromFile() throws FileNotFoundException, IOException, ParseException;
	
	//public abstract GameData convertData(JSONObject obj);
}
