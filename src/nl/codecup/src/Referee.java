package nl.codecup.src;

public class Referee {

	private Manager manager;

	public Referee(Manager manager) {
		this.manager = manager;
	}

	public void handleInput() {
		String input = IO.input();
		IO.output(input);
	}

	/**
	 * This method will send the report
	 */
	public void sendReport() {
	}

	/**
	 * This method will stop the object itself
	 */
	// private void stopReferee() { }

	/**
	 * This will return if an move is valid
	 * 
	 * @param move
	 * @return
	 */
	public boolean validMove(Move move) {
		Board board = this.manager.getGameState().getBoard();
		if(this.mayBeMoved(move, board)) {
			return true;
		} 
		// this.manager.getConverter().displayMove(move);
		return false;
	}

	private boolean mayBePlaced(Move move, Board board, boolean inGroup){
		int originX = this.manager.getConverter().convertStringToPoint(move.getOriginX());
		int originY = (Integer.parseInt(move.getOriginY()) - 1);
		int targetX = this.manager.getConverter().convertStringToPoint(move.getTargetX());
		int targetY = (Integer.parseInt(move.getTargetY()) - 1);
		if(board.isBlankSpace(targetX, targetY)) {
			if(!inGroup && board.hasNeighbour(targetX, targetY)) {
				return true;
			} else if(inGroup && board.isMoveNeighbourOfSameGroup(originX, originY, targetX, targetY)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean mayBeMoved(Move move, Board board) {
		int x = this.manager.getConverter().convertStringToPoint(move.getOriginX());
		int y = (Integer.parseInt(move.getOriginY()) - 1);
		if (board.hasNeighbour(x,y) && board.onEdges(x, y)) {
			return this.mayBePlaced(move, board, true);
		} else if(!board.hasNeighbour(x, y)) {
			return this.mayBePlaced(move, board, false);
		}
		
		return false;
	}

	/**
	 * This will write some data to the log
	 * 
	 * @return
	 */
	public String writeLog() {
		return null;
	}

}
