package elevator.model;

public class WeightSensor {

    public static final double MAX_WEIGHT_KG = 750.0;

    private double currentWeightKg;

    public WeightSensor() {
        this.currentWeightKg = 0.0;
    }

    public void addWeight(double kg) {
        this.currentWeightKg += kg;
    }

    public void removeWeight(double kg) {
        this.currentWeightKg = Math.max(0, this.currentWeightKg - kg);
    }

    public double getCurrentWeightKg() {
        return currentWeightKg;
    }

    public boolean isOverloaded() {
        return currentWeightKg > MAX_WEIGHT_KG;
    }

    @Override
    public String toString() {
        return String.format("WeightSensor{current=%.1f kg, max=%.1f kg, overloaded=%b}",
                currentWeightKg, MAX_WEIGHT_KG, isOverloaded());
    }
}
