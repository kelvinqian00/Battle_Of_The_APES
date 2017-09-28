package com.apes.misc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.apes.main.SaveGame;

public class GameIOSystem {

	private SaveGame saveGame;
	private JFrame gameFrame;
	private JFileChooser fileChooser;
	
	public GameIOSystem(JFrame gameFrame) {
		this.gameFrame = gameFrame;
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".SER Files", "ser");
		fileChooser.setFileFilter(filter);
	}
	
	public void showSaveChooser(SaveGame saveGame) throws IOException {
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.saveGame = saveGame;
		String name = saveGame.getName();
		int turnNum = saveGame.getCurrentTurn();
		String fileName = name + "-Turn" + Integer.toString(turnNum) + ".ser";
		
		int saveChoice = fileChooser.showSaveDialog(gameFrame);
		File fileDir = fileChooser.getSelectedFile();
		File newFile = new File(fileDir.getPath() + "/" + fileName);
		
		if (saveChoice == JFileChooser.APPROVE_OPTION) {
			if (newFile.exists()) {
				int response = JOptionPane.showConfirmDialog(fileChooser, "A file of the same name exists. "
						+ "Do you wish to overwrite it?", "File Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					saveGame(newFile);
				}
			} else {
				saveGame(newFile);
			}
		}
	}
	
	public SaveGame showOpenChooser() throws ClassNotFoundException, IOException {
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int openChoice = fileChooser.showOpenDialog(gameFrame);
		File saveFile = null;
		if(openChoice == JFileChooser.APPROVE_OPTION) {
			saveFile = fileChooser.getSelectedFile();
			//System.out.println(saveFile.getPath());
			SaveGame save = openGame(saveFile);
			return save;
		} else {
			return null;
		}
	}
	
	private void saveGame(File file) throws IOException {
		FileOutputStream fileStream = new FileOutputStream(file);
		ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
		outputStream.writeObject(saveGame);
		outputStream.close();
	}
	
	private SaveGame openGame(File file) throws IOException, ClassNotFoundException {
		FileInputStream fileStream = new FileInputStream(file);
		//System.out.println(fileStream.toString());
		ObjectInputStream inputStream = new ObjectInputStream(fileStream);
		SaveGame save = (SaveGame) inputStream.readObject();
		//assert (inputStream.readObject() != null);
		inputStream.close();
		assert (save != null);
		return save;
	}
	
}
