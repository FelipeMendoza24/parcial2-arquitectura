package co.edu.unisabana.parcial;
import co.edu.unisabana.parcial.controller.dto.ResponseGate;
import co.edu.unisabana.parcial.service.model.Checkout;
import co.edu.unisabana.parcial.service.port.CheckpointPort;
import co.edu.unisabana.parcial.controller.dto.CheckpointDTO;
import co.edu.unisabana.parcial.service.CheckpointService;
import co.edu.unisabana.parcial.service.model.Checkin;
import co.edu.unisabana.parcial.service.port.CheckpointPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ParcialApplicationTests {

	@Mock
	private CheckpointPort checkpointPort;  // Mock the checkpointPort

	@InjectMocks
	private CheckpointService checkpointService;  // Inject the mock into the service

	@Test
	void testValidCheckout() {
		CheckpointDTO validCheckpoint = new CheckpointDTO("Facility A", "Driver A", 15);
		Checkin lastCheckin = new Checkin("Facility A", "Driver A", 10);  // Mocked last checkin
		when(checkpointPort.findLastCheckin("Driver A", "Facility A")).thenReturn(lastCheckin);
		checkpointService.checkout(validCheckpoint);
		verify(checkpointPort, times(1)).saveCheckout(any(Checkout.class));
	}
	@Test
	void testCheckoutWithNoPreviousCheckin() {
		CheckpointDTO checkpointWithNoCheckin = new CheckpointDTO("Facility A", "Driver A", 15);
		when(checkpointPort.findLastCheckin("Driver A", "Facility A")).thenReturn(null);
		assertThrows(IllegalArgumentException.class, () -> checkpointService.checkout(checkpointWithNoCheckin));
		verify(checkpointPort, never()).saveCheckout(any(Checkout.class));
	}

	@Test
	void testInvalidCheckoutDayTooHigh() {
		CheckpointDTO invalidCheckpoint = new CheckpointDTO("Facility A", "Driver A", 31);
		Checkin lastCheckin = new Checkin("Facility A", "Driver A", 10);  // Mocked last checkin
		when(checkpointPort.findLastCheckin("Driver A", "Facility A")).thenReturn(lastCheckin);
		assertThrows(IllegalArgumentException.class, () -> checkpointService.checkout(invalidCheckpoint));
		verify(checkpointPort, never()).saveCheckout(any(Checkout.class));
	}
	@Test
	void testValidCheckin() {
		CheckpointDTO validCheckpoint = new CheckpointDTO("Facility A", "Driver A", 15);  // Create valid DTO
		checkpointService.checkin(validCheckpoint);
		verify(checkpointPort, times(1)).saveCheckin(any(Checkin.class));
	}

	@Test
	void testInvalidCheckinDayTooHigh() {
		CheckpointDTO invalidCheckpoint = new CheckpointDTO("Facility A", "Driver A", 31);  // Invalid dayOfMonth
		assertThrows(IllegalArgumentException.class, () -> checkpointService.checkin(invalidCheckpoint));
		verify(checkpointPort, never()).saveCheckin(any(Checkin.class));
	}

	@Test
	void testInvalidCheckinDayTooLow() {
		CheckpointDTO invalidCheckpoint = new CheckpointDTO("Facility A", "Driver A", 0);
		assertThrows(IllegalArgumentException.class, () -> checkpointService.checkin(invalidCheckpoint));
		verify(checkpointPort, never()).saveCheckin(any(Checkin.class));
	}

}
