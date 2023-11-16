package br.com.voteapp.repository;

import br.com.voteapp.model.ScheduleSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScheduleSessionRepository extends JpaRepository<ScheduleSession, Long> {
    Optional<ScheduleSession> findById(UUID id);

    Optional<ScheduleSession> findByStartTimeIsNotNull();
}
