package exercise;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            ElevatorManager manager = new ElevatorManager();
            
            // Creazione e avvio ascensori
            Elevator lift1 = new Elevator(1);
            Elevator lift2 = new Elevator(2);
            
            manager.addElevator(lift1);
            manager.addElevator(lift2);
            
            executor.submit(lift1);
            executor.submit(lift2);

            System.out.println("--- Elevator System Ready ---");
            System.out.println("Enter a floor (0-10) or 'exit' to quit:");

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("exit")) {
                        manager.stopAll();
                        System.out.println("Shutting down...");
                        break;
                    }

                    try {
                        int targetFloor = Integer.parseInt(input);
                        
                        Elevator best = manager.findBestElevator(targetFloor);
                        
                        if (best != null) {
                            if (best.addStop(targetFloor)) {
                                System.out.printf("Request for floor %d assigned to Elevator %d%n", 
                                                  targetFloor, best.getId());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a number between 0 and 10.");
                    }
                }
            }
        } 
        System.out.println("System offline.");
    }
}