package ch10.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QueueTest {

  @Test
  public void testQ() {
    final Queue q = new Queue();
    q.addRear("E1");
    q.addRear("E2");
    assertEquals("E1", q.removeFront());
    assertEquals("E2", q.removeFront());
    assertEquals(0, q.getSize());
  }

}