package nl.codecup.src;

public class Board {
	private MoveConverter moveConverter = new MoveConverter();
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
		if (x+1 < SIZE && this.boardGrid[x + 1][y] == WHITE)
			return true;
		if (y-1 >= 0 && this.boardGrid[x][y - 1] == WHITE)
			return true;
		if (x-1 >= 0 && this.boardGrid[x - 1][y] == WHITE)
			return true;

		return false;
	}

	public boolean onEdges(int x, int y) {
		int neighBours = 0;

		if (y+1 < SIZE && this.boardGrid[x][y + 1] == WHITE)
			neighBours++;
		if (x+1 < SIZE && this.boardGrid[x + 1][y] == WHITE)
			neighBours++;
		if (y-1 >= 0 && this.boardGrid[x][y - 1] == WHITE)
			neighBours++;
		if (x-1 >= 0 && this.boardGrid[x - 1][y] == WHITE)
			neighBours++;

		return neighBours < 2;
	}

	public boolean isMoveNeighbourOfSameGroup(int originX, int originY,
			int targetX, int targetY) {				
		
		return findPath(originX, originY, originX, originY, targetX, targetY);
	}

	public boolean findPath(int originX, int originY, int nextX, int nextY,
			int targetX, int targetY) {
		boolean found = false;
		
		found = isNeighbour(nextX, nextY, targetX, targetY);
		
		if (!found && nextY+1 < SIZE && this.boardGrid[nextX][nextY+1] == WHITE && ((nextY + 1) != originY)) 
			found = findPath(nextX, nextY, nextX, nextY+1, targetX, targetY);
		if (!found && nextX+1 < SIZE && this.boardGrid[nextX + 1][nextY] == WHITE && ((nextX + 1) != originY))
			found = findPath(nextX, nextY, nextX+1, nextY, targetX, targetY);
		if (!found && nextY-1 >= 0 && this.boardGrid[nextX][nextY-1] == WHITE && ((nextY - 1) != originY)) 
			found = findPath(nextX, nextY, nextX, nextY-1, targetX, targetY);
		if (!found && nextX-1 >= 0 && this.boardGrid[nextX - 1][nextY] == WHITE && ((nextX - 1) != originY))
			found = findPath(nextX, nextY, nextX-1, nextY, targetX, targetY);
		
		return found;
	}
	
	private boolean isNeighbour(int originX, int originY, int targetX, int targetY) {
		if(originX == targetX && ((originY - 1) == targetY))
			return true;
		if(originX == targetX && ((originY + 1) == targetY))
			return true;
		if(originY == targetY && ((originX - 1) == targetX))
			return true;
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

	/**
	 * Get converter
	 * 
	 * @return
	 */
	private MoveConverter getConverter() {
		return this.moveConverter;
	}

	private String convertPointToString(int row) {
		return this.getConverter().convertPointToString(row);
	}

	private int convertStringToPoint(String position) {
		return this.getConverter().convertStringToPoint(position);
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
		int originX = this.convertStringToPoint(move.getOriginX()), originY = Integer
				.parseInt(move.getOriginY()) - 1;

		int targetX = this.convertStringToPoint(move.getTargetX()), targetY = Integer
				.parseInt(move.getTargetY()) - 1;

		switchPosition(originX, originY, targetX, targetY);

		IO.debug(this.toString());
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
	}

	/**
	 * Count the amount of groups
	 * 
	 * @param player
	 * @return
	 */
	public int amountOfGroups(Player player) {
		int playerColor = WHITE;
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
		// Switch the pieces. Should receive Move object?
		return this;
	}
	
	public int[][] getBoardContents() {
		return this.boardGrid;
	}
}
