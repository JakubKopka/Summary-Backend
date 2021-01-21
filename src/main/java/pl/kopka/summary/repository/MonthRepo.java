package pl.kopka.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kopka.summary.domain.model.Month;

@Repository
public interface MonthRepo extends JpaRepository<Month, Long> {
    Month findMonthByMonthId(String monthId);
    Month findMonthByYearAndNumber(int year, int number);
}
