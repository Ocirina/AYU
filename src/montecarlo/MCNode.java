package montecarlo;

public class MCNode {
	
	private MCNode[] children = new MCNode[0];
	private int numOfChildren = 0;
	private float winpercentage = (float) 0.0;
	private boolean isLeaf = false;
	private boolean isWin = false;
	
	/**
	 * creates a new Node
	 * 
	 * @param isLeaf	- is a leaf of the tree
	 */
	public MCNode(boolean isLeaf) {
		setLeaf(isLeaf);
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
	
}