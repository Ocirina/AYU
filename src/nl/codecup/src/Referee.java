package nl.codecup.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Referee {

    private Manager manager;

    public Referee(Manager manager) {
        this.manager = manager;
    }

    public void handleInput() {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		String input;
		try {
			input = reader.readLine();
			
			System.err.println(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method will send the report
     */
    public void sendReport() { }

    /**
     * This method will stop the object itself
     */
//  private void stopReferee() { }

    /**
     * This will return if an move is valid
     * 
     * @param move
     * @return
     */
    public Boolean validMove(Move move) {
        this.manager.getConverter().displayMove(move);
        return false;
    }

    /**
     * This will write some data to the log
     * 
     * @return
     */
    public String writeLog() {
        return null;
    }

}
