package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.*;

import nl.codecup.src.*;

public class MoveConverterTest {
	
	private MoveConverter converter;
	
	@Before
	public void create(){
		converter = new MoveConverter();
	}
	
	@Test
	public void testReadMove() {
		String originX = "D";
		String originY = "10";
		String destinationX = "F";
		String destinationY = "5";

		String move = "D10-F5";
		Move m = new Move(originX, originY, destinationX, destinationY);
		assertEquals(m.toString(), converter.readMove(move).toString());
	}
	
	@Test
	public void testIsMove() {
		String validMoveOne = "D5-F10";
		assertTrue(converter.isMoveFormat(validMoveOne));
		
		String validMoveTwo = "D10-F10";
		assertTrue(converter.isMoveFormat(validMoveTwo));

		String validMoveThree ="D10-F5";
		assertTrue(converter.isMoveFormat(validMoveThree));

		String validMoveFour ="D5-F5";
		assertTrue(converter.isMoveFormat(validMoveFour));
		
		String validMoveFive ="2 D5-F5";
		assertTrue(converter.isMoveFormat(validMoveFive));
		
		String invalidMoveOne = "D-F5";
		assertFalse(converter.isMoveFormat(invalidMoveOne));
		
		String invalidMoveTwo = "D-F";
		assertFalse(converter.isMoveFormat(invalidMoveTwo));
		
		String invalidMoveThree = "D5-F";
		assertFalse(converter.isMoveFormat(invalidMoveThree));
		
		String invalidMoveFour = "";
		assertFalse(converter.isMoveFormat(invalidMoveFour));
		
		String invalidMoveFive ="2 D-F5";
		assertFalse(converter.isMoveFormat(invalidMoveFive));
	}
	
	@Test
	public void testOptionalPlayerInIsMoveFormat() {
		String withPlayer = "2 D5-F10";
		assertTrue(converter.isMoveFormat(withPlayer));
		
		String withoutPlayer = "D5-F10";
		assertTrue(converter.isMoveFormat(withoutPlayer));
		
		String withInvalidPlayer = "A D5-F10";
		assertFalse(converter.isMoveFormat(withInvalidPlayer));
	}
	
	@After
	public void cleanUp() {
		converter = null;
	}
}
