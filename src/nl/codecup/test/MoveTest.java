package nl.codecup.test;

import static org.junit.Assert.*;
import nl.codecup.src.*;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class MoveTest {

	private Move move = null;
	@Before
	public void create() {
		move = new Move("C","5","D","5");
	}
	
	@Test
	public void testOriginXConverted() {
		assertEquals(move.getIndexOriginX(), 2);
	}
	
	@Test
	public void testOriginYConverted() {
		assertEquals(move.getIndexOriginY(), 4);
	}
	
	@Test
	public void testTargetXConverted() {
		assertEquals(move.getIndexTargetX(), 3);
	}

	@Test
	public void testTargetYConverted() {
		assertEquals(move.getIndexTargetY(), 4);
	}
	
	@Test
	public void testGetFrom() {
		assertEquals(move.getBoardOrigin(), move.getBoardOriginX()+move.getBoardOriginY());
	}
	
	@Test
	public void testGetTo() {
		assertEquals(move.getBoardTarget(), move.getBoardTargetX()+move.getBoardTargetY());
	}
	
	@After
	public void destroy() {
		move = null;
	}
}
