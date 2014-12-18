package nl.codecup.src;

public class Move {

	/**
	 * Instance of the player
	 */
	private Player player;

	/**
	 * From location X
	 */
	private String boardOriginX;

	/**
	 * From location Y
	 */
	private String boardOriginY;
	
	/**
	 * Target location X
	 */
	private String boardTargetX;

	/**
	 * Target location Y
	 */
	private String boardTargetY;

	/**
	 * Constructor without user
	 *
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	public Move(String originX, String originY, String targetX, String targetY) {
		this.setUpMove(originX, originY, targetX, targetY);
	}
	
	/**
	 * Constructor from int
	 *
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	public Move(int originX, int originY, int targetX, int targetY) {
		this.setUpMove(MoveConverter.convertPointToString(originX), "" + (originY + 1), MoveConverter.convertPointToString(targetX), "" + (targetY + 1));
	}

	/**
	 * Create move with player
	 *
	 * @param player
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	public Move(Player player, String originX, String originY, String targetX, String targetY) {
		this.setUpMove(originX, originY, targetX, targetY);
		this.player = player;
	}

	/**
	 * Set up move positions
	 *
	 * @param originX
	 * @param originY
	 * @param targetX
	 * @param targetY
	 */
	public void setUpMove(String originX, String originY, String targetX, String targetY) {
		this.boardOriginX = originX;
		this.boardOriginY = originY;
		this.boardTargetX = targetX;
		this.boardTargetY = targetY;
	}
	
	/**
	 * Returns converted
	 * 
	 * @return
	 */
	public int getIndexOriginX() {
		return MoveConverter.convertStringToPoint(this.boardOriginX);
	}
	
	/**
	 * Returns converted
	 * 
	 * @return
	 */
	public int getIndexOriginY() {
		return (Integer.parseInt(this.boardOriginY) - 1);
	}
	
	/**
	 * Returns converted
	 * 
	 * @return
	 */
	public int getIndexTargetX() {
		return MoveConverter.convertStringToPoint(this.boardTargetX);
	}
	
	/**
	 * Returns converted
	 * 
	 * @return
	 */
	public int getIndexTargetY() {
		return (Integer.parseInt(this.boardTargetY) - 1);
	}

	/**
	 * Get From Location
	 *
	 * @return
	 */
	public String getBoardOrigin() {
		return this.boardOriginX + this.boardOriginY;
	}

	/**
	 * Get to Location
	 * @return
	 */
	public String getBoardTarget() {
		return this.boardTargetX + this.boardTargetY;
	}

	/**
	 * @return the boardOriginX
	 */
	public String getBoardOriginX() {
		return boardOriginX;
	}

	/**
	 * @param boardOriginX the boardOriginX to set
	 */
	public void setBoardOriginX(String boardOriginX) {
		this.boardOriginX = boardOriginX;
	}

	/**
	 * @return the boardOriginY
	 */
	public String getBoardOriginY() {
		return boardOriginY;
	}

	/**
	 * @param boardOriginY the boardOriginY to set
	 */
	public void setBoardOriginY(String boardOriginY) {
		this.boardOriginY = boardOriginY;
	}

	/**
	 * @return the boardTargetX
	 */
	public String getBoardTargetX() {
		return boardTargetX;
	}

	/**
	 * @param boardTargetX the boardTargetX to set
	 */
	public void setBoardTargetX(String boardTargetX) {
		this.boardTargetX = boardTargetX;
	}

	/**
	 * @return the boardTargetY
	 */
	public String getBoardTargetY() {
		return boardTargetY;
	}

	/**
	 * @param boardTargetY the boardTargetY to set
	 */
	public void setBoardTargetY(String boardTargetY) {
		this.boardTargetY = boardTargetY;
	}
	

	/**
	 * To string for debugging and sending data to caiaio
	 */
	public String toString() {
		String output = (this.player != null ? this.player.toString() : "");
		return output + this.boardOriginX + this.boardOriginY + "-" + this.boardTargetX + this.boardTargetY;
	}
}
