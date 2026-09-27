package adapter;

import java.util.UUID;

public interface PaymentGatewayAdapter {
  boolean pay(UUID ticketID, double amount);
}