package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathFinder {
	private static PathFinder instance;
	private Group[] playerGroups;
	private Board board;
	private final String SEPERATOR = ",";

	private PathFinder() {
	}

	public static synchronized PathFinder getInstance() {
		if (instance == null)
			instance = new PathFinder();
		return instance;
	}

	public String[] findShortestPathForGroup(Group[] groups, Board board,
			Group group) {
		this.board = board;
		this.playerGroups = groups;
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
						split(coordinate2), unvisitedNodes,
						new ArrayList<String>());
				if (coordinateList == null || (coordinateList != null)
						&& temp != null && temp.length < coordinateList.length) {
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
		/*
		 * TODO: Recheck groups, they were not correct the last test
		 * (19-11-2014). Before changing algorithm, make sure that works
		 * completely. Wrong groups means wrong paths. If coordinates are not
		 * present in the groups while our pieces are on it, we're fucked :)
		 */
		List<Group> remainingGroups = Arrays.asList(getRemainingGroups());
		List<Group> sortedList = new ArrayList<Group>();
		List<Integer> distances = new ArrayList<Integer>();
		int minimumDistance = 0;

		/*
		 * Loop through each group to get it's minimum distance to the current
		 * group as start group and add them sorted to the list.
		 */
		for (Group g : remainingGroups) {
			/*
			 * Should skip itself
			 */
			if (!group.equals(g)) {
				int distance = Integer.MAX_VALUE;

				/*
				 * This loop checks all coordinates of the group with each
				 * coordinate of the current group in the loop to retrieve the
				 * minimum distance. It checks it in a many to many way. Example
				 * with numbers List 1: [1,2,3] List 2: [4,5,6] It checks for
				 * number 1 : 1 -> 4 5 6 It checks for number 2 : 2 -> 4 5 6
				 * Same for number 3 So all possibilities are checked. In our
				 * case, the numbers are coordinates and for each coordinate it
				 * returns the minimum distance.
				 */
				for (int c = 0; c < g.getCoordinates().size(); c++) {
					int tempDistance = group.getMinimumDistance(g
							.getCoordinates().get(c));
					if (tempDistance <= distance) {
						distance = tempDistance;
					}
				}

				/*
				 * If it's the current minimum distance of all groups, it will
				 * be added in addGroupToSortedList and it will return true.
				 * Else it returns false.
				 */
				boolean added = addGroupToSortedList(sortedList, distances,
						minimumDistance, g, distance);
				if (added) {
					minimumDistance = distance;
				}

				if (!added) {
					/*
					 * Insertion sort Insert the group where the distance is
					 * lower than the current distance in the loop. Indexes of
					 * 'distances' and 'sortedList' always match
					 */
					for (int i = 0; i < distances.size(); i++) {
						if (distance <= distances.get(i).intValue()) {
							added = addDistanceAndGroupToLists(sortedList,
									distances, g, distance, i);
						}
						/*
						 * Cancel loop if its inserted
						 */
						if (added) {
							break;
						}
					}

					/*
					 * If the group is not yet added, add it to the end of the
					 * list.
					 */
					if (!added) {
						sortedList.add(g);
						distances.add(distance);
					}
				}
			} else {
				IO.debug("Same group skipped");
			}
		}

		IO.debug("Expected " + (remainingGroups.size() - 1) + " groups, got "
				+ sortedList.size());

		String[] path = findShortestPossiblePath(group, sortedList);
		return path;

	}

	private boolean addDistanceAndGroupToLists(List<Group> sortedList,
			List<Integer> distances, Group g, int distance, int i) {
		sortedList.add(i, g);
		distances.add(i, distance);
		return true;
	}

	private boolean addGroupToSortedList(List<Group> sortedList,
			List<Integer> distances, int minimumDistance, Group g, int distance) {
		if (minimumDistance == 0 || distance <= minimumDistance) {
			return addDistanceAndGroupToLists(sortedList, distances, g,
					distance, 0);
		}
		return false;
	}

	private String[] findShortestPossiblePath(Group start,
			List<Group> sortedList) {
		String[] list = null;
		IO.debug(start.toString());

		IO.debug("TRY TO FIND HERE IF THE GIVEN MOVE IS INCORRECT, MAYBE WE CAN CONNECT THE GROUP THIS SHOULD BE OVERIDDEN BY THE GIVEN MOVE");
		/*
		 * For each group, check the shortest path. If it's the shortest path,
		 * take that path. Return the shortest path at the end.
		 */
		for (Group g : sortedList) {
			String sPath = "";
			for (String c : g.getCoordinates()) {
				String[] coords = c.split(",");
				sPath += MoveConverter.convertPointToString(Integer.parseInt(coords[0]))
						+ ", "
						+ (Integer.parseInt(coords[1]) + 1)
						+ " ";

			}
			IO.debug("Group coordinates: " + sPath);

			String[] tempList = getShortestPathBetweenGroups(start, g);
			if (tempList != null) {
				sPath = "";
				for (String c : tempList) {
					String[] coords = c.split(",");
					sPath += MoveConverter.convertPointToString(Integer.parseInt(coords[0]))
							+ ", "
							+ (Integer.parseInt(coords[1]) + 1)
							+ " ";

				}
				IO.debug("Possible path: " + sPath);

			}

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
		String[] neighbors = board.getNeighborsByPiece(
				Integer.parseInt(current[0]), Integer.parseInt(current[1]), 0);
		String[] returnValue = null;
		List<String> unvisitedLocal = new ArrayList<String>(unvisited);

		/*
		 * Visit each neighbour to check a path. Neighbours are places that
		 * contain no piece, so a value of 0.
		 */
		for (int i = 0; i < neighbors.length; i++) {
			String neighbor = neighbors[i];
			if (neighbor != null && unvisitedLocal.contains(neighbor)) {
				String[] coords = split(neighbor);
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);

				/*
				 * Neighbour must be a blank space (0)
				 */
				if (board.isBlankSpace(x, y)) {
					unvisited.remove(unvisited.indexOf(neighbor));
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
					String[] tempReturn = findShortestPath(coords, end,
							unvisited, newPath);
					returnValue = assignPath(returnValue, tempReturn);
					unvisited = unvisitedLocal;
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
					coordinates.add(join(i, j));
				}
			}
		}
		return coordinates;
	}

	private String[] split(String coordinates) {
		return coordinates.split(SEPERATOR);
	}

	private String join(Object x, Object y) {
		return x + SEPERATOR + y;
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
