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
    	this.setMove(this.readMove("B5-C5"));
    }

    public void stop() { }
    
    public void setMove(Move move) {
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
    
    public int amountOfGroups() {
    	return this.getManager().getBoard().amountOfGroups(this);
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
