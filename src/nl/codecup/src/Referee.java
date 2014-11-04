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
	// private void stopReferee() { }

	/**
	 * This will return if an move is valid
	 * 
	 * @param move
	 * @return
	 */
	public boolean validMove(Move move) {
		return this.mayBeMoved(move, this.manager.getGameState().getBoard());
	}

	private boolean mayBePlaced(Move move, Board board, boolean inGroup){ 
		int originX = move.getOriginXConverted();
		int originY = move.getOriginYConverted();
		
		int targetX = move.getTargetXConverted();
		int targetY = move.getTargetYConverted();
		
		if(board.isBlankSpace(targetX, targetY)) {
			if(!inGroup && board.hasNeighbour(targetX, targetY)) {
				return true;
				//TODO MOVE object as PARAM??
			} else if(inGroup && board.isMoveNeighbourOfSameGroup(originX, originY, targetX, targetY)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean mayBeMoved(Move move, Board board) {
		if(move != null) {
			int x = move.getOriginXConverted();
			int y = move.getOriginYConverted();
			
			if (board.hasNeighbour(x,y) && board.onEdges(x, y)) {
				return this.mayBePlaced(move, board, true);
			} else if(!board.hasNeighbour(x, y)) {
				return this.mayBePlaced(move, board, false);
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
		return null;
	}

}
