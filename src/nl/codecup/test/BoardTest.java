package nl.codecup.test;

import static org.junit.Assert.*;
import nl.codecup.src.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class BoardTest {

	private Board board = null;
	
	@Before
	public void create() {
		Player.piece = 1;
		board = new Board();
	}
	
	@Test
	public void testHasNeighbour() {
		board.movePiece(new Move("B","1","B","2"));
		assertTrue(board.hasNeighbor(1,1));
		assertFalse(board.hasNeighbor(3,0));
	}
	
	@Test
	public void testOnEdgesOfGroup() {
		board.movePiece(new Move("B","1","B","2"));
		board.movePiece(new Move("B","2","B","4"));
		assertFalse(board.onEdgesOfGroup(1,3));
		assertTrue(board.onEdgesOfGroup(1,2));
		assertTrue(board.onEdgesOfGroup(1,4));	
	}
	
	@Test
	public void testIsMoveNeighbourOfSameGroup() {
		board.movePiece(new Move("B","1","B","2"));
		board.movePiece(new Move("B","2","B","4"));
		assertTrue(board.isMoveNeighbourOfSameGroup(1,2,1,5));
		assertTrue(board.isMoveNeighbourOfSameGroup(1,2,2,4));
		assertFalse(board.isMoveNeighbourOfSameGroup(1,2,0,0));
	}
	
	@Test
	public void testIsBlankSpace() {
		assertTrue(board.isBlankSpace(0,0));
		assertTrue(board.isBlankSpace(0,2));
		assertFalse(board.isBlankSpace(1,0));
		assertFalse(board.isBlankSpace(0,1));
	}
	
	@After
	public void destroy() {
		board = null;
	}

}
