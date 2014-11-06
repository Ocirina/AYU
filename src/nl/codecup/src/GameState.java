package nl.codecup.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameState {
	private Board board;
	private int playingPiece;
	private int opponentPiece;
	private int winner = 0;
	private Group[] playerGroups = new Group[30];

	public GameState(int playerPiece, int opponentPiece) {
		this.playingPiece = playerPiece;
		this.opponentPiece = opponentPiece;
	}

	public GameState(Board board, int playerPiece, int opponentPiece) {
		this.board = board;
		this.playingPiece = playerPiece;
		this.opponentPiece = opponentPiece;
		this.checkForWin();
		this.createGroupsForPlayer();
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

	private void createGroupsForPlayer() {
		int[][] contents = board.getBoardContents();
		int count = 0;
		for (int i = 0; i < contents.length; i++) {
			for (int j = 0; j < contents[i].length; j++) {
				if (contents[i][j] == playingPiece) {
					Group group = new Group(count);
					group.addCoordinate(i + "," + j);
					playerGroups[count] = group;
					count++;
				}
			}
		}
	}

	/**
	 * Sets a group to null.
	 * 
	 * @param group
	 *            : The group to set to null
	 */
	public void setPlayerGroupNull(Group group) {
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
			if (group != null && group.getCoordinates().contains(x + "," + y))
				return group;
		}
		return null;
	}
	
	public Group getGroupByCoordinate(String coordinate) {
		String[] coords = coordinate.split(",");
		return this.getGroupByCoordinate(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
	}

	/**
	 * Places the move on the board and returns this in a new state.
	 * 
	 * @param row
	 *            the y coordinates
	 * @param column
	 *            the x coordinates
	 * @return A new GameState with the move added
	 */
	public GameState makeMove(Move move) {
		GameState newState = this.clone();
		Board newBoard = this.board.clone();
		newState.setPlayerPiece(opponentPiece);
		newState.setComputerPiece(playingPiece);
		newState.setBoard(newBoard.placePiece(move));
		this.checkGroupsForMove(move.getOriginXConverted(),
				move.getOriginYConverted(), move.getTargetXConverted(),
				move.getTargetYConverted());
		IO.debug(groupsToString());
		return newState;
	}

	private void checkGroupsForMove(int originX, int originY, int targetX, int targetY) {
		/* If piece is not in group */
		if (!board.hasNeighbour(originX, originY)) {
			mergeNonGroupedPiece(originX, originY, targetX, targetY);
		} else {
			mergeGroupedPiece(originX, originY, targetX, targetY);
		}
	}

	/**
	 * Merges the group of the piece of the origin to the group(s) of the targetX and targetY's neighbours.
	 */
	private void mergeGroupedPiece(int originX, int originY, int targetX, int targetY){
		Group[] groups = new Group[5];
		Group group = groups[0] = this.getGroupByCoordinate(originX, originY);
		if(group != null) {
			int position = group.getPositionOfCoordinate(originX+","+originY);
			if(position != -1) {
				group.getCoordinates().set(position, targetX+","+targetY);
 			}
			String[] neighBours = board.getNeighbours(targetX, targetY);
			for(int i = 0; i < neighBours.length && (i+1) < groups.length; i++) {
				if(neighBours[i] != null && !neighBours[i].equalsIgnoreCase("")) {
					Group tempGroup = this.getGroupByCoordinate(neighBours[i]);
					if(group != tempGroup) {
						groups[i+1] = tempGroup;
					}
				}
			}
			mergeGroups(groups);
		}
	}
	
	private void mergeNonGroupedPiece(int originX, int originY, int targetX, int targetY) {
		Group group = this.getGroupByCoordinate(originX, originY);
		Group[] groups = new Group[4];
		if (group != null) {
			List<String> tempList = group.getCoordinates();
			tempList.set(0, targetX+","+targetY);
			this.playerGroups[group.getIndexInList()] = null;
			String[] neighBours = board.getNeighbours(targetX, targetY);
			for(int i = 0; i < neighBours.length; i++) {
				if(neighBours[i] != null && !neighBours[i].equalsIgnoreCase("")) {
					groups[i] = this.getGroupByCoordinate(neighBours[i]);
				}
			}
			this.setPlayerGroupNull(group); // Set origin group to null
			mergeGroups(groups, tempList);
		}
	}
	
	/**
	 * Merges the given groups
	 * @param groups
	 */
	private Group mergeGroups(Group[] groups) {
		Group mergedGroup = new Group(0);
		mergedGroup.setIndexInList(groups[0].getIndexInList());
		for(Group group : groups) {
			if(group != null) {
				for(String coordinate : group.getCoordinates()) {
					mergedGroup.addCoordinate(coordinate);
					this.setPlayerGroupNull(group);
				}
			}
		}
		playerGroups[mergedGroup.getIndexInList()] = mergedGroup;
		return mergedGroup;
	}
	
	/**
	 * Merges the given groups and adds the given coordinates to the merged group.
	 * @param groups
	 * @param coordinates
	 */
	private void mergeGroups(Group[] groups, List<String> coordinates) {
		Group mergedGroup = this.mergeGroups(groups);
		for(String coordinate : coordinates) {
			mergedGroup.addCoordinate(coordinate);
		}
		
	}

	/**
	 * Returns the current opponent piece.
	 * 
	 * @return returns the opponent piece
	 */
	private void setComputerPiece(int computerPiece) {
		this.opponentPiece = computerPiece;
	}

	/**
	 * Sets the board on the state. It also sets the size of the board. And
	 * calculates the minimum moves required to win. After this it checks if
	 * there is a winner.
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
		checkForWin();
	}

	/**
	 * Sets the current player piece.
	 * 
	 * @param playerPiece
	 */
	private void setPlayerPiece(int playerPiece) {
		this.playingPiece = playerPiece;
	}

	public boolean isGameOver() {
		return false;
	}

	/**
	 * Checks if the given piece wins.
	 * 
	 * @param piece
	 * @return true if there is a winner and it is the given piece
	 */
	public boolean hasWon(int piece) {
		return hasWinner() && winner == piece;
	}

	/**
	 * Checks if the given piece loses.
	 * 
	 * @param piece
	 * @return true if there is a loser and it is the given piece
	 */
	public boolean hasLost(int piece) {
		return hasWinner() && winner != piece;
	}

	/**
	 * Makes a clone of the state object.
	 * 
	 * @return A cloned state
	 */
	public GameState clone() {
		return new GameState(this);
	}

	/**
	 * Draws the board with pieces in place.
	 * 
	 * @return a visual representation of the board in this state
	 */
	public String toString() {
		return this.board.toString();
	}
	
	/**
	 * Prints the groa
	 * @return
	 */
	public String groupsToString() {
		String groups = "Groups: \n";
		for(Group group : playerGroups) {
			groups += group == null ? "Empty Group\n" : group.toString() + "\n";
		}
		return groups;
	}

	/**
	 * Checks if the game has resulted in a win.
	 * 
	 * @return true if there is a winner
	 */
	private boolean hasWinner() {
		return winner == 1 || winner == 2;
	}

	private boolean checkForWin() {
		winner = 0;
		return false;
	}

	public int getPlayingPiece() {
		// TODO Auto-generated method stub
		return this.playingPiece;
	}

	public int[][] getBoardContents() {
		return this.board.getBoardContents();
	}
}
