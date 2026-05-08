package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.kataelevator.model.Elevator;
import it.kataelevator.service.ElevatorService;

class TestingUnit {
	@Test
	void testElevatorReachesTargetFloor() {
		
		//imposto 1 ascensore
		ElevatorService service = new ElevatorService();
		Elevator lift = new Elevator(1);
		service.registerElevator(lift);

		//scelgo un piano
		int targetFloor = 2;
		service.requestFloor(targetFloor);

		//simulo gli step
		int maxSteps = 10;
		int steps = 0;
		while (lift.hasStops() && steps < maxSteps) {
			service.simulateStep();
			steps++;
		}

		//testo il piano raggiunto
		assertEquals(targetFloor, lift.getCurrentFloor(),
				"L'ascensore dovrebbe aver raggiunto il piano " + targetFloor);

		//testo lo stato della porta
		assertEquals(it.kataelevator.model.DoorState.OPEN, lift.getDoorState(),
				"Le porte dovrebbero aprirsi una volta raggiunto il piano");
	}

	@Test
	void checkIfFloorExists() {
		
		Elevator elevator = new Elevator(1);

		//  piani validi
		assertTrue(elevator.addStop(0), "Il piano 0 dovrebbe essere valido");
		assertTrue(elevator.addStop(10), "Il piano 10 dovrebbe essere valido");
		assertTrue(elevator.addStop(5), "Il piano 5 dovrebbe essere valido");

		// piani non esistenti
		assertFalse(elevator.addStop(-1), "Un piano negativo non dovrebbe essere accettato");
		assertFalse(elevator.addStop(11), "Un piano superiore a 10 non dovrebbe essere accettato");
	}
}