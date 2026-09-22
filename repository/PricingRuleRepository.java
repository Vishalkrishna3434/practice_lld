package repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import domain.PricingRule;
import domain.VehicleType;

public class PricingRuleRepository {
  private Map<UUID,PricingRule> rules =new ConcurrentHashMap<>();
  private Map<VehicleType,UUID> VehicleTypeToRule = new ConcurrentHashMap<>();   
   
  public PricingRule save(PricingRule rule){
    rules.put(rule.getId(),rule);
    VehicleTypeToRule.put(rule.getVehicleType(),rule.getId());
    return rule;
  }
  
  public Optional<PricingRule> findByID(UUID ruleID){
    return Optional.ofNullable(rules.get(ruleID));
  }
 
  public Optional<PricingRule> findByVehicle(VehicleType vehicleType){
    UUID ruleID= VehicleTypeToRule.get(vehicleType);
    return ruleID!= null ? Optional.ofNullable(rules.get(ruleID)) : Optional.empty();
  }
 
  public List<PricingRule> findAll(){
    return new ArrayList<>(rules.values());
  }

  public void update(PricingRule rule){
    if(rules.containsKey(rule.getId())){
      rules.put(rule.getId(),rule);
      VehicleTypeToRule.put(rule.getVehicleType(),rule.getId());
    }
  }
 
  public void 

  public void clear(){
    rules.clear();
    VehicleTypeToRule.clear();
  }
}
