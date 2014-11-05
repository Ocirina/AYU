package nl.codecup.src;

public class Referee {

	private Manager manager;
   
	public Referee(Manager manager) {
		this.manager = manager;
		
		
	}

	public void handleInput() {
		String input = IO.input();
		IO.debug(input);
	}

	/**
	 * This method will send the report
	 */
	public void sendReport() {
	}

	/**
	 * This method will stop the object itself
	 */
	private void stopReferee() {
		// TODO stop has to be implementend
	}

	/**
	 * This will return if an move is valid
	 * 
	 * @param move
	 * @return
	 */
	public boolean validMove(Move move) {
		IO.debug("IS VALID?: " + move);
		return this.mayBeMoved(move, this.manager.getGameState().getBoard());
	}

	// TODO MOVE object as param
	private boolean mayBePlaced(Move move, Board board, boolean inGroup) {
		int piece = manager.getGameState().getPlayingPiece();
		int originX = move.getOriginXConverted();
		int originY = move.getOriginYConverted();

		int targetX = move.getTargetXConverted();
		int targetY = move.getTargetYConverted();

		if (validCoordinates(targetX, targetY) && board.isBlankSpace(targetX, targetY)) {
			if (!inGroup && board.hasNeighbour(targetX, targetY, piece)) {
				return true;
				// TODO MOVE object as PARAM??
			} else if (inGroup
					&& board.isMoveNeighbourOfSameGroup(originX, originY,
							targetX, targetY)) {
				return true;
			}
		}
		return false;
	}

	private boolean mayBeMoved(Move move, Board board) {
		int piece = manager.getGameState().getPlayingPiece();
//		System.out.println(piece);
		if (move != null) {
			int x = move.getOriginXConverted();
			int y = move.getOriginYConverted();
			
			if (validCoordinates(x,y) && board.getBoardContents()[x][y] == piece) {
				if (board.hasNeighbour(x, y, piece) && board.onEdgesOfGroup(x, y, piece)) {
					return this.mayBePlaced(move, board, true);
				} 
				else if (!board.hasNeighbour(x, y, piece)) {
					return this.mayBePlaced(move, board, false);
				}
			}

		}
		return false;
	}
	
	private boolean validCoordinates(int x, int y) {
		return (x >= 0 && x < Board.SIZE && y >= 0 && y < Board.SIZE);
	}

	/**
	 * This will write some data to the log
	 * 
	 * @return
	 */
	public String writeLog() {
		// TODO WRITE STUFF TO LOG
		return null;
	}

}
