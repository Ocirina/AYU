package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import nl.codecup.src.*;

public class GroupTest {

	private Group group;
	
	@Before
	public void create() {
		group = new Group(0);
	}
	
	@Test
	public void testAddCoordinate() {
		assertTrue(group.addCoordinate("10,1"));
		
	}

}
