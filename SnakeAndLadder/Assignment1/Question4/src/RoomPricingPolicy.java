public interface RoomPricingPolicy {
    Money basePrice(String roomType);
}