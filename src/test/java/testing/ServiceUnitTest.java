package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.kataelevator.model.DoorState;
import it.kataelevator.model.Elevator;
import it.kataelevator.service.ElevatorService;

class ServiceUnitTest {

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
    void serviceElevatorTest() {

        service.requestFloor(7);

        //si muove solo l'ascensore più vicino
        assertFalse(elevator1.hasStops(), "Ascensore più lontana dal piano scelto (NON SI MUOVE)");
        assertTrue(elevator2.hasStops(), "Ascensore più vicina al piano scelto (SI MUOVE)");
    }
}