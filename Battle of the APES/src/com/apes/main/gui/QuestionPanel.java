package com.apes.main.gui;

import java.awt.Dimension;

import javax.swing.*;

import com.apes.main.GameEngine;
import com.apes.main.data.Question;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class QuestionPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private JLayeredPane panel;
	
	private String questionText;
	private String[] questionChoices;
	private int questionAnswer;
	
	private JRadioButton[] questionChoicesButtons;
	private ButtonGroup questionChoicesGroup;
	
	private String questionAnsText;
	private int playerSelectedAnswer = -1;
	
	public QuestionPanel(GamePanel gamePanel, GameEngine gameEngine, Question question) {
		assert (gamePanel != null);
		assert (gameEngine != null);
		assert (question != null);
		
		panel = new JLayeredPane();
		panel.setLayout(new MigLayout("", "", ""));
		
		//System.out.println("Almost successful");
		//question = selectQuestion();
		questionText = question.getQuestionText();
		questionChoices = question.getQuestionChoices();
		questionAnswer = question.getCorrectChoice();
		
		questionChoicesButtons = new JRadioButton[5];
		questionChoicesGroup = new ButtonGroup();
		for (int i=0; i<questionChoices.length; i++) {
			questionChoicesButtons[i] = new JRadioButton();
			questionChoicesButtons[i].setText(questionChoices[i]);
			questionChoicesGroup.add(questionChoicesButtons[i]);
			panel.add(questionChoicesButtons[i], "wrap");
		}
		questionChoicesButtons[0].setSelected(true);
		
		//setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//setSize(350, 300);
		setPreferredSize(new Dimension(330, 150));
		setViewportView(panel);
	}
	
	public boolean checkAnswer() {
		assert((questionAnswer>=0) && (questionAnswer<5));
		for (int i=0; i<questionChoicesButtons.length; i++) {
			if (questionChoicesButtons[i].isSelected()) {
				playerSelectedAnswer = i;
			}
			questionChoicesButtons[i].setEnabled(false);
		}
		//return playerSelectedAnswer;
		if (playerSelectedAnswer == questionAnswer) {
			setQuestionAnsText(true);
			return true;
		} else {
			setQuestionAnsText(false);
			return false;
		}
	}

	public String getModifiedQuesText() {
		String text = "<html>You must demonstrate your knowledge, by answering this question, "
				+ "in order to complete this action and level up: <br>"
				+ "<br>" + questionText + "</html>";
		return text;
	}

	private void setQuestionAnsText(boolean isCorrect) {
		String correctChoice = Integer.toString(questionAnswer + 1);
		String playerChoice = Integer.toString(playerSelectedAnswer + 1);
		String correctAnsText = "Choice " + correctChoice + ": " + questionChoices[questionAnswer];
		if (isCorrect) {
			questionAnsText = "<html>You have selected the correct answer! "
					+ "You may proceed with leveling up! <br>"
					+ "<br>Your answer: <br>" + correctAnsText + "</html>";
		} else {
			questionAnsText = "<html>Alas, you have answered incorrectly! "
					+ "As you have failed the trial, you will not be leveling up.<br>"
					+ "<br>Your Answer: <br>"
					+ "Choice " + playerChoice + ": " + questionChoices[playerSelectedAnswer] + "<br>"
					+"<br>Correct Answer: <br>" + correctAnsText + "</html>";
		}
	}
	
	public String getQuestionAnsText() {
		return questionAnsText;
	}
}
