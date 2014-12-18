package nl.codecup.test;
import static org.junit.Assert.*;

import org.junit.*;

import nl.codecup.src.*;

public class PlayerTest {
	private static final int NONE = 0;
	private static final int WHITE = 1;
	private static final int BLACK = 2;
	
	private GameState gameState = new GameState(WHITE, BLACK);
	private Manager manager = new Manager();
	private Player player = new Player(gameState, WHITE);
	private Board board = new Board(new int[][] {
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ NONE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ WHITE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE },
			{ WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE, NONE, WHITE },
			{ NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE, BLACK, NONE }});
	private int y;
	
	@Before
	public void create() {
		gameState.setBoard(board);
	}
	
	@Test
	public void testChooseMove() {
		Move move = player.chooseMove();
		/* This move should be done 
		assertTrue(move.getOriginX().equals("B"));
		assertTrue(move.getOriginY().equals("3"));
		assertTrue(move.getTargetX().equals("B"));
		assertTrue(move.getTargetY().equals("5"));*/
	}
	
	@After
	public void destroy() {
		player = null;
	}

}
