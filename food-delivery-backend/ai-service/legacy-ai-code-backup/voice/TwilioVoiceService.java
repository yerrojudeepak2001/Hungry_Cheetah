package com.foodapp.ai.voice;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioVoiceService {
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String TWILIO_NUMBER;

    public void initializeTwilio() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendOrderConfirmation(String customerPhone, String orderDetails) {
        Message message = Message.creator(
            new PhoneNumber(customerPhone),
            new PhoneNumber(TWILIO_NUMBER),
            "Your order has been confirmed: " + orderDetails)
        .create();
    }

    public void handleIncomingCall(String callSid, String audioUrl) {
        // TODO: Implement call handling logic
        // 1. Record the audio
        // 2. Convert speech to text
        // 3. Process the order
        // 4. Send confirmation
    }
}