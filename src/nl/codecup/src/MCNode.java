package nl.codecup.src;

import java.util.Arrays;

public class MCNode {

    private MCNode[] children = new MCNode[0];
    private float winPercentage = (float) 0.0;
    private int moveValue = 0;
    private boolean isLeaf = false;
    private boolean isWin = false;
    private GameState state;

    /**
     * Creates a new Node with a GameState. Then checks if the game is over and
     * if it is a win or not.
     * 
     * @param state
     *            the GameState of the node
     */
    public MCNode(GameState state, IAlgorithm algorithm) {
        this.state = state.makeMove(algorithm.getMove());
        updateMoveValue();

        this.isWin = this.state.hasWon(this.state.getPlayingPiece());
        if (this.isWin) {
            this.isLeaf = true;
        }
        this.winPercentage = (float) (this.isWin ? 1.0 : 0.0);
    }

    /**
     * updates the move value
     */
    private void updateMoveValue() {
        Move playableMove = this.state.getPlayableMove();
        Board board = this.state.getBoard();
        Group originGroup = GroupManager.getGroupByCoordinate(playableMove.getIndexOriginX(), playableMove.getIndexOriginY(), Arrays.asList(this.state.getGroups()));
        this.state.setGroups(GroupManager.getGroupsByPiece(board, state.getPlayingPiece()));
        Group[] oppponentGroups = GroupManager.getGroupsByPiece(board, this.state.getOpponentPiece());

        int x = playableMove.getIndexTargetX();
        int y = playableMove.getIndexTargetY();
        Group group = GroupManager.getGroupByCoordinate(x, y, Arrays.asList(this.state.getGroups()));
        String neighbors[] = board.getNeighborsByPiece(x, y, this.state.getOpponentPiece());
        if (originGroup.getSize() == 1) {
            this.moveValue = 25;
        }

        if (group != null) {
            this.moveValue += group.getCoordinates().size() * 2;
        }

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

    /**
     * get the playable from the state
     * 
     * @return Move
     */
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
     * get the win percentage
     * 
     * @return float win percentage
     */
    public float getWinPercentage() {
        return winPercentage;
    }

    /**
     * set the win percentage
     * 
     * @param winPercentage
     *            - float the new win percentage
     */
    public void setWinPercentage(float winPercentage) {
        this.winPercentage = winPercentage;
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
     * get Move value
     * 
     * @return int move value
     */
    public int getMoveValue() {
        return moveValue;
    }

    /**
     * set move value
     * 
     * @param moveValue
     */
    public void setMoveValue(int moveValue) {
        this.moveValue = moveValue;
    }

}
