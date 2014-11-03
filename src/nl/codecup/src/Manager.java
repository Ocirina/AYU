package nl.codecup.src;

import java.io.IOException;

public class Manager {
//	private boolean debugMode = true;
	private Player player;
//	private Player player2;
	private Referee referee;
	private MoveConverter converter;
	private Board board;

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String args[]) {				
		new Manager();
	}
	
	/**
	 * Constructor
	 */
	public Manager() {
		this.converter = new MoveConverter();
		this.board = new Board();
		
		if (IO.input().equals("Start")) {
			this.startPlayer(new Player(this, 1));
			this.startReferee();
		} else {
			//OUR DEBUG
			System.out.println(this.getBoard().findOpenMove());
		}
	}
	
	/**
	 * This will load the terminal configuration and the file configuration
	 */
	public void startGame() {
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
	 * Gets the player
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Get the IO
	 * @return
	 */
	public MoveConverter getConverter() {
		return this.converter;
	}
	
	/**
	 * Get the board
	 * 
	 * @return
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Get the referee
	 * 
	 * @return
	 */
	public Referee getReferee() {
		return this.referee;
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
				this.getBoard().movePiece(move);
				
				IO.output(this.getConverter().readMove(this.player.takeTurn()).toString() );
			} 
			
			input = IO.input();
		}
	}
	
	public void movePiece(Move move) {
		this.getBoard().movePiece(move);
	}

	public Move readMove(String move) {
		return this.getConverter().readMove(move);
	}

}
