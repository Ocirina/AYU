package montecarlo;

import nl.codecup.src.GameState;
import nl.codecup.src.Player;

public class MCNode {
	
	private MCNode[] children = new MCNode[0];
	private int numOfChildren = 0;
	private float winpercentage = (float) 0.0;
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
		boolean win = false;
		
		GameState state = this.player.getState();
		this.state = state.makeMove(this.player.getRandomMove());
		
		this.isLeaf = this.state.isGameOver();
		if (this.isLeaf) {
			win = this.state.hasWon(this.state.getPlayingPiece());
		} else {
			this.state = state.makeMove(this.player.getRandomMove());
			this.isLeaf = this.state.isGameOver();
			if (this.isLeaf) {
				win = !this.state.hasWon(this.state.getPlayingPiece());
			}
		}
		this.isWin = win;
		this.winpercentage = (float) (win ? 1.0 : 0.0);
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
	
}