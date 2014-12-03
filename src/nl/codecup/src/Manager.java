package nl.codecup.src;

import java.io.IOException;

public class Manager {
    private Player player;
    private Referee referee;
    private GameState gameState;
    private final int STARTPLAYER = 1;

    private MoveConverter converter;

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
        String input = IO.input(); // leave for debug
        int playerNumber = (input.equals("Start") ? 1 : 2);
        initGame(playerNumber);
        this.handleInput(input);
    }

    private void initGame(int playerNumber) {
        this.converter = new MoveConverter();
        this.gameState = new GameState(new Board(), playerNumber, (playerNumber == 1 ? 2 : 1));
        this.referee = new Referee(this);
        this.player = new Player(this.gameState, playerNumber);
        if (playerNumber == STARTPLAYER) {
            this.gameState = this.player.takeTurn(this.gameState);
        }
    }

    public Manager(boolean testing) {
    }

    /**
     * This will load the terminal configuration and the file configuration
     */
    public void startGame() {
        loadConfig();
    }

    /**
     * Load the system configuration file
     */
    private void loadConfig() {
        IO.output("This should load the config via file");
    }

    /**
     * Returns the current game state
     * 
     * @return the current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Sets the gamestate with the given game state
     * 
     * @param gamestate
     *            : The new game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Gets the player
     * 
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Sets the player
     * 
     * @param player
     *            : the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the IO
     * 
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
        this.handleInput(IO.input());
    }

    public Board getBoard() {
        return gameState.getBoard();
    }

    /**
     * Handles input
     * 
     * @param input
     */
    public void handleInput(String input) {
        while (!input.equals("Quit!")) {
            if (converter.isMoveFormat(input)) {
                gameState = gameState.makeMove(this.readMove(input));
                gameState = this.player.takeTurn(gameState);
            }

            input = IO.input();
        }
    }

    public Move readMove(String move) {
        return getConverter().readMove(move);
    }
}
