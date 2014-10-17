package nl.codecup.src;

public class Player {

    private Manager manager;
    private int playerNumber;

    public Player(Manager manager, int playerNumber) {
        this.manager = manager;
        this.playerNumber = playerNumber;
    }

    public void start() {
    	System.err.println("R player" + this.playerNumber);
    	System.out.println(this.playerNumber + " Start");
    }

    public void stop() { }
    
    public void setMove(Move move) {
    	if (manager.getReferee().validMove(move)) {
    		System.err.print("MOVE" + move);
    	}
    }

    public String generateMove() {
        return null;
    }

    public String toString() {
        return "" + this.playerNumber;
    }
}
