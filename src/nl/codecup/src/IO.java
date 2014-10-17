package nl.codecup.src;

import java.util.Scanner;

public class IO {
	
	/**
	 * Reads input from the System.in stream.
	 * 
	 * @return input from System.in
	 */
	public String input() {
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
	public void output(String output) {
		System.err.print(output);
	}
}
