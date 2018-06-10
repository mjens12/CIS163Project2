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

public class SuperTicTacToePanel extends JPanel {

	private JButton[][] board;
	private CellStatus[][] iBoard;
	private JLabel xWon;
	private JLabel oWon;
	private JButton quitButton;
	private JButton undoButton;
	private JButton resetButton;

	private JMenuItem gameItem;
	private JMenuItem quitItem;

	private JPanel bottom;
	private JPanel center;

	private SuperTicTacToeGame game;

	private ImageIcon emptyIcon;
	private ImageIcon xIcon;
	private ImageIcon oIcon;

	public SuperTicTacToePanel(JMenuItem pquitItem, JMenuItem pgameItem) {

		gameItem = pgameItem;
		quitItem = pquitItem;

		bottom = new JPanel();
		center = new JPanel();

		xIcon = new ImageIcon("src/package1/X.png");
		oIcon = new ImageIcon("src/package1/O.png");
		emptyIcon = new ImageIcon("src/package1/empty.png");

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		game = new SuperTicTacToeGame(3);

		// create Undo and quit buttons.
		quitButton = new JButton("Quit");
		quitButton.addActionListener(listener);
		undoButton = new JButton("Undo");
		undoButton.addActionListener(listener);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(listener);

		gameItem = new JMenuItem("Quit");
		quitItem = new JMenuItem("New Game");

		int boardLength = 0;
		int lengthToWin = 0;

		// Get input for board length

		String inputValue = JOptionPane.showInputDialog("Please input a value between 3 and 9: ");

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			boardLength = Integer.parseInt(inputValue);
			if ((boardLength < 10 && boardLength > 2))
				game = new SuperTicTacToeGame(boardLength);
			else
				throw new IllegalArgumentException();
		}

		// Get input for length of symbols to win
		String rowValue = JOptionPane.showInputDialog("Please enter number of rows needed to win: ");

		if (rowValue == null) {
			System.exit(0);
		} else {
			lengthToWin = Integer.parseInt(rowValue);
			if (lengthToWin <= boardLength)
				game.setConnections(lengthToWin); // !
			else
				throw new IllegalArgumentException();
		}

		// create the board
		center.setLayout(new GridLayout(boardLength, boardLength, boardLength, 2));
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

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		JLabel labxWins = new JLabel("X Wins: ");
		JLabel laboWins = new JLabel("O Wins: ");
		xWon = new JLabel("0");
		oWon = new JLabel("0");

		bottom.add(labxWins);
		bottom.add(xWon);
		bottom.add(laboWins);
		bottom.add(oWon);
		bottom.add(quitButton);
		bottom.add(undoButton);
		bottom.add(resetButton);

		// add all to contentPane

		add(new JLabel("!!!!!!  Super TicTacToe  !!!!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}

	private void remakeGame() {
		int lengthToWin = 0;
		int boardLength = 0;

		ButtonListener listener = new ButtonListener();

		Dimension temp = new Dimension(100, 100);
		// Get input for board length

		String inputValue = JOptionPane.showInputDialog("Please input a value between 3 and 9: ");

		if (inputValue == (null)) {
			System.exit(0);
		} else {
			boardLength = Integer.parseInt(inputValue);
			if (boardLength < 10 && boardLength > 2)
				game = new SuperTicTacToeGame(boardLength);
			else
				throw new IllegalArgumentException();
		}

		// Get input for length of symbols to win
		String rowValue = JOptionPane.showInputDialog("Please enter number of rows needed to win: ");

		if (rowValue == null) {
			System.exit(0);
		} else {
			lengthToWin = Integer.parseInt(rowValue);
			if (lengthToWin <= boardLength)
				game.setConnections(lengthToWin); // !
			else
				throw new IllegalArgumentException();
		}

		game.resetGame();

		board = new JButton[boardLength][boardLength];

		center.setLayout(new GridLayout(boardLength, boardLength, boardLength, 2));

		center.removeAll();
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
		xWon.setText("0");
		oWon.setText("0");
	}

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

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (quitButton == e.getSource())
				System.exit(0);

			if (quitItem == e.getSource())
				System.exit(0);

			if (resetButton == e.getSource()) {
				remakeGame();
			}

			for (int r = 0; r < board.length; r++)
				for (int c = 0; c < board.length; c++)
					if (board[r][c] == e.getSource() && game.getOK(r, c))
						game.select(r, c);

			displayBoard();

			// O wins
			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null, "O won and X lost!\n The game will reset");
				game.resetBoard();
				displayBoard();
				oWon.setText("" + (Integer.parseInt(oWon.getText()) + 1));
			}
			// X wins
			if (game.getGameStatus() == GameStatus.X_WON) {
				JOptionPane.showMessageDialog(null, "X won and O lost!\n The game will reset");
				game.resetBoard();
				displayBoard();
				xWon.setText("" + (Integer.parseInt(xWon.getText()) + 1));
				displayBoard();
			}
			if (game.getGameStatus() == GameStatus.CATS) {
				JOptionPane.showMessageDialog(null, "Cats game!\nThe game will reset");
				game.resetBoard();
				displayBoard();
			}
		}
	}
}
