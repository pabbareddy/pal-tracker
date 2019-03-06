package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TimeEntryController {

    private final Counter actionCounter;
    @Autowired
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;

    public TimeEntryController(TimeEntryRepository timeEntryRepository ,
                               MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }


    
     @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody  TimeEntry timeEntryToCreate) {
        timeEntryToCreate = timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity(timeEntryToCreate, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value = "id",required = true) long id) {

         TimeEntry timeEntry = timeEntryRepository.find(id);
         if (timeEntry == null){
             return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
         }else {
             return new ResponseEntity(timeEntry, HttpStatus.OK);
         }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> list = timeEntryRepository.list();
;        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable(value = "id",required = true) long timeEntryId, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId,expected);
        if (timeEntry == null){
            return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity(timeEntry, HttpStatus.OK);
        }
    }
    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(value = "id",required = true) long timeEntryId) {
         timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
