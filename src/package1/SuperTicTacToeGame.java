package package1;

public class SuperTicTacToeGame {
	public static final int BDSIZE = 3;
	private CellStatus[][] board;
	private GameStatus status;

	public SuperTicTacToeGame() {
		status = GameStatus.IN_PROGRESS;
		board = new CellStatus[BDSIZE][BDSIZE];

		for (int row = 0; row < BDSIZE; row++)
			for (int col = 0; col < BDSIZE; col++)
				board[row][col] = CellStatus.EMPTY;
	}

	public void select(int row, int col) {

	}

	public void reset() {

	}

	public GameStatus getGameStatus() {

	}

	public CellStatus getCell(int row, int col) {
		return board[row][col];
	}
}
