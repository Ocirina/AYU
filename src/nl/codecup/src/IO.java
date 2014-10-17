package nl.codecup.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// TODO: Move handling logic into adapters for command line (and file?).
public class IO {
	
	/**
	 * Reads input from the System.in stream.
	 * 
	 * @return input from System.in
	 */
	public static String input() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
	    try {
			return reader.readLine();
		} catch (IOException e) {
			//e.printStackTrace();
		}
	    return null;
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
