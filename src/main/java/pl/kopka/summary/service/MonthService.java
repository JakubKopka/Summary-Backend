package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.repository.BillingRepo;
import pl.kopka.summary.repository.MonthRepo;

import java.util.List;


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
        Billing billing = userService.getCurrentLoginUser().getBilling();
        month.setMonthId(RandomStringUtils.randomNumeric(20));
        month = monthRepo.save(month);
        billing.addMonth(month);
        billingRepo.save(billing);
        return month;
    }

    public List<Month> getAllUserMonths(){
        return userService.getCurrentLoginUser().getBilling().getMonths();
    }

}