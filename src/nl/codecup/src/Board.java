package nl.codecup.src;

import java.util.ArrayList;
import java.util.List;

public class Board {
	public static final int SIZE = 11;
	public static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	private static final int NEIGHBOR_DISTANCE = 1;
	private static final int ON_EDGES = 1;

	/**
	 * The actual board
	 */
	private int[][] boardGrid = new int[][] {
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE } };

	/**
	 * Constructor
	 */
	public Board() {
	}

	/**
	 * Constructor
	 * 
	 * @param grid
	 */
	public Board(int[][] grid) {
		this.boardGrid = cloneBoard(grid);
	}

	/**
	 * Checks if a coordinate with the given piece is part of a group.
	 * 
	 * @param x
	 *            : The X coordinate.
	 * @param y
	 *            : The Y coordinate
	 * @return True if it's a group, else returns false.
	 */
	public boolean hasNeighbor(int x, int y) {
		return ((y + NEIGHBOR_DISTANCE < SIZE && this.boardGrid[x][y
				+ NEIGHBOR_DISTANCE] == Player.piece)
				|| (x + NEIGHBOR_DISTANCE < SIZE && this.boardGrid[x
						+ NEIGHBOR_DISTANCE][y] == Player.piece)
				|| (y - NEIGHBOR_DISTANCE >= 0 && this.boardGrid[x][y
						- NEIGHBOR_DISTANCE] == Player.piece) || (x
				- NEIGHBOR_DISTANCE >= 0 && this.boardGrid[x
				- NEIGHBOR_DISTANCE][y] == Player.piece));
	}

	/**
	 * Check if an item is on the edge of a group
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean onEdgesOfGroup(int x, int y) {
		int neighBours = 0;
		if (y + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x][y + NEIGHBOR_DISTANCE] == Player.piece) {
			neighBours++;
		}

		if (x + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x + NEIGHBOR_DISTANCE][y] == Player.piece) {
			neighBours++;
		}

		if (y - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x][y - NEIGHBOR_DISTANCE] == Player.piece) {
			neighBours++;
		}

		if (x - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x - NEIGHBOR_DISTANCE][y] == Player.piece) {
			neighBours++;
		}

		return neighBours == ON_EDGES;
	}

	/**
	 * Check if the move is a neighbor
	 * 
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public boolean isMoveNeighbourOfSameGroup(int originX, int originY,
			int targetX, int targetY) {
		return findPath(originX, originY, originX, originY, targetX, targetY);
	}

	/**
	 * Find a path between two coordinates
	 * 
	 * @param originX
	 * @param originY
	 * @param nextX
	 * @param nextY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	private boolean findPath(int originX, int originY, int nextX, int nextY,
			int targetX, int targetY) {
		boolean found = false;

		if (!(originX == nextX && originY == nextY)) {
			found = isNeighbour(nextX, nextY, targetX, targetY);
		}

		if (!found
				&& nextY + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[nextX][nextY + NEIGHBOR_DISTANCE] == Player.piece
				&& ((nextY + NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX, nextY + NEIGHBOR_DISTANCE,
					targetX, targetY);
		}

		if (!found
				&& nextX + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[nextX + NEIGHBOR_DISTANCE][nextY] == Player.piece
				&& ((nextX + NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX + NEIGHBOR_DISTANCE, nextY,
					targetX, targetY);
		}

		if (!found
				&& nextY - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[nextX][nextY - NEIGHBOR_DISTANCE] == Player.piece
				&& ((nextY - NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX, nextY - NEIGHBOR_DISTANCE,
					targetX, targetY);
		}

		if (!found
				&& nextX - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[nextX - NEIGHBOR_DISTANCE][nextY] == Player.piece
				&& ((nextX - NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX - NEIGHBOR_DISTANCE, nextY,
					targetX, targetY);
		}

		return found;
	}

	/**
	 * Get the neighbors of the given locations of the player piece
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public String[] getNeighbors(int x, int y) {
		return this.getNeighborsByPiece(x, y, Player.piece);
	}

	/**
	 * Get the neighbors of the given locations by the given piece
	 * 
	 * @param x
	 * @param y
	 * @param piece
	 * @return
	 */
	public String[] getNeighborsByPiece(int x, int y, int piece) {
		List<String> neighbors = new ArrayList<String>();
		if (y + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x][y + NEIGHBOR_DISTANCE] == piece) {
			neighbors.add(x + "," + (y + NEIGHBOR_DISTANCE));
		}

		if (x + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x + NEIGHBOR_DISTANCE][y] == piece) {
			neighbors.add((x + NEIGHBOR_DISTANCE) + "," + y);
		}

		if (y - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x][y - NEIGHBOR_DISTANCE] == piece) {
			neighbors.add(x + "," + (y - NEIGHBOR_DISTANCE));
		}

		if (x - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x - NEIGHBOR_DISTANCE][y] == piece) {
			neighbors.add((x - NEIGHBOR_DISTANCE) + "," + y);
		}

		return neighbors.toArray(new String[neighbors.size()]);
	}

	/**
	 * Check if the given pieces are neighbors
	 * 
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public boolean isNeighbour(int originX, int originY, int targetX,
			int targetY) {
		return ((originX == targetX && ((originY - NEIGHBOR_DISTANCE) == targetY))
				|| (originX == targetX && ((originY + NEIGHBOR_DISTANCE) == targetY))
				|| (originY == targetY && ((originX - NEIGHBOR_DISTANCE) == targetX)) || (originY == targetY && ((originX + NEIGHBOR_DISTANCE) == targetX)));
	}

	/**
	 * Check if the given position is empty
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public boolean isBlankSpace(int row, int column) {
		return (this.boardGrid[row][column] == NONE);
	}

	/**
	 * Place a piece, and move these
	 * 
	 * @param move
	 * @return
	 */
	public Board placePiece(Move move) {
		this.movePiece(move);
		return this;
	}

	/**
	 * Move an certain piece
	 * 
	 * @param move
	 */
	public void movePiece(Move move) {
		switchPosition(move.getIndexOriginX(), move.getIndexOriginY(),
				move.getIndexTargetX(), move.getIndexTargetY());
	}

	/**
	 * Switch the positions
	 * 
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	private void switchPosition(int originX, int originY, int targetX,
			int targetY) {
		int tempPiece = this.boardGrid[originX][originY];
		this.boardGrid[originX][originY] = this.boardGrid[targetX][targetY];
		this.boardGrid[targetX][targetY] = tempPiece;
		//IO.debug(this.toString());
	}

	/**
	 * Count the amount of groups
	 * 
	 * @param player
	 * @return
	 */
	public int amountOfGroups(Player player) {
		return 0;
	}

	/**
	 * To string
	 */
	public String toString() {
		return this.toString(false);
	}

	/**
	 * Display board
	 */
	public String toString(boolean normal) {
		String rowSeperator = "    +---+---+---+---+---+---+---+---+---+---+---+\n";
		String returnString = "      A   B   C   D   E   F   G   H   I   J   K\n"
				+ rowSeperator;
		if (!normal) {
			for (int column = (SIZE - 1); column >= 0; column--) {
				returnString += String.format("%02d", (column + 1)) + " ";
				for (int row = 0; row < SIZE; row++)
					returnString += " | "
							+ this.convertPiece(this.boardGrid[row][column]);
				returnString += " |\n" + rowSeperator;
			}
		} else {
			for (int column = 0; column < SIZE; column++) {
				returnString += String.format("%02d", (column + 1)) + " ";
				for (int row = 0; row < SIZE; row++)
					returnString += " | "
							+ this.convertPiece(this.boardGrid[row][column]);
				returnString += " |\n" + rowSeperator;
			}

		}
		return returnString;
	}

	/**
	 * Covert the number to an certain piece
	 * 
	 * @param piece
	 * @return
	 */
	public String convertPiece(int piece) {
		if (piece != NONE) {
			return piece == WHITE ? "X" : "O";
		}

		return " ";
	}

	/**
	 * Clones this board instance.
	 */
	public Board clone() {
		return new Board(this.cloneBoard(this.boardGrid));
	}

	/**
	 * Completely clones the board so no references are made and the board is a
	 * perfect clone of the given one.
	 * 
	 * @param board
	 *            The board to clone
	 * @return A clone board
	 */
	private int[][] cloneBoard(int[][] board) {
		int[][] tempBoard = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				tempBoard[row][column] = board[row][column];
			}
		}

		return tempBoard;
	}

	/**
	 * Displays the debugboard so we can copy the code to our board as base to
	 * begin
	 * 
	 * @return
	 */
	public String toDebugBoard() {
		String[] pieces = { "NONE", "WHITE", "BLACK" };
		String output = "private int[][] boardGrid = new int[][] {";

		for (int row = 0; row < SIZE; row++) {
			output += "{ ";
			for (int column = 0; column < SIZE; column++) {
				int piece = boardGrid[row][column];
				output += " " + pieces[piece] + " ";
				output += (column < (SIZE - 1) ? "," : "");
			}
			output += " }";
			output += (row < (SIZE - 1) ? ", " : "");
		}
		output += " };";
		return output;
	}

	/**
	 * Get the board
	 * 
	 * @return
	 */
	public int[][] getBoardContents() {
		return this.boardGrid;
	}
}
