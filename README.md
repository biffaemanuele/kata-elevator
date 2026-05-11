# 🛗 Java Elevator Kata

Training project based on: http://kata-log.rocks/lift-kata

A robust Java implementation of an elevator control system. This project simulates a fleet of elevators responding to floor requests by optimizing for the nearest available unit.

## 🚀 Features

*   **Proximity-Based Dispatching**: Automatically assigns floor requests to the closest elevator to minimize wait times.
*   **State Management**: Tracks elevator movement (`UP`, `DOWN`, `IDLE`) and door status (`OPEN`, `CLOSED`).
*   **Step-Based Simulation**: Advances the system state through discrete steps, allowing for granular monitoring of movement.
*   **Smart Stop Management**: Utilizes a `TreeSet` to store and sort floor stops efficiently.
*   **Boundary Validation**: Built-in logic to prevent out-of-bounds floor requests (Range: 0 to 10).

---

## 🛠️ Tech Stack

*   **Language**: Java 21
*   **Build Tool**: Maven
*   **Testing**: JUnit 5, AssertJ

---

## 🏗️ Project Architecture

The project is organized into clear, decoupled packages:

### 1. Model (`it.kataelevator.model`)
*   **`Elevator`**: The core entity. Manages its own floor position, door state, and movement logic.
*   **`Direction` & `DoorState`**: Enums representing the physical status of the hardware.

### 2. Service (`it.kataelevator.service`)
*   **`ElevatorService`**: The controller/orchestrator. It manages the pool of elevators and implements the selection logic for incoming requests.

### 3. Testing
*   **`ModelUnitTest`**: Validates core movement logic, door safety, and floor boundaries.
*   **`ServiceUnitTest`**: Verifies the dispatcher's ability to pick the optimal elevator and reach the target.
