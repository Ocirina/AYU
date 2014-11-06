package nl.codecup.src;

public class Player {
	private GameState state;
	public static int piece;
	public static int empty = 0;
	private Referee referee;

    public Player(GameState state, int piece, Referee referee) {
        this.state = state;
        Player.piece = piece;
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
		
		IO.output("NO VALID MOVE! " + move.toString());
		return null;		
    }
    
    /**
     * Generate random move, in the future this will be a move which is generated by monte carlo
     * 
     * @return
     */
    public Move chooseMove() {
    	int[][] content = this.state.getBoardContents();
    	Move move = null;
    	int contentLength = content.length - 1;
		for (int column = 0; column < contentLength; column++) {
			for (int row = 0; row < contentLength; row++) {
    			
    			/**
    			 * Situation 1
    			 * Start:  |   | W |   | W |   |   |
    			 * Result: |   |   | W | W |   |   |
    			 * 
    			 * Situation 2
    			 * Start:  |   | W | W |   | W |   |
    			 * Result: |   |   | W | W | W |   |
    			 * 
    			 * Situation 3
    			 * Start:  |   |   | W | W | W |   | W |   |
    			 * Result: |   |   |   | W | W | W | W |   |
    			 */
    			boolean gapScenario = (column < 8 && row < 8 && content[row][column] == Player.piece && content[row+1][column] == 0 && content[row+2][column] == Player.piece);
    			int columnPieceToMove = 0;
    			
    			if (gapScenario) {
	    			for (int i = column; i > 0; i--) {
	    				IO.debug("DEBUG: " + content[row][i + 1]);
	    				if (content[row][i + 1] == Player.piece){
	    					if(content[row][i] != Player.piece ){
		    					columnPieceToMove = i + 1;
		    					break;
	    					}
	    				}   				    					
	    			}

	    			IO.debug("COLPIECE: " + columnPieceToMove);
	    			return new Move(row, columnPieceToMove, row, column + 1);
    			}
    		}
    	}
    	return move;
	}

	public void stop() { }

	/**
	 * Method to tell caiaio which player ours is
	 */
    public String toString() {
        return "R player: " + Player.piece;
    }

 
}
