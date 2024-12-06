package com.iabdinur.testing.sms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SmsServiceTest {

    @Mock
    private SmsSender smsSender;

    private SmsService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new SmsService(smsSender);
    }

    @Test
    void itShouldName() {
        // Given
        // When
        // Then
    }
}