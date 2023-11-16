package br.com.voteapp.service.batch;

import br.com.voteapp.service.batch.vo.NotificationScheduleClosedMessage;
import br.com.voteapp.model.ScheduleSession;
import br.com.voteapp.model.Vote;
import br.com.voteapp.service.ScheduleSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ScheduleSessionChecker {

    @Autowired
    private KafkaTemplate<String, NotificationScheduleClosedMessage> kafkaTemplate;

    @Autowired
    private ScheduleSessionService scheduleSessionService;

    @Scheduled(fixedRate = 120000) //cada 2 minuto
    @Transactional
    public void checkClosedSessionsAndNotify() {
        List<ScheduleSession> closedSessions = scheduleSessionService.findClosedSessions();
        closedSessions.forEach(session -> {
            NotificationScheduleClosedMessage message = createMessageForClosedSession(session);
            kafkaTemplate.send("my-sample-topic-for-vote-app", message);
        });
    }

    private NotificationScheduleClosedMessage createMessageForClosedSession(ScheduleSession session) {
        return new NotificationScheduleClosedMessage(
                session.getId(),
                session.getTitle(),
                session.getStartTime().plusMinutes(session.getDurationMinutes()),
                session.getVotes().stream().filter(Vote::isVoteValue).count(),
                session.getVotes().stream().filter(v -> !v.isVoteValue()).count(),
                "Resultado da Votação"
        );
    }
}

