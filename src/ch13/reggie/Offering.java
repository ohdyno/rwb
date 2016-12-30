package ch13.reggie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Offering {

  static String url = "jdbc:mysql://localhost:8889/Reggie";    // MySql
  static String username = "root";
  static String password = "root";

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();  // MySQL
    } catch (final Exception ignored) {
    }
  }

  private final int id;
  private final Course course;
  private final String daysTimes;

  public Offering(final int id, final Course course, final String daysTimesCsv) {
    this.id = id;
    this.course = course;
    this.daysTimes = daysTimesCsv;
  }

  public static Offering create(final Course course, final String daysTimesCsv) throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      final ResultSet result = statement.executeQuery("SELECT MAX(id) FROM offering;");
      result.next();
      final int newId = 1 + result.getInt(1);

      statement.executeUpdate("INSERT INTO offering VALUES ('" + newId + "','"
          + course.getName() + "','" + daysTimesCsv + "');");
      return new Offering(newId, course, daysTimesCsv);
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public static Offering find(final int id) {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();
      final ResultSet result = statement.executeQuery("SELECT * FROM offering WHERE id =" + id
          + ";");
      if (result.next() == false) {
        return null;
      }

      final String courseName = result.getString("name");
      final Course course = Course.find(courseName);
      final String dateTime = result.getString("daysTimes");
      conn.close();

      return new Offering(id, course, dateTime);
    } catch (final Exception ex) {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
      return null;
    }
  }

  public void update() throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      statement.executeUpdate("DELETE FROM offering WHERE id=" + id + ";");
      statement.executeUpdate("INSERT INTO offering VALUES('" + id + "','" + course.getName()
          + "','" + daysTimes + "');");
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public int getId() {
    return id;
  }

  public Course getCourse() {
    return course;
  }

  public String getDaysTimes() {
    return daysTimes;
  }

  public String toString() {
    return "ch13.reggie.Offering " + getId() + ": " + getCourse() + " meeting " + getDaysTimes();
  }
}
