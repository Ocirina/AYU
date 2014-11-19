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

    public Group[] mergeGroupsByMove(Group[] groups, Board board, Move move) {
        this.playerGroups = groups;
        this.board = board;
        checkGroupsForMove(move.getIndexOriginX(), move.getIndexOriginY(), move.getIndexTargetX(), move.getIndexTargetY());
        return this.playerGroups;
    }

    public Group[] recheckGroups(Board board) {
        List<Group> groupsArray = new ArrayList<Group>();
        int[][] boardArray = this.board.getBoardContents();

        for (int row = 0; row < 11; row++) {
            for (int column = 0; column < 11; column++) {
                if (boardArray[row][column] == Player.piece) {
                    boolean added = false;

                    for (Group group : groupsArray) {
                        if (!added) {
                            for (String coordinate : group.getCoordinates()) {
                                String[] coords = coordinate.split(",");

                                if (this.board.isNeighbour(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), row, column)) {
                                    group.addCoordinate(row + "," + column);
                                    added = true;

                                    break;
                                }
                            }
                        }
                    }

                    if (!added) {
                        int index = groupsArray.size();

                        groupsArray.add(new Group(index));
                        groupsArray.get(index).addCoordinate(row + "," + column);
                    }
                }
            }
        }

        return groupsArray.toArray(new Group[groupsArray.size()]);
    }

    private void checkGroupsForMove(int originX, int originY, int targetX, int targetY) {
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

    private void addGroupNeighbor(Group[] groups, Group group, String[] neighbors, int index, int step) {
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
    private void mergeGroupedPiece(int originX, int originY, int targetX, int targetY) {
        Group[] groups = new Group[5];
        Group group = groups[0] = this.getGroupByCoordinate(originX, originY);
        if (group != null) {
            int position = group.getPositionOfCoordinate(joinCoordinates(originX, originY));
            if (position != -1) {
                group.getCoordinates().set(position, joinCoordinates(targetX, targetY));
            }
            String[] neighbors = board.getNeighbors(targetX, targetY);
            for (int i = 0; i < neighbors.length && (i + 1) < groups.length; i++) {
                addGroupNeighbor(groups, group, neighbors, i, 1);
            }
            mergeGroups(groups, 0);
        }
    }

    private void mergeNonGroupedPiece(int originX, int originY, int targetX, int targetY) {
        Group group = this.getGroupByCoordinate(originX, originY);
        Group[] groups = new Group[4];
        int firstIndex = -1;
        if (group != null) {
            String[] neighbors = board.getNeighbors(targetX, targetY);
            if (neighbors.length > 0) {
                for (int i = 0; i < neighbors.length; i++) {
                    addGroupNeighbor(groups, group, neighbors, i, 0);
                    firstIndex = groups[i] == null ? firstIndex : i;
                }
                if (firstIndex >= 0) {
                    List<String> tempList = group.getCoordinates();
                    tempList.set(0, joinCoordinates(targetX, targetY));
                    this.playerGroups[group.getIndexInList()] = null;
                    this.setPlayerGroupNull(group); // Set origin group to null
                    mergeGroups(groups, tempList, firstIndex);
                }
            }
        }
    }

    /**
     * Merges the given groups
     * 
     * @param groups
     */
    private Group mergeGroups(Group[] groups, int firstIndex) {
        Group mergedGroup = new Group(0);
        mergedGroup.setIndexInList(groups[firstIndex].getIndexInList());
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
    private void mergeGroups(Group[] groups, List<String> coordinates, int firstIndex) {
        Group mergedGroup = this.mergeGroups(groups, firstIndex);
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
    public Group getGroupByCoordinate(int x, int y) {
        for (Group group : playerGroups) {
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
