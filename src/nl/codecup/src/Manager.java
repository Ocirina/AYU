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
		gameState = new GameState(PLAYER, COMPUTER);
		Board board = new Board();
		gameState.setBoard(board);
		
		String input = IO.input(); //leave for debug
		if (input.equals("Start")) {
			this.referee = new Referee(this);
			IO.debug("Referee created");
			this.player = new Player(this.gameState, PLAYER, this.referee);
			IO.debug("Player created");
			this.gameState = this.player.takeTurn(this.gameState);	
			IO.debug("Player turn taken.");
			
			this.handleInput();
		} else {
			//OUR DEBUG
			System.out.println(gameState);
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
		IO.debug("Start handle input");	
		String input = IO.input(); 		
		IO.debug("Start handle input with input: "+input);	
		while (!input.equals("Quit!")) {
			IO.debug("Retrieved input: "+input);
			if (converter.isMoveFormat(input)) {
				IO.debug("INPUT FROM PLAYER:" + input);
				Move move = this.converter.readMove(input);
				gameState = gameState.makeMove(move);
				gameState = this.player.takeTurn(gameState);
			} 
			
			input = IO.input();
		}
	}

	public Move readMove(String move) {
		return this.getConverter().readMove(move);
	}
}
