package ch3.matcher;

public class Matcher {

  public boolean match(final int[] expected, final int[] actual,
      final int clipLimit, final int delta) {

    clipLargeValues(actual, clipLimit);

    if (isDifferentLength(expected, actual)) {
      return false;
    }

    return allEntriesWithinExpectedDelta(expected, actual, delta);
  }

  private boolean allEntriesWithinExpectedDelta(final int[] expected, final int[] actual, final int delta) {
    for (int i = 0; i < actual.length; i++) {
      if (Math.abs(expected[i] - actual[i]) > delta) {
        return false;
      }
    }
    return true;
  }

  private boolean isDifferentLength(final int[] expected, final int[] actual) {
    return actual.length != expected.length;
  }

  private void clipLargeValues(final int[] actual, final int clipLimit) {
    for (int i = 0; i < actual.length; i++) {
      if (actual[i] > clipLimit) {
        actual[i] = clipLimit;
      }
    }
  }
}