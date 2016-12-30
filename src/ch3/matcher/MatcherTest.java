package ch3.matcher;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatcherTest {

  @Test
  public void testMatch() {
    final Matcher matcher = new Matcher();

    final int[] expected = new int[]{10, 50, 30, 98};
    final int clipLimit = 100;
    final int delta = 5;

    int[] actual = new int[]{12, 55, 25, 110};

    assertTrue(matcher.match(expected, actual, clipLimit, delta));

    actual = new int[]{10, 60, 30, 98};
    assertTrue(!matcher.match(expected, actual, clipLimit, delta));

    actual = new int[]{10, 50, 30};
    assertTrue(!matcher.match(expected, actual, clipLimit, delta));
  }
}
