package br.com.voteapp.test.controller;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import br.com.voteapp.service.dto.VoteDTO;
import br.com.voteapp.service.dto.VotingResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDoVote() {
        ResponseEntity<ScheduleSessionCreateDTO> schedule = createSchedule();

        UUID sessionId = Objects.requireNonNull(schedule.getBody()).getId();
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/pautas/" + sessionId + "/open", null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setVoteValue(true);//SIM
        voteDTO.setCpf("56350216032");
        voteDTO.setScheduleID(schedule.getBody().getId());
        ResponseEntity<String> finalResponse = restTemplate.postForEntity("http://localhost:" + port + "/votes", voteDTO, String.class);

        assertThat(finalResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetVotingResult() {
        UUID scheduleSessionID = UUID.randomUUID();
        ResponseEntity<VotingResultDTO> response = restTemplate.getForEntity("http://localhost:" + port + "/votes/results/" + scheduleSessionID, VotingResultDTO.class);

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
