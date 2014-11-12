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
                String[] temp = findShortestPath(coordinate1.split(","), coordinate2.split(","), unvisitedNodes,
                        (List<String>) new ArrayList<String>());
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
     * Returns the remaining groups
     * @return The remaining groups
     */
    private Group[] getRemainingGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Group group : playerGroups) {
             if(group != null) {
            	 groups.add(group);
             }
        }
        return groups.toArray(new Group[groups.size()]);
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
     * Sets a group to null.
     * 
     * @param group
     *            : The group to set to null
     */
    public void setPlayerGroupNull(Group group) {
        if (group != null && Arrays.asList(playerGroups).contains(group)) {
            playerGroups[group.getIndexInList()] = null;
        }
    }

    /**
     * Returns a group
     * 
     * @param x
     *            : row
     * @param y
     *            : column
     * @return The group that contains the coordinate.
     */
    public Group getGroupByCoordinate(int x, int y) {
        for (Group group : playerGroups) {
            if (group != null && group.getCoordinates().contains(x + "," + y))
                return group;
        }
        return null;
    }

    public Group getGroupByCoordinate(String coordinate) {
        String[] coords = coordinate.split(",");
        return this.getGroupByCoordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
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
        this.checkGroupsForMove(move.getOriginXConverted(), move.getOriginYConverted(), move.getTargetXConverted(),
                move.getTargetYConverted());

        if (getRemainingGroups().length == 1) {
            // Player has won.
            IO.debug("Player has won.");
        }
        return newState;
    }

    private void checkGroupsForMove(int originX, int originY, int targetX, int targetY) {
        /* If piece is not in group */
        if (!board.hasNeighbor(originX, originY)) {
            mergeNonGroupedPiece(originX, originY, targetX, targetY);
        } else {
            mergeGroupedPiece(originX, originY, targetX, targetY);
        }
    }

    /**
     * Merges the group of the piece of the origin to the group(s) of the
     * targetX and targetY's neighbors.
     */
    private void mergeGroupedPiece(int originX, int originY, int targetX, int targetY) {
        Group[] groups = new Group[5];
        Group group = groups[0] = this.getGroupByCoordinate(originX, originY);
        if (group != null) {
            int position = group.getPositionOfCoordinate(originX + "," + originY);
            if (position != -1) {
                group.getCoordinates().set(position, targetX + "," + targetY);
            }
            String[] neighbors = board.getNeighbors(targetX, targetY);
            for (int i = 0; i < neighbors.length && (i + 1) < groups.length; i++) {
                addGroupNeighbor(groups, group, neighbors, i, 1);
            }
            mergeGroups(groups);
        }
    }

    private void addGroupNeighbor(Group[] groups, Group group, String[] neighbors, int index, int step) {
        if (neighbors[index] != null && !neighbors[index].equalsIgnoreCase("")) {
            Group tempGroup = this.getGroupByCoordinate(neighbors[index]);
            if (group != tempGroup) {
                groups[index + step] = tempGroup;
            }
        }
    }

    private void mergeNonGroupedPiece(int originX, int originY, int targetX, int targetY) {
        Group group = this.getGroupByCoordinate(originX, originY);
        Group[] groups = new Group[4];
        if (group != null) {
            List<String> tempList = group.getCoordinates();
            tempList.set(0, targetX + "," + targetY);
            this.playerGroups[group.getIndexInList()] = null;
            String[] neighbors = board.getNeighbors(targetX, targetY);
            for (int i = 0; i < neighbors.length; i++) {
                addGroupNeighbor(groups, group, neighbors, i, 0);
            }
            this.setPlayerGroupNull(group); // Set origin group to null
            mergeGroups(groups, tempList);
        }
    }

    /**
     * Merges the given groups
     * 
     * @param groups
     */
    private Group mergeGroups(Group[] groups) {
        Group mergedGroup = new Group(0);
        mergedGroup.setIndexInList(groups[0].getIndexInList());
        for (Group group : groups) {
            if (group != null) {
                for (String coordinate : group.getCoordinates()) {
                    mergedGroup.addCoordinate(coordinate);
                    this.setPlayerGroupNull(group);
                }
            }
        }
        playerGroups[mergedGroup.getIndexInList()] = mergedGroup;
        return mergedGroup;
    }

    /**
     * Merges the given groups and adds the given coordinates to the merged
     * group.
     * 
     * @param groups
     * @param coordinates
     */
    private void mergeGroups(Group[] groups, List<String> coordinates) {
        Group mergedGroup = this.mergeGroups(groups);
        for (String coordinate : coordinates) {
            mergedGroup.addCoordinate(coordinate);
        }

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
