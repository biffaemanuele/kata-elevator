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
    private Elevator elevator1;
    private Elevator elevator2;

	
    //setup prima di ogni test
	@BeforeEach
    void setup() {
		
        service = new ElevatorService();
        
        //elevator1
        elevator1 = new Elevator(1, 0);
        service.registerElevator(elevator1);
        
        //elevator2
        elevator2 = new Elevator(2, 8);
        service.registerElevator(elevator2);
    }
	
	@Test
	void testElevatorReachesTargetFloor() {
		
		//scelgo un piano
		int targetFloor = 2;
		service.requestFloor(targetFloor);

		//simulo gli step
		int maxSteps = 10;
		int steps = 0;
		while (elevator1.hasStops() && steps < maxSteps) {
			service.simulateStep();
			steps++;
		}

		//testo il piano raggiunto
		assertEquals(targetFloor, elevator1.getCurrentFloor(),
				"L'ascensore dovrebbe aver raggiunto il piano " + targetFloor);

		//testo lo stato della porta
		assertEquals(DoorState.OPEN, elevator1.getDoorState(),
				"Le porte dovrebbero aprirsi una volta raggiunto il piano");
	}

	@Test
	void checkIfFloorExists() {
		
		//  piani validi
		assertTrue(elevator1.addStop(0), "Il piano 0 dovrebbe essere valido");
		assertTrue(elevator1.addStop(10), "Il piano 10 dovrebbe essere valido");
		assertTrue(elevator1.addStop(5), "Il piano 5 dovrebbe essere valido");

		// piani non esistenti
		assertFalse(elevator1.addStop(-1), "Un piano negativo non dovrebbe essere accettato");
		assertFalse(elevator1.addStop(11), "Un piano superiore a 10 non dovrebbe essere accettato");
	}
	
	@Test
	void invalidFloorTest() {
		
	    boolean added = elevator1.addStop(11);
	    elevator1.step();

	    //se il piano inserito non è valido addStop torna false
	    assertFalse(added);
	    
	    //ascensore non si muove 
	    assertEquals(0, elevator1.getCurrentFloor());
	    assertEquals(Direction.IDLE, elevator1.getDirection());
	}
	
	@Test
	void elevatorMovesUpTest() {
		
	    elevator1.addStop(3);
	    elevator1.step();

	    //faccio solo 1 step quindi ascensore si muove in alto di 1
	    assertEquals(1, elevator1.getCurrentFloor());
	    assertEquals(Direction.UP, elevator1.getDirection());
	}
	
	@Test
	void closeDoorsTest() {
		
	    elevator1.addStop(1);
	    elevator1.step(); 
	    
	    //quando arriva OPEN
	    assertEquals(DoorState.OPEN, elevator1.getDoorState());

	    elevator1.step(); 
	    
	    //il metodo step cambiare DoorState a CLOSED
	    assertEquals(DoorState.CLOSED, elevator1.getDoorState());
	}
	
	
	@Test
	void serviceElevatorTest() {

	    service.requestFloor(7);

	    //si muove solo l'ascensore più vicino
	    assertFalse(elevator1.hasStops());
	    assertTrue(elevator2.hasStops());
	}
	
}