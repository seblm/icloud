package name.lemerdy.sebastian.icloud.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class ReminderResourceResult {
    public final String guid;
    public final int startX;
    public final int startY;
    public final List<Integer> steps;
    @JsonIgnore
    public final List<Integer> eachY;

    @JsonIgnore
    public int currentY;

    private boolean complete;

    public ReminderResourceResult(String guid, int startX, int startY) {
        this.guid = guid;
        this.startX = startX;
        this.startY = startY;
        this.steps = new ArrayList<>();
        this.eachY = new ArrayList<>();
        this.complete = false;
        this.currentY = startY;
    }

    @JsonIgnore
    public boolean isCompleted() {
        return complete;
    }

    void complete() {
        complete = true;
        Integer previousY = null;
        for (Integer y : eachY) {
            if (y.equals(previousY)) {
                int lastIndex = steps.size() - 1;
                steps.set(lastIndex, steps.get(lastIndex) + 1);
            } else {
                steps.add(1);
            }
            previousY = y;
        }
    }

    public void thisResourceHasComplete(ReminderResourceResult completedReminder) {
        if (currentY > completedReminder.currentY) {
            currentY--;
        }
        next();
    }

    public void aResourceHasBeenAdded() {
        next();
    }

    private void next() {
        eachY.add(currentY);
    }
}
