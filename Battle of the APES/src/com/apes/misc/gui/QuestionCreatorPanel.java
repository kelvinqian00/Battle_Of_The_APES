package com.apes.misc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.main.data.Question;
import com.apes.misc.AboutInfo;
import com.apes.misc.io.QuestionWriter;

import net.miginfocom.swing.MigLayout;

public class QuestionCreatorPanel extends CheatPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();
	
	private GameManager gameManager;
	
	private JLabel questionTitleLabel;
	private JLabel questionLabel;
	private JLabel[] choiceLabels;
	
	private JTextField questionField;
	private JTextField[] choiceFields;
	
	private JLabel answerLabel;
	private JRadioButton[] answerButtons;
	private JLayeredPane answerButtonsPane;
	private ButtonGroup answerButtonGroup;
	
	private JLabel createConfirm;
	private JButton create;
	private JButton close;
	
	private int numberOfQuestions = 0;
	
	public QuestionCreatorPanel(GameManager gameManger) {
		this.gameManager = gameManger;
		
		/*path = super.showFileChooser();
		if (path == null) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {*/
		setLayout(new MigLayout());
		
		createLabels();
		createFields();
		createButtons();
		addToPane();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == create) {
			try {
				loadObject();
				//createConfirm.setText("You created a new question!");
			} catch (NullPointerException | NumberFormatException e) {
				createConfirm.setText("You forgot one or more fields!");
				e.printStackTrace();
			}
		} else if (event.getSource() == close) {
			gameManager.setPanel(PanelType.MAIN_PANEL);
		} else {}
		
	}
	
	@Override
	protected void createLabels() {
		questionTitleLabel = new JLabel("Here, you can add new questions that the player will "
				+ "answer during gameplay.");
		questionLabel = new JLabel("Question Text:");
		choiceLabels = new JLabel[5];
		answerLabel = new JLabel("Correct Answer Number:");
		
		for (int i=0; i < choiceLabels.length; i++) {
			choiceLabels[i] = new JLabel("Answer " + Integer.toString(i+1));
		}
	}

	@Override
	protected void createFields() {
		questionField = new JTextField();
		choiceFields = new JTextField[5];
		answerButtons = new JRadioButton[5];
		answerButtonsPane = new JLayeredPane();
		answerButtonGroup = new ButtonGroup();
		
		answerButtonsPane.setLayout(new MigLayout());
		add(answerLabel, "growx, span 2, wrap");
		add(answerButtonsPane, "growx, span 2, wrap");
		
		assert((choiceLabels.length == choiceFields.length));
		for (int i=0; i < choiceLabels.length; i++) {
			choiceFields[i] = new JTextField();
		}
		
		assert((choiceLabels.length == answerButtons.length) && (choiceFields.length == answerButtons.length));
		for (int i=0; i < answerButtons.length; i++) {
			answerButtons[i] = new JRadioButton("Answer " + Integer.toString(i+1));
			answerButtons[i].setActionCommand(Integer.toString(i));
			answerButtonGroup.add(answerButtons[i]);
			answerButtonsPane.add(answerButtons[i]);
		}
		
		answerButtons[0].setSelected(true);
	}

	@Override
	protected void createButtons() {
		createConfirm = new JLabel("You have not created any questions yet.");
		create = new JButton("Create Question");
		close = new JButton("Go Back");
		
		create.addActionListener(this);
		close.addActionListener(this);
	}

	@Override
	protected void addToPane() {
		add(questionTitleLabel, "growx, span 2, wrap");
		add(questionLabel);
		add(questionField, "growx, wrap");
		for (int i=0; i < choiceLabels.length; i++) {
			add(choiceLabels[i]);
			add(choiceFields[i], "growx, wrap");
		}
		add(answerLabel, "growx, span 2, wrap");
		add(answerButtonsPane, "growx, span 2, wrap");
		add(createConfirm, "growx, span 2, wrap");
		add(create, "growx");
		add(close, "growx, wrap");
		
	}

	@Override
	protected void loadObject() throws NumberFormatException, NullPointerException {
		String questionText = questionField.getText();
		String[] questionChoices = new String[choiceFields.length];
		int questionAnswer = 0;
		for (int i = 0; i < choiceFields.length; i++) {
			questionChoices[i] = choiceFields[i].getText();
			if (answerButtons[i].isSelected()) {
				questionAnswer = i;
			}
		}
		Question question = new Question();
		question.setQuestionText(questionText);
		question.setQuestionChoices(questionChoices);
		question.setCorrectChoice(questionAnswer);
		
		numberOfQuestions++;
		
		createConfirm.setText("You have successfully created " + numberOfQuestions + " questions!");
		new QuestionWriter(question);
	}
	
}
