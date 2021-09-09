package controllers;

import domain.Employee;
import domain.Skill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public ResponseEntity<?> getAll() {
        Employee e = new Employee(); e.setName("Ana"); e.setId(1L);
        Skill skill = new Skill(); skill.setName("adapt"); skill.setId(1L);
        Set<Skill> skills = new HashSet<>(); skills.add(skill);
        e.setSkills(skills);

        return new ResponseEntity<>(e, HttpStatus.OK);
    }
}
