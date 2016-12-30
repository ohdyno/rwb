package ch3.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class RobotTest {

  @Test
  public void testRobot() {
    final Machine sorter = new Machine("Sorter", "left");
    sorter.put("chips");
    final Machine oven = new Machine("Oven", "middle");
    final Robot robot = new Robot();

    assertEquals("chips", sorter.bin());
    assertNull(oven.bin());
    assertNull(robot.location());
    assertNull(robot.bin());

    robot.moveTo(sorter);
    robot.pick();
    robot.moveTo(oven);
    robot.release();

    assertNull(robot.bin());
    assertEquals(oven, robot.location());
    assertNull(sorter.bin());
    assertEquals("chips", oven.bin());
  }
}
