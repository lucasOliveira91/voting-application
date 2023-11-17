package br.com.voteapp.controller;

import br.com.voteapp.service.VoteService;
import br.com.voteapp.service.dto.VoteDTO;
import br.com.voteapp.service.dto.VotingResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/votes")
@Api(value = "Votos", tags = {"Votos"})
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    @ApiOperation(value = "Realiza votação")
    public ResponseEntity<String> doVote(@RequestBody @Validated VoteDTO voteDTO) {
        voteService.doVote(voteDTO);
        return ResponseEntity.ok("Votação realizada!");
    }

    @GetMapping("/results/{scheduleSessionID}")
    @ApiOperation(value = "Consulta resultado da votação pelo número da Pauta")
    public ResponseEntity<VotingResultDTO> getVotingResult(@PathVariable UUID scheduleSessionID) {
        VotingResultDTO result = voteService.calculateVotingResult(scheduleSessionID);
        return ResponseEntity.ok(result);
    }
}
