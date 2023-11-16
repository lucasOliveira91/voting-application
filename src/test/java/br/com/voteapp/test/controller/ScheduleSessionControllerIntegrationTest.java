package br.com.voteapp.test.controller;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ScheduleSessionControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateScheduleSession() {
        ResponseEntity<ScheduleSessionCreateDTO> response = createSchedule();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void testOpenVotingSession() {
        ResponseEntity<ScheduleSessionCreateDTO> responseCreation = createSchedule();

        UUID sessionId = Objects.requireNonNull(responseCreation.getBody()).getId();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/pautas/" + sessionId + "/open", null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<ScheduleSessionCreateDTO> createSchedule() {
        ScheduleSessionCreateDTO newSession = new ScheduleSessionCreateDTO();
        newSession.setTitle("teste");
        newSession.setDescription("teste desc");
        ResponseEntity<ScheduleSessionCreateDTO> response = restTemplate.postForEntity("http://localhost:" + port + "/pautas", newSession, ScheduleSessionCreateDTO.class);
        return response;
    }
}
