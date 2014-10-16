package nl.codecup;

public class Player {

    private Manager manager;
    private int playerNumber;

    public Player(Manager manager, int type) {
        this.manager = manager;
        this.playerNumber = type;
    }

    public void start() {
    	System.err.println("R player" + this.playerNumber);
    }

    public void stop() {

    }
    
    public void setMove(Move move) {
    	if(manager.getReferee().validMove(move)) {
    		//DO-MOVE
    		System.err.print("MOVE" + move);
    	}
    }

    public String generateMove() {
        return null;
    }

    public String toString() {
        return this.playerNumber + "";
    }
}
