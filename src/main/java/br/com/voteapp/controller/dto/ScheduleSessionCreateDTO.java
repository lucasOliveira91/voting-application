package br.com.voteapp.controller.dto;

import br.com.voteapp.model.ScheduleSession;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ScheduleSessionCreateDTO {
    private UUID id;
    private String title;
    private String description;
    private int durationMinutes = 1;

    public ScheduleSessionCreateDTO(ScheduleSession entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.title = entity.getTitle();
        this.durationMinutes = entity.getDurationMinutes();
    }
}
