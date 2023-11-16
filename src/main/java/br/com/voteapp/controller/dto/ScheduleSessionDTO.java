package br.com.voteapp.controller.dto;

import br.com.voteapp.model.Vote;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ScheduleSessionDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private int durationMinutes = 1;
    private List<Vote> votes;
}
