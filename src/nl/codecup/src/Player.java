package nl.codecup.src;

public class Player {

    private Manager manager;
    private int playerNumber;

    public Player(Manager manager, int playerNumber) {
        this.manager = manager;
        this.playerNumber = playerNumber;
    }

    public void start() {
    	IO.debug("R player" + this.playerNumber);
    	this.setMove(this.manager.getConverter().readMove("B5-C5"));
    }

    public void stop() { }
    
    public void setMove(Move move) {
//    	if (this.manager.getReferee().validMove(move)) {
    		IO.output(move.toString());
    		this.manager.getBoard().movePiece(move);
//    	}
    }

    public String generateMove() {
    	return this.getManager().getBoard().findOpenMove();
    }

    public String toString() {
        return "" + this.playerNumber;
    }
    
    public Manager getManager() {
    	return this.manager;
    }
}
