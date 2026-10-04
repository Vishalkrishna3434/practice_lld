import controller.EntryController;
import controller.ExitController;

import domain.PricingRule;
import domain.VehicleType;

import repository.PaymentRepository;
import repository.PricingRuleRepository;
import repository.SlotRepository;
import repository.TicketRepository;

import service.PaymentService;
import service.PricingService;
import service.ReceiptService;
import service.SlotService;
import service.TicketService;

import java.util.UUID;

public class Main {

        public static void main(String[] args) {

                System.out.println("=================================");
                System.out.println("      PARKING SYSTEM STARTED     ");
                System.out.println("=================================");

                // ==========================================
                // 1. CREATE REPOSITORIES
                // ==========================================

                TicketRepository ticketRepository = new TicketRepository();
                SlotRepository slotRepository = new SlotRepository();
                PricingRuleRepository pricingRuleRepository = new PricingRuleRepository();
                PaymentRepository paymentRepository = new PaymentRepository();

                // ==========================================
                // 2. CREATE SERVICES
                // ==========================================

                TicketService ticketService = new TicketService(ticketRepository);

                SlotService slotService = new SlotService(slotRepository);

                PricingService pricingService = new PricingService(pricingRuleRepository);

                PaymentService paymentService = new PaymentService(paymentRepository);

                ReceiptService receiptService = new ReceiptService();

                // ==========================================
                // 3. INITIALIZE PRICING RULES
                // ==========================================

                System.out.println();
                System.out.println("========== INITIALIZING PRICING ==========");

                pricingService.addPricingRule(new PricingRule(VehicleType.CAR,100.0,50.0));

                pricingService.addPricingRule(new PricingRule(VehicleType.BIKE,50.0,25.0));

                pricingService.addPricingRule(new PricingRule(VehicleType.TRUCK,200.0,100.0));

                System.out.println("[MAIN] Pricing rules initialized");

                // ==========================================
                // 4. INITIALIZE PARKING SLOTS
                // ==========================================

                System.out.println();
                System.out.println("========== INITIALIZING SLOTS ==========");

                slotService.createSlot(VehicleType.CAR, 1);
                slotService.createSlot(VehicleType.CAR, 1);

                slotService.createSlot(VehicleType.BIKE, 1);
                slotService.createSlot(VehicleType.BIKE, 1);

                slotService.createSlot(VehicleType.TRUCK, 1);

                System.out.println("[MAIN] Parking slots initialized");

                // ==========================================
                // 5. CREATE CONTROLLERS
                // ==========================================

                EntryController entryController = new EntryController(
                                ticketService,
                                slotService);

                ExitController exitController = new ExitController(
                                ticketService,
                                pricingService,
                                paymentService,
                                receiptService,
                                slotService);

                // ==========================================
                // 6. VEHICLE ENTRY
                // ==========================================

                System.out.println();
                System.out.println("========== VEHICLE ENTRY ==========");

                EntryController.EntryResult entryResult = entryController.enterVehicle(
                                "TS09AB1234",
                                VehicleType.CAR);

                if (!entryResult.isSuccess()) {
                        System.out.println("Entry failed: " + entryResult.getMessage());
                        return;
                }

                System.out.println("Vehicle entered successfully!");
                System.out.println("Ticket ID: " + entryResult.getTicketId());
                System.out.println("Message: " + entryResult.getMessage());

                // ==========================================
                // 7. VEHICLE EXIT
                // ==========================================

                System.out.println();
                System.out.println("========== VEHICLE EXIT ==========");

                UUID ticketId = entryResult.getTicketId();

                ExitController.ExitResult exitResult = exitController.exitVehicle(ticketId);

                // ==========================================
                // 8. DISPLAY EXIT RESULT
                // ==========================================

                if (exitResult.isSuccess()) {

                        System.out.println("Vehicle exited successfully!");

                        System.out.println("Receipt ID: " + exitResult.getReceiptId());

                        System.out.println("Fee: " + exitResult.getFee());

                        System.out.println("Message: " + exitResult.getMessage());

                } else {

                        System.out.println("Exit failed: " + exitResult.getMessage());
                }

                // ==========================================
                // 9. END
                // ==========================================

                System.out.println();
                System.out.println("=================================");
                System.out.println("       PARKING SYSTEM ENDED      ");
                System.out.println("=================================");
        }
}