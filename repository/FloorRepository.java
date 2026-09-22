package repository;

import domain.Floor;
import java.util.*;
import java.util.concurrent.*;

public class FloorRepository {

  private Map<UUID, Floor> floors = new ConcurrentHashMap<>();
  private Map<Integer, UUID> floorNumberToID = new ConcurrentHashMap<>();

  public Floor save(Floor floor) {
    floors.put(floor.getId(), floor);
    floorNumberToID.put(floor.getFloorNumber(), floor.getId());
    return floor;
  }

  public Optional<Floor> findByID(UUID floorID) {
    return Optional.ofNullable(floors.get(floorID));
  }

  public Optional<Floor> findByNumber(int floorNumber) {
    UUID floorID = floorNumberToID.get(floorNumber);
    return floorID != null ? Optional.ofNullable(floors.get(floorID)) : Optional.empty();
  }

  public List<Floor> FindAll() {
    List<Floor> NewFloorList = new ArrayList<>();
    for (Floor floor : floors.values()) {
      NewFloorList.add(floor);
    }
    return NewFloorList;
  }

  public boolean existsByNumber(int floorNumber) {
    return floorNumberToID.containsKey(floorNumber);
  }

  public void delete(UUID floorID) {
    Floor floor = floors.remove(floorID);
    if (floor != null)
      floorNumberToID.remove(floor.getFloorNumber());
  }

  public void clear() {
    floors.clear();
    floorNumberToID.clear();
  }
}
