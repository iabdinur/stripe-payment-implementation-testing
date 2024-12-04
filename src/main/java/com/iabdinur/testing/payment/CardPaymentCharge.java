package com.iabdinur.testing.payment;

public class CardPaymentCharge {

    private final boolean wasCardDebited;

    public CardPaymentCharge(boolean wasCardDebited) {
        this.wasCardDebited = wasCardDebited;
    }

    public boolean wasCardDebited() {
        return wasCardDebited;
    }

    @Override
    public String toString() {
        return "CardPaymentCharge{" +
                "wasCardDebited=" + wasCardDebited +
                '}';
    }
}
