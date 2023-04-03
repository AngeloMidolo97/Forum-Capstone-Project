package it.epicode.forum.controller;

import it.epicode.forum.entity.Notification;
import it.epicode.forum.entity.NotificationPost;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.UserRepo;
import it.epicode.forum.service.NotificationPostService;
import it.epicode.forum.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification_post")
@CrossOrigin
public class NotificationPostController {

    @Autowired
    UserRepo ur;

    @Autowired
    NotificationPostService nps;

    @GetMapping("/unread")
    public List<NotificationPost> getUnreadNotification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        // logged user
        User currentUser = ur.findByUsername(currentPrincipalName);
        return nps.findUnread(currentUser.getId());
    }

    @PutMapping("/{notification_id}")
    public ResponseEntity<NotificationPost> switchNotification(@PathVariable int notification_id , @RequestBody NotificationPost notification){

        NotificationPost currentNotification = nps.findById(notification_id).get();

        currentNotification.setRead(true);

        nps.saveNotification(currentNotification);

        return new ResponseEntity<> (currentNotification, HttpStatus.CREATED);
    }
}
