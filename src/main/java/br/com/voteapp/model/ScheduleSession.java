package br.com.voteapp.model;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ScheduleSession {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private int durationMinutes = 1;

    @OneToMany(mappedBy = "scheduleSession")
    private List<Vote> votes;

    public ScheduleSession(ScheduleSessionCreateDTO scheduleDTO) {
        id  = scheduleDTO.getId();
        title = scheduleDTO.getTitle();
        description = scheduleDTO.getDescription();
        durationMinutes = scheduleDTO.getDurationMinutes();
    }
}
