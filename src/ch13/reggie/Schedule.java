package ch13.reggie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class Schedule {

  static final int minCredits = 12;
  static final int maxCredits = 18;
  static String url = "jdbc:mysql://localhost:8889/Reggie"; // MySql
  static String username = "root";
  static String password = "root";

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();  // MySQL
    } catch (final Exception ignored) {
    }
  }

  String name;
  int credits = 0;
  boolean overloadAuthorized = false;
  ArrayList<Offering> schedule = new ArrayList<>();

  public Schedule(final String name) {
    this.name = name;
  }

  public static void deleteAll() throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      statement.executeUpdate("DELETE FROM schedule;");
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public static Schedule create(final String name) throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");
      return new Schedule(name);
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public static Schedule find(final String name) {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();
      final ResultSet result = statement.executeQuery("SELECT * FROM schedule WHERE name= '" + name
          + "';");

      final Schedule schedule = new Schedule(name);

      while (result.next()) {
        final int offeringId = result.getInt("offeringId");
        final Offering offering = Offering.find(offeringId);
        schedule.add(offering);
      }

      return schedule;
    } catch (final Exception ex) {
      return null;
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public static Collection<Schedule> all() throws Exception {
    final ArrayList<Schedule> result = new ArrayList<>();
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();
      final ResultSet results = statement.executeQuery("SELECT DISTINCT name FROM schedule ORDER BY name;");

      while (results.next()) {
        result.add(Schedule.find(results.getString("name")));
      }
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }

    return result;
  }

  public void update() throws Exception {
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url, username, password);
      final Statement statement = conn.createStatement();

      statement.executeUpdate("DELETE FROM schedule WHERE name = '" + name + "';");

      for (int i = 0; i < schedule.size(); i++) {
        final Offering offering = (Offering) schedule.get(i);
        statement.executeUpdate("INSERT INTO schedule VALUES('" + name + "','"
            + offering.getId() + "');");
      }
    } finally {
      try {
        conn.close();
      } catch (final Exception ignored) {
      }
    }
  }

  public void add(final Offering offering) {
    credits += offering.getCourse().getCredits();
    schedule.add(offering);
  }

  public void authorizeOverload(final boolean authorized) {
    overloadAuthorized = authorized;
  }

  public List<String> analysis() {
    final ArrayList<String> result = new ArrayList<>();

    if (credits < minCredits) {
      result.add("Too few credits");
    }

    if (credits > maxCredits && !overloadAuthorized) {
      result.add("Too many credits");
    }

    checkDuplicateCourses(result);

    checkOverlap(result);

    return result;
  }

  public void checkDuplicateCourses(final ArrayList<String> analysis) {
    final HashSet<Course> courses = new HashSet<>();
    for (int i = 0; i < schedule.size(); i++) {
      final Course course = ((Offering) schedule.get(i)).getCourse();
      if (courses.contains(course)) {
        analysis.add("Same course twice - " + course.getName());
      }
      courses.add(course);
    }
  }

  public void checkOverlap(final ArrayList<String> analysis) {
    final HashSet<String> times = new HashSet<>();

    for (final Iterator<Offering> iterator = schedule.iterator(); iterator.hasNext(); ) {
      final Offering offering = (Offering) iterator.next();
      final String daysTimes = offering.getDaysTimes();
      final StringTokenizer tokens = new StringTokenizer(daysTimes, ",");
      while (tokens.hasMoreTokens()) {
        final String dayTime = tokens.nextToken();
        if (times.contains(dayTime)) {
          analysis.add("ch13.reggie.Course overlap - " + dayTime);
        }
        times.add(dayTime);
      }
    }
  }

  public String toString() {
    return "ch13.reggie.Schedule " + name + ": " + schedule;
  }
}
