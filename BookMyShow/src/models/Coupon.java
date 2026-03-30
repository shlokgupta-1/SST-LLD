package models;

import java.time.LocalDateTime;
import java.util.UUID;

public class Coupon {
    private final String id;
    private final String code;
    private final double discountPercent;
    private final double maxDiscountAmount;
    private final LocalDateTime validTill;
    private boolean used;

    public Coupon(String code, double discountPercent,
                  double maxDiscountAmount, LocalDateTime validTill) {
        this.id                = UUID.randomUUID().toString();
        this.code              = code;
        this.discountPercent   = discountPercent;
        this.maxDiscountAmount = maxDiscountAmount;
        this.validTill         = validTill;
        this.used              = false;
    }

    public String getId()              { return id; }
    public String getCode()            { return code; }
    public double getDiscountPercent() { return discountPercent; }
    public double getMaxDiscountAmount() { return maxDiscountAmount; }
    public LocalDateTime getValidTill()  { return validTill; }
    public boolean isUsed()            { return used; }

    public boolean isValid() {
        return !used && LocalDateTime.now().isBefore(validTill);
    }

    public double apply(double amount) {
        if (!isValid()) return amount;
        double discount = Math.min(amount * discountPercent / 100.0, maxDiscountAmount);
        this.used = true;
        return amount - discount;
    }

    @Override
    public String toString() {
        return "Coupon{code=" + code + ", discount=" + discountPercent + "%, valid=" + isValid() + "}";
    }
}
