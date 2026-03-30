package elevator.strategy;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.model.Elevator;
import elevator.model.ExternalRequest;

import java.util.List;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest request) {
        Elevator best = null;
        int bestScore = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {

            if (elevator.getStatus() == ElevatorStatus.MAINTENANCE) {
                continue;
            }

            if (elevator.getWeightSensor().isOverloaded()) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - request.getSourceFloor());

            boolean sameDirection = elevator.getDirection() == request.getDirection();
            boolean onCorrectSide =
                    (request.getDirection() == Direction.UP
                            && elevator.getCurrentFloor() <= request.getSourceFloor())
                    || (request.getDirection() == Direction.DOWN
                            && elevator.getCurrentFloor() >= request.getSourceFloor());

            boolean isIdle = elevator.getStatus() == ElevatorStatus.IDLE
                    || elevator.getDirection() == Direction.IDLE;

            int score;
            if (isIdle) {
                score = distance;
            } else if (sameDirection && onCorrectSide) {
                score = distance - 5;
            } else {
                score = distance + 20;
            }

            if (score < bestScore) {
                bestScore = score;
                best = elevator;
            }
        }

        return best;
    }
}
