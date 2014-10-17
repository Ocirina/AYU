package nl.codecup.src;

public class Board {
	private static final int SIZE = 11;
	private int[][] boardGrid = new int[SIZE][SIZE];
	
	public Board() {
		this.makeGrid(this.boardGrid);
	}
	
	public Board(int[][] grid) {
		this.boardGrid = grid;
	}
	
	private void makeGrid(int[][] grid) {
		for(int i = 0; i < SIZE; i = i + 2) {
			for(int j = 0; j < SIZE; j = j + 2) {
				
			}
		}
	}
}
