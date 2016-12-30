package ch13.reggie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Course {

  static String url = "jdbc:mysql://localhost:8889/Reggie";    // MySql
  static String username = "root";
  static String password = "root";

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();  // MySQL
    } catch (final Exception ignored) {
    }
  }

  private final String name;
  private final int credits;

  Course(final String name, final int credits) {
    this.name = name;
    this.credits = credits;
  }

  public static Course create(final String name, final int credits) throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();
      statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
      statement.executeUpdate("INSERT INTO course VALUES ('" + name + "', '" + credits
          + "');");
      return new Course(name, credits);
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public static Course find(final String name) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();
      final ResultSet result = statement.executeQuery("SELECT * FROM course WHERE name = '" + name
          + "';");
      if (!result.next()) {
        return null;
      }

      final int credits = result.getInt("Credits");
      return new Course(name, credits);
    } catch (final Exception ex) {
      return null;
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public void update() throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      statement.executeUpdate("DELETE FROM course WHERE name = '" + name + "';");
      statement.executeUpdate("INSERT INTO course VALUES('" + name + "','" + credits + "');");
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public int getCredits() {
    return credits;
  }

  public String getName() {
    return name;
  }
}
