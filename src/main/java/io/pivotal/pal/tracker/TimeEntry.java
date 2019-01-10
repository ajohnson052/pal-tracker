package io.pivotal.pal.tracker;

import java.time.LocalDate;

public class TimeEntry {
  private long id;
  private long projectId;
  private long userId;
  private LocalDate date;
  private int hours;

  public TimeEntry() {}

  public TimeEntry(long projectId, long userId, LocalDate date, int hours) {
    this.projectId = projectId;
    this.userId = userId;
    this.date = date;
    this.hours = hours;
  }

  public TimeEntry(long id, long projectId, long userId, LocalDate date, int hours) {
    this(projectId, userId, date, hours);
    this.id = id;
  }

  public long getId() {
    return this.id;
  }
  public void setId(long id) { this.id = id; }

  public long getProjectId() { return this.projectId; }

  public long getUserId() { return this.userId; }

  public LocalDate getDate() { return this.date; }

  public int getHours() { return this.hours; }


  @Override
  public boolean equals(Object other) {
    if(this == other) {
      return true;
    }

    if(other == null) {
      return false;
    }

    if(!(other instanceof TimeEntry)){
      return false;
    }

    TimeEntry otherTimeEntry = (TimeEntry) other;
    return this.projectId == otherTimeEntry.projectId &&
        this.userId == otherTimeEntry.userId &&
        this.date.equals(otherTimeEntry.date) &&
        this.hours == otherTimeEntry.hours;
  }
}
