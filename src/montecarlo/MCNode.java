package montecarlo;

import java.util.ArrayList;

public class MCNode {
	
	private ArrayList<MCNode> children = new ArrayList<MCNode>();
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
	 * get the children
	 * 
	 * @return ArrayList of children
	 */
	public ArrayList<MCNode> getChildren() {
		return children;
	}

	/**
	 * set the children
	 * 
	 * @param children	- ArrayList of children
	 */
	public void setChildren(ArrayList<MCNode> children) {
		this.children = children;
	}
	
	/**
	 * adds a child to the children ArrayList
	 * 
	 * @param child	- node to add
	 */
	public void addChild(MCNode child) {
		getChildren().add(child);
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