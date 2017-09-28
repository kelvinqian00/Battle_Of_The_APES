package com.apes.misc.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.apes.main.data.Question;
import com.apes.main.data.QuestionList;

public class QuestionLoader extends GameJSONReader {
	
	public QuestionLoader() {
		cl = getClass().getClassLoader();
		input = cl.getResourceAsStream("com/apes/resources/Questions.json");
	}

	@Override
	public QuestionList readFromFile () throws FileNotFoundException, IOException, ParseException {
		ArrayList<JSONObject> questionListJSON = new ArrayList<JSONObject>();
		Scanner fileScanner = new Scanner(/*new File("Resources/Text/Questions.json")*/input);
		while (fileScanner.hasNext()){
			JSONObject obj = (JSONObject) new JSONParser().parse(fileScanner.nextLine());
			questionListJSON.add(obj);
		}
		fileScanner.close();
		Collections.shuffle(questionListJSON);
		
		QuestionList questionList = new QuestionList();
		for (int i=0; i < questionListJSON.size(); i++) {
			questionList.add(convertData(questionListJSON.get(i)));
		}
		
		return questionList;
	}
	
	//@Override
	private Question convertData(JSONObject obj) {
		Question question = new Question();
		
		question.setQuestionText((String) obj.get("question"));
		for (int i=0; i<5; i++) {
			String choiceKey = "choice " + Integer.toString(i);
			question.setIndividualChoices(i, (String)obj.get(choiceKey));
			assert(question.getQuestionChoices() != null);
		}
		question.setCorrectChoice((int)(long)obj.get("question answer"));
		return question;
	}
}
