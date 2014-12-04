package nl.codecup.src;

import java.util.Random;

public class MCTree {
	
	private MCNode root;
	private Player player;
	
	/**
	 * creates a new tree 
	 * 
	 * @param treeWidth	
	 * 					number of children for the root
	 * @param searchTime
	 * 					search time in milliseconds
	 * @param state
	 * 				The current GameState
	 */
	public MCTree(int treeWidth, int treeDepth, Player player) {
		this.player = player;
		this.root = new MCNode(player);
		
		generateTree(this.root, treeWidth, treeDepth);
	}
	
	/**
	 * generates the Monte Carlo tree
	 * 
	 * @param node			- base node, this is where the new children will be added
	 * @param numChilds 	- number of children for the node
	 * @param searchDepth	- the search depth of the tree
	 */
	private void generateTree(MCNode node, int numChilds, int searchDepth) {
		if (searchDepth > 0) {
			MCNode children[] = new MCNode[numChilds];
			for (int i = 0; i < numChilds; i++) {
				MCNode child = new MCNode(this.player);
				if (!child.isLeaf()) {
					generateTree(child, numChilds/*randInt(0, numChilds)*/, searchDepth - 1);
				}
				children[i] = child;
			}
			node.setChildren(children);
			updateMoveValues(node);
		}
	}
	
	/**
	 * updates the win percentage for the given node based on the win percentages of all its children
	 * 
	 * @param node
	 */
	private void updateMoveValues(MCNode node) {
		float winPercentage = (float) 0.0;
		int moveValue = 0;
		int childrenLength = node.getChildren().length;
		for (int i = 0; i < childrenLength; i++) {
			MCNode child = node.getChildren()[i];
			winPercentage += child.getWinPercentage();
			moveValue += child.getMoveValue();
		}
		node.setWinPercentage(winPercentage / ((childrenLength == 0) ? 1 : childrenLength));
		node.setMoveValue(moveValue);
	}
	
	/**
	 * print the monte-carlo search tree
	 * 
	 * @param node			 - the node that will be printed
	 * @param whitespace	 - the whitespace before the win percentage (for visibility) 
	 */
	private String printTree(MCNode node, String whitespace) {
		String output = whitespace + node.getWinPercentage() + " - " + " ---> " + node.getMoveValue();
		output += whitespace + 
				node.getPlayableMove().getBoardOriginX() + "," +  
				node.getPlayableMove().getBoardOriginY() + "--->" + 
				node.getPlayableMove().getBoardTargetX() + "," +
				node.getPlayableMove().getBoardTargetY();
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildren().length; i++) {
				output += printTree(node.getChildren()[i], whitespace + "    ");
			}
		}
		return output;
	}
	
	public String toString() {
		return printTree(root, "");
	}
	
	/**
	 * get best move based on win percentage and number of children
	 * 
	 * @return best move
	 */
	public Move getPlayableMove() {
		MCNode node = getRoot().getChildren()[0];
		int childrenSize = getRoot().getChildren().length;
		for (int i = 1; i < childrenSize; i++) {
			MCNode childNode = getRoot().getChildren()[i];
			if (isChildNodeBetterThanNode(node, childNode)) {
				node = childNode;
			}
		}
		return node.getPlayableMove();
	}

	/**
	 * compares current best node with the new childNode
	 * 
	 * @param node		- current best node
	 * @param childNode	- node to compare to
	 * @return boolean is better node
	 */
	private boolean isChildNodeBetterThanNode(MCNode node, MCNode childNode) {
		return childNode.getWinPercentage() > node.getWinPercentage()
				&& childNode.getMoveValue() > node.getMoveValue();
	}
	
	/**
	 * get a random number between the min and max parameters
	 * 
	 * @param min
	 * @param max
	 * @return random number
	 */
	private int randInt(int min, int max) {
	    Random rand = new Random();
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * get the root node
	 * 
	 * @return root
	 */
	public MCNode getRoot() {
		return root;
	}

}