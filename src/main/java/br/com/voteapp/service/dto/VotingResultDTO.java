package br.com.voteapp.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VotingResultDTO {
    private UUID schedule;
    private int totalVotes;
    private int yesVotes;
    private int noVotes;
}
