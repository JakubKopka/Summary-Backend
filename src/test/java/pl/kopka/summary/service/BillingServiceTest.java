package pl.kopka.summary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.kopka.summary.domain.model.Category;
import pl.kopka.summary.domain.model.Month;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

class BillingServiceTest {

    @Mock
    private CategoryService categoryService;
    @Mock
    private MonthService monthService;

    @InjectMocks
    private BillingService mockedBillingService;

    private List<Category> categories;
    private  List<Month> months;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        categories = new ArrayList<>();
        Category category = new Category();
        category.setName("Test Category");
        category.setDescription("Test Category Description");
        categories.add(category);

        months = new ArrayList<>();
        Month month = new Month();
        month.setYear(2021);
        month.setNumber(1);
        months.add(month);
    }

    @Test
    void shouldReturnMonthsAndCategories() {
        //given
        when(categoryService.getAllUserCategories()).thenReturn(categories);
        when(monthService.getAllUserMonths()).thenReturn(months);

        //when
        Map<String, List<?>> data = mockedBillingService.getMonthsAndCategories();

        //then
        Assertions.assertNotNull(data.get("categories"));
        Assertions.assertNotNull(data.get("months"));
        Assertions.assertEquals(categories, data.get("categories"));
        Assertions.assertEquals(months, data.get("months"));
    }
}