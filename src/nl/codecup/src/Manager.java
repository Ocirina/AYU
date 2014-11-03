package nl.codecup.src;

import java.io.IOException;

public class Manager {
	private Player player;
	private Referee referee;
	private GameState gameState;

	private MoveConverter converter;
	private static final int PLAYER = 1;
	private static final int COMPUTER = 2;

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
		this.gameState = new GameState(PLAYER, COMPUTER);
		this.gameState.setBoard(new Board());
		
		if (IO.input().equals("Start")) {
<<<<<<< HEAD
			this.player = new Player(this.gameState, PLAYER);
			this.referee = new Referee(this);
			this.gameState = this.player.takeTurn(this.gameState);			
=======
			this.referee = new Referee(this);
			this.player = new Player(gameState, PLAYER, this.referee);
			this.player.start();

>>>>>>> c12a100388334f0464d141f77cdfacea8cfb09e1
			this.handleInput();
		} else {
			System.out.println(this.gameState);
		}
	}
	
	public Manager(boolean testing) {
		
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
	 * This will call the stop method on the player
	 * 
	 * @param player
	 */
	public void stopPlayer(Player player) {
		player.stop();
	}
	
	/**
	 * Returns the current game state
	 * @return the current game state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Sets the gamestate with the given game state
	 * @param gamestate : The new game state
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
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
				Move move = this.converter.readMove(input);

				this.gameState = this.gameState.makeMove(move);
				IO.debug(gameState.toString());

				this.gameState = this.player.takeTurn(this.gameState);
				IO.debug(gameState.toString());
			} 			
			input = IO.input();
		}
	}

	public Move readMove(String move) {
		return this.getConverter().readMove(move);
	}
}
