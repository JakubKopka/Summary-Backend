package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.repository.BillingRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillingService {

    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private MonthService monthService;
    @Autowired
    private CategoryService categoryService;


    public Billing addNewBilling(Billing billing){
        billing.setBillingId(RandomStringUtils.randomNumeric(20));
        return billingRepo.save(billing);
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
