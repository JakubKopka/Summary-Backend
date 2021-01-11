package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.service.BillingService;

import java.util.List;

@Controller
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping("/months")
    public ResponseEntity<List<Month>> getAllUserMonths(){
        return new ResponseEntity<>(billingService.getAllUserMonths(), HttpStatus.OK);
    }
}
