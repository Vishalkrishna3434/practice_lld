package repository;

import domain.Ticket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {
  private Map<UUID,Ticket> tickets=new ConcurrentHashMap<>();

  public Ticket save(Ticket ticket){
      tickets.put(ticket.getId(),ticket);
      return ticket;
  }
  
  public List<Ticket> findActiveTickets(){
     return tickets.values().stream() 
            .filter(Ticket::isActive)
            .toList();
  }

  public Optional<Ticket> findByID(UUID ticketID){
     return Optional.ofNullable(tickets.get(ticketID));
  }

  public void deactivateTicket(UUID ticketID){
     tickets.computeIfPresent(ticketID,(id,ticket)->{
         ticket.deactivate();
         return ticket;
     });
  }

  public void clear() {
    tickets.clear(); 
  }
}
