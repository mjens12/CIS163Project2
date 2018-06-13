package package1;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**********************************************************************
 * The game class that handles all TicTacToe game logic.
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 *********************************************************************/

public class SuperTicTacToeGame {
	/** An array of CellStatuses to represent the board **/
	private CellStatus[][] board;

	/** Gives game status of in progress or if X or O won **/
	private GameStatus status;

	/** Int size of board length and width **/
	private int size;

	/** Shows if it is X's or O's turn in game **/
	private CellStatus turn;

	/** Int size of number of symbols needed to win **/
	private int connections;

	/**
	 * ArrayList to hold all moves in game, to then be able to undo
	 * moves when players want
	 **/
	private ArrayList<Point> undoList;

	/******************************************************************
	 * Default constructor for SuperTicTacToeGame. Constructs the board
	 * based on passed size parameter, sets game status to in progress,
	 * creates a new array of CellStatuses, creates the undoList
	 * ArrayList, and resets the board
	 * 
	 * @param size
	 *            size of board length and width
	 *****************************************************************/
	public SuperTicTacToeGame(int size) {
		status = GameStatus.IN_PROGRESS;
		this.size = size;
		board = new CellStatus[size][size];
		turn = CellStatus.X;
		undoList = new ArrayList<Point>();
		resetBoard();
	}

	/******************************************************************
	 * Setter for connections. Sets the input value of connections to
	 * the variable connections in SuperTicTacToeGame
	 * 
	 * @param connections
	 *****************************************************************/
	public void setConnections(int connections) {
		this.connections = connections;
	}

	/******************************************************************
	 * Getter for board. Returns the array of all cell statuses
	 * 
	 * @return CellStatus
	 *****************************************************************/
	public CellStatus[][] getBoard() {
		return board;
	}

	/******************************************************************
	 * Method that accepts two integers which are the grid location on
	 * game board. If grid box is empty select that location for the
	 * current player
	 * 
	 * Adds grid point to undoList to store when player wants to undo
	 * 
	 * Check status if any player has won.
	 * 
	 * @param row
	 *            number row in board
	 * @param col
	 *            number column in board
	 *****************************************************************/
	public void select(int row, int col) {
		if (board[row][col] != CellStatus.EMPTY) {
			return;
		}

		// Selects the location for the current player
		board[row][col] = turn;

		// Adds player move to undoList
		undoList.add(new Point(col, row));

		// Checks if a player won
		status = isWinner();
	}

	/******************************************************************
	 * Method that starts the next turn. Checks if it is player O's
	 * turn, if not makes it X's turn. Else make it O's turn.
	 *****************************************************************/
	public void nextTurn() {
		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;
	}

	/******************************************************************
	 * Method that checks after each move if a player has won, if no one
	 * has won then return game still in progress.
	 * 
	 * @param r
	 *            grid point row
	 * @param c
	 *            grid point column
	 * @return GameStatus if player won return GameStatus.X_WON or
	 *         GameStatus.O_WON, else return GameStatus.IN_PROGRESS
	 *****************************************************************/
	public GameStatus checkWin(int r, int c) {
		int equalLengthXH = 0;
		int equalLengthOH = 0;
		int equalLengthXV = 0;
		int equalLengthOV = 0;

		// connections is symbols in a row needed to win
		for (int i = 0; i < connections; i++) {

			// check same row and each column for number of X/O's
			if (board[r][(c + i) % size] == CellStatus.X) // X HORZ
				equalLengthXH++;
			if (board[r][(c + i) % size] == CellStatus.O) // O HORZ
				equalLengthOH++;

			// check same column and each row for number of X/O's
			if (board[(r + i) % size][c] == CellStatus.X) // X VERT
				equalLengthXV++;
			if (board[(r + i) % size][c] == CellStatus.O) // O VERT
				equalLengthOV++;

			// check win
			if (equalLengthXH == connections
					|| equalLengthXV == connections)
				return GameStatus.X_WON;
			if (equalLengthOH == connections
					|| equalLengthOV == connections)
				return GameStatus.O_WON;
		}
		// if no win return in progress
		return GameStatus.IN_PROGRESS;
	}

	/******************************************************************
	 * Check if player has won by looping through board and calling
	 * checkWin(r,c) method, if found return checkWin(r,c) else check if
	 * at least one grid spot is empty, if it is not return
	 * GameStatus.CATS
	 * 
	 * @return GameStatus if winner is found return that winner, else
	 *         return in progress, unless board is filled, then return
	 *         GameStatus.CATS
	 *******************************************************************/
	private GameStatus isWinner() {
		// size is length and width of board
		for (int r = 0; r < size; r++) { // row
			for (int c = 0; c < size; c++) {// column
				if (checkWin(r, c) == GameStatus.X_WON
						|| checkWin(r, c) == GameStatus.O_WON)
					return checkWin(r, c);
			}
		}
		// Check that at least one spot is empty, return in progress if
		// found
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c] == CellStatus.EMPTY)
					return GameStatus.IN_PROGRESS;
			}
		}

		// if board is found full from previous for loop
		return GameStatus.CATS;
	}

	/******************************************************************
	 * Method for the AI to undo its selections
	 *****************************************************************/
	public void undo() {
		if (undoList.size() > 0) {
			Point p = undoList.remove(undoList.size() - 1);
			board[p.y][p.x] = CellStatus.EMPTY;
		}
	}

	/******************************************************************
	 * Method that is called when undo button is clicked. Removes the
	 * just placed players move and swaps turns
	 *****************************************************************/
	public void undoTurnSwap() {
		if (undoList.size() > 0) {
			Point p = undoList.remove(undoList.size() - 1);
			board[p.y][p.x] = CellStatus.EMPTY;

			nextTurn();
		}
	}

	/******************************************************************
	 * Getter for status. Returns the status of the game (if someone has
	 * won, if it's in progress, or if it's a cats game).
	 * 
	 * @return status
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * Reset method. Resets the game board and asks for who should play
	 * first.
	 * 
	 * 
	 * @throws IllegalArgumentException
	 *             if input isn't x or o
	 *****************************************************************/
	public void resetGame() {
		resetBoard();

		String question = JOptionPane
				.showInputDialog("Who goes first? Type X or O");
		if (question.equalsIgnoreCase("x")
				|| question.equalsIgnoreCase("o")) {
			if (question.equalsIgnoreCase("x")) {
				turn = CellStatus.X;
			}

			else if (question.equalsIgnoreCase("o")) {
				turn = CellStatus.O;
			}
			status = GameStatus.IN_PROGRESS;
		} else
			throw new IllegalArgumentException();
	}

	/******************************************************************
	 * Method that clears board
	 *****************************************************************/
	public void resetBoard() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				// sets grid point board[r][c] to empty
				board[r][c] = CellStatus.EMPTY;
	}

	/******************************************************************
	 * Boolean method that checks if at grid point board[r][c] is empty
	 * or not
	 * 
	 * @param r
	 *            row in board grid
	 * @param c
	 *            column in board grid
	 * @return boolean true if grid point board[r][c] is empty, return
	 *         false otherwise
	 *****************************************************************/
	public boolean getOK(int r, int c) {
		return (board[r][c] == CellStatus.EMPTY);
	}

	/******************************************************************
	 * Method that handles AI moves. First tries to win if possible. If
	 * not tries to block if possible, if not places a random move
	 *****************************************************************/
	public void aiMove() {

		// Check if potential placement in board[r][c] will result in
		// win
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (getOK(r, c)) {
					select(r, c);
					if (status == GameStatus.X_WON
							|| status == GameStatus.O_WON)
						return;

					// if win was not found in board[r][c] remove move
					// made to check with method undo()
					undo();
				}
			}
		}

		// Check if Human is about to win, if so block human move
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (getOK(r, c)) {
					nextTurn();
					select(r, c);
					if (status == GameStatus.X_WON
							|| status == GameStatus.O_WON) {
						undo();
						nextTurn();
						select(r, c);
						return;
					}
					undo();
					nextTurn();
				}
			}
		}

		// selects random open spot for AI
		Random rand = new Random();
		int r = rand.nextInt(size);
		int c = rand.nextInt(size);

		while (board[r][c] != CellStatus.EMPTY) {
			r = rand.nextInt(size);
			c = rand.nextInt(size);
		}
		select(r, c);
	}
}
