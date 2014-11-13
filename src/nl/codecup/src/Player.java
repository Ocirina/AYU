package nl.codecup.src;

import java.util.List;
import java.util.Random;

public class Player {
    private GameState state;
    public static int piece;
    public static int empty = 0;
    private Referee referee;
    private final int MAXRANDOMMOVES = 10;

    public Player(GameState state, int piece, Referee referee) {
        this.state = state;
        Player.piece = piece;
        this.referee = referee;
    }

    private Referee getReferee() {
        return this.referee;
    }

    public GameState takeTurn(GameState state) {
        if (state.isGameOver()) {
            return state;
        }

        this.state = state.clone();
        Player.piece = state.getPlayingPiece();
        Move move = chooseMove();
        // if (this.getReferee().validMove(move)) {
        IO.output(move.toString());
        return state.makeMove(move);
        // }

        // Let program quit, more easy debugging.
        // IO.output("NO VALID MOVE: " + move.toString());

        // return null;
    }

    /**
     * get's a random move
     * 
     * @return Move
     */
    public Move getRandomMove() {
        Group[] remainingGroups = this.state.getRemainingGroups();
        int groupIndex = randomInt(0, remainingGroups.length - 1);

        Group startGroup = remainingGroups[groupIndex];
        String[] shortestPath = this.state.getShortestPathsToGroups(startGroup);

        return constructMoveFromShortestPath(startGroup, shortestPath);
    }

    /**
     * constructs a move based on the shortest path between two groups
     * 
     * @param startGroup
     * @param shortestPath
     * @return Move
     */
    private Move constructMoveFromShortestPath(Group startGroup, String[] shortestPath) {
        List<String> coordinates = startGroup.getCoordinates();
        int originX = 0;
        int originY = 0;
        for (int j = 0; j < coordinates.size(); j++) {
            originX = Integer.parseInt(coordinates.get(j).split(",")[0]);
            originY = Integer.parseInt(coordinates.get(j).split(",")[1]);
            if (this.state.getBoard().onEdgesOfGroup(originX, originY)) {
                break;
            }
        }
        int targetX = Integer.parseInt(shortestPath[0].split(",")[0]);
        int targetY = Integer.parseInt(shortestPath[0].split(",")[1]);

        String[] origin = startGroup.findPointMostFarAway(originX, originY);
        return new Move(Integer.parseInt(origin[0]), Integer.parseInt(origin[1]), targetX, targetY);
    }

    /**
     * get a random number between the min and max parameters
     * 
     * @param min
     * @param max
     * @return random number
     */
    private int randomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * Generate random move, in the future this will be a move which is
     * generated by monte carlo
     * 
     * @return
     */
    public Move chooseMove() {
        int[][] content = this.state.getBoardContents();
        int contentLength = content.length - 1;
        IO.debug("TRY TO FIND MOVE!");

        // Strategy 1: Make long groups in each column. (Should make a group of
        // 6 on each row).
        for (int row = 0; row < contentLength; row++) {
            for (int column = 0; column < contentLength; column++) {
                /**
                 * Situation 1 Start: | | W | | W | | | Result: | | | W | W | |
                 * |
                 * 
                 * Situation 2 Start: | | W | W | | W | | Result: | | | W | W |
                 * W | |
                 * 
                 * Start: | | | W | W | W | | W | | Result: | | | | W | W | W |
                 * W | |
                 */
                boolean gapScenario = (column < 9 && row < 9 && content[row][column] == Player.piece
                        && content[row][column + 1] == 0 && content[row][column + 2] == Player.piece);

                if (gapScenario) {
                    int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
                    return new Move(row, columnPieceToMove, row, column + 1);
                }
            }
        }

        if (true)
            return getRandomMove();

        // for (int column = 0; column < contentLength; column++) {
        // for (int row = 0; row < contentLength; row++) {
        // boolean gapScenario = (column < 9 && row < 9
        // && content[row][column] == Player.piece
        // && content[row + 1][column] == 0
        // && content[row + 2][column] == Player.piece);
        //
        // if (gapScenario) {
        //
        // int rowPieceToMove = findLastPieceInTheColumn(content, row, column,
        // 0);
        // IO.debug("VERTICAL: R:" + new Move(rowPieceToMove, column, row + 1,
        // column));
        // // return new Move(row, columnPieceToMove, row, column + 1);
        // }
        // }
        // }

        for (int row = 0; row < contentLength; row++) {
            for (int column = 0; column < contentLength; column++) {
                boolean gapScenario = (column <= 9 && content[row][column] == Player.piece && content[row][column + 1] == 0);

                if (gapScenario) {
                    int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
                    return new Move(row, columnPieceToMove, row, column + 1);
                }
            }

        }
        // Strategy 3: If there are no more possibilities of making a group on a
        // column.
        // Connect them.
        /**
         * Situation:
         * 
         * Start: | | | W | W | W | W | W | W | | | | | | | | | | | | | W | W |
         * W | W | W | W |
         * 
         * Result: | | | | W | W | W | W | W | | | | | | | | | W | | | | W | W |
         * W | W | W | W |
         * 
         */
        for (int row = 1; row < (contentLength - 1); row++) {
            if (content[row][10] == 0) {
                IO.debug("Got it!");
                int columnPieceToMove = findLastPieceInTheRow(content, row, 10, 5);
                return new Move((row - 1), columnPieceToMove, row, 10);
            }
        }

        /**
         * Situation: | | | W | W | W | | W | W | | | | | W | | B | B | W | | |
         * | | W | | W | B | W | | | | | W | | W | B | W | | | | | W | | W | B |
         * W | | | | | | | W | | | Result: | | | W | W | W | W | W | W | | | | |
         * W | | B | B | W | | | | | W | W | W | B | W | | | | | W | | W | B | W
         * | | | | | W | | | B | W | | | | | | | | | |
         */

        return getRandomMove();
    }

    /**
     * Find the last piece of a row
     * 
     * @param content
     * @param row
     * @param column
     * @param defaultColumn
     * @return
     */
    private int findLastPieceInTheRow(int[][] content, int row, int column, int defaultColumn) {
        int columnPieceToMove = defaultColumn;
        for (int i = column; i > 0; i--) {
            if (content[row][i] == Player.piece)
                columnPieceToMove = i;
            else
                break;
        }
        return columnPieceToMove;
    }

    /**
     * Method to tell caiaio which player ours is
     */
    public String toString() {
        return "R player: " + Player.piece;
    }

}
