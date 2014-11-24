package nl.codecup.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import nl.codecup.src.*;

public class GameStateTest {

	private GameState gameState;
	private Board board;
	
	@Before
	public void create() {
		Player.piece = 1;
		board = new Board();
		gameState = new GameState(board, 1, 2);
	}
	
	@Test
	public void testRemainingGroups() {
		assertEquals(gameState.getGroupsLength(), 30);
	}
	
	@Test
	public void testAyuGroups() {
		Move move = new Move("B","1","B","2");
		gameState = gameState.makeMove(move);
		
		assertEquals(gameState.getIndexesOfAyuGroups().length, 1);
	}
	
	@Test
	public void testMakeMove() {
		assertEquals(gameState.getBoardContents()[1][1], 0);
		
		Move move = new Move("B","1","B","2");
		gameState = gameState.makeMove(move);
		
		assertEquals(gameState.getGroupsLength(), 29);
		assertEquals(gameState.getIndexesOfAyuGroups().length, 1);
		assertEquals(gameState.getBoardContents()[1][1], Player.piece);
	}
	
	@Test
	
	@After
	public void destroy() {
		board = null;
		gameState = null;
	}

}
