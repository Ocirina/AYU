package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.*;

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
		
		String validMoveFive ="2 D5-F5";
		assertTrue(io.isMoveFormat(validMoveFive));
		
		String invalidMoveOne = "D-F5";
		assertFalse(io.isMoveFormat(invalidMoveOne));
		
		String invalidMoveTwo = "D-F";
		assertFalse(io.isMoveFormat(invalidMoveTwo));
		
		String invalidMoveThree = "D5-F";
		assertFalse(io.isMoveFormat(invalidMoveThree));
		
		String invalidMoveFour = "";
		assertFalse(io.isMoveFormat(invalidMoveFour));
		
		String invalidMoveFive ="2 D-F5";
		assertFalse(io.isMoveFormat(invalidMoveFive));
	}
	
	@Test
	public void testOptionalPlayerInIsMoveFormat() {
		String withPlayer = "2 D5-F10";
		assertTrue(io.isMoveFormat(withPlayer));
		
		String withoutPlayer = "D5-F10";
		assertTrue(io.isMoveFormat(withPlayer));
		
		String withInvalidPlayer = "A D5-F10";
		assertFalse(io.isMoveFormat(withInvalidPlayer));
	}
	
	@After
	public void cleanUp() {
		io = null;
	}
}
