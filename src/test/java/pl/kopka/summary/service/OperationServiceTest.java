package pl.kopka.summary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.CategoryRepo;
import pl.kopka.summary.repository.MonthRepo;
import pl.kopka.summary.repository.OperationRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OperationServiceTest {

    @Mock private OperationRepo operationRepo;
    private CategoryRepo categoryRepo;
    private MonthRepo monthRepo;
    @Mock private MonthService monthService;
    @InjectMocks private OperationService operationService;

    private List<Month> months;
    private Month month;
    private Operation operation;
    private List<Operation> operations;

    @BeforeEach
    private void init() {
        categoryRepo = mock(CategoryRepo.class, Mockito.RETURNS_DEEP_STUBS);
        monthRepo = mock(MonthRepo.class, Mockito.RETURNS_DEEP_STUBS);

        MockitoAnnotations.openMocks(this);

        months = new ArrayList<>();
        month = new Month();
        month.setMonthId("6543217890");
        month.setNumber(1);
        month.setYear(2021);
        months.add(month);

        operation = new Operation();
        operation.setOperationId("1234567890");
        operation.setAmount(100.59);
        operation.setDate(new Date());
        operation.setDescription("Test");
        operations = new ArrayList<>();
        operations.add(operation);
    }


    @Test
    void shouldAddNewOperation() {
        //given
        Category category = new Category();
        category.setCategoryId("1234567890");
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        when(categoryRepo.findCategoryByCategoryId(any(String.class))).thenReturn(category);


        when(monthRepo.findMonthByMonthId(any(String.class))).thenReturn(month);
        when(operationRepo.save(any(Operation.class))).thenReturn(operation);
        when(monthService.getAllUserMonths()).thenReturn(months);
        //when
        List<Month> newMonths = operationService.addNewOperation(operation);
        //then
        Assertions.assertNotNull(newMonths);
        Assertions.assertEquals(1, newMonths.size());
    }

    @Test
    void shouldDeleteOperation() {
        //given
        doAnswer(i -> {
            operations = operations.stream().filter(o -> !o.getOperationId().equals(operation.getOperationId())).collect(Collectors.toList());
            return null;
        }).when(operationRepo).deleteOperationByOperationId(any(String.class));

        Assertions.assertEquals(1, operations.size());
        operationService.deleteOperation(operation.getOperationId());
        Assertions.assertEquals(0, operations.size());
    }

}