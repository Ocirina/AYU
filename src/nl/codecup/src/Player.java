package nl.codecup.src;

public class Player {

	private GameState state;
	private int piece;

    public Player(GameState state, int piece) {
        this.state = state;
        this.piece = piece;
    }
    
    public GameState takeTurn(GameState state) {
    	if (state.isGameOver())
			return state;
		this.state = state.clone();
		this.piece = state.getPlayingPiece();
		Move move = chooseMove();
		IO.output(move.toString());
		return state.makeMove(move);
    }
    
    private Move chooseMove() {
    	int[][] content = this.state.getBoardContents();
    	MoveConverter converter = new MoveConverter();
    	int contentLength = content.length - 1;
		for (int column = 0; column < contentLength; column++) {
			for (int row = 0; row < contentLength; row++) {
    			/**
    			 * Check if there are 3 items near each other. E.g.:
    			 * Start:  |   | W |   | W |   |
    			 * Result: |   |   | W | W |   |
    			 * 
    			 * But also when 2 are already connected
    			 * Start:  |   | W | W |   | W |
    			 * Result: |   | W | W | W |   |
    			 */
//    			if (column < 9)
//    				IO.debug(converter.readMove(row, column+1, row, column).toString()+" => "+content[row][column]+"|"+content[row][column+1]+"|"+content[row][column+2]);
    			if (column < 8 && content[row][column] == 1 && content[row][column+1] == 0 && content[row][column+2] == 1) {
    				if (column > 0 && content[row][column-1] == 1) {
    					return converter.readMove(row, column+1, row, column);
    				}
    				else {
    					return converter.readMove(row, column+1, row, column+2);
    				}
    			}
    		}
    	}
    	return null;
	}

	public void stop() { }

    public String toString() {
        return "R player: " + this.piece;
    }
}
