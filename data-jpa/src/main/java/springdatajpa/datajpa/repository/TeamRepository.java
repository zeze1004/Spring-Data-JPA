package springdatajpa.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springdatajpa.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
