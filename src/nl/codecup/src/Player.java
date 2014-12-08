package nl.codecup.src;

import java.sql.Timestamp;

public class Player {
    private GameState state;
    private IPathFinder pathFinder = new AStarPathFinder();
    public static int piece;
    private static int FIVESECONDS = 5000;

    public Player(GameState state, int piece) {
        this.state = state;
        Player.piece = piece;
    }

    public GameState takeTurn(GameState state) {
        if (state.isGameOver()) {
            return state;
        }

        this.state = state.clone();
        Move move = chooseMove();
        IO.output(move.toString());
        return state.makeMove(move);
    }

    /**
     * Generate random move, in the future this will be a move which is
     * generated by monte carlo
     * 
     * @return
     */
    public Move chooseMove() {
        Move move = null;

        this.state.recheckGroups();
        // IO.debug("TRY TO FIND MOVE FOR PLAYER: " + Player.piece);
        // IO.debug("AMOUNT OF GROUPS: " + state.getGroupsLength());
        IAlgorithm random = new RandomMoveAlgorithm(state.getGroups(), state.getBoard(), pathFinder);

        if (state.getTimePastInMiliseconds() > FIVESECONDS) {
            if (move == null && state.getGroupsLength() > 15) {
                IO.debug("Move direct start: " + new Timestamp(new java.util.Date().getTime()));
                move = new DirectionAlgorithm(state).getMove();
                IO.debug("Move direct end: " + new Timestamp(new java.util.Date().getTime()));
            }

            if (move == null && state.getGroupsLength() > 10) {
                IO.debug("Move monte 10 start: " + new Timestamp(new java.util.Date().getTime()));
                move = getMonteCarloMove(2, 4, random);
                IO.debug("Move monte 10 end: " + new Timestamp(new java.util.Date().getTime()));
            }

            if (move == null && state.getGroupsLength() > 5) {
                IO.debug("Move monte 5 start: " + new Timestamp(new java.util.Date().getTime()));
                move = getMonteCarloMove(5, 1, random);
                IO.debug("Move monte 5 end: " + new Timestamp(new java.util.Date().getTime()));
            }
        }

        if (move == null) {
            IO.debug("Move random start: " + new Timestamp(new java.util.Date().getTime()));
            move = random.getMove();
            IO.debug("Move random end: " + new Timestamp(new java.util.Date().getTime()));
        }

        return move;
    }

    private Move getMonteCarloMove(int width, int height, IAlgorithm algorithm) {
        MCTree tree = new MCTree(width, height, state, algorithm);
        return tree.getPlayableMove();
    }

    /**
     * Method to tell caiaio which player ours is
     */
    public String toString() {
        return "R player: " + Player.piece;
    }

    public GameState getState() {
        return this.state;
    }

}
