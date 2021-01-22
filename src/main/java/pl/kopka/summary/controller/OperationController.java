package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kopka.summary.domain.model.Month;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.service.MonthService;
import pl.kopka.summary.service.OperationService;

import java.util.List;

@Controller
@RequestMapping("/operation")
public class OperationController {

    private final MonthService monthService;
    private final OperationService operationService;

    @Autowired
    public OperationController(MonthService monthService, OperationService operationService) {
        this.monthService = monthService;
        this.operationService = operationService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<Month>> addNewOperation(@RequestBody Operation operation){
        return new ResponseEntity<>(operationService.addNewOperation(operation), HttpStatus.OK);
    }

    @DeleteMapping("/{operationId}")
    public ResponseEntity<List<Month>> deleteOperation(@PathVariable String operationId){
        operationService.deleteOperation(operationId);
        return new ResponseEntity<>(monthService.getAllUserMonths(), HttpStatus.OK);
    }
}
