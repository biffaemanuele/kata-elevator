package exercise;

import java.util.concurrent.ConcurrentSkipListSet;

public class Elevator implements Runnable {
	private final int id;
	private int currentFloor = 0;

	private final ConcurrentSkipListSet<Integer> stops = new ConcurrentSkipListSet<>();
	private boolean running = true;

	public Elevator(int id) {
		this.id = id;
	}

	public void addStop(int floor) {
		stops.add(floor);
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(1000);
				tick();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	private void tick() {
		if (stops.isEmpty())
			return;

		int target = stops.first();

		if (currentFloor < target)
			currentFloor++;
		else if (currentFloor > target)
			currentFloor--;

		System.out.printf("[Elevator %d] Current floor: %d%n", id, currentFloor);

		if (currentFloor == target) {
			stops.remove(currentFloor);
			System.out.printf("[Elevator %d] DING! Floor %d reached 🔔%n", id, currentFloor);
		}
	}

	public int getId() {
		return id;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void stop() {
		this.running = false;
	}
}