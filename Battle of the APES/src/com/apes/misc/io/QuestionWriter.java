package com.apes.misc.io;

import org.json.simple.JSONObject;

import com.apes.main.data.Question;

public class QuestionWriter extends GameJSONWriter{

	private Question question;
	private JSONObject questionJSON;
	private String path /*= "Resources/Text/Questions.json"*/;
	
	public QuestionWriter(Question question) {
		this.question = question;
		this.path = GameJSONWriter.path + "Questions.json";
		questionJSON = setupJSON();
		super.writeToFile(questionJSON, path);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected JSONObject setupJSON() {
		JSONObject jsonObject = new JSONObject();
		
		String questionText = question.getQuestionText();
		String[] questionChoice = question.getQuestionChoices();
		int questionAnswer = question.getCorrectChoice();
		assert(questionChoice.length == 5);
		
		jsonObject.put("question", questionText);
		for (int i=0; i<questionChoice.length; i++) {
			jsonObject.put("choice " + i, questionChoice[i]);
		}
		jsonObject.put("question answer", questionAnswer);
		return jsonObject;
	}
}
