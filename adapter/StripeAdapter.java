package adapter;

import java.util.UUID;
import java.util.Random;

public class StripeAdapter implements PaymentGatewayAdapter {

  @Override
  public boolean pay(UUID ticketId, double amount) {
    System.out.println("[ADAPTER] RazorpayAdapter processing payment for ticket: " + ticketId + " amount: " + amount);

    // Simulate 95% success rate
    Random random = new Random();
    boolean success = random.nextDouble() < 0.95;

    System.out.println("[ADAPTER] RazorpayAdapter payment result: " + (success ? "SUCCESS" : "FAILED"));

    return success;
  }
}