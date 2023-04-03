package it.epicode.forum.repo;

import it.epicode.forum.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM notifications WHERE notifications.read = false AND notifications.user_id = :c")
    List<Notification> findUnreadNotifications(@Param("c") int id);
}
