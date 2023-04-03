package it.epicode.forum.repo;

import it.epicode.forum.entity.Notification;
import it.epicode.forum.entity.NotificationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationPostRepo extends JpaRepository<NotificationPost, Integer> {
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM notifications_post WHERE notifications_post.read = false AND notifications_post.user_id = :c")
    List<NotificationPost> findUnreadNotifications(@Param("c") int id);
}
