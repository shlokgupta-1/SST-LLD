public class DefaultRoomPricingPolicy implements RoomPricingPolicy {

    @Override
    public Money basePrice(String roomType) {

        switch (roomType) {
            case "SINGLE":
                return new Money(5000);
            case "DOUBLE":
                return new Money(3500);
            case "TRIPLE":
                return new Money(2500);
            default:
                throw new IllegalArgumentException("Invalid room type");
        }
    }
}