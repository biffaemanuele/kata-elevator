package testing;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercise.Elevator;

class TestingUnit {
	private Elevator elevator;

    @BeforeEach
    void setUp() {

        elevator = new Elevator(1);
    }

    @Test
    void shouldReachTargetFloor() {
       
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(elevator);

            elevator.addStop(2);

            await().atMost(3, TimeUnit.SECONDS)
                   .untilAsserted(() -> {
                       assertThat(elevator.getCurrentFloor()).isEqualTo(2);
                   });
        }
    }
}