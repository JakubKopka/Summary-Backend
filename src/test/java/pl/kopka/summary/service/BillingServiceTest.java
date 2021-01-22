package pl.kopka.summary.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.repository.BillingRepo;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class BillingServiceTest {

    @Mock
    private BillingService mockedBillingService;

    @Test
    void shouldReturnMonthsAndCategories() {
        //given
        List<Category> categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        categories.add(category);

        List<Month> months = new ArrayList<>();
        Month month = new Month();
        month.setYear(2021);
        month.setNumber(1);
        months.add(month);

        //when
        when(mockedBillingService.getAllUserCategories()).thenReturn(categories);
        when(mockedBillingService.getAllUserMonths()).thenReturn(months);

        //then
        System.out.println(mockedBillingService.getAllUserMonths()); // [.....Month@1694cbc]
        System.out.println(mockedBillingService.getAllUserCategories()); //        [Category(name=Test Category, description=Test Category Description, operationList=[], total=0.0)]
        System.out.println(mockedBillingService.getMonthsAndCategories()); {}
    }
}