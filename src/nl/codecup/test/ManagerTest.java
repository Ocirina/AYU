package nl.codecup.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.junit.*;

import nl.codecup.src.*;

public class ManagerTest {

	private Manager manager;
	
	@Before
	public void create() {
	}
	
	@Test (expected = IOException.class)
	public void testHandleInput() throws IOException {		
		throw new IOException();
	}
	
	@After
	public void cleanUp() {
		manager = null;
	}

}
