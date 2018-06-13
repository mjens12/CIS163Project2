package package1;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**********************************************************************
 * Class that runs and displays the TicTacToe game
 * 
 * @author Max Jensen and Monica Klosin
 * @version 1.0
 **********************************************************************/
public class SuperTicTacToe {

	/******************************************************************
	 * Main class that runs and displays the TicTacToe game
	 * 
	 * @throws IllegalArgumentException
	 *             When parameters for setting the board size, length to
	 *             win, first move, or number of players are out of
	 *             their useful settings
	 *****************************************************************/
	public static void main(String[] args) {

		// Creates menu bar and menu items
		JMenuBar menus;
		JMenu fileMenu;
		JMenuItem quitItem;
		JMenuItem gameItem;

		// Creates the panel
		SuperTicTacToePanel panel = null;

		JFrame frame = new JFrame("Super Tic Tac Toe!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Instantiates Menu items
		fileMenu = new JMenu("File");
		quitItem = new JMenuItem("quit");
		gameItem = new JMenuItem("new game");

		fileMenu.add(gameItem);
		fileMenu.add(quitItem);
		menus = new JMenuBar();
		frame.setJMenuBar(menus);
		menus.add(fileMenu);

		// Catches IllegalArgumentExceptions, displays a textbox, and
		// reprompts for input
		while (panel == null) {
			try {
				panel = new SuperTicTacToePanel(quitItem, gameItem);
				frame.getContentPane().add(panel);

				frame.setSize(1800, 1300);
				frame.setVisible(true);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"Please enter an appropriate parameter");
			}
		}

	}
}
