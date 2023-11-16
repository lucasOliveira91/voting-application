package br.com.voteapp.controller;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import br.com.voteapp.service.ScheduleSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pautas")
public class ScheduleSessionController {

    @Autowired
    private ScheduleSessionService scheduleSessionService;

    @PostMapping
    public ResponseEntity<ScheduleSessionCreateDTO> createScheduleSession(@RequestBody ScheduleSessionCreateDTO scheduleSession) {
        return ResponseEntity.ok(scheduleSessionService.createSchedule(scheduleSession));
    }

    @PostMapping("/{id}/open")
    public ResponseEntity<String> openVotingSession(@PathVariable UUID id, @RequestParam Optional<Integer> durationMinutes) {
        boolean isOpened = scheduleSessionService.openVotingSession(id, durationMinutes.orElse(1));

        if (isOpened) {
            return ResponseEntity.ok("Votação aberta para a pauta: " + id);
        }

        return ResponseEntity.badRequest().body("Não foi posssível abrir uma sessão para a pauta " + id);
    }
}
