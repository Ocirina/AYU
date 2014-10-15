
public class Player {

	private Manager manager;
	private int playerNumber;
	
	public Player (Manager manager, int type) {
		this.manager = manager;
		this.playerNumber = type;
	}

	public void start() {

	}

	public void stopPlayer() {

	}

	public String generateMove() {
		return null;
	}

	public String toString() {
		return this.playerNumber + "";
	}
}
