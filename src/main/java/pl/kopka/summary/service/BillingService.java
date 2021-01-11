package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.repository.BillingRepo;

import java.util.List;

@Service
public class BillingService {


    @Autowired
    BillingRepo billingRepo;
//    @Autowired
//    UserService userService;

    @Autowired
    MonthService monthService;

    public Billing addNewBilling(Billing billing){
        billing.setBillingId(RandomStringUtils.randomNumeric(20));
        return billingRepo.save(billing);
    }

    public List<Month> getAllUserMonths() {
        return monthService.getAllUserMonths();
    }

//    public Billing getBilling() {
//        return userService.getCurrentLoginUser().getBilling();
//    }

//    public Billing getCurrentLoginUserBilling(){
//        return userService.getCurrentLoginUser().getBilling();
//    }
}
