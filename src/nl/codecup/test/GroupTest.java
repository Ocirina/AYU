package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import nl.codecup.src.*;

public class GroupTest {

	public static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	
	private Group group;
	private Board board;
	
	@Before
	public void create() {
		Player.piece = 1;
		group = new Group(0);
		board = new Board(new int[][] {
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, NONE, NONE, WHITE, WHITE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE,
					WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK,
					NONE } });
	}
	
	@Test
	public void testAddCoordinate() {
		assertTrue(group.addCoordinate("10,1"));		
	}
	
	@Test
	public void testGetPositionOfCoordinate() {
		group.addCoordinate("10,1");		
		assertTrue(group.getPositionOfCoordinate("10,1") != -1);
		assertTrue(group.getPositionOfCoordinate("10,2") == -1);
	}
	
	@Test
	public void testGetMinimumDistance() {
		group.addCoordinate("10,1");
		group.addCoordinate("10,2");
		group.addCoordinate("10,3");
		
		assertEquals(group.getMinimumDistance("5,1"), 5);
		assertEquals(group.getMinimumDistance("5,5"), 7);
		assertNotEquals(group.getMinimumDistance("5,1"), 6);
	}
	
	@Test
	public void testFindPointMostFarAway() {
		group.addCoordinate("5,4");
		group.addCoordinate("5,5");
		group.addCoordinate("5,6");
		
		String[] coords = group.findPointMostFarAway(5, 0, board);
		assertEquals(coords[0], "5");
		assertEquals(coords[1], "6");
		
		coords = group.findPointMostFarAway(5, 9, board);
		assertEquals(coords[0], "5");
		assertEquals(coords[1], "4");
	}

	@After
	public void destroy() {
		group = null;
	}
}
