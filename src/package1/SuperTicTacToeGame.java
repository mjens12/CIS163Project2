package package1;

public class SuperTicTacToeGame {
	private CellStatus[][] board;
	private GameStatus status;
	private int size;
	private CellStatus turn;
	private int connections; // used in to do 4.

	public SuperTicTacToeGame(int size) {
		status = GameStatus.IN_PROGRESS;
		this.size = size;
		this.connections = 3;
		board = new CellStatus[size][size];
		turn = CellStatus.X;
		reset();
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

		for (int r = 0; r < 3; r++) {
			if (board[r][0] == CellStatus.X
					&& board[r][1] == CellStatus.X
					&& board[r][2] == CellStatus.X) {
				return GameStatus.X_WON;
			}
		}

		for (int r = 0; r < 3; r++) {
			if (board[r][0] == CellStatus.O
					&& board[r][1] == CellStatus.O
					&& board[r][2] == CellStatus.O) {
				return GameStatus.O_WON;
			}
		}

		for (int c = 0; c < 3; c++) {
			if (board[0][c] == CellStatus.O
					&& board[1][c] == CellStatus.O
					&& board[2][c] == CellStatus.O) {
				return GameStatus.O_WON;
			}
		}

		for (int c = 0; c < 3; c++) {
			if (board[0][c] == CellStatus.X
					&& board[1][c] == CellStatus.X
					&& board[2][c] == CellStatus.X) {
				return GameStatus.X_WON;
			}
		}

		/*
		 * TODO 4: Change all code to allow any size board (i.e., not
		 * just 3) Change the code from TODO 3 to allow a horizontal win
		 * of any length, defined by 'this.connections' (see above)
		 * (Hint: you will also need to think about how this.connections
		 * should be set)
		 */

		return GameStatus.IN_PROGRESS;

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
