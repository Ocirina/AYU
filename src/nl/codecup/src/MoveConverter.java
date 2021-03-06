package nl.codecup.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO STATIC METHODS?
public class MoveConverter {

	private static final String COLUMNS = "ABCDEFGHIJK";
	
	/**
	 * Reads the move and converts it to an Move object.
	 * The format expected is: xy-xy
	 * An example: D10-H6
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
	 * An example: 2 D10-H6
	 * The player (number 2 in example) is optional.
	 * 
	 * @param moveString
	 * @return boolean
	 */
	public boolean isMoveFormat(String moveString) {
		String pattern = "([1-9]\\s)?[a-k][1-9][0-1]?-[a-k][1-9][0-1]?";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(moveString.toLowerCase());
	    return matcher.matches();
	}
	
	/**
	 * Returns a number according to the given string for the board.
	 * 
	 * @returns A point
	 */
	public static int convertStringToPoint(String string) {
		return COLUMNS.indexOf(string);
	}
	
	/**
	 * Returns the position of the given string
	 * 
	 * @param position
	 * @return
	 */
	public static String convertPointToString(int position) {
		char[] character = COLUMNS.toCharArray();
		return position < character.length ? ""+character[position] : " ";
	}
}