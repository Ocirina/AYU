package nl.codecup.src;

public class Board {
	private MoveConverter moveConverter = new MoveConverter();
	private static final int SIZE = 11;
	
	private static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	
	private int[][] boardGrid = new int[][] {
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE},
		{ BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK},
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE},
		{ BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK},
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE},
		{ BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK},
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE},
		{ BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK},
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE},
		{ BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK},
		{ NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE}
	};
	
	public Board(int[][] grid) {
		this.boardGrid = grid;
	}
	
	public Board() {
		
	}
	
	public movePiece(Move move) {
		int originX = this.moveConverter.convertStringToPoint(move.getOriginX()),
			originY = Integer.parseInt(move.getOriginY());
		
		int targetX = this.moveConverter.convertStringToPoint(move.getTargetX()),
			targetY = Integer.parseInt(move.getTargetY());
		
		int tempPiece = this.boardGrid[originX][originY];
		
		this.boardGrid[originX][originY] = this.boardGrid[targetX][targetY];		
		this.boardGrid[targetX][targetY] = tempPiece;
	}
	
	public String toString() {
		String returnString = "";
		
		for(int row = 0; row < SIZE; row++) {
			for(int column = 0; column < SIZE; column++) {
				returnString += "|" + this.convertPiece(this.boardGrid[row][column]);
			}
			
			returnString += "|\n";
		}
		
		return returnString;
	}
	
	private String convertPiece(int player) {
		if (player != 0) {
			return player == 1 ? "W" : "B";
		}
		
		return " ";
	}
}
