package nl.codecup.src;

import java.util.Arrays;
import java.util.List;

public class GroupManager {
	private static GroupManager instance;
	private Group[] playerGroups;
	private Board board;

	private GroupManager() {
	}

	public static synchronized GroupManager getInstance() {
		if (instance == null)
			instance = new GroupManager();
		return instance;
	}

	public Group[] mergeGroupsByMove(Group[] groups, Board board, Move move) {
		this.playerGroups = groups;
		this.board = board;
		checkGroupsForMove(move.getIndexOriginX(),
				move.getIndexOriginY(), move.getIndexTargetX(),
				move.getIndexTargetY());
		return this.playerGroups;
	}

	private void checkGroupsForMove(int originX, int originY, int targetX,
			int targetY) {
		/* If piece is not in group */
		if (!board.hasNeighbor(originX, originY)) {
			mergeNonGroupedPiece(originX, originY, targetX, targetY);
		} else {
			mergeGroupedPiece(originX, originY, targetX, targetY);
		}
	}
	

	private String joinCoordinates(int x, int y) {
		return x + "," + y;
	}

	private void addGroupNeighbor(Group[] groups, Group group,
			String[] neighbors, int index, int step) {
		if (neighbors[index] != null && !neighbors[index].equalsIgnoreCase("")) {
			Group tempGroup = this.getGroupByCoordinate(neighbors[index]);
			if (group != tempGroup) {
				groups[index + step] = tempGroup;
			}
		}
	}

	/**
	 * Merges the group of the piece of the origin to the group(s) of the
	 * targetX and targetY's neighbors.
	 */
	private void mergeGroupedPiece(int originX, int originY, int targetX,
			int targetY) {
		Group[] groups = new Group[5];
		Group group = groups[0] = this.getGroupByCoordinate(originX, originY);
		if (group != null) {
			int position = group.getPositionOfCoordinate(joinCoordinates(
					originX, originY));
			if (position != -1) {
				group.getCoordinates().set(position,
						joinCoordinates(targetX, targetY));
			}
			String[] neighbors = board.getNeighbors(targetX, targetY);
			for (int i = 0; i < neighbors.length && (i + 1) < groups.length; i++) {
				addGroupNeighbor(groups, group, neighbors, i, 1);
			}
			mergeGroups(groups);
		}
	}

	private void mergeNonGroupedPiece(int originX, int originY, int targetX,
			int targetY) {
		Group group = this.getGroupByCoordinate(originX, originY);
		Group[] groups = new Group[4];
		if (group != null) {
			String[] neighbors = board.getNeighbors(targetX, targetY);
			if(neighbors.length > 0) {
				for (int i = 0; i < neighbors.length; i++) {
					addGroupNeighbor(groups, group, neighbors, i, 0);
				}
				List<String> tempList = group.getCoordinates();
				tempList.set(0, joinCoordinates(targetX, targetY));
				this.playerGroups[group.getIndexInList()] = null;
				this.setPlayerGroupNull(group); // Set origin group to null
				mergeGroups(groups, tempList);
			}
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
	 * Sets a group to null.
	 * 
	 * @param group
	 *            : The group to set to null
	 */
	private void setPlayerGroupNull(Group group) {
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
	private Group getGroupByCoordinate(int x, int y) {
		for (Group group : playerGroups) {
			if (group != null
					&& group.getCoordinates().contains(joinCoordinates(x, y)))
				return group;
		}
		return null;
	}

	private Group getGroupByCoordinate(String coordinate) {
		String[] coords = coordinate.split(",");
		return this.getGroupByCoordinate(Integer.parseInt(coords[0]),
				Integer.parseInt(coords[1]));
	}
}
