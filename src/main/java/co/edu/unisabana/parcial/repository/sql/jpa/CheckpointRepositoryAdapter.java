package co.edu.unisabana.parcial.repository.sql.jpa;

import co.edu.unisabana.parcial.repository.sql.entity.Checkpoint;
import co.edu.unisabana.parcial.service.model.Checkin;
import co.edu.unisabana.parcial.service.model.Checkout;
import co.edu.unisabana.parcial.service.port.CheckpointPort;
import org.springframework.stereotype.Component;

@Component
public class CheckpointRepositoryAdapter implements CheckpointPort {

    private final CheckpointRepository repository;

    public CheckpointRepositoryAdapter(CheckpointRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveCheckin(Checkin checkin) {
        // Lógica para convertir el modelo Checkin a la entidad Checkpoint y guardarlo usando el repositorio
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setDriver(checkin.getDriver());
        checkpoint.setFacility(checkin.getFacility());
        checkpoint.setDayOfMonth(checkin.getDayOfMonth());
        checkpoint.setFinalized(false);  // Ejemplo de mapeo
        repository.save(checkpoint);
    }

    @Override
    public void saveCheckout(Checkout checkout) {
        // Lógica para guardar Checkout similar a Checkin
        Checkpoint checkpoint = repository.findFirstByDriverAndFacilityAndFinalizedIsFalse(checkout.getDriver(), checkout.getFacility())
                .orElseThrow(() -> new IllegalArgumentException("No checkin found"));
        checkpoint.setFinalized(true);  // Marcar como finalizado
        checkpoint.setDayOfMonth(checkout.getDayOfMonth());
        repository.save(checkpoint);
    }

    @Override
    public Checkin findLastCheckin(String driver, String facility) {
        return repository.findFirstByDriverAndFacilityAndFinalizedIsFalse(driver, facility)
                .map(entity -> new Checkin(entity.getFacility(), entity.getDriver(), entity.getDayOfMonth()))
                .orElse(null);
    }
    @Override
    public void finishCheckin(Checkin checkin) {
        Checkpoint checkpoint = repository.findFirstByDriverAndFacilityAndFinalizedIsFalse(checkin.getDriver(), checkin.getFacility())
                .orElseThrow(() -> new IllegalArgumentException("No active checkin found"));

        checkpoint.setFinalized(true);  // Marcar como finalizado
        repository.save(checkpoint);  // Guardar los cambios
    }
}
