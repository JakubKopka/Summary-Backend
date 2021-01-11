package pl.kopka.summary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kopka.summary.domain.model.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findCategoryByCategoryId(String categoryId);
}
