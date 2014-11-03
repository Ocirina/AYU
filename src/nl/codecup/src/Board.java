package nl.codecup.src;

public class Board {
	private MoveConverter moveConverter = new MoveConverter();
	private static final int SIZE = 11;
	
	/**
	 * The actual players
	 */
	private static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	
	/**
	 * The actual board
	 */
	private int[][] boardGrid = new int[][] {		
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE},
		{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE},
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE},
		{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE},
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE},
		{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE},
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE},
		{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE},
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE},
		{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE},
		{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE}
	};
	
	/**
	 * Constructor
	 * 
	 * @param grid
	 */
	public Board(int[][] grid) {
		this.boardGrid = grid;
	}
	
	/**
	 * Constructor
	 */
	public Board() {
		
	}
	
	/**
	 * Get converter
	 * 
	 * @return
	 */
	private MoveConverter getConverter() {
		return this.moveConverter;
	}
	
	/**
	 * Move an certain piece
	 * 
	 * @param move
	 */
	public void movePiece(Move move) {
		int originX = this.getConverter().convertStringToPoint(move.getOriginX()),
			originY = Integer.parseInt(move.getOriginY()) - 1;
		
		int targetX = this.getConverter().convertStringToPoint(move.getTargetX()),
			targetY = Integer.parseInt(move.getTargetY()) - 1;
		
		switchPosition(originX, originY, targetX, targetY);
		
		IO.debug(this.toString());
	}
	
	/**
	 * Find an open move
	 * 
	 * @return
	 */
	public String findOpenMove() {
		String newMove = "";
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				if(this.boardGrid[row][column] == WHITE) {
					if((column + 1) < 11 && this.boardGrid[row][column + 1] == NONE) {
						
						newMove += this.getConverter().convertPointToString(row) + "";
						newMove += (column + 1) + "-";
						
						newMove += this.getConverter().convertPointToString(row) + "";
						newMove += (column + 2) + "";
						
//						newMove = this.getConverter().convertPointToString(row) + (column + 1) + "-" + this.getConverter().convertPointToString(row) + (column + 2);
//						IO.debug("DEB: " + newMove);
						return newMove;
					}
				}
			}
		}
		
		return newMove;
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
	}
	
	/**
	 * Count the ammount of groups
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
		String returnString = "    A B C D E F G H I J K\n";
		for (int column = (SIZE-1); column >= 0; column--) {
			returnString += String.format("%02d", (column + 1)) + " ";
			for (int row = 0; row < SIZE; row++) {			
				returnString += "|" + this.convertPiece(this.boardGrid[row][column]);
			}
			returnString += "|\n";
		}
		return returnString;
	}
	
	/**
	 * Covert the number to an certain player
	 * @param player
	 * @return
	 */
	private String convertPiece(int player) {
		if (player != 0) {
			return player == 1 ? "W" : "B";
		}
		return " ";
	}
}
