package nl.codecup.src;

import java.io.IOException;

public class Manager {
//	private boolean debugMode = true;
	private Player player;
//	private Player player2;
	private Referee referee;
	private MoveConverter converter;
	
	public Referee getReferee() {
		return this.referee;
	}
	
	public static void main(String args[]) {		
		new Manager();
	}
	
	public Manager() {
		this.converter = new MoveConverter();
		String input = IO.input();
		if (input.equals("Start")) {
			this.startPlayer(new Player(this, 1));
			this.startReferee();
		}
	}
	
	/**
	 * This will load the terminal configuration and the file configuration
	 * 
	 * @param configFile
	 */
	public void startGame(String configFile) {
		this.loadConfig();
	}

	/**
	 * Load the system configuration file
	 */
	private void loadConfig() {
		IO.output("This should load the config via file");
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
		this.player = player;
		player.start();
		this.handleInput();
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
	public void handleInput() {
		String input = IO.input(); 		
		while (!input.equals("Quit!")) {
			if (converter.isMoveFormat(input)) {
				IO.debug("INPUTZ:" + input);
				Move move = this.converter.readMove(input);
				this.player.setMove(this.getConverter().readMove("C5-E5"));
//				IO.output(move.toString());
	//			this.player1.setMove(move); 
			} 
		}
	}
	
	/**
	 * Get the IO
	 * @return
	 */
	public MoveConverter getConverter() {
		return this.converter;
	}

}
