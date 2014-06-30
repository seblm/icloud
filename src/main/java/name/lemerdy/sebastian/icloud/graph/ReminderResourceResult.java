package name.lemerdy.sebastian.icloud.graph;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class ReminderResourceResult {
    public final String guid;
    public final int startX;
    public final int startY;
    public final List<Integer> steps;

    private boolean complete;

    public ReminderResourceResult(String guid, int startX, int startY) {
        this.guid = guid;
        this.startX = startX;
        this.startY = startY;
        this.steps = new ArrayList<>();
        this.complete = false;
    }

    @JsonIgnore
    public boolean isCompleted() {
        return complete;
    }

    void complete() {
        this.complete = true;
    }

    void addStep(int currentX) {
        int sum = steps.stream().mapToInt(step -> step).sum();
        steps.add(currentX - sum - startX);
    }
}
