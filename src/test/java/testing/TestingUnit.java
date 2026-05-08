package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.kataelevator.model.Direction;
import it.kataelevator.model.DoorState;
import it.kataelevator.model.Elevator;
import it.kataelevator.service.ElevatorService;

class TestingUnit {
	
    private ElevatorService service;
    private Elevator elevator;
	
	@BeforeEach
    void setup() {
		//setup prima di ogni test
        service = new ElevatorService();
        elevator = new Elevator(1);
        service.registerElevator(elevator);
    }
	
	@Test
	void testElevatorReachesTargetFloor() {
		
		//scelgo un piano
		int targetFloor = 2;
		service.requestFloor(targetFloor);

		//simulo gli step
		int maxSteps = 10;
		int steps = 0;
		while (elevator.hasStops() && steps < maxSteps) {
			service.simulateStep();
			steps++;
		}

		//testo il piano raggiunto
		assertEquals(targetFloor, elevator.getCurrentFloor(),
				"L'ascensore dovrebbe aver raggiunto il piano " + targetFloor);

		//testo lo stato della porta
		assertEquals(DoorState.OPEN, elevator.getDoorState(),
				"Le porte dovrebbero aprirsi una volta raggiunto il piano");
	}

	@Test
	void checkIfFloorExists() {
		
		//  piani validi
		assertTrue(elevator.addStop(0), "Il piano 0 dovrebbe essere valido");
		assertTrue(elevator.addStop(10), "Il piano 10 dovrebbe essere valido");
		assertTrue(elevator.addStop(5), "Il piano 5 dovrebbe essere valido");

		// piani non esistenti
		assertFalse(elevator.addStop(-1), "Un piano negativo non dovrebbe essere accettato");
		assertFalse(elevator.addStop(11), "Un piano superiore a 10 non dovrebbe essere accettato");
	}
	
	@Test
	void invalidFloorTest() {
		
	    boolean added = elevator.addStop(11);
	    elevator.step();

	    //se il piano inserito non è valido addStop torna false
	    assertFalse(added);
	    
	    //ascensore non si muove 
	    assertEquals(0, elevator.getCurrentFloor());
	    assertEquals(Direction.IDLE, elevator.getDirection());
	}
	
	@Test
	void elevatorMovesUpTest() {
		
	    elevator.addStop(3);
	    elevator.step();

	    //faccio solo 1 step quindi ascensore si muove in alto di 1
	    assertEquals(1, elevator.getCurrentFloor());
	    assertEquals(Direction.UP, elevator.getDirection());
	}
	
	@Test
	void CloseDoorsTest() {
		
	    elevator.addStop(1);
	    elevator.step(); 
	    
	    //quando arriva OPEN
	    assertEquals(DoorState.OPEN, elevator.getDoorState());

	    elevator.step(); 
	    
	    //il metodo step cambiare DoorState a CLOSED
	    assertEquals(DoorState.CLOSED, elevator.getDoorState());
	}
}