package elevator.controller;

import elevator.enums.Direction;
import elevator.enums.ElevatorStatus;
import elevator.model.Elevator;
import elevator.model.ExternalRequest;
import elevator.model.InternalRequest;
import elevator.observer.BuildingObserver;
import elevator.state.IdleState;
import elevator.state.MaintenanceState;
import elevator.strategy.ElevatorSelectionStrategy;
import elevator.strategy.StopSchedulingStrategy;

import java.util.List;

public class ElevatorController implements BuildingObserver {

    private final List<Elevator> elevators;
    private final ElevatorSelectionStrategy selectionStrategy;
    private final StopSchedulingStrategy schedulingStrategy;

    public ElevatorController(List<Elevator> elevators,
                              ElevatorSelectionStrategy selectionStrategy,
                              StopSchedulingStrategy schedulingStrategy) {
        this.elevators = elevators;
        this.selectionStrategy = selectionStrategy;
        this.schedulingStrategy = schedulingStrategy;
    }

    public void handleExternalRequest(ExternalRequest request) {
        System.out.println("\n📥 External request received: " + request);

        Elevator chosen = selectionStrategy.selectElevator(elevators, request);

        if (chosen == null) {
            System.out.println("  ❌ No elevator available for: " + request);
            return;
        }

        System.out.println("  ✅ Assigned to Elevator-" + chosen.getId());
        chosen.addStop(request.getSourceFloor());
        dispatchElevator(chosen);
    }

    public void handleInternalRequest(InternalRequest request) {
        System.out.println("\n📥 Internal request: " + request);

        Elevator elevator = getElevatorById(request.getElevatorId());
        if (elevator == null) {
            System.out.println("  ❌ Elevator-" + request.getElevatorId() + " not found.");
            return;
        }
        if (elevator.getStatus() == ElevatorStatus.MAINTENANCE) {
            System.out.println("  ❌ Elevator-" + request.getElevatorId() + " is under maintenance.");
            return;
        }

        elevator.addStop(request.getDestinationFloor());
        dispatchElevator(elevator);
    }

    public void dispatchElevator(Elevator elevator) {
        if (elevator.getPendingStops().isEmpty()) {
            return;
        }

        System.out.println("\n🚀 Dispatching Elevator-" + elevator.getId()
                + " | Current floor: " + elevator.getCurrentFloor()
                + " | Stops: " + elevator.getPendingStops());

        List<Integer> orderedStops = schedulingStrategy.getNextStops(elevator);
        System.out.println("  📋 Scheduled stop order: " + orderedStops);

        elevator.getPendingStops().clear();
        for (int stop : orderedStops) {
            elevator.getPendingStops().add(stop);
        }

        while (!elevator.getPendingStops().isEmpty()) {
            int nextStop = peekNextStop(elevator);
            setDirectionToward(elevator, nextStop);
            elevator.move();
        }

        System.out.println("  🏁 Elevator-" + elevator.getId()
                + " finished all stops at floor " + elevator.getCurrentFloor());
    }

    @Override
    public void onFloorMaintenanceChanged(int floorNumber, boolean underMaintenance) {
        System.out.println("\n🔧 Floor-" + floorNumber + " maintenance=" + underMaintenance + " (notified controller)");
    }

    @Override
    public void onElevatorMaintenanceChanged(int elevatorId, boolean underMaintenance) {
        Elevator elevator = getElevatorById(elevatorId);
        if (elevator == null) return;

        if (underMaintenance) {
            System.out.println("\n🔧 Elevator-" + elevatorId + " going into maintenance.");
            elevator.setState(new MaintenanceState());
            elevator.setStatus(ElevatorStatus.MAINTENANCE);
            elevator.getPendingStops().clear();
        } else {
            System.out.println("\n✅ Elevator-" + elevatorId + " returning from maintenance.");
            elevator.setState(new IdleState());
            elevator.setStatus(ElevatorStatus.IDLE);
            elevator.setDirection(Direction.IDLE);
        }
    }

    private void setDirectionToward(Elevator elevator, int targetFloor) {
        if (targetFloor > elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.UP);
        } else if (targetFloor < elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.DOWN);
        }
    }

    private int peekNextStop(Elevator elevator) {
        if (elevator.getDirection() == Direction.DOWN) {
            return elevator.getPendingStops().last();
        }
        return elevator.getPendingStops().first();
    }

    private Elevator getElevatorById(int id) {
        return elevators.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
