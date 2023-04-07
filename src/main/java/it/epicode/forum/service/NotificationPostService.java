package it.epicode.forum.service;

import it.epicode.forum.entity.NotificationPost;
import it.epicode.forum.repo.NotificationPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationPostService {

    @Autowired
    NotificationPostRepo npr;

    public List<NotificationPost> findUnread(int id){
        return npr.findUnreadNotifications(id);
    }

    /* public void deleteNotification(int id){
        npr.deleteAllByPost(id);
    }*/

    public Optional<NotificationPost> findById(int id){
        return npr.findById(id);
    }

    public void saveNotification(NotificationPost n) {
        npr.save(n);
    }
}
