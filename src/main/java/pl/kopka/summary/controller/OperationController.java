package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kopka.summary.domain.model.Operation;
import pl.kopka.summary.service.OperationService;

@Controller
@RequestMapping("/operation")
@CrossOrigin
public class OperationController {

    private final OperationService operationService;
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/add")
    public ResponseEntity<Operation> addNewOperation(@RequestBody Operation operation){
        return new ResponseEntity<>(operationService.addNewOperation(operation), HttpStatus.OK);
    }
}
