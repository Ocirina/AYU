package nl.codecup.src;

import java.util.Scanner;

// TODO: Move handling logic into adapters for command line (and file?).
public class IO {
	
	/**
	 * Reads input from the System.in stream.
	 * 
	 * @return input from System.in
	 */
	public static String input() {
		Scanner scanIn = new Scanner(System.in);
	    String input = scanIn.nextLine();
	    scanIn.close();            
	    return input;
	}
	
	/**
	 * Outputs the String to System.err.
	 * 
	 * @param output
	 */
	public static void output(String output) {
		System.err.print(output);
	}
}
