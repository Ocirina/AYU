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
		int originX = move.getOriginXConverted();
		int originY = move.getOriginYConverted();

		int targetX = move.getTargetXConverted();
		int targetY = move.getTargetYConverted();

		if (board.isBlankSpace(targetX, targetY)) {
			if (!inGroup && board.hasNeighbour(targetX, targetY)) {
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
		if (move != null) {
			int x = move.getOriginXConverted();
			int y = move.getOriginYConverted();
			if (board.getBoardContents()[x][y] == this.manager.getPlayer().getPiece()) {
				if (board.hasNeighbour(x, y) && board.onEdgesOfGroup(x, y)) {
					return this.mayBePlaced(move, board, true);
				} else if (!board.hasNeighbour(x, y)) {
					return this.mayBePlaced(move, board, false);
				}
			}
		}

		return false;
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
