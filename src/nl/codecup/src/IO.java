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
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(streamReader);
	    try {
			return reader.readLine();
		} catch (IOException e) { }
	    return null;
	}
	
	/**
	 * Outputs the String to System.err.
	 * 
	 * @param output
	 */
	public static void output(String output) {
		IO.outputDebug("WAS SENT TO CAIAIO: " + output);
		System.out.println(output);
	}
	
	/**
	 * Log
	 * 
	 * @param output
	 */
	public static void outputDebug(String output) {
		System.err.println(output);
	}
}
