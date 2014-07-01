package name.lemerdy.sebastian.icloud.graph;

import name.lemerdy.sebastian.icloud.model.Reminder;
import name.lemerdy.sebastian.icloud.model.Reminders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.time.ZonedDateTime;
import java.util.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/reminders")
public class RemindersResource {
    private final Set<ReminderResourceResult> reminderResourceResults = new LinkedHashSet<>();

    public RemindersResource(Reminders reminders) {
        computeGraph(guidsSortedByDates(reminders.reminders));
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Set<ReminderResourceResult> reminders() {
        return reminderResourceResults;
    }

    private SortedMap<ZonedDateTime, String> guidsSortedByDates(List<Reminder> reminders) {
        SortedMap<ZonedDateTime, String> guidsSortedByDates = new TreeMap<>();
        for (Reminder reminder : reminders) {
            guidsSortedByDates.put(reminder.createdDate, reminder.guid);
            if (reminder.completedDate != null) {
                guidsSortedByDates.put(reminder.completedDate, reminder.guid);
            }
        }
        return guidsSortedByDates;
    }

    private Set<ReminderResourceResult> computeGraph(SortedMap<ZonedDateTime, String> guidsSortedByDates) {
        int x = 0;
        int y = 0;

        for (String guid : guidsSortedByDates.values()) {
            ReminderResourceResult reminderResourceResult = getOrCreateGraphResourceResult(x, y, guid);
            reminderResourceResults.add(reminderResourceResult);
            y += (reminderResourceResult.isCompleted() ? -1 : 1);
            x += 1;
        }

        addStepForEachNotCompleted(x);

        return reminderResourceResults;
    }

    private ReminderResourceResult getOrCreateGraphResourceResult(int x, int y, String guid) {
        return reminderResourceResults.stream()
                .filter(graphResource -> graphResource.guid.equals(guid))
                .limit(1)
                .peek(ReminderResourceResult::complete)
                .peek(graphResource -> {
                    graphResource.addStep(x);
                    addStepForEachNotCompleted(x);
                })
                .findFirst()
                .orElse(new ReminderResourceResult(guid, x, y));
    }

    private void addStepForEachNotCompleted(int x) {
        reminderResourceResults.stream()
                .filter(graphResource -> !graphResource.isCompleted())
                .forEach(graphResource -> graphResource.addStep(x));
    }
}
