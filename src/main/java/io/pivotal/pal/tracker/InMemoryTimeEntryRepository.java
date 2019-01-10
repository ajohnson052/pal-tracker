package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {
  private Map<Long, TimeEntry> timeEntries = new HashMap<>();
  private long lastId = 0;

  public TimeEntry create(TimeEntry timeEntry) {
    long id = lastId += 1;
    timeEntry.setId(id);
    timeEntries.put(id, timeEntry);
    return timeEntry;
  }

  public TimeEntry find(long id) {
    return timeEntries.get(id);
  }

  public List<TimeEntry> list() {
    return new ArrayList(this.timeEntries.values());
  }

  public TimeEntry update(long id, TimeEntry timeEntry) {
    timeEntry.setId(id);
    timeEntries.replace(id, timeEntry);
    return find(id);
  }

  public void delete(long id) {
    timeEntries.remove(id);
  }
}
