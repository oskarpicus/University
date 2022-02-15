package controllers;

import model.Expense;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.Service;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/expenses")
public class ExpenseController {
    private final Service service;

    {
        service = new ClassPathXmlApplicationContext("classpath:spring.xml").getBean(Service.class);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findExpense(@PathVariable(value = "id") String id) {
        Expense expense = service.findExpense(id);
        if (expense == null) {
            return new ResponseEntity<>("Expense does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable(value = "id") String id) {
        Expense result = service.deleteExpense(id);
        if (result == null) {
            return new ResponseEntity<>("Failed to delete expense", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveExpense(@RequestBody Expense expense) {
        try {
            Expense result = service.addExpense(expense);
            if (result == null) {
                return new ResponseEntity<>("Failed to save expense", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        try {
            expense.setId(id);
            Expense result = service.updateExpense(expense);
            if (result == null) {
                return new ResponseEntity<>("Failed to update expense", HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
