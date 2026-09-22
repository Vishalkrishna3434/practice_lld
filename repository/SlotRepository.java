package repository;

import java.util.*;
import domain.ParkingSlot;
import domain.VehicleType;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class SlotRepository {

  private Map<UUID, ParkingSlot> slots = new ConcurrentHashMap<>();

  public ParkingSlot save(ParkingSlot slot) {
    slots.put(slot.getId(), slot);
    return slot;
  }

  public Optional<ParkingSlot> findByID(UUID slotID) {
    return Optional.ofNullable(slots.get(slotID));
  }

  public List<ParkingSlot> findAvailableSlots(VehicleType vehicleType) {
    return slots.values().stream()
        .filter(slot -> slot.getSlotType() == vehicleType && !slot.getIsOccupied())
        .collect(Collectors.toList());
  }

  public Optional<ParkingSlot> allocateSlot(VehicleType vehicleType) {
    return slots.values().stream()
        .filter(slot -> slot.getSlotType() == vehicleType && !slot.getIsOccupied())
        .findFirst().map(slot -> {
          slot.setOccupied(true);
          return slot;
        });
  }

  public void releaseSlot(UUID slotID) {
    slots.computeIfPresent(slotID, (id, slot) -> {
      slot.setOccupied(false);
      return slot;
    });
  }

  public Map<VehicleType, Long> getSlotStatistics() {
    return slots.values().stream().collect(Collectors.groupingBy(
        ParkingSlot::getSlotType,
        Collectors.counting()));
  }

  public void clear() {
    slots.clear();
  }
}