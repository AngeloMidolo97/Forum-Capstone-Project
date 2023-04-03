package it.epicode.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private String message;

    @ManyToOne
    @JoinColumn(name="sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name="recipient_id", referencedColumnName = "id")
    private User recipient;
}
