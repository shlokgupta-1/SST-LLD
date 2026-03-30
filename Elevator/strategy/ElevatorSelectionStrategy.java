package elevator.strategy;

import elevator.model.Elevator;
import elevator.model.ExternalRequest;

import java.util.List;

public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, ExternalRequest request);
}
