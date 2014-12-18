package nl.codecup.src;

import java.util.ArrayList;
import java.util.List;

public class AStarPathFinder implements IPathFinder {
	private static final String COMMA_SEPARATOR = ",";

	private Board board;

	public String[] findShortestPath(Group[] groups, Board board,
			Group group) {
		this.board = board;
		return getShortestPathsToGroups(group, groups);
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

		/*
		 * This loop checks all coordinates of the group with each coordinate of
		 * the current group in the loop to retrieve the shortest path. It
		 * checks it in a many to many way. Example with numbers List 1: [1,2,3]
		 * List 2: [4,5,6] It checks for number 1 : 1 -> 4 5 6 It checks for
		 * number 2 : 2 -> 4 5 6 Same for number 3 So all possibilities are
		 * checked. In our case, the numbers are coordinates and for each
		 * coordinate it returns the shortest path. If it's the shortest set it
		 * and return it at the end
		 */
		for (String coordinate1 : group1.getCoordinates()) {
			for (String coordinate2 : group2.getCoordinates()) {
				List<String> unvisitedNodes = this.fillListWithUnvistedNodes();
				String[] temp = findShortestPath(split(coordinate1),
						split(coordinate2), new ArrayList<String>(
								unvisitedNodes), new ArrayList<String>());
				if (coordinateList == null && temp != null || (coordinateList != null)
						&& temp != null && temp.length < coordinateList.length) {
					coordinateList = temp;
					if(coordinateList.length == 1) 
						break;
				}
			}
			if(coordinateList != null && coordinateList.length == 1) 
				break;
		}

		return coordinateList;
	}

	/**
	 * Finds the shortest paths to groups by the given group.
	 * 
	 * @param group
	 * @return
	 */
	private String[] getShortestPathsToGroups(Group group, Group[] playerGroups) {
		List<Group> remainingGroups = getRemainingGroups(playerGroups);
		remainingGroups.remove(group);
		return findShortestPossiblePath(group, remainingGroups);
	}

	private String[] findShortestPossiblePath(Group start,
			List<Group> remainingGroups) {
		String[] list = null;
		/*
		 * For each group, check the shortest path. If it's the shortest path,
		 * take that path. Return the shortest path at the end.
		 */
		for (Group g : remainingGroups) {
			String[] tempList = getShortestPathBetweenGroups(start, g);
			if (tempList != null
					&& (list == null || list.length > tempList.length)) {
				/*
				 * New shortest path, set it.
				 */
				list = tempList;
			}
		}

		return list;
	}

	private String[] findShortestPath(String[] current, String[] end,
			List<String> unvisited, List<String> path) {
		String[] neighbors = board.getNeighborsByPiece(Integer.parseInt(current[0]), Integer.parseInt(current[1]), 0);
		String[] returnValue = null;
		List<String> unvisitedRef = new ArrayList<String>(unvisited);

		/*
		 * Visit each neighbour to check a path. Neighbours are places that
		 * contain no piece, so a value of 0.
		 */
		for (int i = 0; i < neighbors.length; i++) {
			String neighbor = neighbors[i];
			if (neighbor != null && unvisitedRef.contains(neighbor)) {
				String[] coords = split(neighbor);
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);

				/*
				 * Neighbour must be a blank space (0)
				 */
				if (board.isBlankSpace(x, y)) {
					unvisited.remove(neighbor);
					List<String> newPath = new ArrayList<String>(path);
					if (!newPath.contains(neighbor))
						newPath.add(neighbor);
					/*
					 * If the current neighbor of the 'current' coordinate that
					 * is checked is a neighbor of the end coordinate, a path is
					 * found and set if it is shorter. Move on to the next
					 * neighbor of the 'current' coordinate to check whether
					 * there is another path possible.
					 */
					if (board.isNeighbour(x, y, Integer.parseInt(end[0]),
							Integer.parseInt(end[1]))) {
						returnValue = assignPath(returnValue,
								newPath.toArray(new String[newPath.size()]));
						continue;
					}

					/**
					 * The path is not yet found, take the coordinates of the
					 * neighbor and try to check it's neighbors if it is a
					 * neighbor of the end coordinate. Set the path if it's
					 * shorter. Reset the unvisited nodes for the next neighbor,
					 * else it cannot go over the nodes the previous neighbor
					 * has visited, which should not happen.
					 */
					returnValue = assignPath(returnValue,
							findShortestPath(coords, end, new ArrayList<String>(unvisited), newPath));
					unvisited = unvisitedRef;
				}
			}
		}

		/*
		 * Return the shortest path of all neighbors.
		 */
		return returnValue;
	}

	/**
	 * @param returnValue
	 * @param tempReturn
	 * @return
	 */
	private String[] assignPath(String[] returnValue, String[] tempReturn) {
		if (returnValue == null
				|| (tempReturn != null && returnValue.length > tempReturn.length))
			returnValue = tempReturn;
		return returnValue;
	}

	/**
	 * Fills a list with nodes that whose cells are empty.
	 * 
	 * @return A list with coordinates as string
	 */
	private List<String> fillListWithUnvistedNodes() {
		List<String> coordinates = new ArrayList<String>();

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				if (board.isBlankSpace(i, j)) {
					coordinates.add(join(i, j));
				}
			}
		}
		return coordinates;
	}

	/**
	 * Splits a coordinate on the comma separator
	 * 
	 * @param coordinate
	 *            the string coordinate
	 * @return A string array with the x and y coordinate split
	 */
	private String[] split(String coordinate) {
		return coordinate.split(COMMA_SEPARATOR);
	}

	/**
	 * Joins Object X and Y with a comma separator
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 * @return A coordinate like: '5,7'
	 */
	private String join(Object x, Object y) {
		return (x + COMMA_SEPARATOR + y);
	}

	/**
	 * Returns the remaining groups
	 * 
	 * @return The remaining groups
	 */
	private List<Group> getRemainingGroups(Group[] playerGroups) {
		List<Group> groups = new ArrayList<Group>();
		for (Group group : playerGroups) {
			if (group != null) {
				groups.add(group);
			}
		}
		return groups;
	}
}