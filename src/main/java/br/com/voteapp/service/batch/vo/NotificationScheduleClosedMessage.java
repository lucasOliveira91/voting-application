package br.com.voteapp.service.batch.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationScheduleClosedMessage {

    private UUID scheduleSessionId;
    private String sessionTitle;
    private LocalDateTime sessionEndTime;
    private long totalYesVotes;
    private long totalNoVotes;
    private String additionalMessage;
}
