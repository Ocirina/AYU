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
		assertEquals(move.getOriginXConverted(), 2);
	}
	
	@Test
	public void testOriginYConverted() {
		assertEquals(move.getOriginYConverted(), 4);
	}
	
	@Test
	public void testTargetXConverted() {
		assertEquals(move.getTargetXConverted(), 3);
	}

	@Test
	public void testTargetYConverted() {
		assertEquals(move.getTargetYConverted(), 4);
	}
	
	@Test
	public void testGetFrom() {
		assertEquals(move.getFrom(), move.getOriginX()+move.getOriginY());
	}
	
	@Test
	public void testGetTo() {
		assertEquals(move.getTo(), move.getTargetX()+move.getTargetY());
	}
	
	@After
	public void destroy() {
		move = null;
	}
}
