package it.kataelevator.service;

import it.kataelevator.model.*;
import java.util.ArrayList;
import java.util.List;

public class ElevatorService {
	private final List<Elevator> elevators = new ArrayList<>();

	public void registerElevator(Elevator e) {
		elevators.add(e);
	}

	//logica di controllo per assegnare l'ascensore più vicino
	public void requestFloor(int targetFloor) {
		Elevator best = null;
		int minDistance = Integer.MAX_VALUE;

		for (Elevator e : elevators) {
			int distance = Math.abs(e.getCurrentFloor() - targetFloor);
			if (distance < minDistance) {
				minDistance = distance;
				best = e;
			}
		}

		if (best != null) {
			best.addStop(targetFloor);
		}
	}


	//simula gli step per ogni ascensore
	public void simulateStep() {
		for (Elevator e : elevators) {
			e.step();
		}
	}

	public List<Elevator> getElevators() {
		return elevators;
	}
}
