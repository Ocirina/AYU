package nl.codecup.test;
import static org.junit.Assert.*;

import org.junit.*;

import nl.codecup.src.*;

public class PlayerTest {

	private Player player;
	private Manager manager;
	private int y;
	
	@Before
	public void create() {
		manager = new Manager();
		player = new Player(manager, y);
	}
	
	@Test
	public void testSetMove() {
		
	}

}
