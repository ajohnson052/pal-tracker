package io.pivotal.pal.tracker;

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

  public TimeEntryController(TimeEntryRepository timeEntryRepository) {
    this.timeEntryRepository = timeEntryRepository;
  }

  @PostMapping()
  public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
      TimeEntry newEntry = timeEntryRepository.create(timeEntryToCreate);

      return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TimeEntry> read(@PathVariable long id) {
    TimeEntry entry = timeEntryRepository.find(id);

    if(entry != null){
      return new ResponseEntity<>(entry, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
    TimeEntry updatedEntry = timeEntryRepository.update(id, timeEntry);

    if(updatedEntry != null){
      return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping()
  public ResponseEntity<List<TimeEntry>> list() {
    return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable long id) {
    timeEntryRepository.delete(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
