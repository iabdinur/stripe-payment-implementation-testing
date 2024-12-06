package com.iabdinur.testing.sms.twilio;

import com.iabdinur.testing.sms.SmsRequest;
import com.iabdinur.testing.sms.SmsSender;
import com.iabdinur.testing.utils.PhoneNumberValidator;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;
    private final PhoneNumberValidator phoneNumberValidator;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration, PhoneNumberValidator phoneNumberValidator) {
        this.twilioConfiguration = twilioConfiguration;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (phoneNumberValidator.test(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}", smsRequest);
        } else {
            throw new IllegalArgumentException("Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }
    }
}
