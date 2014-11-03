package nl.codecup.src;

public class Player {

    private Manager manager;
    private int playerNumber;
	private GameState state;
	private int piece;

    public Player(Manager manager, int playerNumber) {
        this.manager = manager;
        this.playerNumber = playerNumber;
    }

    public void start() {
    	IO.debug("R player" + this.playerNumber);
    	this.takeTurn();
    }
    
    public String takeTurn() {
    	return this.getBoard().findOpenMove();
    }
    
    public GameState takeTurn(GameState state) {
    	if (state.isGameOver())
			return state;
		this.state = state.clone();
		this.piece = state.getPlayingPiece();
		Move move = chooseMove();
		return state.makeMove(move);
    }
    
    private Move chooseMove() {
		return this.readMove("B5-C5");
	}

	public void stop() { }
    
    private void setMove(Move move) {
//    	if (this.manager.getReferee().validMove(move)) {
    		IO.output(move.toString());
    		this.movePiece(move);
//    	}
    }
    
    private Move readMove(String move) {
    	return this.getManager().readMove(move);
    }
    
    private void movePiece(Move move) {
    	this.getManager().movePiece(move);
    }
    
    private Board getBoard() {
    	return this.getManager().getBoard();
    }

    public String toString() {
        return "" + this.playerNumber;
    }
    
    private Manager getManager() {
    	return this.manager;
    }
}
