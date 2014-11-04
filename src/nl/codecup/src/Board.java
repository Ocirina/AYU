package nl.codecup.src;

public class Board {
	private static final int SIZE = 11;
	private static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;

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
	public boolean hasNeighbour(int x, int y) {
		if (y+1 < SIZE && this.boardGrid[x][y + 1] == WHITE)
			return true;
		//TODO ELSEIF to improve speed?
		if (x+1 < SIZE && this.boardGrid[x + 1][y] == WHITE)
			return true;
		//TODO ELSEIF to improve speed?
		if (y-1 >= 0 && this.boardGrid[x][y - 1] == WHITE)
			return true;
		//TODO ELSEIF to improve speed?
		if (x-1 >= 0 && this.boardGrid[x - 1][y] == WHITE)
			return true;

		return false;
	}

	public boolean onEdgesOfGroup(int x, int y) {
		int neighBours = 0;
		//TODO ELSEIF to improve speed?
		if (y+1 < SIZE && this.boardGrid[x][y + 1] == WHITE)
			neighBours++;
		//TODO ELSEIF to improve speed?
		if (x+1 < SIZE && this.boardGrid[x + 1][y] == WHITE)
			neighBours++;
		//TODO ELSEIF to improve speed?
		if (y-1 >= 0 && this.boardGrid[x][y - 1] == WHITE)
			neighBours++;
		//TODO ELSEIF to improve speed?
		if (x-1 >= 0 && this.boardGrid[x - 1][y] == WHITE)
			neighBours++;

		return neighBours < 2;
	}

	public boolean isMoveNeighbourOfSameGroup(int originX, int originY,
			int targetX, int targetY) {				
		//TODO move object?
		return findPath(originX, originY, originX, originY, targetX, targetY);
	}

	private boolean findPath(int originX, int originY, int nextX, int nextY,
			int targetX, int targetY) {
		boolean found = false;
		
		found = isNeighbour(nextX, nextY, targetX, targetY);
		
		if (!found && nextY+1 < SIZE && this.boardGrid[nextX][nextY+1] == WHITE && ((nextY + 1) != originY)) 
			found = findPath(nextX, nextY, nextX, nextY+1, targetX, targetY);
		
		//TODO ELSEIF to improve speed?
		if (!found && nextX+1 < SIZE && this.boardGrid[nextX + 1][nextY] == WHITE && ((nextX + 1) != originY))
			found = findPath(nextX, nextY, nextX+1, nextY, targetX, targetY);
		//TODO same..
		if (!found && nextY-1 >= 0 && this.boardGrid[nextX][nextY-1] == WHITE && ((nextY - 1) != originY)) 
			found = findPath(nextX, nextY, nextX, nextY-1, targetX, targetY);
		//TODO same..
		if (!found && nextX-1 >= 0 && this.boardGrid[nextX - 1][nextY] == WHITE && ((nextX - 1) != originY))
			found = findPath(nextX, nextY, nextX-1, nextY, targetX, targetY);
		
		return found;
	}
	
	private boolean isNeighbour(int originX, int originY, int targetX, int targetY) {
		if(originX == targetX && ((originY - 1) == targetY))
			return true;
		//TODO ELSEIF?
		if(originX == targetX && ((originY + 1) == targetY))
			return true;
		//TODO ELSEIF?
		if(originY == targetY && ((originX - 1) == targetX))
			return true;
		//TODO ELSEIF?
		if(originY == targetY && ((originX - 1) == targetX))
			return true;
			
		return false;
	}

	/**
	 * Constructor
	 */
	public Board() {

	}

	public boolean isBlankSpace(int row, int column) {
		return (this.boardGrid[row][column] == NONE);
	}

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
		int originX = move.getOriginXConverted();
		int originY = move.getOriginYConverted();		
		int targetX = move.getTargetXConverted();
		int targetY = move.getTargetYConverted();

		switchPosition(originX, originY, targetX, targetY);
	}
	
	/**
	 * Switch the positions
	 * 
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	private void switchPosition(int originX, int originY, int targetX, int targetY) {
		int tempPiece = this.boardGrid[originX][originY];
		this.boardGrid[originX][originY] = this.boardGrid[targetX][targetY];
		this.boardGrid[targetX][targetY] = tempPiece;
		IO.debug(this.toString());
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
	 * Display board
	 */
	public String toString() {
		String rowSeperator = "    +---+---+---+---+---+---+---+---+---+---+---+\n";
		String returnString = "      A   B   C   D   E   F   G   H   I   J   K\n"
				+ rowSeperator;
		for (int column = (SIZE - 1); column >= 0; column--) {
			returnString += String.format("%02d", (column + 1)) + " ";
			for (int row = 0; row < SIZE; row++)
				returnString += " | "
						+ this.convertPiece(this.boardGrid[row][column]);
			returnString += " |\n" + rowSeperator;
		}
		
		return returnString;
	}

	/**
	 * Covert the number to an certain piece
	 * 
	 * @param piece
	 * @return
	 */
	private String convertPiece(int piece) {
		if (piece != NONE) {
			return piece == WHITE ? "W" : "B";
		}
		
		return " ";
	}

	/**
	 * Clones this board instance.
	 */
	public Board clone() {
		return new Board(this.boardGrid);
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

	public Board placePiece(int piece, int row, int column) {
		//TODO Switch the pieces. Should receive Move object?
		return this;
	}
	
	public int[][] getBoardContents() {
		return this.boardGrid;
	}
}
