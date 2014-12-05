package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomMoveAlgorithm implements IAlgorithm {
	private GameState state;
	private IPathFinder pathFinder;
	
	public RandomMoveAlgorithm(GameState state, IPathFinder pathFinder) {
		this.state = state;
		this.pathFinder = pathFinder;
	}

	public Move getMove() {
		return getRandomMove();
	}
	
	/**
     * get's a random move
     * 
     * @return Move
     */
    public Move getRandomMove() {
        IO.debug("TRY TO FIND RANDOM MOVE!");
        List<Group> remainingGroups = Arrays.asList(this.state.getGroups());
        remainingGroups = new ArrayList<Group>(remainingGroups);
        return getRandomMove(remainingGroups);
    }

    private Move getRandomMove(List<Group> groups) {
        if (groups.size() > 0) {
            int groupIndex = randomInt(0, groups.size() - 1);

            Group startGroup = groups.get(groupIndex);
            String[] shortestPath = pathFinder.findShortestPath(state.getGroups(), state.getBoard(), startGroup);
            if (shortestPath != null) {
                return constructMoveFromShortestPath(startGroup, shortestPath[0].split(","));
            }
            groups.remove(startGroup);
            return getRandomMove(groups);
        }
        return null;
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

        String[] origin = startGroup.findPointMostFarAway(targetX, targetY, this.state.getBoard());
        int originX = Integer.parseInt(origin[0]);
        int originY = Integer.parseInt(origin[1]);

        if (this.state.getBoardContents()[originX][originY] != Player.piece) {
            return getRandomMove();
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
