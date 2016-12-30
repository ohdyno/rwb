package ch8.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import org.junit.jupiter.api.Test;

// The clients are in one file for convenience;
// imagine them as non-test methods in separate client classes.

public class PersonClient {

  public void client1(final Writer out, final Person person) throws IOException {
    out.write(person.first);
    out.write(" ");
    if (person.middle != null) {
      out.write(person.middle);
      out.write(" ");
    }
    out.write(person.last);
  }

  public String client2(final Person person) {
    String result = person.last + ", " + person.first;
    if (person.middle != null) {
      result += " " + person.middle;
    }
    return result;
  }

  public void client3(final Writer out, final Person person) throws IOException {
    out.write(person.last);
    out.write(", ");
    out.write(person.first);
    if (person.middle != null) {
      out.write(" ");
      out.write(person.middle);
    }
  }

  public String client4(final Person person) {
    return person.last + ", " +
        person.first +
        ((person.middle == null) ? "" : " " + person.middle);
  }

  @Test
  public void testClients() throws IOException {
    final Person bobSmith = new Person("Smith", "Bob", null);
    final Person jennyJJones = new Person("Jones", "Jenny", "J");

    StringWriter out = new StringWriter();
    client1(out, bobSmith);
    assertEquals("Bob Smith", out.toString());

    out = new StringWriter();
    client1(out, jennyJJones);
    assertEquals("Jenny J Jones", out.toString());

    assertEquals("Smith, Bob", client2(bobSmith));
    assertEquals("Jones, Jenny J", client2(jennyJJones));

    out = new StringWriter();
    client3(out, bobSmith);
    assertEquals("Smith, Bob", out.toString());

    out = new StringWriter();
    client3(out, jennyJJones);
    assertEquals("Jones, Jenny J", out.toString());

    assertEquals("Smith, Bob", client4(bobSmith));
    assertEquals("Jones, Jenny J", client4(jennyJJones));
  }
}
