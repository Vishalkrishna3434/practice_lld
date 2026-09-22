package repository;

import domain.Payment;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PaymentRepository {
  private Map<UUID, Payment> payments = new ConcurrentHashMap<>();
  private Map<UUID, List<UUID>> TicketToPayments = new ConcurrentHashMap<>();

  public Payment save(Payment payment) {
    payments.put(payment.getId(), payment);
    TicketToPayments.computeIfAbsent(payment.getTicketId(), k -> new ArrayList<>())
        .add(payment.getId());
    return payment;
  }

  public Optional<Payment> findById(UUID paymentId) {
    return Optional.ofNullable(payments.get(paymentId));
  }

  public List<Payment> findAll() {
    return new ArrayList<>(payments.values());
  }

  public void update(Payment payment) {
    if (payments.containsKey(payment.getId())) {
      payments.put(payment.getId(), payment);
    }
  }

  public List<Payment> findByTicketID(UUID TicketID) {
    List<UUID> PaymentIDs = TicketToPayments.get(TicketID);
    if (PaymentIDs != null) {
      return PaymentIDs.stream()
          .map(payments::get)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  public void delete(UUID PaymentID) {
    Payment payment = payments.remove(PaymentID);
    if (payment != null) {
      List<UUID> PaymentList = TicketToPayments.get(payment.getTicketId());
      if (PaymentList != null) {
        PaymentList.remove(PaymentID);
      }
    }
  }

  public void clear() {
    payments.clear();
    TicketToPayments.clear();
  }
}
