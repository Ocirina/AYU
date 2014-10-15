package nl.codecup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Manager {

	private boolean debugMode = true;
	private Player player1;
	private Player player2;
	private Referee referee;
	
	public static void main(String args[]) {
//		IO io = new IO();
//		System.out.println( io.readMove("d10-h6") );
//		System.out.println( io.readMove("d1-h60") );
//		System.out.println( io.readMove("d1-h6") );
		Manager manager = new Manager();
        manager.startPlayer(new Player(manager, 3));
	}

	public void startGame(String configFile) {

	}

	private void loadConfig() {
		
	}

	public void startPlayer(Player player) {
		player.start();
		
		try {
			this.handleInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startReferee() {

	}

	public void handleInput() throws IOException {
		BufferedReader reader = new BufferedReader (new InputStreamReader (System.in)); 
		String s = reader.readLine(); 
		System.err.print(s);
	}

	public void stopPlayer(Player player) {
		player.stop();
	}

}
