package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import nl.codecup.src.*;

public class IOTest {
	
	private IO io;
	
	@Before
	public void create(){
		io = new IO();
	}
	
	@Test
	public void testReadMove() {
		String originX = "D";
		String originY = "10";
		String destinationX = "F";
		String destinationY = "5";

		String move = "D10-F5";
		Move m = new Move(originX, originY, destinationX, destinationY);
		assertEquals(m.toString(), io.readMove(move).toString());
	}
	
	@Test
	public void testIsMove() {
		String validMoveOne = "D5-F10";
		assertTrue(io.isMoveFormat(validMoveOne));
		
		String validMoveTwo = "D10-F10";
		assertTrue(io.isMoveFormat(validMoveTwo));

		String validMoveThree ="D10-F5";
		assertTrue(io.isMoveFormat(validMoveThree));

		String validMoveFour ="D5-F5";
		assertTrue(io.isMoveFormat(validMoveFour));
		
		String invalidMoveOne = "D-F5";
		assertFalse(io.isMoveFormat(invalidMoveOne));
		
		String invalidMoveTwo = "D-F";
		assertFalse(io.isMoveFormat(invalidMoveTwo));
		
		String invalidMoveThree = "D5-F";
		assertFalse(io.isMoveFormat(invalidMoveThree));
		
		String invalidMoveFour = "";
		assertFalse(io.isMoveFormat(invalidMoveFour));
	}
	
	@After
	void cleanUp() {
		io = null;
	}

}
