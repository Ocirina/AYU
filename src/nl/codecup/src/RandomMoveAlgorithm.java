package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomMoveAlgorithm implements IAlgorithm {
    private IPathFinder pathFinder;
    private Group[] playerGroups;
    private Board board;

    public RandomMoveAlgorithm(Group[] playerGroups, Board board, IPathFinder pathFinder) {
        this.board = board;
        this.playerGroups = playerGroups;
        this.pathFinder = pathFinder;
    }

    public Move getMove() {
        List<Group> remainingGroups = Arrays.asList(this.playerGroups);
        remainingGroups = new ArrayList<Group>(remainingGroups);
        return getRandomMove(remainingGroups);
    }

    private Move getRandomMove(List<Group> groups) {
        int groupIndex = randomInt(0, groups.size() - 1);

        Group startGroup = groups.get(groupIndex);
        String shortestPathCoordinate = pathFinder.findShortestPath(this.playerGroups, this.board, startGroup);
        if (shortestPathCoordinate != null) {
            return constructMoveFromShortestPath(startGroup, shortestPathCoordinate.split(","));
        }
        groups.remove(startGroup);
        return getRandomMove(groups);
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

        String[] origin = startGroup.findPointMostFarAway(targetX, targetY, this.board);
        if (origin == null || origin[0] == null || origin[1] == null) {
            return null;
        }

        int originX = Integer.parseInt(origin[0]);
        int originY = Integer.parseInt(origin[1]);

        if (this.board.getBoardContents()[originX][originY] != Player.piece) {
            return getMove();
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

}
