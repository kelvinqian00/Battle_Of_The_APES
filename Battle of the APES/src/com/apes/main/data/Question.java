package com.apes.main.data;

import java.io.Serializable;

import com.apes.misc.AboutInfo;

public class Question implements GameData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private String questionText;
	private String[] questionChoices;
	private int correctChoice;
	
	public Question() {
		questionChoices = new String[5];
	}

	public String getQuestionText() {
		return questionText;
	}

	public String[] getQuestionChoices() {
		return questionChoices;
	}

	public int getCorrectChoice() {
		return correctChoice;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setQuestionChoices(String[] questionChoices) {
		this.questionChoices = questionChoices;
	}
	
	public void setCorrectChoice(int correctChoice) {
		this.correctChoice = correctChoice;
	}
	
	public void setIndividualChoices(int index, String choice) {
		questionChoices[index] = choice;
	}
	
	@Override
	public String toString() {
		String questionString = "Question Text: " + questionText + "\n"
				+ "Choice 1: " + questionChoices[0] + "\n"
				+ "Choice 2: " + questionChoices[1] + "\n"
				+ "Choice 3: " + questionChoices[2] + "\n"
				+ "Choice 4: " + questionChoices[3] + "\n"
				+ "Choice 5: " + questionChoices[4] + "\n"
				+ "Correct Answer Number: " + Integer.toString(correctChoice);
		return questionString;
	}
	
}
