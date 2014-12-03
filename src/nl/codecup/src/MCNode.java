package nl.codecup.src;

import java.util.Arrays;

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
     * Creates a new Node with a GameState. Then checks if the game is over and
     * if it is a win or not.
     * 
     * @param state
     *            the GameState of the node
     */
    public MCNode(Player player) {
        this.player = player;

        GameState state = this.player.getState();
        this.state = state.makeMove(this.player.getRandomMove());
        updateMoveValue();

        this.isLeaf = this.state.isGameOver();
        if (this.isLeaf) {
            this.isWin = this.state.hasWon(this.state.getPlayingPiece());
        }
        this.winpercentage = (float) (this.isWin ? 1.0 : 0.0);
    }

    private void updateMoveValue() {
        Move playedMove = this.state.getPlayableMove();
        Board board = this.state.getBoard();
        this.state.setGroups(GroupManager.recheckGroups(board, Player.piece));
        Group[] oppponentGroups = GroupManager.recheckGroups(board, this.state.getOpponentPiece());

        int x = playedMove.getIndexTargetX();
        int y = playedMove.getIndexTargetY();
        Group group = GroupManager.getGroupByCoordinate(x, y, Arrays.asList(this.state.getGroups()));
        String neighbors[] = board.getNeighborsByPiece(x, y, this.state.getOpponentPiece());

        this.moveValue = group.getCoordinates().size() * 2;

        for (int i = 0; i < neighbors.length; i++) {
            this.moveValue += GroupManager.getGroupByCoordinate(neighbors[i], Arrays.asList(oppponentGroups)).getSize();
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
    
    public Move getPlayableMove() {
    	return state.getPlayableMove();
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
     * @param winpercentage
     *            - float the new winpercentage
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
     * get isWin
     * 
     * @return boolean isWin
     */
    public boolean isWin() {
        return isWin;
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

    /**
     * get Move value
     * 
     * @return int move value
     */
    public int getMoveValue() {
        return moveValue;
    }

}
