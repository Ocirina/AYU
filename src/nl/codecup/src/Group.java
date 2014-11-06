package nl.codecup.src;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Group {
	
	/**
	 * List of coordinates.
	 * Original move format without player number:
	 * Example: 
	 * [0] = "1,4"
	 * [1] = "1,5"
	 */
	private List<String> coordinates = new ArrayList<String>();
	
	/**
	 * The index of this group in the list.
	 */
	private int indexInList = 0;
	
	public Group(int index) {
		this.indexInList = index;
	}
	
	public Group(List<String> coordinates, int index) {
		this.coordinates = coordinates;
		this.indexInList = index;
	}
	
	/**
	 * @return the coordinates
	 */
	public List<String> getCoordinates() {
		return coordinates;
	}

	/**
	 * Sets the coordinates
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(List<String> coordinates) {
		this.coordinates = coordinates;
	}
	
	public int getPositionOfCoordinate(String coordinate) {
		for(int i = 0; i < coordinates.size(); i++) {
			if(coordinates.get(i).equalsIgnoreCase(coordinate)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * @return the indexInList
	 */
	public int getIndexInList() {
		return indexInList;
	}

	/**
	 * @param indexInList the indexInList to set
	 */
	public void setIndexInList(int indexInList) {
		this.indexInList = indexInList;
	}

	/**
	 * Adds a coordinate to the group.
	 * Coordinate must match pattern.
	 */
	public boolean addCoordinate(String coordinate) {
		String pattern = "[0-9]{1,2}\\,[0-9]{1,2}";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(coordinate.toLowerCase());
		if(matcher.matches()) {
			coordinates.add(coordinate);	
			return true;
		}
		return false;
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
			int tempDistance  = Math.abs(Integer.parseInt(coords[0]) - x);
				tempDistance += Math.abs(Integer.parseInt(coords[1]) - y);
				
			if(minimumDistance > tempDistance || minimumDistance == 0) 
				minimumDistance = tempDistance;
		}
		return minimumDistance;
	}
}
