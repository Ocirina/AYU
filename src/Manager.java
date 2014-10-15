

public class Manager {

	private boolean debugMode = true;
	private Player player1;
	private Player player2;
	private Referee referee;
	
	public static void main(String args[]) {
		IO io = new IO();
		System.out.println( io.readMove("d10-h6") );
		System.out.println( io.readMove("d1-h60") );
		System.out.println( io.readMove("d1-h6") );
	}

	public void startGame(String configFile) {

	}

	private void loadConfig() {
		
	}

	public void startPlayer(Player player) {

	}

	public void startReferee() {

	}

	public void handleInput() {

	}

	public void stopPlayer(Player player) {

	}

}
