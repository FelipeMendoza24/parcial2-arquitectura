package co.edu.unisabana.parcial.service;

import co.edu.unisabana.parcial.controller.dto.CheckpointDTO;
import co.edu.unisabana.parcial.service.model.Checkin;
import co.edu.unisabana.parcial.service.model.Checkout;
import co.edu.unisabana.parcial.service.port.CheckpointPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckpointService {

  @Qualifier("checkpointRepositoryAdapter")
  private final CheckpointPort checkpointPort;

  public void checkin(CheckpointDTO checkpoint) {
    if (checkpoint.dayOfMonth > 30 || checkpoint.dayOfMonth < 1) {
      throw new IllegalArgumentException("Invalid date");
    }
    Checkin checkin = new Checkin(checkpoint.facility, checkpoint.driver, checkpoint.dayOfMonth);
    checkpointPort.saveCheckin(checkin);

  }

  public void checkout(CheckpointDTO checkpoint) {
    Checkin lastCheckin = checkpointPort.findLastCheckin(checkpoint.driver, checkpoint.facility);
    if (lastCheckin == null) {
      throw new IllegalArgumentException("don't exist previously check in");
    }
    if (checkpoint.dayOfMonth > 30 || checkpoint.dayOfMonth < 1) {
      throw new IllegalArgumentException("Invalid date");
    }
    Checkout checkout = new Checkout(checkpoint.facility, checkpoint.driver, checkpoint.dayOfMonth);
    checkpointPort.saveCheckout(checkout);
  }
}
