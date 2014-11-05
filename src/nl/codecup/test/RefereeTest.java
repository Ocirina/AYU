package nl.codecup.test;

import static org.junit.Assert.*;
import nl.codecup.src.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class RefereeTest {
	private static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	
	private GameState gameState = new GameState(WHITE, BLACK);
	private Manager manager = new Manager(true);
	private Referee referee = new Referee(manager);
	private Player player = new Player(gameState, WHITE, referee);
	private Board board = new Board(new int[][] {
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE }});
	
	@Before
	public void create() {
		gameState.setBoard(board);
		manager.setGameState(gameState);
		manager.setPlayer(player);
	}
	
	@Test
	public void testDoMoveOfOtherPlayer() {
		Move move = new Move("A","10","B","10");
		assertFalse(referee.validMove(move));
	}
	
	@Test
	public void testValidMoves() {
		Move move = new Move("B","1","B","2");
		assertTrue(referee.validMove(move));
		board.movePiece(move);
	
		move = new Move("B","2","B","4");
		assertTrue(referee.validMove(move));
		board.movePiece(move);
		
		move = new Move("B","5","B","2");
		assertTrue(referee.validMove(move));
		
		move = new Move("D","5","C","5");
		assertTrue(referee.validMove(move));
		board.movePiece(move);
		
		move = new Move("B","3","D","5");
		assertTrue(referee.validMove(move));
		board.movePiece(move);
	}
	
	@Test
	public void testInvalidMoves() {
		board.movePiece(new Move("B","1","B","2"));
		Move move = new Move("B","2","B","1");
		assertFalse(referee.validMove(move));
	
		move = new Move("B","3","B","4");
		assertFalse(referee.validMove(move));
		
		board.movePiece(new Move("B","2","B","4"));
		move = new Move("B","4","B","2");
		assertFalse(referee.validMove(move));
		
		board.movePiece(new Move("D","5","C","5"));
		move = new Move("B","5","B","2");
		assertFalse(referee.validMove(move));
		
		move = new Move("B","4","D","5");
		assertFalse(referee.validMove(move));
	}
	
	@After
	public void destroy() {
		board   = null;
		manager = null;
		referee = null;
	}

}
