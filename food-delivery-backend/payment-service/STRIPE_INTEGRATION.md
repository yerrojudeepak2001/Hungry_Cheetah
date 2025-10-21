# Stripe Integration Guide

## Configuration

The Stripe integration has been successfully configured with the following components:

### 1. Configuration Files

**application.yml** - Added Stripe configuration:
```yaml
stripe:
  secret-key: ${STRIPE_SECRET_KEY:sk_test_your_stripe_secret_key_here}
  publishable-key: ${STRIPE_PUBLISHABLE_KEY:pk_test_your_publishable_key_here}
  webhook-secret: ${STRIPE_WEBHOOK_SECRET:whsec_your_webhook_secret_here}
```

### 2. Dependencies

**pom.xml** - Stripe Java SDK dependency:
```xml
<dependency>
    <groupId>com.stripe</groupId>
    <artifactId>stripe-java</artifactId>
    <version>24.0.0</version>
</dependency>
```

## API Endpoints

### Stripe Payment Endpoints

Base URL: `/api/payment/stripe`

1. **Create Payment Intent**
   - `POST /payment-intent`
   - Creates a new payment intent for processing

2. **Confirm Payment Intent**
   - `POST /payment-intent/{paymentIntentId}/confirm`
   - Confirms a payment with a payment method

3. **Create Customer**
   - `POST /customers`
   - Creates a new Stripe customer

4. **Get Customer**
   - `GET /customers/{customerId}`
   - Retrieves customer details

5. **Create Refund**
   - `POST /refunds`
   - Creates a refund for a payment

6. **Get Payment Status**
   - `GET /payment-intent/{paymentIntentId}/status`
   - Gets the current status of a payment

7. **Cancel Payment**
   - `POST /payment-intent/{paymentIntentId}/cancel`
   - Cancels a payment intent

8. **Create Setup Intent**
   - `POST /setup-intent`
   - Creates a setup intent for saving payment methods

9. **List Payment Methods**
   - `GET /customers/{customerId}/payment-methods`
   - Lists saved payment methods for a customer

### Webhook Endpoint

- `POST /api/payment/stripe/webhook`
- Handles Stripe webhook events for payment status updates

## Example Usage

### Create Payment Intent
```json
POST /api/payment/stripe/payment-intent
{
  "amount": 29.99,
  "currency": "usd",
  "customerId": "cus_stripe_customer_id",
  "description": "Food order payment"
}
```

### Create Customer
```json
POST /api/payment/stripe/customers
{
  "email": "customer@example.com",
  "name": "John Doe",
  "phone": "+1234567890"
}
```

### Create Refund
```json
POST /api/payment/stripe/refunds
{
  "paymentIntentId": "pi_stripe_payment_intent_id",
  "amount": 10.00,
  "reason": "requested_by_customer"
}
```

## Payment Flow

1. **Create Customer** (if not exists)
2. **Create Payment Intent** with order amount
3. **Client confirms payment** using Stripe Elements or SDK
4. **Webhook receives confirmation** and updates order status
5. **Order processing begins**

## Webhook Events Handled

- `payment_intent.succeeded` - Payment completed successfully
- `payment_intent.payment_failed` - Payment failed
- `payment_intent.canceled` - Payment canceled
- `invoice.payment_succeeded` - Subscription payment succeeded
- `invoice.payment_failed` - Subscription payment failed

## Security Notes

- Secret key is configured in application.yml (move to environment variables in production)
- Webhook signature verification is implemented
- All Stripe API calls are logged for debugging

## Testing

Use Stripe's test card numbers for testing:
- `4242424242424242` - Visa (succeeds)
- `4000000000000002` - Visa (declined)
- `4000000000009995` - Visa (insufficient funds)

## Integration with Payment Service

The main `PaymentService` now includes Stripe-specific methods:
- `processStripePayment()` - Process payments through Stripe
- `handleStripePaymentSuccess()` - Handle successful payments
- `handleStripePaymentFailure()` - Handle failed payments
- `processStripeRefund()` - Process refunds through Stripe

The service is fully integrated with the existing payment flow and database structure.