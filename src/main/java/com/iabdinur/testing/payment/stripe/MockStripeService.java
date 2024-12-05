package com.iabdinur.testing.payment.stripe;

import com.iabdinur.testing.payment.CardPaymentCharge;
import com.iabdinur.testing.payment.CardPaymentCharger;
import com.iabdinur.testing.payment.Currency;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@ConditionalOnProperty(
        value = "stripe.enabled",
        havingValue = "false"
)
public class MockStripeService implements CardPaymentCharger {
    @Override
    public CardPaymentCharge chargeCard(String cardSource,
                                        BigDecimal amount,
                                        Currency currency,
                                        String description) {
        return new CardPaymentCharge(true);
    }
}
