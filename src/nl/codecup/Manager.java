package nl.codecup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Manager {

	private boolean debugMode = true;
	private Player player1;
	private Player player2;
	private Referee referee;
	private IO IO;
	
	public Referee getReferee() {
		return this.referee;
	}
	
	public static void main(String args[]) {
		new Manager();
	}
	
	public Manager() {
		System.out.println("F");
		this.startPlayer(new Player(this, 1));
		this.IO = new IO();
	}

	public void startGame(String configFile) {

	}

	private void loadConfig() {
		
	}

	public void startPlayer(Player player) {
		player.start();
		
//		try {
//			this.handleInput();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void startReferee() {

	}

	public void handleInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader (System.in)); 
		String s = reader.readLine(); 
		
		if (s.compareTo("Start!") == 0) {
			Move move = this.IO.readMove(s); 
			player1.setMove(move); 
		} 
	}

	public void stopPlayer(Player player) {
		player.stop();
	}

}
