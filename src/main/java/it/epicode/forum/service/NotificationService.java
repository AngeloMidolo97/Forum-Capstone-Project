package it.epicode.forum.service;

import it.epicode.forum.entity.Notification;
import it.epicode.forum.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    NotificationRepo nr;

    public List<Notification> findUnread(int id){
        return nr.findUnreadNotifications(id);
    }

    public Optional<Notification> findById(int id){
        return nr.findById(id);
    }

    public void saveNotification(Notification n) {
        nr.save(n);
    }
}
