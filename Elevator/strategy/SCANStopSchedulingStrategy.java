package elevator.strategy;

import elevator.enums.Direction;
import elevator.model.Elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class SCANStopSchedulingStrategy implements StopSchedulingStrategy {

    @Override
    public List<Integer> getNextStops(Elevator elevator) {
        TreeSet<Integer> stops = new TreeSet<>(elevator.getPendingStops());
        int current = elevator.getCurrentFloor();
        Direction direction = elevator.getDirection();

        List<Integer> ordered = new ArrayList<>();

        if (direction == Direction.UP || direction == Direction.IDLE) {
            ordered.addAll(stops.tailSet(current, true));
            List<Integer> below = new ArrayList<>(stops.headSet(current, false));
            Collections.sort(below, Collections.reverseOrder());
            ordered.addAll(below);
        } else {
            List<Integer> above = new ArrayList<>(stops.headSet(current, true));
            Collections.sort(above, Collections.reverseOrder());
            ordered.addAll(above);
            ordered.addAll(stops.tailSet(current, false));
        }

        return ordered;
    }
}
