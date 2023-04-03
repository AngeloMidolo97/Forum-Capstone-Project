package it.epicode.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @OneToOne
    private Message message;

    private boolean read = false;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User recipient;
}
