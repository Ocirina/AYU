package nl.codecup.src;

public class Move {

//	private Boolean validMove;

	/**
	 * Instance of the player
	 */
	private Player player;

	/**
	 * From location X
	 */
	private String originX;

	/**
	 * From location Y
	 */
	private String originY;

	/**
	 * Target location X
	 */
	private String targetX;

	/**
	 * Target location Y
	 */
	private String targetY;

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
		this.originX = originX;
		this.originY = originY;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	/**
	 * Get From Location
	 *
	 * @return
	 */
	public String getFrom() {
		return this.originX + this.originY;
	}

	/**
	 * Get to Location
	 * @return
	 */
	public String getTo() {
		return this.targetX + this.targetY;
	}

	/**
	 * To string for debugging and sending data to caiaio
	 */
	public String toString() {
		String output = (this.player != null ? this.player.toString() : "");
		return output + this.originX + this.originY + "-" + this.targetX + this.targetY;
	}
}
