package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.constant.MonthsAndCategoriesConst;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.exception.exceptions.MonthException;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.MonthRepo;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MonthService {
    private final MonthRepo monthRepo;
    private final UserService userService;
    private final BillingRepo billingRepo;

    @Autowired
    public MonthService(MonthRepo monthRepo, UserService userService, BillingRepo billingRepo) {
        this.monthRepo = monthRepo;
        this.userService = userService;
        this.billingRepo = billingRepo;
    }

    public List<Month> addNewMonth(Month month) throws MonthException {
        List<Month> userMonths = getAllUserMonths();
        Month finalMonth = month;
        if (userMonths.stream().filter(m -> m.getNumber() == finalMonth.getNumber() && m.getYear() == finalMonth.getYear()).collect(Collectors.toList()).stream().findFirst().isPresent()) {
            throw new MonthException(MonthsAndCategoriesConst.MONTH_EXIST);
        }
        month.setMonthId(RandomStringUtils.randomNumeric(20));
        month = monthRepo.save(month);
        Billing billing = userService.getCurrentLoginUser().getBilling();
        billing.addMonth(month);
        billingRepo.save(billing);
        return getAllUserMonths();
    }

    public List<Month> getAllUserMonths() {
        List<Month> months = userService.getCurrentLoginUser().getBilling().getMonths();
        months.forEach(obj -> obj.setTotal(obj.getOperationList().stream().mapToDouble(Operation::getAmount).sum()));
        return months;
    }

}
