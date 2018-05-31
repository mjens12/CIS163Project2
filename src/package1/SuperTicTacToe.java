package package1;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SuperTicTacToe {

	public static void main(String[] args) {

		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem gameItem;

		JFrame frame = new JFrame("Super Tic Tac Toe!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("quit");
		gameItem = new JMenuItem("new game");

		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		SuperTicTacToePanel panel = new SuperTicTacToePanel(quitItem,
				gameItem);
		frame.getContentPane().add(panel);

		frame.setSize(1800, 1300);
		frame.setVisible(true);

	}
}
