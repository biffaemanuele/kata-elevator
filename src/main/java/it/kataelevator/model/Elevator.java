package it.kataelevator.model;

import java.util.TreeSet;

public class Elevator {
	private final int id;
	private int currentFloor;
	private DoorState doorState = DoorState.CLOSED;
	private Direction direction = Direction.IDLE;
	private static final int MIN_FLOOR = 0;
	private static final int MAX_FLOOR = 10;
	//treeset per ordinare le fermate
	private final TreeSet<Integer> stops = new TreeSet<>();

	public Elevator(int id, int currentFloor) {
		this.id = id;
		this.currentFloor = currentFloor;
	}

	public boolean addStop(int floor) {
		if (floor >= MIN_FLOOR && floor <= MAX_FLOOR) {
			stops.add(floor);
			updateDirection();
			return true;
		}return false;
	}

	public void step() {
		//logica degli step per la movimentazione degli ascensori
		
		if (doorState == DoorState.OPEN) {
			doorState = DoorState.CLOSED;
			updateDirection();
			return;
		}

		if (stops.isEmpty()) {
			direction = Direction.IDLE;
			return;
		}

		int target = stops.first();

		if (currentFloor < target) {
			currentFloor++;
			direction = Direction.UP;
		} else if (currentFloor > target) {
			currentFloor--;
			direction = Direction.DOWN;
		}

		// arrivo a destinazione
		if (currentFloor == target) {
			stops.remove(currentFloor);
			doorState = DoorState.OPEN;
			updateDirection();
		}
	}

	private void updateDirection() {
		// logica per la scelta della direzione dell'ascensore
		if (stops.isEmpty()) {
			direction = Direction.IDLE;
		} else {
			direction = (stops.first() > currentFloor) ? Direction.UP : Direction.DOWN;
		}
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public DoorState getDoorState() {
		return doorState;
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean hasStops() {
		return !stops.isEmpty();
	}
}
