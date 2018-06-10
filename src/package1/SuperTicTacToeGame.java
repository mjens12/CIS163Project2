package package1;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SuperTicTacToeGame {
	private CellStatus[][] board;
	private GameStatus status;
	private int size;
	private CellStatus turn;
	private int connections;
	private ArrayList<Point> undoList;

	public SuperTicTacToeGame(int size) {
		status = GameStatus.IN_PROGRESS;
		this.size = size;
		board = new CellStatus[size][size];
		turn = CellStatus.X;
		undoList = new ArrayList<Point>();
		resetBoard();
	}

	public void setConnections(int connections) {
		this.connections = connections;
	}

	public CellStatus[][] getBoard() {
		return board;
	}

	public void select(int row, int col) {

		if (board[row][col] != CellStatus.EMPTY) {
			return;
		}

		board[row][col] = turn;

		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;

		undoList.add(new Point(col, row));

		status = isWinner();
	}

	public GameStatus checkWin(int r, int c) {
		int equalLengthX = 0;
		int equalLengthO = 0;
		int equalLengthXV = 0;
		int equalLengthOV = 0;

		for (int i = 0; i < connections; i++) {
			if (board[r][(c + i) % size] == CellStatus.X) {// X HORZ
				equalLengthX++;
			}
			if (board[r][(c + i) % size] == CellStatus.O) {// O HORZ
				equalLengthO++;
			}
			if (board[(r + i) % size][c] == CellStatus.X) {// X VERT
				equalLengthXV++;
			}
			if (board[(r + i) % size][c] == CellStatus.O) {// O VERT
				equalLengthOV++;
			}

			if (equalLengthX == connections || equalLengthXV == connections)
				return GameStatus.X_WON;
			if (equalLengthO == connections || equalLengthOV == connections)
				return GameStatus.O_WON;
		}
		return GameStatus.IN_PROGRESS;
	}

	// Check status if X or O wins
	private GameStatus isWinner() {
		for (int r = 0; r < size; r++) { // row
			for (int c = 0; c < size; c++) {// column
				if (checkWin(r, c) == GameStatus.X_WON || checkWin(r, c) == GameStatus.O_WON)
					return checkWin(r, c);
			}
		}
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c] == CellStatus.EMPTY)
					return GameStatus.IN_PROGRESS;
			}
		}
		return GameStatus.CATS;
	}

	public void undo() {
		Point p = undoList.remove(undoList.size() - 1);
		board[p.y][p.x] = CellStatus.EMPTY;

		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void resetGame() {
		resetBoard();
		
		String question = JOptionPane.showInputDialog("Who goes first? Type X or O");
		if(question.equals("x") || question.equals("X")) {
			turn = CellStatus.X;
		}
		
		else if(question.equals("o")|| question.equals("O")) {
			turn = CellStatus.O;
		}
		
		status = GameStatus.IN_PROGRESS;
	}

	public void resetBoard() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				board[r][c] = CellStatus.EMPTY;
	}

	public boolean getOK(int r, int c) {
		return board[r][c] == CellStatus.EMPTY;
	}
}