package it.epicode.forum.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String descrizione;

    private String imgUrl;

    private LocalDate dataPubb;

    @Enumerated(EnumType.STRING)
    private CategoryEnum categoria;

    @Column(name = "mi_piace")
    private int miPiace;

    @ManyToOne
    @JoinColumn(name = "id_user_", referencedColumnName = "id")
    //@JsonManagedReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private List<RispostaPost> risposte;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_likes",
            joinColumns = @JoinColumn(name = "post_id_like"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes;



}
