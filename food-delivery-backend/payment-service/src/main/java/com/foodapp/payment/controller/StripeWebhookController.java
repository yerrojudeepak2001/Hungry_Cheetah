package com.foodapp.payment.controller;

import com.foodapp.payment.config.StripeProperties;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/stripe/webhook")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final StripeProperties stripeProperties;

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        
        try {
            Event event = Webhook.constructEvent(
                    payload, sigHeader, stripeProperties.getWebhookSecret());

            log.info("Received Stripe webhook event: {}", event.getType());

            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    handlePaymentIntentSucceeded(event);
                    break;
                case "payment_intent.payment_failed":
                    handlePaymentIntentFailed(event);
                    break;
                case "payment_intent.canceled":
                    handlePaymentIntentCanceled(event);
                    break;
                case "invoice.payment_succeeded":
                    handleInvoicePaymentSucceeded(event);
                    break;
                case "invoice.payment_failed":
                    handleInvoicePaymentFailed(event);
                    break;
                default:
                    log.warn("Unhandled event type: {}", event.getType());
            }

            return ResponseEntity.ok("Webhook handled successfully");

        } catch (SignatureVerificationException e) {
            log.error("Invalid signature: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid signature");
        } catch (Exception e) {
            log.error("Error processing webhook: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing webhook");
        }
    }

    private void handlePaymentIntentSucceeded(Event event) {
        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
        if (stripeObject instanceof PaymentIntent) {
            PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
            log.info("Payment succeeded for PaymentIntent: {}", paymentIntent.getId());
            
            // TODO: Update order status to PAID
            // TODO: Send confirmation email/notification
            // TODO: Trigger order processing workflow
        }
    }

    private void handlePaymentIntentFailed(Event event) {
        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
        if (stripeObject instanceof PaymentIntent) {
            PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
            log.warn("Payment failed for PaymentIntent: {}", paymentIntent.getId());
            
            // TODO: Update order status to PAYMENT_FAILED
            // TODO: Send failure notification
            // TODO: Trigger retry logic or order cancellation
        }
    }

    private void handlePaymentIntentCanceled(Event event) {
        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);
        if (stripeObject instanceof PaymentIntent) {
            PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
            log.info("Payment canceled for PaymentIntent: {}", paymentIntent.getId());
            
            // TODO: Update order status to CANCELED
            // TODO: Release any reserved inventory
        }
    }

    private void handleInvoicePaymentSucceeded(Event event) {
        log.info("Invoice payment succeeded");
        // TODO: Handle subscription/recurring payment success
    }

    private void handleInvoicePaymentFailed(Event event) {
        log.warn("Invoice payment failed");
        // TODO: Handle subscription/recurring payment failure
    }
}