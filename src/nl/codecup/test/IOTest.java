/**
 * 
 */
package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nl.codecup.src.*;

/**
 * @author Patrick
 *
 */
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
		
	}

}
