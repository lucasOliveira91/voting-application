package br.com.voteapp.repository;

import br.com.voteapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByScheduleSessionId(UUID agendaItemId);

    boolean existsByCpfAndScheduleSessionId(String cpf, UUID schedule);
}
