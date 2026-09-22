package domain;

import java.util.UUID;

public class ParkingSlot {
  private UUID id;
  private VehicleType slotType;
  private boolean isOccupied;
  private int floorNumber;

  public ParkingSlot(VehicleType slotType,int floorNumber){
        this.id=UUID.randomUUID();
        this.slotType=slotType;
        this.isOccupied=false;
        this.floorNumber=floorNumber;
  }

  //getters and setters

  public UUID getId(){
    return id;
  }
  
  public VehicleType getSlotType(){
    return slotType;
  }

  public boolean getIsOccupied(){
    return isOccupied;
  }
  public void setOccupied(boolean occupied){
    this.isOccupied=occupied;
  }
  
  @Override
  public String toString(){
    return "Parking Slot{"+
           "id="+id+
           ", slotType="+slotType+
           ", isOccupied="+isOccupied+
           ", floorNumber="+floorNumber+
           "}";
  }
  
}
