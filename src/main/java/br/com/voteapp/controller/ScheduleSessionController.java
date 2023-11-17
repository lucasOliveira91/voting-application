package br.com.voteapp.controller;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import br.com.voteapp.service.ScheduleSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Pautas", tags = {"Pautas"})
public class ScheduleSessionController {

    @Autowired
    private ScheduleSessionService scheduleSessionService;

    @PostMapping
    @ApiOperation(value = "Cria uma nova pauta", response = ScheduleSessionCreateDTO.class)
    public ResponseEntity<ScheduleSessionCreateDTO> createScheduleSession(
            @ApiParam(value = "Detalhes da criação da pauta", required = true)
            @RequestBody ScheduleSessionCreateDTO scheduleSession) {
        return ResponseEntity.ok(scheduleSessionService.createSchedule(scheduleSession));
    }

    @PostMapping("/{id}/open")
    @ApiOperation(value = "Abre votação da pauta selecionada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Abertura de votação com sucesso"),
            @ApiResponse(code = 400, message = "Parametro incorreto")
    })
    public ResponseEntity<String> openVotingSession(
            @ApiParam(value = "Identificador da pauta", required = true)
            @PathVariable UUID id,
            @ApiParam(value = "Duranção em minutos da votação")
            @RequestParam Optional<Integer> durationMinutes) {
        boolean isOpened = scheduleSessionService.openVotingSession(id, durationMinutes.orElse(1));

        if (isOpened) {
            return ResponseEntity.ok("Votação aberta para a pauta: " + id);
        }

        return ResponseEntity.badRequest().body("Não foi posssível abrir uma sessão para a pauta " + id);
    }
}
