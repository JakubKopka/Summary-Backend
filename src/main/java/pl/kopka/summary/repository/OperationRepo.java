package pl.kopka.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kopka.summary.domain.model.Operation;

@Repository
public interface OperationRepo extends JpaRepository<Operation, Long> {
}
