package ch3.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class ReportTest {

  @Test
  public void testReport() throws IOException {
    final ArrayList<Machine> line = new ArrayList<>();
    line.add(new Machine("mixer", "left"));

    final Machine extruder = new Machine("extruder", "center");
    extruder.put("paste");
    line.add(extruder);

    final Machine oven = new Machine("oven", "right");
    oven.put("chips");
    line.add(oven);

    final Robot robot = new Robot();
    robot.moveTo(extruder);
    robot.pick();

    final StringWriter out = new StringWriter();
    Report.report(out, line, robot);

    final String expected =
        "FACTORY REPORT\n" +
            "Machine mixer\nMachine extruder\n" +
            "Machine oven bin=chips\n\n" +
            "Robot location=extruder bin=paste\n" +
            "========\n";

    assertEquals(expected, out.toString());
  }
}

