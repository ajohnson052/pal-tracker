package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
  @Autowired
  private TimeEntryRepository timeEntryRepository;
  private final DistributionSummary timeEntrySummary;
  private final Counter actionCounter;

  public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
    this.timeEntryRepository = timeEntryRepository;

    timeEntrySummary = meterRegistry.summary("timeEntry.summary");
    actionCounter = meterRegistry.counter("timeEntry.actionCounter");
  }

  @PostMapping()
  public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
      TimeEntry newEntry = timeEntryRepository.create(timeEntryToCreate);
      actionCounter.increment();
      timeEntrySummary.record(timeEntryRepository.list().size());

      return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TimeEntry> read(@PathVariable long id) {
    TimeEntry entry = timeEntryRepository.find(id);

    if(entry != null){
      actionCounter.increment();
      return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
    TimeEntry updatedEntry = timeEntryRepository.update(id, timeEntry);

    if(updatedEntry != null){
      actionCounter.increment();
      return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping()
  public ResponseEntity<List<TimeEntry>> list() {
    actionCounter.increment();
    return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable long id) {
    timeEntryRepository.delete(id);
    actionCounter.increment();
    timeEntrySummary.record(timeEntryRepository.list().size());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
