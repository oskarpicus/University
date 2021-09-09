package controllers;

import domain.Participant;
import domain.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ParticipantRepository;

import java.util.List;

@RestController
@RequestMapping(value = "participants")
public class ParticipantController {

    private final ParticipantRepository participantRepository;

    {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Spring.xml");
        participantRepository = context.getBean(ParticipantRepository.class);
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getParticipantsByStatus(@RequestParam("status") Status status) {
        List<Participant> participants = participantRepository.findParticipantByStatus(status);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

}
