package nl.codecup.src;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	/**
	 * List of coordinates.
	 * Original move format without player number:
	 * Example: 
	 * [0] = "1,4"
	 * [1] = "1,5"
	 */
	private List<String> coordinates = new ArrayList<String>();
	
	public Group() {
		
	}
	
	public Group(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	
	/**
	 * @return the coordinates
	 */
	public List<String> getCoordinates() {
		return coordinates;
	}

	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * 
	 */
	public void addCoordinate(String coordinate) {
		coordinates.add(coordinate);
	}
	
	/**
	 * Gets the minimum distance from a coordinate to the group.
	 */
	public int getMinimumDistance(String coordinate) {
		String[] coords = coordinate.split(",");
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);
		int minimumDistance = 0;
		for(int i = 0; i < this.coordinates.size(); i++) {
			coords = this.coordinates.get(i).split(",");
			int tempDistance = Math.abs(Integer.parseInt(coords[0]) - x);
				tempDistance += Math.abs(Integer.parseInt(coords[1]) - y);
				
			if(minimumDistance > tempDistance || minimumDistance == 0) 
				minimumDistance = tempDistance;
		}
		return minimumDistance;
	}
}
