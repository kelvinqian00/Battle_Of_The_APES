package com.apes.misc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import com.apes.main.GameManager;
import com.apes.main.enums.PanelType;
import com.apes.misc.AboutInfo;

import net.miginfocom.swing.MigLayout;

public class InfoPanel extends JLayeredPane implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = AboutInfo.getSerialUID();

	protected GameManager gameManager;
	protected ClassLoader cl;
	protected InputStream input;
	
	private JScrollPane scrollPane;
	private JLabel label;
	private JPanel labelPanel;
	private JButton returnButton;
	
	public InfoPanel(GameManager gameManager) {
		this.gameManager = gameManager;
		cl = getClass().getClassLoader();
		input = cl.getResourceAsStream("com/apes/resources/Help.txt");
		try {
			setUpPane(readLabel());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(gameManager.getGameFrame(), "A file I/O error has occured.", "Error", JOptionPane.ERROR_MESSAGE);
			gameManager.setPanel(PanelType.MAIN_PANEL);
			e.printStackTrace();
		}
	}
	
	private String readLabel() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String label = null;
		String line = null;
		ArrayList<String> list = new ArrayList<String>();
		while((line = br.readLine()) != null) {
			if (line.equals("")) {
				list.add("<br><br>");
			} else {
				list.add(line);
				}
			}
		br.close();
		label = String.join("", list);
		return label;
	}
	
	protected void setUpPane(String labelText) {
		setLayout(new MigLayout("", "[grow]", "[grow]push[]"));
		
		label = new JLabel(labelText);
		labelPanel = new JPanel();
		labelPanel.setLayout(new MigLayout("fillx", "[500]", ""));
		labelPanel.add(label);
		
		scrollPane = new JScrollPane();
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(labelPanel);
		scrollPane.setPreferredSize(gameManager.getGameFrame().getPreferredSize());
		
		returnButton = new JButton("Go Back");
		returnButton.addActionListener(this);
		
		add(scrollPane, "growx, growy");
		add(returnButton, "growx, dock south");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		gameManager.setPanel(PanelType.MAIN_PANEL);
	}
}
