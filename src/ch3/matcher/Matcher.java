package ch3.matcher;

public class Matcher {

  public Matcher() {
  }

  public boolean match(final int[] expected, final int[] actual,
      final int clipLimit, final int delta) {

    // Clip "too-large" values
    for (int i = 0; i < actual.length; i++) {
      if (actual[i] > clipLimit) {
        actual[i] = clipLimit;
      }
    }

    // Check for length differences
    if (actual.length != expected.length) {
      return false;
    }

    // Check that each entry within expected +/- delta
    for (int i = 0; i < actual.length; i++) {
      if (Math.abs(expected[i] - actual[i]) > delta) {
        return false;
      }
    }

    return true;
  }
}