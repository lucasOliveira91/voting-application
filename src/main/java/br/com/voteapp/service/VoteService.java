package br.com.voteapp.service;

import br.com.voteapp.model.ScheduleSession;
import br.com.voteapp.model.Vote;
import br.com.voteapp.repository.VoteRepository;
import br.com.voteapp.service.dto.VoteDTO;
import br.com.voteapp.service.dto.VotingResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private ScheduleSessionService scheduleSessionService;

    public void doVote(VoteDTO voteDTO) {
        Optional<ScheduleSession> schedule = scheduleSessionService.findById(voteDTO.getScheduleID());

        if (schedule.isEmpty()) {
            throw new RuntimeException("Pauta não foi localizada.");
        }
        if(!isVotingOpen(schedule.get())) {
            throw new RuntimeException("Pauta fora do período de votação.");
        }

        boolean hasVoted = voteRepository.existsByCpfAndScheduleSessionId(voteDTO.getCpf(), voteDTO.getScheduleID());
        if (hasVoted) {
            throw new RuntimeException("Este usuário já realizou a votação.");
        }else {
            Vote vote = new Vote();
            vote.setCpf(voteDTO.getCpf());
            vote.setScheduleSession(schedule.get());
            vote.setVoteValue(voteDTO.isVoteValue());
            vote.setCpf(voteDTO.getCpf());
            voteRepository.save(vote);
        }

    }

    private boolean isVotingOpen(ScheduleSession agendaItem) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sessionEndTime = agendaItem.getStartTime().plusMinutes(agendaItem.getDurationMinutes());
        return now.isAfter(agendaItem.getStartTime()) && now.isBefore(sessionEndTime);
    }

    public VotingResultDTO calculateVotingResult(UUID scheduleID) {
        List<Vote> votes = voteRepository.findByScheduleSessionId(scheduleID);

        int yesVotes = 0;
        int noVotes = 0;

        for (Vote vote : votes) {
            if (vote.isVoteValue()) {
                yesVotes++;
            } else {
                noVotes++;
            }
        }

        VotingResultDTO resultDTO = new VotingResultDTO();
        resultDTO.setSchedule(scheduleID);
        resultDTO.setTotalVotes(votes.size());
        resultDTO.setYesVotes(yesVotes);
        resultDTO.setNoVotes(noVotes);

        return resultDTO;
    }

}
