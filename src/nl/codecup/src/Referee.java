package nl.codecup.src;

public class Referee {

    private Manager manager;

    public Referee(Manager manager) {
        this.manager = manager;
    }

    public void handleInput() {
		String input = IO.input();
		IO.output(input);
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
    	return true;
//        this.manager.getConverter().displayMove(move);
//        return false;
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
