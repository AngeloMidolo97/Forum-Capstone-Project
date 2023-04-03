package it.epicode.forum.controller;

import it.epicode.forum.entity.Notification;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.UserRepo;
import it.epicode.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    NotificationService ns;

    @Autowired
    UserRepo ur;


    @GetMapping("/unread")
    public List<Notification> getUnreadNotification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);
        return ns.findUnread(currentUser.getId());
    }

    @PutMapping("/{notification_id}")
    public ResponseEntity<Notification> switchNotification(@PathVariable int notification_id , @RequestBody Notification notification){

        Notification currentNotification = ns.findById(notification_id).get();

        currentNotification.setRead(true);

        ns.saveNotification(currentNotification);

        return new ResponseEntity<> (currentNotification, HttpStatus.CREATED);
    }
}
