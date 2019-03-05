package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    public InMemoryTimeEntryRepository() {
        timesheets= new ArrayList<>();
    }

    public  List<TimeEntry> timesheets= null;
    public  int counter = 0;
    @Override
    public TimeEntry create(TimeEntry any) {
        counter++;
        any.setId(counter);
        timesheets.add(any);
        return any;

    }

    @Override
    public TimeEntry find(long timeEntryId) {
        for (TimeEntry timeEntry  : timesheets){
            if (timeEntry.getId() == timeEntryId){
                return  timeEntry;
            }
        }
        return null;
    }

    @Override
    public void delete(long timeEntryId) {
        for (TimeEntry timeEntry  : timesheets){
            if (timeEntry.getId() == timeEntryId){
                timesheets.remove(timeEntry);
                break;
            }
        }
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        for (TimeEntry timeEntry  : timesheets){
            if (timeEntry.getId() == eq){
                timesheets.remove(timeEntry);
                any.setId(eq);
                timesheets.add(any);
                return  any;
            }
        }
        return null;
    }

    @Override
    public List<TimeEntry> list() {
        return timesheets;
    }


}
