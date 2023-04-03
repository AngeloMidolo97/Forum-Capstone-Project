package it.epicode.forum.controller;

import it.epicode.forum.entity.Message;
import it.epicode.forum.entity.Notification;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.MessageRepo;
import it.epicode.forum.repo.NotificationRepo;
import it.epicode.forum.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class MessageController {

    @Autowired
    UserRepo ur;

    @Autowired
    MessageRepo mr;

    @Autowired
    NotificationRepo nr;

    //    MESSAGGI INVIATI DALL'UTENTE LOGGATO ALL'UTENTE SPECIFICATO
    @GetMapping("/sent")
    public List<Message> getSentMessages(@RequestParam int rec_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);

        return mr.findByRecipientSenderId(currentUser.getId(), rec_id );

    }

    @GetMapping("/all")
    public List<Message> getAllMessages(@RequestParam int rec_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);

        return mr.findAllMessages(currentUser.getId(), rec_id );

    }

    //    MESSAGGI RICEVUTI DALL'UTENTE LOGGATO DA parte dell'UTENTE SPECIFICATO
    @GetMapping("/received")
    public List<Message> getReceivedMessages(@RequestParam int sender_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);

        return mr.findByRecipientSenderId(sender_id, currentUser.getId() );

    }

    @PostMapping("/{recipient_id}")
    public ResponseEntity<?> sendMessage(@RequestBody Message message, @PathVariable int recipient_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);
        // user to give a review
        Optional<User> u = ur.findById(recipient_id);

        message.setSender(currentUser);

        message.setRecipient(u.get());

        message.setDate(LocalDate.now());

        mr.save(message);


        Notification n = new Notification();
        n.setTitle(message.getMessage());
        n.setMessage(message);
        n.setRecipient(u.get());

        nr.save(n);



        return new ResponseEntity<> (message, HttpStatus.CREATED);
    }
}
