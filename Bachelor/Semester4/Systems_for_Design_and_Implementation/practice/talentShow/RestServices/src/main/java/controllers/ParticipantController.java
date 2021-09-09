package controllers;

import domain.Participant;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ParticipantRepository;

import java.util.Optional;

@RestController
@RequestMapping(value = "participants")
public class ParticipantController {

    private final ParticipantRepository repository;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        repository = context.getBean(ParticipantRepository.class);
    }

    @GetMapping(value = "/{id}/marks")
    public ResponseEntity<?> getMarksByParticipant(@PathVariable Long id) {
        Optional<Participant> participant = repository.find(id);
        if (participant.isEmpty()) {
            return new ResponseEntity<>("Participant does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(participant.get().getMarks(), HttpStatus.OK);
    }
}
