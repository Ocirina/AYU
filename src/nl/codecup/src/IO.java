package nl.codecup.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	/**
	 * Checks if the string is in the move format.
	 * An example: 2 d10-h6
	 * The player (number 2 in example) is optional.
	 * 
	 * @param moveString
	 * @return boolean
	 */
	public boolean isMove(String moveString) {
		String pattern = "([1-9]\\s)?[a-k][1-9][0-1]?-[a-k][1-9][0-1]?";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(moveString.toLowerCase());
	    return matcher.matches();
	}
	
	/**
	 * This method will display an given move
	 * 
	 * @param move
	 */
	public void displayMove(Move move) {
		System.out.println(move);
	}
}