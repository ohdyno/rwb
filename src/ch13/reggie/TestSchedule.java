package ch13.reggie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

public class TestSchedule {

  @Test
  public void minCredits() {
    final Schedule schedule = new Schedule("name");
    final List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("Too few credits"));
  }

  @Test
  public void justEnoughCredits() {
    final Course cs110 = new Course("CS110", 11);
    final Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
    Schedule schedule = new Schedule("name");
    schedule.add(mwf10);
    List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("Too few credits"));

    schedule = new Schedule("name");
    final Course cs101 = new Course("CS101", 12);
    final Offering th11 = new Offering(1, cs101, "T11,H11");
    schedule.add(th11);
    analysis = schedule.analysis();
    assertEquals(0, analysis.size());
  }

  @Test
  public void maxCredits() {
    final Course cs110 = new Course("CS110", 20);
    final Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
    final Schedule schedule = new Schedule("name");
    schedule.add(mwf10);
    List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("Too many credits"));

    schedule.authorizeOverload(true);
    analysis = schedule.analysis();
    assertEquals(0, analysis.size());
  }

  @Test
  public void justBelowMax() {
    final Course cs110 = new Course("CS110", 19);
    final Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
    Schedule schedule = new Schedule("name");
    schedule.add(mwf10);
    List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("Too many credits"));

    schedule = new Schedule("name");
    final Course cs101 = new Course("CS101", 18);
    final Offering th11 = new Offering(1, cs101, "T11,H11");
    schedule.add(th11);
    analysis = schedule.analysis();
    assertEquals(0, analysis.size());
  }

  @Test
  public void dupCourses() {
    final Course cs110 = new Course("CS110", 6);
    final Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
    final Offering th11 = new Offering(1, cs110, "T11,H11");
    final Schedule schedule = new Schedule("name");
    schedule.add(mwf10);
    schedule.add(th11);
    final List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("Same course twice - CS110"));
  }

  @Test
  public void overlap() {
    final Schedule schedule = new Schedule("name");

    final Course cs110 = new Course("CS110", 6);
    final Offering mwf10 = new Offering(1, cs110, "M10,W10,F10");
    schedule.add(mwf10);

    final Course cs101 = new Course("CS101", 6);
    final Offering mixed = new Offering(1, cs101, "M10,W11,F11");
    schedule.add(mixed);

    List<String> analysis = schedule.analysis();
    assertEquals(1, analysis.size());
    assertTrue(analysis.contains("ch13.reggie.Course overlap - M10"));

    final Course cs102 = new Course("CS102", 1);
    final Offering mixed2 = new Offering(1, cs102, "M9,W10,F11");
    schedule.add(mixed2);

    analysis = schedule.analysis();
    assertEquals(3, analysis.size());
    assertTrue(analysis.contains("ch13.reggie.Course overlap - M10"));
    assertTrue(analysis.contains("ch13.reggie.Course overlap - W10"));
    assertTrue(analysis.contains("ch13.reggie.Course overlap - F11"));
  }

  @Test
  public void courseCreate() throws Exception {
    final Course c = Course.create("CS202", 1);
    assertEquals("CS202", c.getName());
    assertEquals(1, c.getCredits());

    final Course c2 = Course.find("CS202");
    assertEquals("CS202", c2.getName());

    final Course c3 = Course.find("Nonexistent");
    assertNull(c3);
  }

  @Test
  public void offeringCreate() throws Exception {
    final Course c = Course.create("CS202", 2);
    final Offering offering = Offering.create(c, "M10");
    assertNotNull(offering);
  }

  @Test
  public void persistentSchedule() throws Exception {
    final Schedule s = Schedule.create("Bob");
    assertNotNull(s);
  }

  @Test
  public void scheduleUpdate() throws Exception {
    final Course cs101 = Course.create("CS101", 3);
    cs101.update();
    final Offering off1 = Offering.create(cs101, "M10");
    off1.update();
    final Offering off2 = Offering.create(cs101, "T9");
    off2.update();

    final Schedule s = Schedule.create("Bob");
    s.add(off1);
    s.add(off2);
    s.update();

    final Schedule s2 = Schedule.create("Alice");
    s2.add(off1);
    s2.update();

    final Schedule s3 = Schedule.find("Bob");
    assertEquals(2, s3.schedule.size());

    final Schedule s4 = Schedule.find("Alice");
    assertEquals(1, s4.schedule.size());
  }
}
