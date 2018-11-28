package edu.sjsu.cs.cs151.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.event.*;

import edu.sjsu.cs.cs151.LeftClickMessage;
import edu.sjsu.cs.cs151.Message;
import edu.sjsu.cs.cs151.NewGameMessage;
import edu.sjsu.cs.cs151.RightClickMessage;
import edu.sjsu.cs.cs151.controller.GameInfo;
import edu.sjsu.cs.cs151.model.Model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.List;
import java.util.ArrayList;

/**
 * Class that will render visual representation of the game
 */
public class View extends JFrame {

	JPanel controlPanel;
	JPanel fieldPanel;
	JLabel mineCounter;

	BlockingQueue<Message> queue;
	Model model;
	int numberOfColumns;
	int numberOfRows;

  List<JButton> CellButtonList;

	/**
	 * Create instance of View and pass queue that will store messages about user
	 * actions
	 * 
	 * @param queue
	 *            synchronized queue to store Messages, shared with Controller
	 */
	public View(BlockingQueue<Message> queue) {
		this.queue = queue;

		// Replace with information from GameInfo
		int numberOfMines = 4;
		int numberOfRows = 8;
		int numberOfColumns = 8;

		this.numberOfColumns = numberOfColumns;
		this.numberOfRows = numberOfRows;

		// CONTROLPANEL
		// Top row that holds mine counter, new game button, and timer
		controlPanel = new JPanel();

		// Create elements for control panel
		mineCounter = new JLabel("" + numberOfMines);
		JButton newGameButton = new JButton("New Game");
		// Add listener to button
		newGameButton.addActionListener(new NewGameListener());
		JLabel timer = new JLabel("00:00");
		controlPanel.add(mineCounter);
		controlPanel.add(newGameButton);
		controlPanel.add(timer);

		// FIELDPANEL
		// Panel for a mine field
		fieldPanel = new JPanel();
		fieldPanel.setSize(200, 500);
		fieldPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		fieldPanel.setLayout(new GridLayout(numberOfRows, numberOfColumns));

    CellButtonList = new ArrayList<>();

		for (int i = 0; i < numberOfRows * numberOfColumns; i++) {
			JButton cell = new JButton();

			cell.setName(i + ", ");
			cell.setPreferredSize(new Dimension(30, 30));
			cell.addMouseListener(new MineFieldListener());
			fieldPanel.add(cell);
      CellButtonList.add(cell);
		}

		// this.setSize(200, 600);
		this.add(controlPanel, BorderLayout.CENTER);
		this.add(fieldPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * Send updated model to render in GameView
	 * 
	 * @param model
	 *            is updated model of the game
	 */

	public void change(GameInfo gameInfo) {
		// TODO: Iterate over integer representation of mine field in gameInfo
		// gameInfo.getGameStatus();
		// Populate JPanel fieldPanel with buttons.
		// Assign integer from 0 to N (where N = width*height) number to each button.
		// height = row, width = column => buttonNumber = width + (height * 8)
		// Update number of remaining mines in JLabel mineCounter.

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {

      int buttonNumber = 0;
      ImageIcon icon;

      for (JButton jb: CellButtonList) {                                         
	      int row = (int) buttonNumber / numberOfColumns;
	      int column = buttonNumber - (row * numberOfColumns);
                                                                                 
        int adjacentMines = gameInfo.getGameStatus()[row][column];
                                                                                 
        if (adjacentMines == -1) { // mine
          jb.setText("M");
        }
        else if (adjacentMines == 10) { // flag
          //icon = new ImageIcon(getClass().getResource("/resource/img/f.png"));
          //jb.setIcon(icon);
          jb.setText("?");
        }
        else if (adjacentMines > 0) { // number cell
          jb.setText(adjacentMines + "");
        }
        else if (adjacentMines == 0) { // empty cell
          jb.setText("0");
        }
        else if (adjacentMines == 20) {
          jb.setText("X");
        }
        //else {
        //  ;
        //}
        buttonNumber++;
      } // EO-for
      } // EO-run()
    }); // EO-invokeLater
  }

	/**
	 * Initialize new View with specified queue
	 * 
	 * @param queue
	 * @return
	 */
	public static View init(BlockingQueue<Message> queue) {
		return new View(queue);
	}

	/**
	 * Inner class to handle click on NewGameButton
	 */
	private class NewGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				queue.put(new NewGameMessage());
				printQueue();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		}

	}

	/**
	 * Inner class to handle right-click and left-click on cell button
	 *
	 */
	private class MineFieldListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// Get id of a clicked button
			// There are a lot of information, we need to clean it
			// to get just the first number
			String buttonInfo = e.getSource().toString();
			buttonInfo = buttonInfo.replaceFirst("javax.swing.JButton\\[", "");
			buttonInfo = buttonInfo.split(",")[0];
			int buttonNumber = Integer.parseInt(buttonInfo);

			// Calculate row (height) and column (width) of the button
			int row = (int) buttonNumber / numberOfColumns;
			int column = buttonNumber - (row * numberOfColumns);
			System.out.println("Button with row: " + row + ", column: " + column);

			// Check if it was right or left click. Create appropriate Message
			// for message queue
			if (SwingUtilities.isRightMouseButton(e)) {
				try {
					// Create message for Right-click
					queue.put(new RightClickMessage(row, column));
          replaceFlag(e); // toggle view for flag
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				System.out.println("Right button clicked");
			} else {
				try {
					// Create message for Left-click
					queue.put(new LeftClickMessage(row, column));
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
				System.out.println("Left button clicked");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	/**
	 * Helper function to test the content of message queue
	 */
	public void printQueue() {
		System.out.println("\nPrinting everything in queue!");
		for (Message message : queue) {
			System.out.println(message);
		}
	}

	public void run() {
	}

  public void replaceFlag(MouseEvent e) {
    JButton jb = (JButton) e.getSource();
    jb.setText("");
  }
}
