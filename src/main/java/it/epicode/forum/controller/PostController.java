package it.epicode.forum.controller;

import it.epicode.forum.entity.NotificationPost;
import it.epicode.forum.entity.Post;
import it.epicode.forum.entity.RispostaPost;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.PostRepo;
import it.epicode.forum.repo.RispostaPostRepo;
import it.epicode.forum.repo.UserRepo;
import it.epicode.forum.service.NotificationPostService;
import it.epicode.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PostController {

    @Autowired
    PostRepo pr;

    @Autowired
    PostService ps;

    @Autowired
    UserRepo ur;

    @Autowired
    RispostaPostRepo rp;

    @Autowired
    NotificationPostService nps;

    //AGGIUNGE POST
    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        //SET THE LOGGED USER AS THE CREATOR OF THE POST
        post.setUser(ur.findByUsername(currentPrincipalName));

        post.setDataPubb(LocalDate.now());

        Post p = pr.save(post);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //RISPOSTA AL POST
    @PostMapping("/post/risposta")
    public ResponseEntity<?> rispostaPost(@RequestBody RispostaPost rispostaPost, @RequestParam int id) {

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Post p = pr.findById(id).get();

        List<RispostaPost> risposte = p.getRisposte();

        rispostaPost.setDataPubb(LocalDate.now());

        //SET THE LOGGED USER AS THE CREATOR OF THE POST
        rispostaPost.setUser(ur.findByUsername(currentPrincipalName));

        //rispostaPost.setPost(pr.findById(id).get());

        risposte.add(rispostaPost);

        RispostaPost r = rp.save(rispostaPost);

        /*NotificationPost n = new NotificationPost();
        n.setRecipient(p.getUser());
        n.setTitle(r.getUser().getUsername() + " ha risposto al tuo post");
        n.setPost(p);
        nps.saveNotification(n);
        */

        return new ResponseEntity<>(rispostaPost, HttpStatus.CREATED);
    }

    //MODIFICA PROFILO
    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User uId = ur.findByUsername(currentPrincipalName);

        id = uId.getId();

        User u = ur.findById(id).get();

        if(user.getBio() == "" | user.getBio() == null) {
            u.setBio(u.getBio());
        } else {
            u.setBio(user.getBio());
        }

        if(user.getImgUrl() == "" | user.getImgUrl() == null) {
            u.setImgUrl(u.getImgUrl());
        } else {
            u.setImgUrl(user.getImgUrl());
        }

        ur.save(u);

        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    //LIKE AL POST
    @PutMapping("/like/{id}")
    public ResponseEntity<Post> like(@PathVariable int id, @RequestBody Post post) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User u = ur.findByUsername(currentPrincipalName);

        Post p = pr.findById(id).get();

        List<User> likes = p.getLikes();



        if (p.getLikes().contains(ur.findByUsername(currentPrincipalName))) {
            p.setMiPiace(p.getMiPiace() - 1);
            likes.remove(
                    ur.findByUsername(currentPrincipalName)
            );
        } else {
            likes.add(
                    ur.findByUsername(currentPrincipalName)
            );

            p.setMiPiace(p.getMiPiace() + 1);
        }

        p.setLikes(likes);

        pr.save(p);

        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    //CANCELLA POST
    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        Optional<Post> p = pr.findById(id);

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if (p.get().getUser().getUsername().equals(currentPrincipalName)) {
            pr.deleteById(id);

        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/risposta")
    public List<RispostaPost> getByPostId(@RequestParam int id) {
        return rp.findAllByUserId(id);
    }

    @GetMapping("/post")
    public Page<Post> getAllPost(Pageable pageable, @RequestParam int page, @RequestParam int size) {
        return pr.findAllByIdDesc(pageable);
    }

    @GetMapping("/post/title")
    public Page<Post> getAllPostByTitle(Pageable pageable, @RequestParam int page, @RequestParam int size) {
        return pr.findAllByTitle(pageable);
    }

    @GetMapping("/post/categoria")
    public Page<Post> getAllPostByCategoria(Pageable pageable, @RequestParam int page, @RequestParam int size) {
        return pr.findAllByCategoriaASC(pageable);
    }

    @GetMapping("/post/top3")
    public Optional<List<Post>> getTop3() {
        return pr.findTop3();
    }

    @GetMapping("/mypost")
    public Optional<List<Post>> getPostByUser(@RequestParam int id) {
        return pr.getPostByUser(id);
    }

    @GetMapping("/categoria")
    public Optional<List<Post>> getPostByCategoria(@RequestParam String categoria) {
        return ps.filterByCategoria(categoria);
    }

    @GetMapping("/post/name")
    public Optional<List<Post>> getByTitleContain(@RequestParam String nome) {
        return ps.filterByNome(nome);
    }

    @GetMapping("/post/{id}")
    public Optional<Post> getByPostById(@RequestParam int id) {
        return pr.findById(id);
    }


}
