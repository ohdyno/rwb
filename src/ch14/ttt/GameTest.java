package ch14.ttt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GameTest {

  @Test
  public void testDefaultMove() {
    Game game = new Game("XOXOX-OXO");
    assertEquals(5, game.move('X'));

    game = new Game("XOXOXOOX-");
    assertEquals(8, game.move('O'));

    game = new Game("---------");
    assertEquals(0, game.move('X'));

    game = new Game("XXXXXXXXX");
    assertEquals(-1, game.move('X'));
  }

  @Test
  public void testFindWinningMove() {
    final Game game = new Game("XO-XX-OOX");
    assertEquals(5, game.move('X'));
  }

  @Test
  public void testWinConditions() {
    final Game game = new Game("---XXX---");
    assertEquals('X', game.winner());
  }
}