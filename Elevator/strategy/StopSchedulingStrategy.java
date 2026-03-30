package elevator.strategy;

import elevator.model.Elevator;

import java.util.List;

public interface StopSchedulingStrategy {
    List<Integer> getNextStops(Elevator elevator);
}
