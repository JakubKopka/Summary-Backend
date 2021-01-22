package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.exception.exceptions.MonthException;
import pl.kopka.summary.service.MonthService;

import java.util.List;

@Controller
@RequestMapping("/month")
public class MonthController {

    private final MonthService monthService;
    @Autowired
    public MonthController(MonthService monthService) {
        this.monthService = monthService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Month>> add(@RequestBody Month month) throws MonthException {
        return new ResponseEntity<>(monthService.addNewMonth(month), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Month>> all(){
        return new ResponseEntity<>(monthService.getAllUserMonths(), HttpStatus.OK);
    }
}
