package name.lemerdy.sebastian.icloud.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.time.ZonedDateTime;
import java.util.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/reminders")
public class ReminderResource {
    private final SortedMap<ZonedDateTime, String> guidsSortedByDate;
    private Set<ReminderResourceResult> reminderResourceResults;

    public ReminderResource(Reminders reminders) {
        this.guidsSortedByDate = guidsSortedByDates(reminders.reminders);
        this.reminderResourceResults = new LinkedHashSet<>();
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

    @GET
    @Produces(APPLICATION_JSON)
    public Set<ReminderResourceResult> computeGraph() {
        int x = 0;
        int y = 0;

        for (String guid : guidsSortedByDate.values()) {
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
