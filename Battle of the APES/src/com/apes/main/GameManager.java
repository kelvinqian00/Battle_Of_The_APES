package com.apes.main;

import javax.swing.*;

import com.apes.main.enums.PanelType;
import com.apes.main.gui.*;
import com.apes.misc.AboutInfo;
import com.apes.misc.gui.*;
import com.apes.misc.io.GameIOSystem;

public class GameManager {

	private JFrame gameFrame;
	private MainPanel mainPanel;
	private GamePanel gamePanel;
	private OptionsPanel optionsPanel;
	private EnemyCreatorPanel enemyCreatorPanel;
	private QuestionCreatorPanel questionCreatorPanel;
	private PlayerConfigPanel configPanel;
	private InfoPanel helpPanel;
	
	private GameIOSystem fileSystem;
	private GameEngine gameEngine;
	
	public GameManager(boolean isInit) {
		if (isInit) {
			gameFrame = new JFrame("Battle of the APES");
			setPanel(PanelType.MAIN_PANEL);
			gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			//gameFrame.setSize(new Dimension(350, 300));
			gameFrame.pack();
			gameFrame.setVisible(true);
			//if (gameFrame == null) System.out.println("Game Frame is null");
			fileSystem = new GameIOSystem(gameFrame);
		}
	}
	
	/*public enum PanelType {
		MAIN_PANEL, NEW_GAME_PANEL, LOAD_GAME_PANEL, RESUME_GAME_PANEL, 
		OPTIONS_PANEL, ENEMY_CREATOR_PANEL, QUESTION_CREATOR_PANEL, CONFIG_PANEL, 
		ABOUT_PANEL
	}*/
	
	public void startGame(boolean isNewGame) {
		gameEngine = new GameEngine(isNewGame, this);
	}
	
	public void setPanel(PanelType panelType) {
		if (gameFrame.isResizable()) gameFrame.setResizable(false);
		switch (panelType){
		case MAIN_PANEL:
			mainPanel = new MainPanel(this);
			gameFrame.setContentPane(mainPanel);
			break;
		case NEW_GAME_PANEL:
			startGame(true);
			break;
		case LOAD_GAME_PANEL:
			startGame(false);
			break;
		case RESUME_GAME_PANEL:
			gameFrame.setContentPane(gameEngine.getGamePanel());
			break;
		case OPTIONS_PANEL:
			optionsPanel = new OptionsPanel(this, gameEngine);
			gameFrame.setContentPane(optionsPanel);
			break;
		case ENEMY_CREATOR_PANEL:
			enemyCreatorPanel = new EnemyCreatorPanel(this);
			gameFrame.setContentPane(enemyCreatorPanel);
			break;
		case QUESTION_CREATOR_PANEL:
			questionCreatorPanel= new QuestionCreatorPanel(this);
			gameFrame.setContentPane(questionCreatorPanel);
			break;
		case CONFIG_PANEL:
			configPanel = new PlayerConfigPanel(this);
			gameFrame.setContentPane(configPanel);
			break;
		case HELP_PANEL:
			gameFrame.setResizable(true);
			helpPanel = new InfoPanel(this);
			gameFrame.setContentPane(helpPanel);
			break;
		case ABOUT_PANEL:
			new AboutInfo(this);
			break;
		default: /*do nothing*/
			break;
		}
		gameFrame.pack();
		gameFrame.revalidate();
	}

	public JFrame getGameFrame() {
		assert (gameFrame != null);
		return gameFrame;
	}
	
	public GamePanel getGamePanel() {
		assert (gamePanel != null);
		if (gamePanel != null) {
			return gamePanel;
		} else {
			throw new NullPointerException();
		}
	}
	
	public GameIOSystem getIOSystem() {
		return fileSystem;
	}
	
}
