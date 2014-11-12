package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameState {
    private Board board;
    private int playingPiece;
    private int opponentPiece;
    private int winner = 0;
    private Group[] playerGroups = new Group[30];

    public GameState(int playerPiece, int opponentPiece) {
        this.playingPiece = playerPiece;
        this.opponentPiece = opponentPiece;
    }

    public GameState(Board board, int playerPiece, int opponentPiece) {
        this.board = board;
        this.playingPiece = playerPiece;
        this.opponentPiece = opponentPiece;
        this.checkForWin();
        this.createGroupsForPlayer();
    }

    public GameState(GameState state) {
        this.playingPiece = state.playingPiece;
        this.opponentPiece = state.opponentPiece;
        this.winner = state.winner;
        this.board = state.board.clone();
        this.playerGroups = state.playerGroups;
    }

    public Board getBoard() {
        return board;
    }    

    /**
     * Creates the groups for the player for a start board.
     */
    private void createGroupsForPlayer() {
        int[][] contents = board.getBoardContents();
        int count = 0;
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents[i].length; j++) {
                if (contents[i][j] == playingPiece) {
                    Group group = new Group(count);
                    group.addCoordinate(i + "," + j);
                    playerGroups[count] = group;
                    count++;
                }
            }
        }
    }
    
    /**
     * Returns a list of coordinates to get the shortest path between groups.
     * Tries to find a path of empty cells.
     * 
     * @param group1
     *            : The first group
     * @param group2
     *            : The second group
     * @return A list of coordinates in a String array with ["x,y"] notation.
     */
    public String[] getShortestPathBetweenGroups(Group group1, Group group2) {
        String[] coordinateList = null;
        for (String coordinate1 : group1.getCoordinates()) {
            for (String coordinate2 : group2.getCoordinates()) {
                List<String> unvisitedNodes = this.fillListWithUnvistedNodes();
                String[] temp = findShortestPath(coordinate1.split(","), coordinate2.split(","), unvisitedNodes, new ArrayList<String>());
                if (coordinateList == null || (coordinateList != null) && temp != null
                        && temp.length < coordinateList.length) {
                    coordinateList = temp;
                }
            }
        }
        return coordinateList;
    }

    private String[] findShortestPath(String[] current, String[] end, List<String> unvisited, List<String> path) {

        String[] neighbors = board.getNeighborsByPiece(Integer.parseInt(current[0]), Integer.parseInt(current[1]), 0);

        for (int i = 0; i < neighbors.length; i++) {
            String neighbor = neighbors[i];
            if (neighbor != null && unvisited.contains(neighbor)) {
                String[] coords = neighbor.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);

                if (board.isBlankSpace(x, y)) {
                    unvisited.remove(unvisited.indexOf(neighbor));
                    List<String> newPath = new ArrayList<String>(path);
                    if (!newPath.contains(neighbor))
                        newPath.add(neighbor);

                    if (board.isNeighbour(x, y, Integer.parseInt(end[0]),Integer.parseInt(end[1])))
                        return newPath.toArray(new String[newPath.size()]);
                    
                    String[] returnValue = findShortestPath(coords, end, unvisited, newPath);

                    if (returnValue != null) 
                        return returnValue;
                }
            }
        }

        // If null there are no neighbors so this is a dead-end.
        return null;
    }

    private List<String> fillListWithUnvistedNodes() {
        List<String> coordinates = new ArrayList<String>();
        int[][] contents = board.getBoardContents();
        for (int i = 0; i < contents.length; i++) {
            for (int j = 0; j < contents[i].length; j++) {
                if (contents[i][j] == 0) {
                    coordinates.add(i + "," + j);
                }
            }
        }
        return coordinates;
    }
    
    /**
     * Returns the amount of remaining groups
     * @return The amount of remaining groups
     */
    private int getAmountOfRemainingGroups() {
        int count = 0;
        for (Group group : playerGroups) {
            count += (group != null ? 1 : 0);
        }
        return count;
    }
    
    /**
     * Gets a random group from the player groups. Returns it if it's not null, else tries to find a random group again.
     * @return A random group
     */
    public Group getRandomGroup() {
    	Group randomGroup = this.playerGroups[randInt(0,29)];
    	return (randomGroup == null) ? getRandomGroup() : randomGroup ;
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
     * Returns the indexes of real ayu groups. Real ayu groups have a length of
     * 2 or higher.
     * 
     * @return An integer array.
     */
    public Integer[] getIndexesOfAyuGroups() {
        List<Integer> groups = new ArrayList<Integer>();
        for (Group group : playerGroups) {
            if (group != null && group.getCoordinates().size() >= 2) {
                groups.add(group.getIndexInList());
            }
        }
        return groups.toArray(new Integer[groups.size()]);
    }

    /**
     * Places the move on the board and returns this in a new state.
     * 
     * @param row
     *            the y coordinates
     * @param column
     *            the x coordinates
     * @return A new GameState with the move added
     */
    public GameState makeMove(Move move) {
        GameState newState = this.clone();
        Board newBoard = this.board.clone();
        newState.setPlayerPiece(opponentPiece);
        newState.setComputerPiece(playingPiece);
        newState.setBoard(newBoard.placePiece(move));
        playerGroups = GroupManager.getInstance().mergeGroupsByMove(playerGroups, board, move);

        if (getAmountOfRemainingGroups() == 1) {
            // Player has won.
            IO.debug("Player has won.");
        }
        return newState;
    }

    

    /**
     * Returns the current opponent piece.
     * 
     * @return returns the opponent piece
     */
    private void setComputerPiece(int computerPiece) {
        this.opponentPiece = computerPiece;
    }

    /**
     * Sets the board on the state. It also sets the size of the board. And
     * calculates the minimum moves required to win. After this it checks if
     * there is a winner.
     * 
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
        checkForWin();
    }

    /**
     * Sets the current player piece.
     * 
     * @param playerPiece
     */
    private void setPlayerPiece(int playerPiece) {
        this.playingPiece = playerPiece;
    }

    public boolean isGameOver() {
        return false;
    }

    /**
     * Checks if the given piece wins.
     * 
     * @param piece
     * @return true if there is a winner and it is the given piece
     */
    public boolean hasWon(int piece) {
        return hasWinner() && winner == piece;
    }

    /**
     * Checks if the given piece loses.
     * 
     * @param piece
     * @return true if there is a loser and it is the given piece
     */
    public boolean hasLost(int piece) {
        return hasWinner() && winner != piece;
    }

    /**
     * Makes a clone of the state object.
     * 
     * @return A cloned state
     */
    public GameState clone() {
        return new GameState(this);
    }

    /**
     * Draws the board with pieces in place.
     * 
     * @return a visual representation of the board in this state
     */
    public String toString() {
        return this.board.toString();
    }

    /**
     * Prints the groa
     * 
     * @return
     */
    public String groupsToString() {
        String groups = "Groups: \n";
        for (Group group : playerGroups) {
            groups += group == null ? "Empty Group\n" : group.toString() + "\n";
        }
        return groups;
    }

    /**
     * Checks if the game has resulted in a win.
     * 
     * @return true if there is a winner
     */
    private boolean hasWinner() {
        return winner == 1 || winner == 2;
    }

    private boolean checkForWin() {
        winner = 0;
        return false;
    }

    public int getPlayingPiece() {
        // TODO Auto-generated method stub
        return this.playingPiece;
    }

    public int[][] getBoardContents() {
        return this.board.getBoardContents();
    }
}
