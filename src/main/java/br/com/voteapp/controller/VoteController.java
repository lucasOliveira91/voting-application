package br.com.voteapp.controller;

import br.com.voteapp.service.VoteService;
import br.com.voteapp.service.dto.VoteDTO;
import br.com.voteapp.service.dto.VotingResultDTO;
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
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<String> doVote(@RequestBody @Validated VoteDTO voteDTO) {
        voteService.doVote(voteDTO);
        return ResponseEntity.ok("Votação realizada!");
    }

    @GetMapping("/results/{scheduleSessionID}")
    public ResponseEntity<VotingResultDTO> getVotingResult(@PathVariable UUID scheduleSessionID) {
        VotingResultDTO result = voteService.calculateVotingResult(scheduleSessionID);
        return ResponseEntity.ok(result);
    }
}
