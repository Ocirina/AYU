package montecarlo;

import java.util.Random;

public class MCTree {
	
	private MCNode root;
	
	public static void main(String[] args) {
		MCTree tree = new MCTree(10, 2);
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
			for (int i = 0; i < numChilds; i++) {
				boolean isLeaf = getRandomBoolean();
				MCNode child = new MCNode(isLeaf);
				if (!isLeaf) {
					generateTree(child, randInt(0, numChilds), searchDepth - 1);
				} else {
					boolean win = getRandomBoolean();
					child.setWin(win);
					child.setWinpercentage((win) ? (float) 1.0 : (float) 0.0);
				}
				node.addChild(child);
			}
			updateWinpercentage(node);
		}
	}
	
	/**
	 * updates the winpercentage for the given node based on the winpercentages of all its children
	 * 
	 * @param node
	 */
	private void updateWinpercentage(MCNode node) {
		float winpercentage = (float) 0.0;
		for (int i = 0; i < node.getChildren().size(); i++) {
			winpercentage += node.getChildren().get(i).getWinpercentage();
		}
		node.setWinpercentage(winpercentage / ((node.getChildren().isEmpty()) ? 1 : node.getChildren().size()));
	}
	
	/**
	 * print the monte-carlo search tree
	 * 
	 * @param node			 - the node that will be printed
	 * @param whitespace	 - the whitespace before the win percentage (for visibility) 
	 */
	private void printTree(MCNode node, String whitespace) {
		System.out.println(whitespace + node.getWinpercentage());
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildren().size(); i++) {
				printTree(node.getChildren().get(i), whitespace + "    ");
			}
		}
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