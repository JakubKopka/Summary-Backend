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

@Service
public class OperationService {
    @Autowired
    OperationRepo operationRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    MonthRepo monthRepo;
    @Autowired
    UserService userService;

    public Operation addNewOperation(Operation operation){
        Category category = categoryRepo.findCategoryByCategoryId(operation.getCategoryId());
        operation.setOperationId(RandomStringUtils.randomNumeric(20));
        operation.setCategory(category);
        operation = operationRepo.save(operation);
        Month month = monthRepo.findMonthByMonthId(operation.getMonthId());
        month.addOperation(operation);
        monthRepo.save(month);
        category.addOperation(operation);
        category = categoryRepo.save(category);
        return operation;
    }
}
