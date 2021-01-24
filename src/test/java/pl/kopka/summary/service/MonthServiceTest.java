package pl.kopka.summary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.exception.exceptions.MonthException;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.MonthRepo;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

class MonthServiceTest {

    private UserService userService;
    @Mock private MonthRepo monthRepo;
    @Mock private BillingRepo billingRepo;
    @InjectMocks private MonthService monthService;
    private List<Month> months;

    @BeforeEach
    private void init(){
        userService = mock(UserService.class, Mockito.RETURNS_DEEP_STUBS);
        MockitoAnnotations.openMocks(this);

        months = new ArrayList<>();
        Month month = new Month();
        month.setYear(2021);
        month.setNumber(1);
        months.add(month);
    }

    @Test
    void shouldAddNewMonth() throws MonthException {
        //given
        Month m = new Month();
        m.setNumber(2021);
        m.setNumber(2);
        m.setMonthId("0987654321");
        when(userService.getCurrentLoginUser().getBilling().getMonths()).thenReturn(months);
        when(monthRepo.save(Mockito.any(Month.class))).then(i -> {
            months.add(m);
            return m;
        });
        //when
        List<Month> newMonths = monthService.addNewMonth(m);
        //then
        Assertions.assertNotNull(newMonths);
        Assertions.assertEquals(months.size(), newMonths.size());
    }

    @Test
    void shouldReturnAllUserMonths() {
        //given
        when(userService.getCurrentLoginUser().getBilling().getMonths()).thenReturn(months);
        //then
        List<Month> monthList = monthService.getAllUserMonths();
        //when
        Assertions.assertEquals(months.size(), monthList.size());
    }
}