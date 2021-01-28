package pl.kopka.summary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.repository.BillingRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillingService {
    private final MonthService monthService;
    private final CategoryService categoryService;

    @Autowired
    public BillingService(MonthService monthService, CategoryService categoryService) {
        this.monthService = monthService;
        this.categoryService = categoryService;
    }

    public List<Month> getAllUserMonths() {
        return monthService.getAllUserMonths();
    }

    public List<Category> getAllUserCategories() {
        return categoryService.getAllUserCategories();
    }

    public Map<String, List<?>> getMonthsAndCategories() {
        List<Month> months = getAllUserMonths();
        List<Category> categories = getAllUserCategories();
        Map<String, List<?>> map = new HashMap<>();
        map.put("months", months);
        map.put("categories", categories);
        return map;
    }
}
