package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathFinder {
    private static PathFinder instance;
    private Group[] playerGroups;
    private Board board;

    private PathFinder() {
    }

    public static synchronized PathFinder getInstance() {
        if (instance == null)
            instance = new PathFinder();
        return instance;
    }

    public String[] findShortestPathForGroup(Group[] groups, Board board, Group group) {
        this.board = board;
        this.playerGroups = groups;
        IO.debug("Test");
        return getShortestPathsToGroups(group);
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
    private String[] getShortestPathBetweenGroups(Group group1, Group group2) {
        String[] coordinateList = null;

        for (String coordinate1 : group1.getCoordinates()) {
            for (String coordinate2 : group2.getCoordinates()) {
                List<String> unvisitedNodes = this.fillListWithUnvistedNodes();
                String[] temp = findShortestPath(coordinate1.split(","), coordinate2.split(","), unvisitedNodes,
                        new ArrayList<String>(), null);
                if (coordinateList == null || (coordinateList != null) && temp != null
                        && temp.length < coordinateList.length) {
                    coordinateList = temp;
                }
            }
        }

        return coordinateList;
    }

    /**
     * Finds the shortest paths to groups by the given group.
     * 
     * @param group
     * @return
     */
    private String[] getShortestPathsToGroups(Group group) {
        IO.debug("TRY TO FIND SHORTEST PATH TO GROUP MOVE!");
        List<Group> remainingGroups = Arrays.asList(getRemainingGroups());
        List<Group> sortedList = new ArrayList<Group>();
        List<Integer> distances = new ArrayList<Integer>();
        int minimumDistance = 0;

        for (Group g : remainingGroups) {
            if (!group.equals(g)) {
                int distance = 0;

                for (int c = 0; c < g.getCoordinates().size(); c++) {
                    int tempDistance = group.getMinimumDistance(g.getCoordinates().get(c));
                    if (distance == 0 || tempDistance <= distance) {
                        distance = tempDistance;
                    }
                }

                boolean added = addGroupToSortedList(sortedList, distances, minimumDistance, g, distance);            
                if(added) {
                	minimumDistance = distance;
                }
                
                if (!added) {
                    for (int i = 0; i < distances.size(); i++) {
                        if (distance <= distances.get(i).intValue()) {
                            added = addDistanceAndGroupToLists(sortedList, distances, g, distance, i);
                        }
                        if(added) {
                        	break;
                        }
                    }

                    if (!added) {
                        sortedList.add(g);
                        distances.add(distance);
                    }
                }
            } else {
            	IO.debug("Same group skipped");
            }
        }

        String[] path = findShortestPossiblePath(group, sortedList);
        IO.debug("Path found with length: " + path.length);
        return path;

    }

    private boolean addDistanceAndGroupToLists(List<Group> sortedList, List<Integer> distances, Group g, int distance,
            int i) {
        sortedList.add(i, g);
        distances.add(i, distance);
        return true;
    }

    private boolean addGroupToSortedList(List<Group> sortedList, List<Integer> distances, int minimumDistance, Group g,
            int distance) {
        if (minimumDistance == 0 || distance <= minimumDistance) {
            return addDistanceAndGroupToLists(sortedList, distances, g, distance, 0);
        }
        return false;
    }

    private String[] findShortestPossiblePath(Group start, List<Group> sortedList) {
        String[] list = null;

        IO.debug("TRY TO FIND HERE IF THE GIVEN MOVE IS INCORRECT, MAYBE WE CAN CONNECT THE GROUP THIS SHOULD BE OVERIDDEN BY THE GIVEN MOVE");
        for (Group g : sortedList) {
            String[] tempList = getShortestPathBetweenGroups(start, g);
            if (tempList != null) {
            	IO.debug("Going to: "+tempList[0]);
                return tempList;
            }
        }

        return list;
    }

    private String[] findShortestPath(String[] current, String[] end, List<String> unvisited, List<String> path,
            String start) {
        String[] neighbors = board.getNeighborsByPiece(Integer.parseInt(current[0]), Integer.parseInt(current[1]), 0);
        String[] returnValue = null;
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

                    if (end != null && board.isNeighbour(x, y, Integer.parseInt(end[0]), Integer.parseInt(end[1]))) {
                        returnValue = assignPath(returnValue, newPath.toArray(new String[newPath.size()]));
                        continue;
                    }

                    String[] tempReturn = findShortestPath(coords, end, unvisited, newPath, start);

                    returnValue = assignPath(returnValue, tempReturn);
                }
            }
        }

        // If null there are no neighbors so this is a dead-end.
        return returnValue;
    }

	/**
	 * @param returnValue
	 * @param tempReturn
	 * @return
	 */
	private String[] assignPath(String[] returnValue, String[] tempReturn) {
		if (returnValue == null || (tempReturn != null &&  returnValue.length > tempReturn.length))
		    returnValue = tempReturn;
		return returnValue;
	}

    public Move findShortestGroup() {
        int smallestDistance = Integer.MAX_VALUE;
        Group closestGroup = null;

        for (Group group : playerGroups) {
            if (group.findClosestGroup() < smallestDistance) {
                smallestDistance = group.findClosestGroup();
                closestGroup = group;

                if (smallestDistance == 1) {
                    return closestGroup.findClosestGroupMove();
                }
            }
        }

        return closestGroup.findClosestGroupMove();
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
     * 
     * @return The remaining groups
     */
    private Group[] getRemainingGroups() {
        List<Group> groups = new ArrayList<Group>();
        for (Group group : playerGroups) {
            if (group != null) {
                groups.add(group);
            }
        }
        return groups.toArray(new Group[groups.size()]);
    }
}
