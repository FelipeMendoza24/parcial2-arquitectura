package co.edu.unisabana.parcial;

import co.edu.unisabana.parcial.controller.dto.CheckpointDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AbstractTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void PruebaIntegracion(){

        CheckpointDTO validCheckpoint = new CheckpointDTO("WarehouseA", "Driver123", 15);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/checkpoint/checkin", validCheckpoint, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        CheckpointDTO validCheckpoint2 = new CheckpointDTO("WarehouseA", "Driver123", 15);
        ResponseEntity<String> response2 = testRestTemplate.postForEntity("/checkpoint/checkout", validCheckpoint2, String.class);
        Assertions.assertEquals(HttpStatus.OK, response2.getStatusCode());

    }

}