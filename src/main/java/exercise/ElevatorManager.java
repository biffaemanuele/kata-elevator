package exercise;


import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {
	private final List<Elevator> elevators = new ArrayList<>();

	public void addElevator(Elevator e) {
		elevators.add(e);
	}

	// Trova l'ascensore più vicino al piano richiesto
	public Elevator findBestElevator(int targetFloor) {
		Elevator best = null;
		int minDistance = Integer.MAX_VALUE;

		for (Elevator e : elevators) {
			int distance = Math.abs(e.getCurrentFloor() - targetFloor);
			if (distance < minDistance) {
				minDistance = distance;
				best = e;
			}
		}
		return best;
	}

	public void stopAll() {
		for (Elevator e : elevators) {
			e.stop();
		}
	}
}