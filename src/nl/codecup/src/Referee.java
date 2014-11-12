package nl.codecup.src;

public class Referee {

    private Manager manager;

    public Referee(Manager manager) {
        this.manager = manager;
    }

    /**
     * This will return if an move is valid
     * 
     * @param move
     * @return
     */
    public boolean validMove(Move move) {
        //IO.debug("IS VALID?: " + move);
        return (move == null ? false : this.mayBeMoved(move, this.manager.getGameState().getBoard()));
    }

    /**
     * Check if a certain move is allowed
     * 
     * @param move
     * @param board
     * @param inGroup
     * @return
     */
    private boolean mayBePlaced(Move move, Board board, boolean inGroup) {
        int originX = move.getIndexOriginX();
        int originY = move.getIndexOriginY();

        int targetX = move.getIndexTargetX();
        int targetY = move.getIndexTargetY();

        if (board.isBlankSpace(targetX, targetY)) {
            if (!inGroup && board.hasNeighbor(targetX, targetY)) {
                return true;
            } else if (inGroup && board.isMoveNeighbourOfSameGroup(originX, originY, targetX, targetY)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if a piece may be moved
     * 
     * @param move
     * @param board
     * @return
     */
    private boolean mayBeMoved(Move move, Board board) {
        if (move != null) {
            int x = move.getIndexOriginX();
            int y = move.getIndexOriginY();

            if (board.getBoardContents()[x][y] == Player.piece) {
                if (board.hasNeighbor(x, y) && board.onEdgesOfGroup(x, y)) {
                    return this.mayBePlaced(move, board, true);
                } else if (!board.hasNeighbor(x, y)) {
                    return this.mayBePlaced(move, board, false);
                }
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
        // TODO WRITE STUFF TO LOG
        return null;
    }

}
