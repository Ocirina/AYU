package nl.codecup.src;

public class DirectionAlgorithm implements IAlgorithm {
	public Move getMove(GameState state) {
		int[][] content = state.getBoardContents();
        int contentLength = content.length - 1;
		for (int row = 0; row < contentLength; row++) {
		    for (int column = 0; column < contentLength; column++) {
		        if (isGapScenario(content, row, column)) {
		            int columnPieceToMove = findLastPieceInTheRow(content, row, column, 0);
		            return new Move(row, columnPieceToMove, row, column + 1);
		        }
		    }
		}
		return null;
	}

    private boolean isGapScenario(int[][] content, int row, int column) {
        return column < 9 && row < 9 && content[row][column] == Player.piece && content[row][column + 1] == Board.NONE && content[row][column + 2] == Player.piece;
    }


    /**
     * Find the last piece of a row
     * 
     * @param content
     * @param row
     * @param column
     * @param defaultColumn
     * @return
     */
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
