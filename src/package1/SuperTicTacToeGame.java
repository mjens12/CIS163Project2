package package1;

public class SuperTicTacToeGame {
	private CellStatus[][] board;
	private GameStatus status;
	private int size;
	private CellStatus turn;
	private int connections;

	public SuperTicTacToeGame(int size) {
		status = GameStatus.IN_PROGRESS;
		this.size = size;
		this.connections = 3;
		board = new CellStatus[size][size];
		turn = CellStatus.X;
		reset();
	}

	public void setConnections(int connections) {
		this.connections = connections;
	}

	public CellStatus[][] getBoard() {
		return board;
	}

	public void select(int row, int col) {
		if (board[row][col] != CellStatus.EMPTY)
			return;

		board[row][col] = turn;

		if (turn == CellStatus.O)
			turn = CellStatus.X;
		else
			turn = CellStatus.O;
		status = isWinner();
	}

	private GameStatus isWinner() {
		int tempConnections = 0;

		// Working on larger board sizes here
		// Checks X horizontal
		for (int r = 0; r < size; r++) {
			if (board[r][0] == CellStatus.X
					&& board[r][size - 1] == CellStatus.X)
				tempConnections++;
			for (int c = 1; c < size; c++) {
				if (board[r][c - 1] == CellStatus.X
						&& board[r][c] == CellStatus.X) {
					tempConnections++;
					if (tempConnections == connections)
						return GameStatus.X_WON;
				}
			}
			tempConnections = 0;
		}

		// Checks O horizontal
		for (int r = 0; r < size; r++) {
			if (board[r][0] == CellStatus.O
					&& board[r][size - 1] == CellStatus.O)
				tempConnections++;
			for (int c = 1; c < size; c++) {
				if (board[r][c - 1] == CellStatus.O
						&& board[r][c] == CellStatus.O) {
					tempConnections++;
					if (tempConnections == connections)
						return GameStatus.O_WON;
				}
			}
			tempConnections = 0;
		}

		// Checks X vertical
		for (int c = 0; c < size; c++) {
			if (board[0][c] == CellStatus.X
					&& board[size - 1][c] == CellStatus.X)
				tempConnections++;
			for (int r = 1; r < size; r++) {
				if (board[r - 1][c] == CellStatus.X
						&& board[r][c] == CellStatus.X) {
					tempConnections++;
					if (tempConnections == connections)
						return GameStatus.X_WON;
				}
			}
			tempConnections = 0;
		}

		// Checks O vertical
		for (int c = 0; c < size; c++) {
			if (board[0][c] == CellStatus.O
					&& board[size - 1][c] == CellStatus.O)
				tempConnections++;
			for (int r = 1; r < size; r++) {
				if (board[r - 1][c] == CellStatus.O
						&& board[r][c] == CellStatus.O) {
					tempConnections++;
					if (tempConnections == connections)
						return GameStatus.O_WON;
				}
			}
			tempConnections = 0;
		}

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (board[r][c] == CellStatus.EMPTY)
					return GameStatus.IN_PROGRESS;
			}
		}
		return GameStatus.CATS;
	}

	/*
	 * TO DO 5, if you have the time...
	 *
	 * Make an Undo feature.
	 */

	public boolean undo() {
		return false;
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				board[r][c] = CellStatus.EMPTY;
	}

	public boolean getOK(int r, int c) {
		return board[r][c] == CellStatus.EMPTY;
	}
}
