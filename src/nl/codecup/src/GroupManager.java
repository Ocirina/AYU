package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupManager {

    private static final String GROUP_SEPERATOR = ",";

	public static Group[] getGroupsByPiece(Board board, int piece) {
        List<Group> groupsArray = new ArrayList<Group>();
        int[][] boardArray = board.getBoardContents();

        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if (boardArray[row][column] == piece && getGroupByCoordinate(row, column, groupsArray) == null) {
            		Group group = new Group(groupsArray.size());
            		group.addCoordinate(row + GROUP_SEPERATOR + column);
            		String[] neighBors = board.getNeighbors(row, column);
                    groupsArray.add(checkNeighBorsForGroup(neighBors, group, board));
                }
            }
        }
        return groupsArray.toArray(new Group[groupsArray.size()]);
    }
    
    private static Group checkNeighBorsForGroup(String[] currentNeighbors, Group group, Board board) {
    	for(String neighBor : currentNeighbors) {
    		if(!group.getCoordinates().contains(neighBor)) {
    			String[] coords = neighBor.split(GROUP_SEPERATOR);
    			group.addCoordinate(neighBor);
    			String[] neighBors = board.getNeighbors(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                group = checkNeighBorsForGroup(neighBors, group, board);
    		}
    	}
    	return group;
    }

    private static String joinCoordinates(int x, int y) {
        return x + GROUP_SEPERATOR + y;
    }
    
    /**
     * Returns a group for the coordinate 
     * @param x
     * @param y
     * @param groups
     * @return
     */
    public static Group getGroupByCoordinate(int x, int y, List<Group> groups) {
        for (Group group : groups) {
            if (group != null && group.containsCoordinates(joinCoordinates(x, y)))
                return group;
        }
        return null;
    }
    
    public static Group getGroupByCoordinate(String coordinates, List<Group> groups) {
    	String[] coords = coordinates.split(GROUP_SEPERATOR);
    	return getGroupByCoordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), groups);
    }
}
