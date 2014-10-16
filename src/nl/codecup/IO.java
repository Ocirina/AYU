package nl.codecup;

public class IO {
	/**
	 * Reads the move and converts it to an Move object.
	 * The format expected is: xy-xy
	 * An example: d10-h6
	 * 
	 * @param moveString
	 * @return Move move
	 */
	public Move readMove(String moveString) {
		String[] splitted = moveString.split("-");
		Move move = new Move(
				splitted[0].substring(0, 1),
				splitted[0].substring(1, splitted[0].length()),
				splitted[1].substring(0, 1),
				splitted[1].substring(1, splitted[1].length()));
				
		return move;
	}

	public void displayMove(Move move) {
		System.out.println(move);
	}

}