package nl.codecup.src;

public class Player {

    private Manager manager;
	private GameState state;
	private int piece;

    public Player(Manager manager, int piece) {
        this.manager = manager;
        this.piece = piece;
    }

    public void start() {
    	IO.debug(this.toString());
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
        return "R player: " + this.piece;
    }
    
    private Manager getManager() {
    	return this.manager;
    }
}
