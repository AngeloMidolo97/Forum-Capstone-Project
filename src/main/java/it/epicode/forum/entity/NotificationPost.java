package it.epicode.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notifications_post")
public class NotificationPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="post_id", referencedColumnName = "id")
    private Post post;

    private boolean read = false;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User recipient;
}
