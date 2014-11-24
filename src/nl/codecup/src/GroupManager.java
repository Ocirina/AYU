package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupManager {
    private static GroupManager instance;
    private Group[] playerGroups;
    private Board board;

    private GroupManager() {
    }

    public void setPlayerGroups(Group[] groups) {
        this.playerGroups = groups;
    }

    public static synchronized GroupManager getInstance() {
        if (instance == null)
            instance = new GroupManager();
        return instance;
    }

    public Group[] recheckGroups(Board board) {
        this.board = board;
        List<Group> groupsArray = new ArrayList<Group>();
        int[][] boardArray = this.board.getBoardContents();

        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if (boardArray[row][column] == Player.piece && this.getGroupByCoordinate(row, column, groupsArray) == null) {
            		Group group = new Group(groupsArray.size());
            		group.addCoordinate(row + "," + column);
            		String[] neighBors = this.board.getNeighbors(row, column);
                    groupsArray.add(checkNeighBorsForGroup(neighBors, group));
                }
            }
        }
        this.playerGroups = groupsArray.toArray(new Group[groupsArray.size()]);
        return playerGroups;
    }
    
    private Group checkNeighBorsForGroup(String[] currentNeighbors, Group group) {
    	for(String neighBor : currentNeighbors) {
    		if(!group.getCoordinates().contains(neighBor)) {
    			String[] coords = neighBor.split(",");
    			group.addCoordinate(neighBor);
    			String[] neighBors = this.board.getNeighbors(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                group = checkNeighBorsForGroup(neighBors, group);
    		}
    	}
    	return group;
    }

    private String joinCoordinates(int x, int y) {
        return x + "," + y;
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
            if (group != null && group.getCoordinates().contains(joinCoordinates(x, y)))
                return group;
        }
        return null;
    }
    
    /**
     * Returns a group for the coordinate 
     * @param x
     * @param y
     * @param groups
     * @return
     */
    public Group getGroupByCoordinate(int x, int y, List<Group> groups) {
        for (Group group : groups) {
            if (group != null && group.getCoordinates().contains(joinCoordinates(x, y)))
                return group;
        }
        return null;
    }

    public Group getGroupByCoordinate(String coordinate) {
        String[] coords = coordinate.split(",");
        return this.getGroupByCoordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }
}
