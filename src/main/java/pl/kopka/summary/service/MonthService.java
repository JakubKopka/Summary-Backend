package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.MonthRepo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Service
public class MonthService {
    @Autowired
    MonthRepo monthRepo;
    @Autowired
    BillingService billingService;
    @Autowired
    BillingRepo billingRepo;
    @Autowired
    UserService userService;


    public Month addNewMonth(Month month){
        month.setMonthId(RandomStringUtils.randomNumeric(20));
        month = monthRepo.save(month);
        Billing billing = userService.getCurrentLoginUser().getBilling();
        billing.addMonth(month);
        billingRepo.save(billing);
        return month;
    }

    public Set<Month> getAllUserMonths(){
        Set<Month> months = userService.getCurrentLoginUser().getBilling().getMonths();
        months.forEach(obj -> obj.setTotal(obj.getOperationList().stream().mapToDouble(Operation::getAmount).sum()));
        return months;
    }

}
