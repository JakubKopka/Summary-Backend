package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.service.MonthService;

@Controller
@RequestMapping("/month")
public class MonthController {
    private final MonthService monthService;
    @Autowired
    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @PostMapping("/add")
    public ResponseEntity<Month> add(@RequestBody Month month){
        return new ResponseEntity<>(monthService.addNewMonth(month), HttpStatus.OK);
    }
}
