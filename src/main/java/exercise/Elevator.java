package exercise;

import java.util.concurrent.ConcurrentSkipListSet;

public class Elevator implements Runnable {
	private final int id;
	
	private volatile int currentFloor = 0;
	private volatile boolean running = true;

	
	private final ConcurrentSkipListSet<Integer> stops = new ConcurrentSkipListSet<>();

	public Elevator(int id) {
		this.id = id;
	}

	public boolean addStop(int floor) {
		if (floor >= 0 && floor <= 10) {
			stops.add(floor);
			return true;
		}
		System.err.println("[Elevator " + id + "] Floor " + floor + " not available!");
		return false;
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
			System.out.printf("[Elevator %d] DING! Floor %d reached %n", id, currentFloor);
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