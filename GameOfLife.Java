import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridPanelDemo extends JFrame implements ActionListener, MouseListener {
	// Private fields
	public final int WIDTH = 500;
	public final int HEIGHT = 500;
	private final int numPanels = 4;
	private int column;
	private int row;
	private JPanel[][] colorPanel;

	private Timer timer;
	private boolean timerOn;

	// GridPanelDemo constructor that takes arguments
	public GridPanelDemo(int row, int column) {
		// Calling the Super
		super("Grid Panel Demonstration");

		// Setting the rows and column
		this.column = column;
		this.row = row;

		// Setting the size of the Panel
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setting the Border layout
		setLayout(new BorderLayout());

		// Creating the centerPanel and setting the gridLayout
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(row, column));

		// Setting the color of each cells of the grid
		colorPanel = new JPanel[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				colorPanel[i][j] = new JPanel();
				colorPanel[i][j].setBackground(Color.WHITE);
				colorPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				colorPanel[i][j].addMouseListener(this);
				centerPanel.add(colorPanel[i][j]);
			}

		}

		// Adding the center panel to the JFrame
		add(centerPanel, BorderLayout.CENTER);

		// Creating the button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout());

		// Creating the Step button
		JButton stepButton = new JButton("Step");
		stepButton.addActionListener(this);
		buttonPanel.add(stepButton);

		// Creating the Random button
		JButton randomButton = new JButton("Random");
		randomButton.addActionListener(this);
		buttonPanel.add(randomButton);

		// Creating the Run button
		JButton runButton = new JButton("Run");
		runButton.addActionListener(this);
		buttonPanel.add(runButton);

		// Creating the Stop button
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(this);
		buttonPanel.add(stopButton);

		// Creating the Clear button
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		buttonPanel.add(clearButton);

		// Adding the button panel to the south of the JFrame
		add(buttonPanel, BorderLayout.SOUTH);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.println("Mouse Clicked");

		if (me.getComponent().getBackground().equals(Color.WHITE)) {
			me.getComponent().setBackground(Color.RED);
		} else {
			me.getComponent().setBackground(Color.WHITE);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	// Button property and implementation
	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonString = e.getActionCommand();
		switch (buttonString) {

		// Step counts the numbers of alive cells
		case "Step":
			System.out.println("--Step Clicked");
			int count = 0;
			for (int i = 0; i < colorPanel.length; i++) {
				for (int j = 0; j < colorPanel.length; j++) {
					if (colorPanel[i][j].getBackground().equals(Color.RED)) {
						count++;

					}
					;
				}
			}
			System.out.println("Alive cell: " + count);
			break;

		// Random creates random live cells
		case "Random":
			System.out.println("--Random Clicked");
			clearGrid();
			int min = 1;
			int max = row * column;
			Random r = new Random();
			int numberofItems = r.nextInt(max + 1) + min;
			for (int i = 0; i < numberofItems; i++) {
				colorPanel[r.nextInt(row)][r.nextInt(column)].setBackground(Color.RED);

			}
			break;

		// Run button starts and run the game
		case "Run":
			System.out.println("--Run Clicked");

			if (timerOn == false) {
				timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						playGame();
					}
				}, 0, 1000);

				timerOn = true;
			}

			break;

		// Stop button stops the game
		case "Stop":
			System.out.println("Stop Clicked");

			if (timerOn == true) {
				timer.cancel();
				timerOn = false;
			}
			break;

		// Clear button remove all life in the cells
		case "Clear":
			System.out.println("Clear Clicked");
			clearGrid();
			break;

		default:
			System.out.println("Unexpected error");
			break;
		}

	}

	// Method the check the alive neighbor
	public int isAlive(int i, int j) {
		if (i < 0 || i > row) {
			return 0;
		}

		if (j < 0 || j > column) {
			return 0;
		}

		if (colorPanel[i][j].getBackground().equals(Color.RED)) {
			return 1;
		}
		;
		return 0;
	}

	// Method to count living neighbors
	public int countLivingNeighbors() {
		int count = 0;
		for (int i = 0; i < numPanels; i++) {
			for (int j = 0; j < numPanels; j++) {
				count += isAlive(i, j);
			}
		}
		System.out.println("Living neighbors: " + count);
		return count;
	}

	// Run method
	public void run() {
		System.out.println("timer working");
	}

	// Method to clear the game
	public void clearGrid() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				colorPanel[i][j].setBackground(Color.WHITE);
			}
		}
	}

	// Method to start the game and game rules
	public void playGame() {
		boolean secondBoard[][] = new boolean[row][column];

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				int numberofNeighbors = getNeighbors(i, j);
				if (colorPanel[i][j].getBackground().equals(Color.WHITE) && numberofNeighbors == 3) {
					secondBoard[i][j] = true;
				}

				if (colorPanel[i][j].getBackground().equals(Color.RED)
						&& (numberofNeighbors == 3 || numberofNeighbors == 2)) {
					secondBoard[i][j] = true;
				}
			}

		}

		// Calling the copyBoard after figuring out the alive cells
		copyBoard(colorPanel, secondBoard);

	}

	// Property to get numbers of Neighbors
	public int getNeighbors(int currRow, int currColumn) {
		int count = 0;
		int maxRow = colorPanel.length - 1;
		int maxColumn = colorPanel[0].length - 1;

		if (currRow > 0 && colorPanel[currRow - 1][currColumn].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currRow > 0 && currColumn < maxColumn && colorPanel[currRow - 1][currColumn + 1].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currColumn < maxColumn && colorPanel[currRow][currColumn + 1].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currRow < maxRow && currColumn < maxColumn && colorPanel[currRow + 1][currColumn + 1].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currRow < maxRow && colorPanel[currRow + 1][currColumn].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currRow < maxRow && currColumn > 0 && colorPanel[currRow + 1][currColumn - 1].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currColumn > 0 && colorPanel[currRow][currColumn - 1].getBackground().equals(Color.RED)) {
			count++;
		}

		if (currColumn > 0 && currRow > 0 && colorPanel[currRow - 1][currColumn - 1].getBackground().equals(Color.RED)) {
			count++;
		}
		return count;
	}

	// Method to copy the old grid to the new Grid
	public void copyBoard(JPanel oldGrid[][], boolean newGrid[][]) {
		for (int i = 0; i < oldGrid.length; i++) {
			for (int j = 0; j < oldGrid[0].length; j++) {
				if (newGrid[i][j]) {
					oldGrid[i][j].setBackground(Color.RED);
				} else {
					oldGrid[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}

	// Main
	public static void main(String[] args) {
		// Asking the size from the user
		System.out.println("Enter the size of the board");
		Scanner s = new Scanner(System.in);
		// -------------------------------

		// Setting the size of the Grid
		int column = s.nextInt();
		int row = column;
		// -------------------------------

		// Testing the GridPanelDemo constructor
		GridPanelDemo gui = new GridPanelDemo(row, column);
		// -------------------------------

		// Make the gridPanelDemo visible
		gui.setVisible(true);
		// -------------------------------
	}
}
