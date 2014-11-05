package nl.codecup.src;

public class GameState {
	private Board board;
	private int playingPiece;
	private int opponentPiece;
	private int winner = 0;

	public GameState(int playerPiece, int opponentPiece) {
		this.playingPiece = playerPiece;
		this.opponentPiece = opponentPiece;
	}
	
	public GameState(Board board, int playerPiece, int opponentPiece) {
		this.board = board;
		this.playingPiece = playerPiece;
		this.opponentPiece = opponentPiece;
		this.checkForWin();
	}

	public GameState(GameState state) {
		this.playingPiece = state.playingPiece;
		this.opponentPiece = state.opponentPiece;
		this.winner = state.winner;
		this.board = state.board.clone();
	}
	
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Places the move on the board and returns this in a new state.
	 * @param row the y coordinates
	 * @param column the x coordinates
	 * @return A new GameState with the move added
	 */
	public GameState makeMove(Move move) {
		GameState newState = this.clone();
		Board newBoard = this.board.clone();
		newState.setPlayerPiece(opponentPiece);
		newState.setComputerPiece(playingPiece);
		newState.setBoard(newBoard.placePiece(move));
		return newState;
	}
	
	/**
	 * Returns the current opponent piece.
	 * @return returns the opponent piece
	 */
	private void setComputerPiece(int computerPiece) {
		this.opponentPiece = computerPiece;
	}

	/**
	 * Sets the board on the state. It also sets the size of the board.
	 * And calculates the minimum moves required to win. After this it 
	 * checks if there is a winner.
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
		checkForWin();
	}
	
	/**
	 * Sets the current player piece.
	 * @param playerPiece
	 */
	private void setPlayerPiece(int playerPiece) {
		this.playingPiece = playerPiece;
	}
	
	public boolean isGameOver() {
		return false;
	}
	
	/**
	 * Checks if the given piece wins.
	 * @param piece
	 * @return true if there is a winner and it is the given piece
	 */
	public boolean hasWon(int piece) {
		return hasWinner() && winner == piece;
	}

	/**
	 * Checks if the given piece loses.
	 * @param piece
	 * @return true if there is a loser and it is the given piece
	 */
	public boolean hasLost(int piece) {
		return hasWinner() && winner != piece;
	}
	
	/**
	 * Makes a clone of the state object.
	 * @return A cloned state
	 */
	public GameState clone() {  
		return new GameState(this);
	}
	
	/**
	 * Draws the board with pieces in place.
	 * @return a visual representation of the board in this state
	 */
	public String toString() {
		return this.board.toString();
	}


	/**
	 * Checks if the game has resulted in a win.
	 * @return true if there is a winner
	 */
	private boolean hasWinner() {
		return winner == 1 || winner == 2;
	}
	
	private boolean checkForWin() {
		winner = 0;
		return false;
	}

	public int getPlayingPiece() {
		// TODO Auto-generated method stub
		return this.playingPiece;
	}
	
	public int[][] getBoardContents() {
		return this.board.getBoardContents();
	}
}
