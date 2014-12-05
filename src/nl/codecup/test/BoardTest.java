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
	public void testMovePiece() {
		assertEquals(board.getBoardContents()[1][0], 1);
		assertEquals(board.getBoardContents()[1][1], 0);
		
		board.movePiece(new Move("B","1","B","2"));
		
		assertEquals(board.getBoardContents()[1][0], 0);
		assertEquals(board.getBoardContents()[1][1], 1);
	}
	
	@Test
	public void testConvertPiece() {
		assertEquals(board.convertPiece(1), "W");
		assertEquals(board.convertPiece(2), "B");
		assertEquals(board.convertPiece(0), " ");		
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
	
	@Test
	public void testGetNeighborsByPiece() {
		board.movePiece(new Move("B","1","B","2"));
		board.movePiece(new Move("B","2","B","4"));
		assertEquals(board.getNeighborsByPiece(1,3,1).length, 2);
		assertEquals(board.getNeighborsByPiece(1,4,1).length, 1);
		assertEquals(board.getNeighborsByPiece(1,2,1).length, 1);
	}
	
	@Test
	public void testIsNeighbour() {
		board.movePiece(new Move("B","1","B","2"));
		board.movePiece(new Move("B","2","B","4"));
		assertTrue(board.isNeighbour(1, 3, 1, 2));
		assertFalse(board.isNeighbour(1, 3, 1, 1));
	}
	
	@After
	public void destroy() {
		board = null;
	}

}
