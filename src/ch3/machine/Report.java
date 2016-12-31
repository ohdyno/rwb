package ch3.machine;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class Report {

  public static void report(final Writer out, final List<Machine> machines, final Robot robot)
      throws IOException {
    reportHeader(out);
    reportMachines(out, machines);
    reportRobot(out, robot);
    reportFooter(out);
  }

  private static void reportFooter(final Writer out) throws IOException {
    out.write("========\n");
  }

  private static void reportRobot(final Writer out, final Robot robot) throws IOException {
    out.write("Robot");
    if (robot.location() != null) {
      out.write(" location=" + robot.location().name());
    }

    if (robot.bin() != null) {
      out.write(" bin=" + robot.bin());
    }

    out.write("\n");
  }

  private static void reportMachines(final Writer out, final List<Machine> machines) throws IOException {
    for (final Machine machine : machines) {
      out.write("Machine " + machine.name());

      if (machine.bin() != null) {
        out.write(" bin=" + machine.bin());
      }

      out.write("\n");
    }
    out.write("\n");
  }

  private static void reportHeader(final Writer out) throws IOException {
    out.write("FACTORY REPORT\n");
  }
}