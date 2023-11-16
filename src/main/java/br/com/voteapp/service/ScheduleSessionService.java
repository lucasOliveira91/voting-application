package br.com.voteapp.service;

import br.com.voteapp.controller.dto.ScheduleSessionCreateDTO;
import br.com.voteapp.model.ScheduleSession;
import br.com.voteapp.repository.ScheduleSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ScheduleSessionService {

    @Autowired
    private ScheduleSessionRepository scheduleSessionRepository;

    public ScheduleSessionCreateDTO createSchedule(ScheduleSessionCreateDTO scheduleDTO) {
        return new ScheduleSessionCreateDTO(scheduleSessionRepository.save(new ScheduleSession(scheduleDTO)));
    }

    public boolean openVotingSession(UUID id, int durationMinutes) {
        Optional<ScheduleSession> scheduleFromDB = scheduleSessionRepository.findById(id);
        if (scheduleFromDB.isPresent()) {
            ScheduleSession schedule = scheduleFromDB.get();
            schedule.setStartTime(LocalDateTime.now());
            schedule.setDurationMinutes(durationMinutes);
            scheduleSessionRepository.save(schedule);
            return true;
        }
        return false;
    }

    public Optional<ScheduleSession> findById(UUID scheduleItemId) {
        return scheduleSessionRepository.findById(scheduleItemId);
    }

    public List<ScheduleSession> findClosedSessions() {
        LocalDateTime now = LocalDateTime.now();
        return scheduleSessionRepository.findByStartTimeIsNotNull().stream()
                .filter(session -> session.getStartTime().plusMinutes(session.getDurationMinutes()).isBefore(now))
                .collect(Collectors.toList());
    }
}
