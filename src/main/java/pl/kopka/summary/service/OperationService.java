package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.repository.CategoryRepo;
import pl.kopka.summary.repository.MonthRepo;
import pl.kopka.summary.repository.OperationRepo;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OperationService {
    private final OperationRepo operationRepo;
    private final CategoryRepo categoryRepo;
    private final MonthRepo monthRepo;
    private final UserService userService;
    private final MonthService monthService;

    @Autowired
    public OperationService(OperationRepo operationRepo, CategoryRepo categoryRepo, MonthRepo monthRepo, UserService userService, MonthService monthService) {
        this.operationRepo = operationRepo;
        this.categoryRepo = categoryRepo;
        this.monthRepo = monthRepo;
        this.userService = userService;
        this.monthService = monthService;
    }

    public List<Month> addNewOperation(Operation operation){
        Category category = categoryRepo.findCategoryByCategoryId(operation.getCategoryId());
        operation.setOperationId(RandomStringUtils.randomNumeric(20));
        operation.setCategory(category);
        operation = operationRepo.save(operation);
        Month month = monthRepo.findMonthByMonthId(operation.getMonthId());
        month.addOperation(operation);
        monthRepo.save(month);

        category.addOperation(operation);
        categoryRepo.save(category);
        return monthService.getAllUserMonths();
    }

    @Transactional
    public void deleteOperation(String operationId) {
        operationRepo.deleteOperationByOperationId(operationId);
    }
}
