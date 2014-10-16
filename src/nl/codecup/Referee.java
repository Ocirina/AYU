package nl.codecup;


public class Referee {

    private Manager manager;
    private IO IO;

    public Referee(Manager manager) {
        this.manager = manager;
        this.IO = new IO();
    }

    public void handleInput() {

    }

    public void sendReport() {

    }

    /**
     * This method will stop the object itself
     */
    private void stopReferee() {

    }

    /**
     * This will return if an move is valid
     * 
     * @param move
     * @return
     */
    public Boolean validMove(Move move) {
        IO.displayMove(move);
        
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
