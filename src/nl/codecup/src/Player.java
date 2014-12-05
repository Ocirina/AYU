package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Player {
    private GameState state;
    private IPathFinder pathFinder = new AStarPathFinder();
    public static int piece;

    public Player(GameState state, int piece) {
        this.state = state;
        Player.piece = piece;
    }

    public GameState takeTurn(GameState state) {
        if (state.isGameOver()) {
            return state;
        }

        this.state = state.clone();
        // Player.piece = state.getPlayingPiece();
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
        List<Group> remainingGroups = Arrays.asList(this.state.getGroups());
        remainingGroups = new ArrayList<Group>(remainingGroups);
        return getRandomMove(remainingGroups);
    }

    public Move getRandomMove(List<Group> groups) {
        if (groups.size() > 0) {
            int groupIndex = randomInt(0, groups.size() - 1);

            Group startGroup = groups.get(groupIndex);
            String[] shortestPath = pathFinder.findShortestPath(state.getGroups(), state.getBoard(), startGroup);
            if (shortestPath != null) {
                return constructMoveFromShortestPath(startGroup, shortestPath[0].split(","));
            }
            groups.remove(startGroup);
            return getRandomMove(groups);
        }
        return null;
    }

    /**
     * constructs a move based on the shortest path between two groups
     * 
     * @param startGroup
     * @param shortestPath
     * @return Move
     */
    private Move constructMoveFromShortestPath(Group startGroup, String[] shortestPath) {
        int targetX = Integer.parseInt(shortestPath[0]);
        int targetY = Integer.parseInt(shortestPath[1]);

        String[] origin = startGroup.findPointMostFarAway(targetX, targetY, this.state.getBoard());
        int originX = Integer.parseInt(origin[0]);
        int originY = Integer.parseInt(origin[1]);

        if (this.state.getBoardContents()[originX][originY] != Player.piece) {
            return getRandomMove();
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
        return rand.nextInt((max - min) + 1) + min;
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
        IO.debug("TRY TO FIND MOVE FOR PLAYER: " + Player.piece);
        IO.debug("AMOUNT OF GROUPS: " + state.getGroupsLength());

        if (move == null && state.getGroupsLength() > 15) {
            move = getMoveUpInBoard(this.state);
        }
         if (move == null && state.getGroupsLength() > 10) {
        	move = getMonteCarloMove(2,4); 
        }
         if (move == null && state.getGroupsLength() > 5) {
        	move = getMonteCarloMove(5,1); 
        }
        if (move == null)
        {
        	move = getRandomMove();
        }
//        if (move == null && state.getGroupsLength() > 5 && state.getGroupsLength() <= 18) {
//            move = getRandomMove();
//        }
        
        return move;
    }

	private Move getMoveUpInBoard(GameState state) {
		int[][] content = state.getBoardContents();
        int contentLength = content.length - 1;
		for (int row = 0; row < contentLength; row++) {
		    for (int column = 0; column < contentLength; column++) {
		        if (isGapScenario(content, row, column)) {
		            int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
		            return new Move(row, columnPieceToMove, row, column + 1);
		        }
		    }
		}
		return null;
	}

    private boolean isGapScenario(int[][] content, int row, int column) {
        return column < 9 && row < 9 && content[row][column] == Player.piece && content[row][column + 1] == Board.NONE && content[row][column + 2] == Player.piece;
    }

    private Move getMonteCarloMove(int width, int height) {
        MCTree tree = new MCTree(width, height, this);
        return tree.getPlayableMove();
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

    public GameState getState() {
        return this.state;
    }

}
