package montecarlo;

import nl.codecup.src.*;

public class MCNode {
	
	private MCNode[] children = new MCNode[0];
	private int numOfChildren = 0;
	private float winpercentage = (float) 0.0;
	private int moveValue = 0;
	private boolean isLeaf = false;
	private boolean isWin = false;
	private Player player;
	private GameState state;
	
	/**
	 * Creates a new Node with a GameState. 
	 * Then checks if the game is over and if it is a win or not.
	 * 
	 * @param state	
	 * 				the GameState of the node
	 */
	public MCNode(Player player) {
		this.player = player;
		
		GameState state = this.player.getState();
		this.state = state.makeMove(this.player.getRandomMove());
		updateMoveValue();
		
		this.isLeaf = this.state.isGameOver();
		if (this.isLeaf) {
			this.isWin = this.state.hasWon(this.state.getPlayingPiece());
		} /*else {
			this.state = state.makeMove(this.player.getRandomMove());
			IO.debug("Random black move " + this.state.getPlayedMove().toString());
			this.isLeaf = this.state.isGameOver();
			if (this.isLeaf) {
				this.isWin = !this.state.hasWon(this.state.getPlayingPiece());
			}
		}*/
		this.winpercentage = (float) (this.isWin ? 1.0 : 0.0);
	}
	
	private void updateMoveValue() {
		Move playedMove = this.state.getPlayedMove();
		GroupManager groupmanager = GroupManager.getInstance();
		Board board = this.state.getBoard();
		groupmanager.recheckGroups(board);
		
		int x = playedMove.getIndexTargetX();
		int y = playedMove.getIndexTargetY();
		Group group = groupmanager.getGroupByCoordinate(x, y);
		String neighbors[] = board.getNeighborsByPiece(x, y, this.state.getOpponentPiece());

		this.moveValue = group.getCoordinates().size() * 2;
		
		for (int i = 0; i < neighbors.length; i++) {
			this.moveValue += groupmanager.getGroupByCoordinate(neighbors[i]).getCoordinates().size();
		}
	}

	/**
	 * get children
	 * 
	 * @return children
	 */
	public MCNode[] getChildren() {
		return children;
	}

	/**
	 * set children
	 * 
	 * @param children
	 */
	public void setChildren(MCNode[] children) {
		this.children = children;
	}

	/**
	 * get the winpercentage
	 * 
	 * @return float winpercentage
	 */
	public float getWinpercentage() {
		return winpercentage;
	}

	/**
	 * set the winpercentage
	 * 
	 * @param winpercentage	- float the new winpercentage
	 */
	public void setWinpercentage(float winpercentage) {
		this.winpercentage = winpercentage;
	}

	/**
	 * get isLeaf
	 * 
	 * @return boolean isLeaf
	 */
	public boolean isLeaf() {
		return isLeaf;
	}

	/**
	 * set isLeaf
	 * 
	 * @param isLeaf
	 */
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * get isWin
	 * 
	 * @return boolean isWin
	 */
	public boolean isWin() {
		return isWin;
	}

	/**
	 * set win
	 * 
	 * @param isWin
	 */
	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	/**
	 * get number of children
	 * 
	 * @return numOfChildren
	 */
	public int getNumOfChildren() {
		return numOfChildren;
	}

	/**
	 * set numOfChildren
	 * 
	 * @param numOfChildren
	 */
	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public int getMoveValue() {
		return moveValue;
	}

	public void setMoveValue(int moveValue) {
		this.moveValue = moveValue;
	}
	
}