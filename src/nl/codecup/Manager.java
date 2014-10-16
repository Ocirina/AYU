package nl.codecup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Manager {
//	private boolean debugMode = true;
	private Player player1;
//	private Player player2;
	private Referee referee;
	private IO IO;
	
	public Referee getReferee() {
		return this.referee;
	}
	
	public static void main(String args[]) {		
		new Manager();
	}
	
	public Manager() {
		this.IO = new IO();
		this.startPlayer(new Player(this, 9));
		this.startReferee();
	}
	
	/**
	 * This wil load the terminal config and the file config
	 * 
	 * @param configFile
	 */
	public void startGame(String configFile) {
		this.loadConfig();
	}

	/**
	 * Load the system config file
	 */
	private void loadConfig() {
		System.out.println("This should load the config via file");
	}

	/**
	 * This method will start the referee and
	 */
	public void startReferee() {
		this.referee = new Referee(this);
	}

	/**
	 * This method will start the player
	 * 
	 * @param player
	 */
	public void startPlayer(Player player) {
		player.start();
		
		try {
			this.handleInput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will call the stop method on the player
	 * 
	 * @param player
	 */
	public void stopPlayer(Player player) {
		player.stop();
	}
	
	/**
	 * This method will handle the input for the manager
	 * 
	 * @throws IOException
	 */
	public void handleInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		String input = reader.readLine(); 
		
		if (IO.isMove(input)) {
			Move move = this.IO.readMove(s); 
			this.player1.setMove(move); 
		} 
	}

}
