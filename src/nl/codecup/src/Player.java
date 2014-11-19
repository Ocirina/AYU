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
        IO.debug("TRY TO FIND RANDOM MOVE!");
        Group[] remainingGroups = this.state.getRemainingGroups();
        int groupIndex = randomInt(0, remainingGroups.length - 1);

        Group startGroup = remainingGroups[groupIndex];
        String[] shortestPath = PathFinder.getInstance().findShortestPathForGroup(state.getGroups(), state.getBoard(), startGroup);
        if(shortestPath != null) {
	        String sPath = "";
	        for(String s : shortestPath) {
	        	sPath += s + " ";
	        }
	        IO.debug("Found path: "+sPath);
            return constructMoveFromShortestPath(startGroup, shortestPath);
        }
        return getRandomMove();   
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
        int targetX = Integer.parseInt(shortestPath[0].split(",")[0]);
        int targetY = Integer.parseInt(shortestPath[0].split(",")[1]);
        
        for (int j = 0; j < coordinates.size(); j++) {
            String[] coords = coordinates.get(j).split(",");
            int tempX = Integer.parseInt(coords[0]);
            int tempY = Integer.parseInt(coords[1]);

            if (this.state.getBoard().onEdgesOfGroup(tempX, tempY)) {
                String[] origin = startGroup.findPointMostFarAway(targetX, targetY);
                if (origin[0].equals(coords[0]) && origin[1].equals(coords[1])) {
                    originX = tempX;
                    originY = tempY;
                    break;
                }
            }
        }

        return new Move(originX, originY, targetX, targetY);
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
        
        if(this.state.getRemainingGroups().length > 12) {
	        for (int row = 0; row < contentLength; row++) {
	            for (int column = 0; column < contentLength; column++) {
	                boolean gapScenario = (column < 9 && row < 9 && content[row][column] == Player.piece
	                        && content[row][column + 1] == 0 && content[row][column + 2] == Player.piece);
	
	                if (gapScenario) {
	                    int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
	                    return new Move(row, columnPieceToMove, row, column + 1);
	                }
	            }
	        }
        }

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
