package package1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**********************************************************************
 * Class that handles creating and managing the panels necessary for the
 * GUI
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 **********************************************************************/

public class SuperTicTacToePanel extends JPanel {

	/** An array of JButtons to represent the board */
	private JButton[][] board;

	/**
	 * An array of CellStatus enums to represent the status of the board
	 * spaces
	 */
	private CellStatus[][] iBoard;

	/** A JLabel that tracks the number of X wins */
	private JLabel xWon;

	/** A JLabel that tracks the number of O wins */
	private JLabel oWon;

	/** A JButton for quit */
	private JButton quitButton;

	/** A JButton for undo */
	private JButton undoButton;

	/** A JButton for reset */
	private JButton resetButton;

	/** A JMenuItem for a new game (not used) */
	private JMenuItem gameItem;

	/** A JMenu item for quit */
	private JMenuItem quitItem;

	/** A JPanel that holds score labels and function buttons */
	private JPanel bottom;

	/** A JPanel that holds the game board */
	private JPanel center;

	/**
	 * The game class variable that tracks all game related info and
	 * changes
	 */
	private SuperTicTacToeGame game;

	/** An icon for an empty space */
	private ImageIcon emptyIcon;

	/** An icon for an X */
	private ImageIcon xIcon;

	/** An icon for an O */
	private ImageIcon oIcon;

	/** Variable that tracks whether the AI is enabled or not */
	private boolean aiOn;

	/******************************************************************
	 * Default constructor for the SuperTicTacToePanel class.
	 * 
	 * @param pquitItem
	 *            Passed quit menu item
	 * @param pgameItem
	 *            Passed new game menu item (not used)
	 * 
	 * @throws IllegalArgumentException
	 *             When parameters for setting the board size, length to
	 *             win, first move, or number of players are out of
	 *             their useful settings
	 *****************************************************************/
	public SuperTicTacToePanel(JMenuItem pquitItem,
			JMenuItem pgameItem) {

		// Sets menu items to their passed parameters
		gameItem = pgameItem;
		quitItem = pquitItem;

		// Instantiates new JPanels for displaying the game
		bottom = new JPanel();
		center = new JPanel();

		// Instantiates the necessary icons for the game from their
		// correct file paths
		xIcon = new ImageIcon("src/package1/X.png");
		oIcon = new ImageIcon("src/package1/O.png");
		emptyIcon = new ImageIcon("src/package1/empty.png");

		// Creates listener
		ButtonListener listener = new ButtonListener();

		// Instantiates the game object
		game = new SuperTicTacToeGame(3);

		// Creates quit, undo, and reset buttons and adds listeners to
		// them
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		// Creates quit and new game menu items
		gameItem = new JMenuItem("Quit");
		quitItem = new JMenuItem("New Game");

		// Sets the ai to off by default
		aiOn = false;

		// Creates two variables to be used in getting user input below
		int boardLength = 0;
		int lengthToWin = 0;

		// Gets input for board length, throws IllegalArgumentError if
		// parameters are outside the requested range
		String inputValue = JOptionPane
				.showInputDialog("Please input the size of the board\n"
						+ "(must be between 3 and 9)");

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			boardLength = Integer.parseInt(inputValue);
			if ((boardLength < 10 && boardLength > 2))
				game = new SuperTicTacToeGame(boardLength);
			else
				throw new IllegalArgumentException();
		}

		// Gets input for length of symbols to win, throws
		// IllegalArgumentError if parameters are outside the requested
		// range
		String rowValue = JOptionPane.showInputDialog(
				"Please enter number of symbols needed to win: ");

		if (rowValue == null) {
			System.exit(0);
		} else {
			lengthToWin = Integer.parseInt(rowValue);
			if (lengthToWin <= boardLength && lengthToWin > 1)
				game.setConnections(lengthToWin); // !
			else
				throw new IllegalArgumentException();
		}

		// Calls resetGame() method to clear the gameboard, and ask if x
		// or o goes first
		game.resetGame();

		// Gets input for number of players and sets aiOn appropriately,
		// throws IllegalArgumentError if parameters are outside the
		// requested range
		String playerValue = JOptionPane
				.showInputDialog("1 or 2 players?");
		int numPlayers = 0;

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			numPlayers = Integer.parseInt(playerValue);
			if (numPlayers == 1 || numPlayers == 2) {
				if (numPlayers == 1)
					aiOn = true;
				else
					aiOn = false;
			} else
				throw new IllegalArgumentException();
		}

		// Creates the board
		center.setLayout(new GridLayout(boardLength, boardLength,
				boardLength, 2));
		Dimension temp = new Dimension(100, 100);

		board = new JButton[boardLength][boardLength];

		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board.length; col++) {

				Border thickBorder = new LineBorder(Color.blue, 2);

				board[row][col] = new JButton(" ");
				board[row][col].setBorder(thickBorder);

				board[row][col].addActionListener(listener);
				board[row][col].setPreferredSize(temp);
				center.add(board[row][col]);
			}

		// Calls the displayBoard() method to display the appropriate
		// icons
		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		JLabel labxWins = new JLabel("X Wins: ");
		JLabel laboWins = new JLabel("O Wins: ");
		xWon = new JLabel("0");
		oWon = new JLabel("0");

		// Adds score trackers and function buttons to their JPanel
		bottom.add(labxWins);
		bottom.add(xWon);
		bottom.add(laboWins);
		bottom.add(oWon);
		bottom.add(quitButton);
		bottom.add(undoButton);
		bottom.add(resetButton);

		// Add everything to contentPane
		add(new JLabel("!!!!!!  Super TicTacToe  !!!!!!"),
				BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	/******************************************************************
	 * Method that recreates the TicTacToe game when reset button is
	 * pushed. Asks for new values for board size, length to win, number
	 * of players, and who goes first.
	 * 
	 * @throws IllegalArgumentException
	 *             When parameters for setting the board size, length to
	 *             win, first move, or number of players are out of
	 *             their useful settings
	 *****************************************************************/
	private void remakeGame() {

		// Creates two variables to be used in getting user input below
		int lengthToWin = 0;
		int boardLength = 0;

		// Creates listener
		ButtonListener listener = new ButtonListener();

		// Sets dimensions for buttons
		Dimension temp = new Dimension(100, 100);

		// Gets input for board length
		String inputValue = JOptionPane.showInputDialog(
				"Please input a the size of the board\n(must be between 3 and 9)");

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			boardLength = Integer.parseInt(inputValue);
			if (boardLength < 10 && boardLength > 2)
				game = new SuperTicTacToeGame(boardLength);
			else
				throw new IllegalArgumentException();
		}

		// Gets input for length of symbols to win
		String rowValue = JOptionPane.showInputDialog(
				"Please enter number of rows needed to win: ");

		if (rowValue == null) {
			System.exit(0);
		} else {
			lengthToWin = Integer.parseInt(rowValue);
			if (lengthToWin <= boardLength)
				game.setConnections(lengthToWin);
			else
				throw new IllegalArgumentException();
		}

		// Calls resetGame() method to clear the gameboard, and ask if x
		// or o goes first
		game.resetGame();

		// Gets input for number of players and sets aiOn appropriately,
		// throws IllegalArgumentError
		// if parameters are outside the requested range
		String playerValue = JOptionPane
				.showInputDialog("1 or 2 players?");

		int numPlayers = 0;

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			numPlayers = Integer.parseInt(playerValue);
			if (numPlayers < 3 && numPlayers > 0) {
				if (numPlayers == 1)
					aiOn = true;
				else
					aiOn = false;
			} else
				throw new IllegalArgumentException();
		}

		// Creates a new array of JButtons based on boardLength
		board = new JButton[boardLength][boardLength];

		// Sets to layout approriately based on boardLength
		center.setLayout(new GridLayout(boardLength, boardLength,
				boardLength, 2));

		// Removes all components from the game board
		center.removeAll();

		// Recreates all components based on user input above
		for (int row = 0; row < board.length; row++)
			for (int col = 0; col < board.length; col++) {
				{
					Border thickBorder = new LineBorder(Color.blue, 2);

					board[row][col] = new JButton(" ");
					board[row][col].setBorder(thickBorder);

					board[row][col].addActionListener(listener);
					board[row][col].setPreferredSize(temp);

					center.add(board[row][col]);
				}
			}

		// Resets win tracking to 0
		xWon.setText("0");
		oWon.setText("0");
	}

	/******************************************************************
	 * Method that displays the TicTacToe board. Gets the status of all
	 * cells from the board object, sets them to a new iBoard variable,
	 * and sets the button icons approriately
	 * 
	 * @throws IllegalArgumentException
	 *             When parameters for setting the board size, length to
	 *             win, first move, or number of players are out of
	 *             their useful settings
	 *****************************************************************/
	private void displayBoard() {
		iBoard = game.getBoard();

		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board.length; c++) {

				board[r][c].setText("");
				if (iBoard[r][c] == CellStatus.O)
					board[r][c].setIcon(oIcon);

				else if (iBoard[r][c] == CellStatus.X)
					board[r][c].setIcon(xIcon);

				else if (iBoard[r][c] == CellStatus.EMPTY)
					board[r][c].setIcon(emptyIcon);
			}
	}

	/******************************************************************
	 * ButtonListener method that implements all button actions. See
	 * below for specifics.
	 *****************************************************************/
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// Quits game if quit button is pressed
			if (quitButton == e.getSource())
				System.exit(0);

			// Resets game if reset button is pressed
			if (resetButton == e.getSource())
				remakeGame();

			// When undo button is pressed, undoes one move and swaps
			// turns back to the player who's move was just undoed
			if (undoButton == e.getSource())
				game.undoTurnSwap();

			// Handles button selection. When a valid button is pressed,
			// selects that button for the current turn, and swaps
			// turns. If ai is enabled, makes the ai move and swaps
			// turns.
			for (int r = 0; r < board.length; r++) {
				for (int c = 0; c < board.length; c++) {
					if (board[r][c] == e.getSource()
							&& game.getOK(r, c)) {
						game.select(r, c);
						game.nextTurn();
						if (aiOn) {
							if (game.getGameStatus() == GameStatus.IN_PROGRESS) {
								game.aiMove();
								game.nextTurn();
							}
						}
					}
				}
			}

			// Displays the board again with any changes
			displayBoard();

			// Handles O winning
			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null,
						"O won and X lost!\n The game will reset");
				game.resetBoard();
				displayBoard();
				oWon.setText(
						"" + (Integer.parseInt(oWon.getText()) + 1));
			}

			// Handles X winning
			if (game.getGameStatus() == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null,
						"X won and O lost!\n The game will reset");
				game.resetBoard();
				displayBoard();
				xWon.setText(
						"" + (Integer.parseInt(xWon.getText()) + 1));
				displayBoard();
			}

			// Handles a cats game
			if (game.getGameStatus() == GameStatus.CATS) {
				JOptionPane.showMessageDialog(null,
						"Cats game!\nThe game will reset");
				game.resetBoard();
				displayBoard();
			}
		}
	}
}
