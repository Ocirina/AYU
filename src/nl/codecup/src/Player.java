package nl.codecup.src;

public class Player {
	private GameState state;
	private int piece;
	private Referee referee;

    public Player(GameState state, int piece, Referee referee) {
        this.state = state;
        this.piece = piece;
        this.referee = referee;
    }
    
    private Referee getReferee() {
    	return this.referee;
    }
    
    public GameState takeTurn(GameState state) {
    	if (state.isGameOver()) {
			return state;
    	}
    	
		this.state = state.clone();
		this.piece = state.getPlayingPiece();
		
		Move move = chooseMove();
		if(this.getReferee().validMove(move)) {
			IO.output(move.toString());
			return state.makeMove(move);
		} 
		
		IO.debug("NO VALID MOVE! " + move.toString());
		return null;		
    }
    
    public Move chooseMove() {
    	int[][] content = this.state.getBoardContents();
    	MoveConverter converter = new MoveConverter();
    	Move move = null;
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
    			if (column < 8 && content[row][column] == 1 && content[row][column+1] == 0 && content[row][column+2] == 1) {
    				if (column > 0 && content[row][column-1] == 1) {
    					//TODO new move constructor with int?
    					move = converter.readMove(row, column+1, row, column);
    				} else {
    					move = converter.readMove(row, column+1, row, column+2);
    				}
    				
    				if(this.referee.validMove(move)) {
						return move;
					}
    			}
    		}
    	}
		
    	return move;
	}

	public void stop() { }

    public String toString() {
        return "R player: " + this.piece;
    }
}
