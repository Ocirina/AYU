package nl.codecup.src;

public class Player {

	private GameState state;
	private int piece;

    public Player(GameState state, int piece) {
        this.state = state;
        this.piece = piece;
    }

    public void start() {
    	IO.debug(this.toString());
    	this.takeTurn(this.state);
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
    	return new Move("B", "5", "C", "5");
	}

	public void stop() { }

    public String toString() {
        return "R player: " + this.piece;
    }
}
