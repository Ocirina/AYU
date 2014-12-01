package nl.codecup.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstract Class IO. Handles the input given by the Caiaio and outputs to the
 * Caiaio
 */
public abstract class IO {

    private static boolean firstMessage = true;

    /**
     * Reads input from the System.in stream.
     * 
     * @return input from System.in
     */
    public static String input() {
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(streamReader);
        try {
            String readLine = reader.readLine();

            if (firstMessage) {
                IO.debug("R JavaPlayer");
                firstMessage = false;
            }

            IO.debug("Read line: " + readLine);

            return readLine;
        }
        catch (IOException e) {
            IO.debug("IOException: " + e.getMessage());
        }
        IO.debug("Returning null");
        return null;
    }

    /**
     * Outputs the String to System.err.
     * 
     * @param output
     */
    public static void output(String output) {
        IO.debug("WAS SENT TO CAIAIO: " + output);
        System.out.println(output);
        System.out.flush();
    }

    /**
     * Log
     * 
     * @param output
     */
    public static void debug(String output) {
        System.err.println(output);
    }
}
