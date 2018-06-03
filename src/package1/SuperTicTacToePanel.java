package package1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SuperTicTacToePanel extends JPanel {
	private JButton[][] board;
	private CellStatus iCell;
	private JButton quitButton;
	private SuperTicTacToeGame game;

	private ImageIcon xIcon;
	private ImageIcon oIcon;
	private ImageIcon emptyIcon;

	private void SuperTicTacToePanel() {
		quitButton = new JButton("Quit Game");

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		ButtonListener buttonListen = new ButtonListener();

		// TODO need these image files
		xIcon = new ImageIcon("x.jpg");
		oIcon = new ImageIcon("y.jpg");
		emptyIcon = new ImageIcon("empty.jpg");

		GridLayout layout = new GridLayout(3, 3);

		game = new SuperTicTacToeGame();

		panel1.setLayout(layout);

		for (int row = 0; row < SuperTicTacToeGame.BDSIZE; row++)
			for (int col = 0; col < SuperTicTacToeGame.BDSIZE; col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].addActionListener(buttonListen);
				panel1.add(board[row][col]);
			}
	}

	private void displayBoard() {
		for (int row = 0; row < SuperTicTacToeGame.BDSIZE; row++)
			for (int col = 0; col < SuperTicTacToeGame.BDSIZE; col++) {
				iCell = game.getCell(row, col);
				// TODO: ImageIcon icon = icon for iCell
				board[row][col].setIcon(icon);
			}
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int row = 0; row < SuperTicTacToeGame.BDSIZE; row++)
				for (int col = 0; col < SuperTicTacToeGame.BDSIZE; col++)
					if (board[row][col] == e.getSource())
						// tell the game which button was selected.
						game.select(row, col);

			displayBoard();

			if (game.getGameStatus() == GameStatus.O_WON) {
				JOptionPane.showMessageDialog(null,
						"O won and X lost!\n The game will reset");
			}
		}
	}
}
