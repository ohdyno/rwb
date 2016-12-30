package ch13.reggie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Report {

  Hashtable<Integer, ArrayList<String>> offeringToName = new Hashtable<>();

  public Report() {
  }

  public void populateMap() throws Exception {
    final Collection<Schedule> schedules = Schedule.all();
    for (final Iterator<Schedule> eachSchedule = schedules.iterator(); eachSchedule.hasNext(); ) {
      final Schedule schedule = (Schedule) eachSchedule.next();

      for (final Iterator<Offering> each = schedule.schedule.iterator(); each.hasNext(); ) {
        final Offering offering = (Offering) each.next();
        populateMapFor(schedule, offering);
      }
    }
  }

  private void populateMapFor(final Schedule schedule, final Offering offering) {
    ArrayList<String> list = offeringToName.get(new Integer(offering.getId()));
    if (list == null) {
      list = new ArrayList<>();
      offeringToName.put(new Integer(offering.getId()), list);
    }
    list.add(schedule.name);
  }

  public void writeOffering(final StringBuffer buffer, final ArrayList<String> list, final Offering offering) {
    buffer.append(offering.getCourse().getName() + " " + offering.getDaysTimes() + "\n");

    for (final Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
      final String s = (String) iterator.next();
      buffer.append("\t" + s + "\n");
    }
  }

  public void write(final StringBuffer buffer) throws Exception {
    populateMap();

    final Enumeration<Integer> enumeration = offeringToName.keys();
    while (enumeration.hasMoreElements()) {
      final Integer offeringId = (Integer) enumeration.nextElement();
      final ArrayList<String> list = offeringToName.get(offeringId);
      writeOffering(buffer, list, Offering.find(offeringId.intValue()));
    }

    buffer.append("Number of scheduled offerings: ");
    buffer.append(offeringToName.size());
    buffer.append("\n");
  }
}
