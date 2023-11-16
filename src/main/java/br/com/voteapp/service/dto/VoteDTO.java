package br.com.voteapp.service.dto;

import br.com.voteapp.config.annotation.ValidCPF;
import lombok.Data;

import java.util.UUID;

@Data
public class VoteDTO {

    private UUID scheduleID;

    @ValidCPF
    private String cpf;
    private boolean voteValue;
}
