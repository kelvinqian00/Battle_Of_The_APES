package com.apes.main.data;

import java.util.ArrayList;
import java.util.Collections;

import com.apes.misc.AboutInfo;

public class QuestionList extends ArrayList<Question> implements GameData{

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private int questionIndex = 0;
	
	public Question getNewQuestion() {
		if (questionIndex >= this.size()) {
			Collections.shuffle(this); // reshuffle questions
			questionIndex = 0; //reset question index
		}
		Question question = this.get(questionIndex);
		incrementIndex();
		return question;
	}
	
	private void incrementIndex() {
		questionIndex++;
	}
	
	public int getQuestionIndex() {
		return questionIndex;
	}
	
	@Override
	public String toString() {
		ArrayList<String> nameString = new ArrayList<String>();
		for (int i=0; i < this.size(); i++) {
			nameString.add(this.get(i).getQuestionText());
		}
		return nameString.toString();
	}
}
