package com.iabdinur.testing.payment;

import ch.qos.logback.core.util.COWArrayList;
import com.iabdinur.testing.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private static final List<Currency> ACCEPTED_CURRENCIES = List.of(Currency.USD, Currency.GBP);

    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;
    private final CardPaymentCharger cardPaymentCharger;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, PaymentRepository paymentRepository, CardPaymentCharger cardPaymentCharger) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.cardPaymentCharger = cardPaymentCharger;
    }


    void chargeCard(UUID customerId, PaymentRequest paymentRequest) {
        // 1. Does customer exists if not throw
        boolean wasCustomerFound = customerRepository.findById(customerId).isPresent();
        if (!wasCustomerFound) {
            throw new IllegalStateException(String.format("Customer with id [%s] not found", customerId));
        }

        // 2. Do we support the currency if not throw
        boolean wasCurrencySupported = ACCEPTED_CURRENCIES.contains(paymentRequest.getPayment().getCurrency());

        if (!wasCurrencySupported) {
            String message = String.format(
                    "Currency [%s] not supported",
                    paymentRequest.getPayment().getCurrency());
            throw new IllegalStateException(message);
        }

        // 3. Charge card
        CardPaymentCharge cardPaymentCharge = cardPaymentCharger.chargeCard(
                paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription()
        );

        // 4. If not debited throw
        if (!cardPaymentCharge.wasCardDebited()) {
            throw new IllegalStateException(String.format("Card not debited for customer %s", customerId));
        }

        // 5. Insert payment
        paymentRequest.getPayment().setCustomerId(customerId);

        paymentRepository.save(paymentRequest.getPayment());

        // 6. TODO: send sms
    }
}
