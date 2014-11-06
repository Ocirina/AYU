package nl.codecup.src;

public class Player {
	private GameState state;
	public static int piece;
	public static int empty = 0;
	private Referee referee;

	public Player(GameState state, int piece, Referee referee) {
		this.state = state;
		this.piece = piece;
		this.referee = referee;
	}

	private Referee getReferee() {
		return this.referee;
	}

	public GameState takeTurn(GameState state) {
		if (state.isGameOver()) {
			return state;
		}

		this.state = state.clone();
		this.piece = state.getPlayingPiece();
		Move move = chooseMove();
		if (this.getReferee().validMove(move)) {
			IO.output(move.toString());
			int gaps = state.getBoard().getPiecesInCol(0, 1).length;
			IO.debug("GAPS IN Col 1: " + gaps);
			return state.makeMove(move);
		}

		IO.debug("NO VALID MOVE! " + move.toString());

		return null;
	}

	/**
	 * Generate random move, in the future this will be a move which is
	 * generated by monte carlo
	 * 
	 * @return
	 */
	public Move chooseMove() {
		int[][] content = this.state.getBoardContents();
		Move move = null;
		int contentLength = content.length - 1;

		// Strategy 1: Make long groups in each column. (Should make a group of
		// 6 on each row).
		for (int row = 0; row < contentLength; row++) {
			for (int column = 0; column < contentLength; column++) {
				/**
				 * Situation 1 Start: | | W | | W | | | Result: | | | W | W | |
				 * |
				 * 
				 * Situation 2 Start: | | W | W | | W | | Result: | | | W | W |
				 * W | |
				 * 
				 * Start: | | | W | W | W | | W | | Result: | | | | W | W | W |
				 * W | |
				 */
				boolean gapScenario = (column < 9 && row < 9
						&& content[row][column] == Player.piece
						&& content[row][column + 1] == 0 
						&& content[row][column + 2] == Player.piece);
				
				int columnPieceToMove = 0;
				if (gapScenario) {
					for (int i = column; i > 0; i--) {
						if (content[row][i] == Player.piece)
							columnPieceToMove = i;
						else
							break;
					}
					return new Move(row, columnPieceToMove, row, column + 1);
				}
			}
		}
		
		for (int row = 0; row < contentLength; row++) {
			for (int column = 0; column < contentLength; column++) {
				boolean gapScenario = (column <= 9
						&& content[row][column] == Player.piece
						&& content[row][column + 1] == 0);
				
				int columnPieceToMove = 0;
				if (gapScenario) {
					for (int i = column; i > 0; i--) {
						if (content[row][i] == Player.piece)
							columnPieceToMove = i;
						else
							break;
					}
					return new Move(row, columnPieceToMove, row, column + 1);
				}
			}

		}

		// Strategy 3: If there are no more possibilities of making a group on a
		// column.
		// Connect them.
		/**
		 * Situation:
		 * 
		 * Start: 
		 * |   |   | W | W | W | W | W | W | 
		 * |   |   |   |   |   |   |   |   |
		 * |   |   | W | W | W | W | W | W | 
		 * 
		 * Result:
		 * |   |   |   | W | W | W | W | W | 
		 * |   |   |   |   |   |   |   | W |
		 * |   |   | W | W | W | W | W | W | 
		 * 
		 */

		for (int row = 1; row < (contentLength-1); row++) {
			if (content[row][10] == 0) {
				IO.debug("Got it!");
				int columnPieceToMove = 5;
				for (int i = 10; i > 0; i--) {
					if (content[row][i] == Player.piece)
						columnPieceToMove = i;
					else
						break;
				}
				return new Move((row -1), columnPieceToMove, row, 10);
			}
		}
		return move;
	}

	public void stop() {
	}

	/**
	 * Method to tell caiaio which player ours is
	 */
	public String toString() {
		return "R player: " + Player.piece;
	}

}
