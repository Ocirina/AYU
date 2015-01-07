import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class AStarPathFinder implements IPathFinder {
	private static final String COMMA_SEPARATOR = ",";
	private Board board;
	public String[] findShortestPath(Group[] groups, Board board,
			Group group) {
		this.board = board;
		return getShortestPathsToGroups(group, groups);
	}
	private String[] getShortestPathBetweenGroups(Group group1, Group group2) {
		String[] coordinateList = null;
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
	private String[] getShortestPathsToGroups(Group group, Group[] playerGroups) {
		List<Group> remainingGroups = getRemainingGroups(playerGroups);
		remainingGroups.remove(group);
		return findShortestPossiblePath(group, remainingGroups);
	}
	private String[] findShortestPossiblePath(Group start,
			List<Group> remainingGroups) {
		String[] list = null;
		for (Group g : remainingGroups) {
			String[] tempList = getShortestPathBetweenGroups(start, g);
			if (tempList != null
					&& (list == null || list.length > tempList.length)) {
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
		for (int i = 0; i < neighbors.length; i++) {
			String neighbor = neighbors[i];
			if (neighbor != null && unvisitedRef.contains(neighbor)) {
				String[] coords = split(neighbor);
				int x = Integer.parseInt(coords[0]);
				int y = Integer.parseInt(coords[1]);
				if (board.isBlankSpace(x, y)) {
					unvisited.remove(neighbor);
					List<String> newPath = new ArrayList<String>(path);
					if (!newPath.contains(neighbor))
						newPath.add(neighbor);
					if (board.isNeighbour(x, y, Integer.parseInt(end[0]),
							Integer.parseInt(end[1]))) {
						returnValue = assignPath(returnValue,
								newPath.toArray(new String[newPath.size()]));
						continue;
					}
					returnValue = assignPath(returnValue,
							findShortestPath(coords, end, new ArrayList<String>(unvisited), newPath));
					unvisited = unvisitedRef;
				}
			}
		}
		return returnValue;
	}
	private String[] assignPath(String[] returnValue, String[] tempReturn) {
		if (returnValue == null
				|| (tempReturn != null && returnValue.length > tempReturn.length))
			returnValue = tempReturn;
		return returnValue;
	}
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
	private String[] split(String coordinate) {
		return coordinate.split(COMMA_SEPARATOR);
	}
	private String join(Object x, Object y) {
		return (x + COMMA_SEPARATOR + y);
	}
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
public class Board {
	public static final int SIZE = 11;
	public static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	private static final int NEIGHBOR_DISTANCE = 1;
	private static final int ON_EDGES = 1;
	private int[][] boardGrid = new int[][] {
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE } };
	public Board() {
	}
	public Board(int[][] grid) {
		this.boardGrid = cloneBoard(grid);
	}
	public boolean hasNeighbor(int x, int y) {
		return ((y + NEIGHBOR_DISTANCE < SIZE && this.boardGrid[x][y
				+ NEIGHBOR_DISTANCE] == Player.piece)
				|| (x + NEIGHBOR_DISTANCE < SIZE && this.boardGrid[x
						+ NEIGHBOR_DISTANCE][y] == Player.piece)
				|| (y - NEIGHBOR_DISTANCE >= 0 && this.boardGrid[x][y
						- NEIGHBOR_DISTANCE] == Player.piece) || (x
				- NEIGHBOR_DISTANCE >= 0 && this.boardGrid[x
				- NEIGHBOR_DISTANCE][y] == Player.piece));
	}
	public boolean onEdgesOfGroup(int x, int y) {
		int neighBours = 0;
		if (y + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x][y + NEIGHBOR_DISTANCE] == Player.piece) {
			neighBours++;
		}
		if (x + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x + NEIGHBOR_DISTANCE][y] == Player.piece) {
			neighBours++;
		}
		if (y - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x][y - NEIGHBOR_DISTANCE] == Player.piece) {
			neighBours++;
		}
		if (x - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x - NEIGHBOR_DISTANCE][y] == Player.piece) {
			neighBours++;
		}
		return neighBours == ON_EDGES;
	}
	public boolean isMoveNeighbourOfSameGroup(int originX, int originY,
			int targetX, int targetY) {
		return findPath(originX, originY, originX, originY, targetX, targetY);
	}
	private boolean findPath(int originX, int originY, int nextX, int nextY,
			int targetX, int targetY) {
		boolean found = false;
		if (!(originX == nextX && originY == nextY)) {
			found = isNeighbour(nextX, nextY, targetX, targetY);
		}
		if (!found
				&& nextY + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[nextX][nextY + NEIGHBOR_DISTANCE] == Player.piece
				&& ((nextY + NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX, nextY + NEIGHBOR_DISTANCE,
					targetX, targetY);
		}
		if (!found
				&& nextX + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[nextX + NEIGHBOR_DISTANCE][nextY] == Player.piece
				&& ((nextX + NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX + NEIGHBOR_DISTANCE, nextY,
					targetX, targetY);
		}
		if (!found
				&& nextY - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[nextX][nextY - NEIGHBOR_DISTANCE] == Player.piece
				&& ((nextY - NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX, nextY - NEIGHBOR_DISTANCE,
					targetX, targetY);
		}
		if (!found
				&& nextX - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[nextX - NEIGHBOR_DISTANCE][nextY] == Player.piece
				&& ((nextX - NEIGHBOR_DISTANCE) != originY)) {
			found = findPath(nextX, nextY, nextX - NEIGHBOR_DISTANCE, nextY,
					targetX, targetY);
		}
		return found;
	}
	public String[] getNeighbors(int x, int y) {
		return this.getNeighborsByPiece(x, y, Player.piece);
	}
	public String[] getNeighborsByPiece(int x, int y, int piece) {
		List<String> neighbors = new ArrayList<String>();
		if (y + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x][y + NEIGHBOR_DISTANCE] == piece) {
			neighbors.add(x + "," + (y + NEIGHBOR_DISTANCE));
		}
		if (x + NEIGHBOR_DISTANCE < SIZE
				&& this.boardGrid[x + NEIGHBOR_DISTANCE][y] == piece) {
			neighbors.add((x + NEIGHBOR_DISTANCE) + "," + y);
		}
		if (y - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x][y - NEIGHBOR_DISTANCE] == piece) {
			neighbors.add(x + "," + (y - NEIGHBOR_DISTANCE));
		}
		if (x - NEIGHBOR_DISTANCE >= 0
				&& this.boardGrid[x - NEIGHBOR_DISTANCE][y] == piece) {
			neighbors.add((x - NEIGHBOR_DISTANCE) + "," + y);
		}
		return neighbors.toArray(new String[neighbors.size()]);
	}
	public boolean isNeighbour(int originX, int originY, int targetX,
			int targetY) {
		return ((originX == targetX && ((originY - NEIGHBOR_DISTANCE) == targetY))
				|| (originX == targetX && ((originY + NEIGHBOR_DISTANCE) == targetY))
				|| (originY == targetY && ((originX - NEIGHBOR_DISTANCE) == targetX)) || (originY == targetY && ((originX + NEIGHBOR_DISTANCE) == targetX)));
	}
	public boolean isBlankSpace(int row, int column) {
		return (this.boardGrid[row][column] == NONE);
	}
	public Board placePiece(Move move) {
		this.movePiece(move);
		return this;
	}
	public void movePiece(Move move) {
		switchPosition(move.getIndexOriginX(), move.getIndexOriginY(),
				move.getIndexTargetX(), move.getIndexTargetY());
	}
	private void switchPosition(int originX, int originY, int targetX,
			int targetY) {
		int tempPiece = this.boardGrid[originX][originY];
		this.boardGrid[originX][originY] = this.boardGrid[targetX][targetY];
		this.boardGrid[targetX][targetY] = tempPiece;
//		IO.debug(this.toString());
	}
	public int amountOfGroups(Player player) {
		return 0;
	}
	public String toString() {
		return this.toString(false);
	}
	public String toString(boolean normal) {
		String rowSeperator = "    +---+---+---+---+---+---+---+---+---+---+---+\n";
		String returnString = "      A   B   C   D   E   F   G   H   I   J   K\n"
				+ rowSeperator;
		if (!normal) {
			for (int column = (SIZE - 1); column >= 0; column--) {
				returnString += String.format("%02d", (column + 1)) + " ";
				for (int row = 0; row < SIZE; row++)
					returnString += " | "
							+ this.convertPiece(this.boardGrid[row][column]);
				returnString += " |\n" + rowSeperator;
			}
		} else {
			for (int column = 0; column < SIZE; column++) {
				returnString += String.format("%02d", (column + 1)) + " ";
				for (int row = 0; row < SIZE; row++)
					returnString += " | "
							+ this.convertPiece(this.boardGrid[row][column]);
				returnString += " |\n" + rowSeperator;
			}
		}
		return returnString;
	}
	public String convertPiece(int piece) {
		if (piece != NONE) {
			return piece == WHITE ? "X" : "O";
		}
		return " ";
	}
	public Board clone() {
		return new Board(this.cloneBoard(this.boardGrid));
	}
	private int[][] cloneBoard(int[][] board) {
		int[][] tempBoard = new int[SIZE][SIZE];
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				tempBoard[row][column] = board[row][column];
			}
		}
		return tempBoard;
	}
	public String toDebugBoard() {
		String[] pieces = { "NONE", "WHITE", "BLACK" };
		String output = "private int[][] boardGrid = new int[][] {";
		for (int row = 0; row < SIZE; row++) {
			output += "{ ";
			for (int column = 0; column < SIZE; column++) {
				int piece = boardGrid[row][column];
				output += " " + pieces[piece] + " ";
				output += (column < (SIZE - 1) ? "," : "");
			}
			output += " }";
			output += (row < (SIZE - 1) ? ", " : "");
		}
		output += " };";
		return output;
	}
	public int[][] getBoardContents() {
		return this.boardGrid;
	}
	public boolean isCoordOnEdgeOfBoard(int coordX, int coordY) {
		return onBounds(coordX) || onBounds(coordY);
	}
	private boolean onBounds(int coord) {
		return (coord == 0) || (coord == (SIZE - 1));
	}
}
public class DirectionAlgorithm implements IAlgorithm {
	private GameState state;
	private int oneStepColumn = 1;
	private int twoStepColumn = 2;
	private int oneStepRow = 0;
	private int twoStepRow = 0;
	private int maxBoundary = 9;
	public DirectionAlgorithm(GameState state) {
		this.state = state;
	}
	public Move getMove() {
		int[][] content = state.getBoardContents();
		int contentLength = content.length - 1;
		return getBottomUpMove(content, contentLength);
	}
	private boolean isGapScenarioInColumnForward(int[][] content, int row, int column) {
		if (column >= maxBoundary || row >= maxBoundary) { return false; }		
		return content[row][column] == Player.piece && 
			   content[row + oneStepRow][column + oneStepColumn] == Board.NONE &&
			   content[row + twoStepRow][column + twoStepColumn] == Player.piece;
	}
	private Move getBottomUpMove(int[][] content, int contentLength) {
		for (int row = 0; row < contentLength; row++) {
			for (int column = 0; column < contentLength; column++) {
				if (isGapScenarioInColumnForward(content, row, column)) {
					int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
					return new Move(row, columnPieceToMove, row, column + 1);
				}
			}
		}
		return null;
	}
	private int findLastPieceInTheRow(int[][] content, int row, int column, int defaultColumn) {
		int columnPieceToMove = defaultColumn;
		for (int i = column; i > 0; i--) {
			if (content[row][i] == Player.piece)
				columnPieceToMove = i;
			else
				break;
		}
		return columnPieceToMove;
	}
}
public class GameState {
    private Board board;
    private int playingPiece;
    private int opponentPiece;
    private int winner = 0;
    private Group[] playerGroups = new Group[30];
    private Move playableMove;
	private long timePastInMiliseconds;
    public GameState(int playerPiece, int opponentPiece) {
        this.playingPiece = playerPiece;
        this.opponentPiece = opponentPiece;
    }
    public GameState(Board board, int playerPiece, int opponentPiece) {
        this.board = board;
        this.playingPiece = playerPiece;
        this.opponentPiece = opponentPiece;
        this.checkForWin();
        this.recheckGroups();
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
    public void recheckGroups() {
        this.playerGroups = GroupManager.getGroupsByPiece(this.getBoard(), Player.piece);
//        IO.debug(groupsToString());
    }
    public int getGroupsLength() {
        return playerGroups.length;
    }
    public GameState makeMove(Move move) {
        GameState newState = this.clone();
        newState.setPlayableMove(move);
        Board newBoard = this.board.clone();
        newState.setPlayerPiece(opponentPiece);
        newState.setComputerPiece(playingPiece);
        newState.setBoard(newBoard.placePiece(move));
        if (getGroupsLength() == 1) {
            // Player has won.
            //IO.debug("Player has won.");
        }
        return newState;
    }
    private void setComputerPiece(int computerPiece) {
        this.opponentPiece = computerPiece;
    }
    public void setBoard(Board board) {
        this.board = board;
        checkForWin();
    }
    private void setPlayerPiece(int playerPiece) {
        this.playingPiece = playerPiece;
    }
    public boolean isGameOver() {
        return hasWinner();
    }
    public boolean hasWon(int piece) {
        return hasWinner() && winner == piece;
    }
    public boolean hasLost(int piece) {
        return hasWinner() && winner != piece;
    }
    public GameState clone() {
        return new GameState(this);
    }
    public String toString() {
        return this.board.toString();
    }
    public String groupsToString() {
        String groups = "Groups: \n";
        for (Group group : playerGroups) {
            groups += group == null ? "Empty Group\n" : group.toString() + "\n";
        }
        return groups;
    }
    private boolean hasWinner() {
        return winner == 1 || winner == 2;
    }
    private boolean checkForWin() {
        winner = 0;
        return false;
    }
    public int getPlayingPiece() {
        return this.playingPiece;
    }
    public int getOpponentPiece() {
    	return this.opponentPiece;
    }
    public int[][] getBoardContents() {
        return this.board.getBoardContents();
    }
    public Group[] getGroups() {
        return playerGroups;
    }
    public void setGroups(Group[] playerGroups) {
    	this.playerGroups = playerGroups;
    }
	public Move getPlayableMove() {
		return playableMove;
	}
	public void setPlayableMove(Move playableMove) {
		this.playableMove = playableMove;
	}
	public void setTimePast(long timePastInMiliseconds) {
		this.timePastInMiliseconds = timePastInMiliseconds;
	}
	public long getTimePastInMiliseconds() {
		return this.timePastInMiliseconds;
	}
}
public class Group {
    private List<String> coordinates = new ArrayList<String>();
    private int indexInList = 0;
    public Group(int index) {
        this.indexInList = index;
    }
    public Group(List<String> coordinates, int index) {
        this.coordinates = coordinates;
        this.indexInList = index;
    }
    public List<String> getCoordinates() {
        return coordinates;
    }
	public boolean containsCoordinates(String coordinates) {
    	return getCoordinates().contains(coordinates);
    }
	public int getSize() {
		return coordinates.size();
	}
    public void setCoordinates(List<String> coordinates) {
        this.coordinates = coordinates;
    }
    public int getPositionOfCoordinate(String coordinate) {
        for (int i = 0; i < coordinates.size(); i++) {
            if (coordinates.get(i).equalsIgnoreCase(coordinate)) {
                return i;
            }
        }
        return -1;
    }
    public int getIndexInList() {
        return indexInList;
    }
    public void setIndexInList(int indexInList) {
        this.indexInList = indexInList;
    }
    public boolean addCoordinate(String coordinate) {
        String pattern = "[0-9]{1,2}\\,[0-9]{1,2}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(coordinate.toLowerCase());
        if (matcher.matches()) {
            coordinates.add(coordinate);
            return true;
        }
        return false;
    }
    public int getMinimumDistance(String coordinate) {
        String[] coords = coordinate.split(",");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        return calculateMinimumDistance(x, y);
    }
    private int calculateMinimumDistance(int x, int y) {
        int minimumDistance = 0;
        for (int i = 0; i < this.coordinates.size(); i++) {
            String[] coords = this.coordinates.get(i).split(",");
            int tempDistance = Math.abs(Integer.parseInt(coords[0]) - x);
            tempDistance += Math.abs(Integer.parseInt(coords[1]) - y);
            minimumDistance = (minimumDistance > tempDistance || minimumDistance == 0) ? tempDistance : minimumDistance;
        }
        return minimumDistance;
    }
    public String[] findPointMostFarAway(int x, int y, Board board) {
        String[] coordsReturned = null, coordinateIfBlock = null;
        int maximumDistance = 0, blockDistance = 0, amountOfEdges = 0;
        if(this.coordinates.size() == 1) 
        	return this.coordinates.get(0).split(",");
        for (int i = 0; i < this.coordinates.size(); i++) {
            String[] coords = this.coordinates.get(i).split(",");
            int coordX = Integer.parseInt(coords[0]);
            int coordY = Integer.parseInt(coords[1]);
        	int tempDistance = Math.abs(coordX - x) + Math.abs(coordY - y);
            if(board.onEdgesOfGroup(coordX, coordY)) {
	            amountOfEdges++;
	            if (tempDistance > maximumDistance || maximumDistance == 0) {
	                maximumDistance = tempDistance;
	                coordsReturned = coords;
	            }
            } else if (tempDistance > maximumDistance || maximumDistance == 0) {
            	blockDistance = tempDistance;
                coordinateIfBlock = coords;
            }
        }
        if(amountOfEdges <= 1 && blockDistance > maximumDistance) {
        	return coordinateIfBlock;
        } else {
            return coordsReturned;
        }
    }
    public String toString() {
        String output = "Group :" + this.getIndexInList() + "   ";
        for (int i = 0; i < this.coordinates.size(); i++) {
            output += " " + this.getCoordinates().get(i);
        }
        return output;
    }
}
public class GroupManager {
    private static final String GROUP_SEPERATOR = ",";
	public static Group[] getGroupsByPiece(Board board, int piece) {
        List<Group> groupsArray = new ArrayList<Group>();
        int[][] boardArray = board.getBoardContents();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                if (boardArray[row][column] == piece && getGroupByCoordinate(row, column, groupsArray) == null) {
            		Group group = new Group(groupsArray.size());
            		group.addCoordinate(joinCoordinates(row, column));
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
public interface IAlgorithm {
	public Move getMove();
}
public abstract class IO {
    private static boolean firstMessage = true;
    public static String input() {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            String readLine = reader.readLine();
            if (firstMessage) {
                IO.debug("R JavaPlayer");
                firstMessage = false;
            }
            IO.debug("Read line: " + readLine);
            return readLine;
        }
        catch (IOException e) {
            IO.debug("IOException: " + e.getMessage());
        }
        IO.debug("Returning null");
        return null;
    }
    public static void output(String output) {
        IO.debug("WAS SENT TO CAIAIO: " + output);
        System.out.println(output);
        System.out.flush();
    }
    public static void debug(String output) {
        System.err.println(output);
    }
}
public interface IPathFinder {
	public String[] findShortestPath(Group[] groups, Board board, Group group);
}
public class Manager {
    private Player player;
    private GameState gameState;
    private final int STARTPLAYER = 1;
    private MoveConverter converter;
    private long startTime;
    public static void main(String args[]) {
    	String input = IO.input();
        int playerNumber = (input.equals("Start") ? 1 : 2);
        new Manager().start(input, playerNumber);
    }
    public Manager() {}
    private void start(String input, int playerNumber) {
    	startTime = System.currentTimeMillis();
        initGame(playerNumber);
        this.handleInput(input);
    }
    private void initGame(int playerNumber) {
        this.converter = new MoveConverter();
        this.gameState = new GameState(new Board(), playerNumber, (playerNumber == 1 ? 2 : 1));
        this.player = new Player(this.gameState, playerNumber);
        if (playerNumber == STARTPLAYER) {
            this.gameState = this.player.takeTurn(this.gameState);
        }
    }
    public long getTimePastInMiliseconds() {
        return startTime - System.currentTimeMillis();
    }
    public void startGame() {
        loadConfig();
    }
    private void loadConfig() {
        IO.output("This should load the config via file");
    }
    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public Player getPlayer() {
        return this.player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public MoveConverter getConverter() {
        return this.converter;
    }
    public void handleInput() {
        this.handleInput(IO.input());
    }
    public Board getBoard() {
        return gameState.getBoard();
    }
    public void handleInput(String input) {
        while (!input.equals("Quit!")) {
            if (converter.isMoveFormat(input)) {
                gameState = gameState.makeMove(this.readMove(input));
                gameState.setTimePast(getTimePastInMiliseconds());
                gameState = this.player.takeTurn(gameState);
            }
            input = IO.input();
        }
    }
    public Move readMove(String move) {
        return getConverter().readMove(move);
    }
}
public class MCNode {
    private MCNode[] children = new MCNode[0];
    private float winPercentage = (float) 0.0;
    private int moveValue = 0;
    private boolean isLeaf = false;
    private boolean isWin = false;
    private GameState state;
    public MCNode(GameState state, IAlgorithm algorithm) {
        this.state = state.makeMove(algorithm.getMove());
        updateMoveValue();
        this.isWin = this.state.hasWon(this.state.getPlayingPiece());
        if (this.isWin) {
            this.isLeaf = true;
        }
        this.winPercentage = (float) (this.isWin ? 1.0 : 0.0);
    }
    private void updateMoveValue() {
        Move playableMove = this.state.getPlayableMove();
        Board board = this.state.getBoard();
        Group originGroup = GroupManager.getGroupByCoordinate(playableMove.getIndexOriginX(), playableMove.getIndexOriginY(), Arrays.asList(this.state.getGroups()));
        this.state.setGroups(GroupManager.getGroupsByPiece(board, state.getPlayingPiece()));
        Group[] oppponentGroups = GroupManager.getGroupsByPiece(board, this.state.getOpponentPiece());
        int x = playableMove.getIndexTargetX();
        int y = playableMove.getIndexTargetY();
        Group group = GroupManager.getGroupByCoordinate(x, y, Arrays.asList(this.state.getGroups()));
        String neighbors[] = board.getNeighborsByPiece(x, y, this.state.getOpponentPiece());
        if (originGroup.getSize() == 1) {
            this.moveValue = 25;
        }
        if (group != null) {
            this.moveValue += group.getCoordinates().size() * 2;
        }
        for (int i = 0; i < neighbors.length; i++) {
            this.moveValue += GroupManager.getGroupByCoordinate(neighbors[i], Arrays.asList(oppponentGroups)).getSize();
        }
    }
    public MCNode[] getChildren() {
        return children;
    }
    public Move getPlayableMove() {
        return state.getPlayableMove();
    }
    public void setChildren(MCNode[] children) {
        this.children = children;
    }
    public float getWinPercentage() {
        return winPercentage;
    }
    public void setWinPercentage(float winPercentage) {
        this.winPercentage = winPercentage;
    }
    public boolean isLeaf() {
        return isLeaf;
    }
    public boolean isWin() {
        return isWin;
    }
    public int getMoveValue() {
        return moveValue;
    }
    public void setMoveValue(int moveValue) {
        this.moveValue = moveValue;
    }
}
public class MCTree {
	private MCNode root;
	private GameState state;
	public MCTree(int treeWidth, int treeDepth, GameState state, IAlgorithm algorithm) {
		this.state = state;
		this.root = new MCNode(state, algorithm);
		generateTree(this.root, treeWidth, treeDepth, algorithm);
	}
	private void generateTree(MCNode node, int numChilds, int searchDepth, IAlgorithm algorithm) {
		if (searchDepth > 0) {
			MCNode children[] = new MCNode[numChilds];
			for (int i = 0; i < numChilds; i++) {
				MCNode child = new MCNode(this.state, algorithm);
				if (!child.isLeaf()) {
					generateTree(child, numChilds, searchDepth - 1, algorithm);
				}
				children[i] = child;
			}
			node.setChildren(children);
			updateMoveValues(node);
		}
	}
	private void updateMoveValues(MCNode node) {
		float winPercentage = (float) 0.0;
		int moveValue = 0;
		int childrenLength = node.getChildren().length;
		for (int i = 0; i < childrenLength; i++) {
			MCNode child = node.getChildren()[i];
			winPercentage += child.getWinPercentage();
			moveValue += child.getMoveValue();
		}
		node.setWinPercentage(winPercentage / ((childrenLength == 0) ? 1 : childrenLength));
		node.setMoveValue(moveValue);
	}
	private String printTree(MCNode node, String whitespace) {
		String output = whitespace + node.getWinPercentage() + " - " + " ---> " + node.getMoveValue();
		output += whitespace + 
				node.getPlayableMove().getBoardOriginX() + "," +  
				node.getPlayableMove().getBoardOriginY() + "--->" + 
				node.getPlayableMove().getBoardTargetX() + "," +
				node.getPlayableMove().getBoardTargetY();
		if (!node.isLeaf()) {
			for (int i = 0; i < node.getChildren().length; i++) {
				output += printTree(node.getChildren()[i], whitespace + "    ");
			}
		}
		return output;
	}
	public String toString() {
		return printTree(root, "");
	}
	public Move getPlayableMove() {
		MCNode node = getRoot().getChildren()[0];
		int childrenSize = getRoot().getChildren().length;
		for (int i = 1; i < childrenSize; i++) {
			MCNode childNode = getRoot().getChildren()[i];
			if (isChildNodeBetterThanNode(node, childNode)) {
				node = childNode;
			}
		}
		return node.getPlayableMove();
	}
	private boolean isChildNodeBetterThanNode(MCNode node, MCNode childNode) {
		return childNode.getWinPercentage() > node.getWinPercentage()
				&& childNode.getMoveValue() > node.getMoveValue();
	}
	public MCNode getRoot() {
		return root;
	}
}
public class Move {
	private Player player;
	private String boardOriginX;
	private String boardOriginY;
	private String boardTargetX;
	private String boardTargetY;
	public Move(String originX, String originY, String targetX, String targetY) {
		this.setUpMove(originX, originY, targetX, targetY);
	}
	public Move(int originX, int originY, int targetX, int targetY) {
		this.setUpMove(MoveConverter.convertPointToString(originX), "" + (originY + 1), MoveConverter.convertPointToString(targetX), "" + (targetY + 1));
	}
	public Move(Player player, String originX, String originY, String targetX, String targetY) {
		this.setUpMove(originX, originY, targetX, targetY);
		this.player = player;
	}
	public void setUpMove(String originX, String originY, String targetX, String targetY) {
		this.boardOriginX = originX;
		this.boardOriginY = originY;
		this.boardTargetX = targetX;
		this.boardTargetY = targetY;
	}
	public int getIndexOriginX() {
		return MoveConverter.convertStringToPoint(this.boardOriginX);
	}
	public int getIndexOriginY() {
		return (Integer.parseInt(this.boardOriginY) - 1);
	}
	public int getIndexTargetX() {
		return MoveConverter.convertStringToPoint(this.boardTargetX);
	}
	public int getIndexTargetY() {
		return (Integer.parseInt(this.boardTargetY) - 1);
	}
	public String getBoardOrigin() {
		return this.boardOriginX + this.boardOriginY;
	}
	public String getBoardTarget() {
		return this.boardTargetX + this.boardTargetY;
	}
	public String getBoardOriginX() {
		return boardOriginX;
	}
	public void setBoardOriginX(String boardOriginX) {
		this.boardOriginX = boardOriginX;
	}
	public String getBoardOriginY() {
		return boardOriginY;
	}
	public void setBoardOriginY(String boardOriginY) {
		this.boardOriginY = boardOriginY;
	}
	public String getBoardTargetX() {
		return boardTargetX;
	}
	public void setBoardTargetX(String boardTargetX) {
		this.boardTargetX = boardTargetX;
	}
	public String getBoardTargetY() {
		return boardTargetY;
	}
	public void setBoardTargetY(String boardTargetY) {
		this.boardTargetY = boardTargetY;
	}
	public String toString() {
		String output = (this.player != null ? this.player.toString() : "");
		return output + this.boardOriginX + this.boardOriginY + "-" + this.boardTargetX + this.boardTargetY;
	}
}
//TODO STATIC METHODS?
public class MoveConverter {
	private static final String COLUMNS = "ABCDEFGHIJK";
	public Move readMove(String moveString) {
		String[] splitted = moveString.split("-");
		Move move = new Move(
				splitted[0].substring(0, 1),
				splitted[0].substring(1, splitted[0].length()),
				splitted[1].substring(0, 1),
				splitted[1].substring(1, splitted[1].length()));
		return move;
	}
	public boolean isMoveFormat(String moveString) {
		String pattern = "([1-9]\\s)?[a-k][1-9][0-1]?-[a-k][1-9][0-1]?";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(moveString.toLowerCase());
	    return matcher.matches();
	}
	public static int convertStringToPoint(String string) {
		return COLUMNS.indexOf(string);
	}
	public static String convertPointToString(int position) {
		char[] character = COLUMNS.toCharArray();
		return position < character.length ? ""+character[position] : " ";
	}
}
public class Player {
    private GameState state;
    private IPathFinder pathFinder = new AStarPathFinder();
    public static int piece;
    private static int TWENTY_FIVE_SECONDS = 25000;
    public Player(GameState state, int piece) {
        this.state = state;
        Player.piece = piece;
    }
    public GameState takeTurn(GameState state) {
        if (state.isGameOver()) {
            return state;
        }
        this.state = state.clone();
        Move move = chooseMove();
        IO.output(move.toString());
        return state.makeMove(move);
    }
    public Move chooseMove() {
        Move move = null;
        this.state.recheckGroups();
        // IO.debug("TRY TO FIND MOVE FOR PLAYER: " + Player.piece);
        // IO.debug("AMOUNT OF GROUPS: " + state.getGroupsLength());
        IAlgorithm random = new RandomMoveAlgorithm(state.getGroups(), state.getBoard(), pathFinder);
        if (state.getTimePastInMiliseconds() < TWENTY_FIVE_SECONDS) {
        	if (move == null && state.getGroupsLength() > 15) {
                IO.debug("Move direct start: " + new Timestamp(new java.util.Date().getTime()));
                move = new DirectionAlgorithm(state).getMove();
                IO.debug("Move direct end: " + new Timestamp(new java.util.Date().getTime()));
            }
            if (move == null && state.getGroupsLength() > 10) {
                IO.debug("Move monte 10 start: " + new Timestamp(new java.util.Date().getTime()));
                move = getMonteCarloMove(3, 3, random);
                IO.debug("Move monte 10 end: " + new Timestamp(new java.util.Date().getTime()));
            }
            if (move == null && state.getGroupsLength() > 2) {
                IO.debug("Move monte 10 start: " + new Timestamp(new java.util.Date().getTime()));
                move = getMonteCarloMove(2, 2, random);
                IO.debug("Move monte 10 end: " + new Timestamp(new java.util.Date().getTime()));
            }
            if (move == null && state.getGroupsLength() == 2) {
                IO.debug("Move random start: " + new Timestamp(new java.util.Date().getTime()));
                move = random.getMove();
                IO.debug("Move random end: " + new Timestamp(new java.util.Date().getTime()));
            }
        }
        if (move == null) {
            IO.debug("Move random start: " + new Timestamp(new java.util.Date().getTime()));
            move = random.getMove();
            IO.debug("Move random end: " + new Timestamp(new java.util.Date().getTime()));
        }
        return move;
    }
    private Move getMonteCarloMove(int width, int height, IAlgorithm algorithm) {
        MCTree tree = new MCTree(width, height, state, algorithm);
        return tree.getPlayableMove();
    }
    public String toString() {
        return "R player: " + Player.piece;
    }
    public GameState getState() {
        return this.state;
    }
}
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
        String[] shortestPathCoordinate = pathFinder.findShortestPath(this.playerGroups, this.board, startGroup);
        if (shortestPathCoordinate != null) {
            return constructMoveFromShortestPath(startGroup, shortestPathCoordinate[0].split(","));
        }
        groups.remove(startGroup);
        return getRandomMove(groups);
    }
    private Move constructMoveFromShortestPath(Group startGroup, String[] shortestPath) {
        int targetX = Integer.parseInt(shortestPath[0]);
        int targetY = Integer.parseInt(shortestPath[1]);
        String[] origin = startGroup.findPointMostFarAway(targetX, targetY, this.board);
        int originX = Integer.parseInt(origin[0]);
        int originY = Integer.parseInt(origin[1]);
        return new Move(originX, originY, targetX, targetY);
    }
    private int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
