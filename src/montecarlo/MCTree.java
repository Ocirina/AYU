package montecarlo;

import java.util.Random;

public class MCTree {
	
	private MCNode root;
	private final int NIMIALNUMOFCHILDREN = 1000000;
	
	public static void main(String[] args) {
		MCTree tree = new MCTree(10, 10);
	}
	
	/**
	 * creates a new tree 
	 * 
	 * @param treeWidth		- number of children for the root
	 * @param searchTime	- search time in milliseconds
	 */
	public MCTree(int treeWidth, int searchDepth) {
		MCNode root = new MCNode(false);
		setRoot(root);
		generateTree(getRoot(), treeWidth, searchDepth);
		printTree(getRoot(), "");
		MCNode bestMove = getBestMove();
		System.out.println("Best move " + bestMove.getWinpercentage() + " - " + bestMove.getNumOfChildren());
	}
	
	/**
	 * generates the monte-carlo tree
	 * 
	 * @param node			- base node, this is where the new children will be added
	 * @param numChilds 	- number of children for the node
	 * @param searchDepth	- the search depth of the tree
	 */
	private void generateTree(MCNode node, int numChilds, int searchDepth) {
		if (searchDepth > 0) {
			MCNode children[] = new MCNode[numChilds];
			for (int i = 0; i < numChilds; i++) {
				boolean isLeaf = getRandomBoolean();
				MCNode child = new MCNode(isLeaf);
				if (!isLeaf) {
					generateTree(child, randInt(0, numChilds), searchDepth - 1);
				} else {
					boolean win = getRandomBoolean();
					child.setWin(win);
					child.setWinpercentage((float) (win ? 1.0 : 0.0));
				}
				children[i] = child;
			}
			node.setChildren(children);
			updateWinpercentage(node);
			updateNumOfChildren(node);
		}
	}
	
	private void updateNumOfChildren(MCNode node) {
		int numOfChildren = 0;
		for (int i = 0; i < node.getChildren().length; i++) {
			numOfChildren += node.getChildren()[i].getNumOfChildren() + 1;
		}
		node.setNumOfChildren(numOfChildren);
	}
	
	/**
	 * updates the winpercentage for the given node based on the winpercentages of all its children
	 * 
	 * @param node
	 */
	private void updateWinpercentage(MCNode node) {
		float winpercentage = (float) 0.0;
		for (int i = 0; i < node.getChildren().length; i++) {
			winpercentage += node.getChildren()[i].getWinpercentage();
		}
		node.setWinpercentage(winpercentage / ((node.getChildren().length == 0) ? 1 : node.getChildren().length));
	}
	
	/**
	 * print the monte-carlo search tree
	 * 
	 * @param node			 - the node that will be printed
	 * @param whitespace	 - the whitespace before the win percentage (for visibility) 
	 */
	private void printTree(MCNode node, String whitespace) {
		System.out.println(whitespace + node.getWinpercentage() + " - " + node.getNumOfChildren());
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildren().length; i++) {
				printTree(node.getChildren()[i], whitespace + "    ");
			}
		}
	}
	
	/**
	 * get best move based on winpercentage and number of children
	 * 
	 * @return best move
	 */
	private MCNode getBestMove() {
		MCNode node = getRoot().getChildren()[0];
		int childrenSize = getRoot().getChildren().length;
		for (int i = 1; i < childrenSize; i++) {
			MCNode childNode = getRoot().getChildren()[i];
			if (isChildNodeBetterThanNode(node, childNode)) {
				node = childNode;
			}
		}
		return node;
	}

	/**
	 * compares current best node with the new childNode
	 * 
	 * @param node		- current best node
	 * @param childNode	- node to compare to
	 * @return boolean is better node
	 */
	private boolean isChildNodeBetterThanNode(MCNode node, MCNode childNode) {
		int minNumOfChildren = NIMIALNUMOFCHILDREN;
		return childNode.getWinpercentage() > node.getWinpercentage()
				&& minNumOfChildren > childNode.getNumOfChildren();
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
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	/**
	 * get a random boolean
	 *  
	 * @return random boolean
	 */
	private boolean getRandomBoolean() {
		Random random = new Random();
	    return random.nextBoolean();
	}
	
	/**
	 * get the root node
	 * 
	 * @return root
	 */
	public MCNode getRoot() {
		return root;
	}

	/**
	 * set the root node
	 * 
	 * @param root
	 */
	public void setRoot(MCNode root) {
		this.root = root;
	}

}