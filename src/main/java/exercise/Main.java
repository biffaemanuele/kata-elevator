package exercise;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
		//Uso executorservice per i virtual thread
		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

			Elevator lift1 = new Elevator(1);
			Elevator lift2 = new Elevator(2);

			// Avvio ogni ascensore sul virtual thread
			executor.submit(lift1);
			executor.submit(lift2);

			Scanner scanner = new Scanner(System.in);
			System.out.println("Elevator enabled. Choose a floor: ");

			while (true) {
				String input = scanner.nextLine();
				if (input.equalsIgnoreCase("exit")) {
					lift1.stop();
				    lift2.stop();
					scanner.close();
					break;
				}

				try {
					int targetFloor = Integer.parseInt(input);

					//Logica per scegliere quali dei due ascensori è più vicino al piano
					Elevator bestLift = (Math.abs(lift1.getCurrentFloor() - targetFloor) <= Math
							.abs(lift2.getCurrentFloor() - targetFloor)) ? lift1 : lift2;

					System.out.printf("Request for floor %d assigned to elevator %d%n", targetFloor, bestLift.getId());
					bestLift.addStop(targetFloor);
					// ------------------------------

				} catch (NumberFormatException e) {
					System.out.println("Insert a valid floor number!");
				}
			}
		}
	}
}
