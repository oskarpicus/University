package controllers;

import domain.JuryMember;
import domain.Mark;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.JuryMemberRepository;
import validator.ValidationException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "jury-members")
public class JuryMemberController {

    private final JuryMemberRepository repository;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        repository = context.getBean(JuryMemberRepository.class);
    }

    @GetMapping(value = "/{id}/marks")
    public ResponseEntity<?> getMarksByJuryMember(@PathVariable Long id) {
        Optional<JuryMember> juryMember = repository.find(id);
        if (juryMember.isEmpty()) {
            return new ResponseEntity<>("Jury member does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(juryMember.get().getMarks(), HttpStatus.OK);
    }


    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validationException(ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String illegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
