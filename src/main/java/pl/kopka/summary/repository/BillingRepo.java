package pl.kopka.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kopka.summary.domain.model.Billing;

public interface BillingRepo  extends JpaRepository<Billing, Long> {
}
