package it.epicode.forum.controller;

import it.epicode.forum.entity.Post;
import it.epicode.forum.entity.RispostaPost;
import it.epicode.forum.entity.User;
import it.epicode.forum.repo.PostRepo;
import it.epicode.forum.repo.RispostaPostRepo;
import it.epicode.forum.repo.UserRepo;
import it.epicode.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @PostMapping("/post/risposta")
    public ResponseEntity<?> rispostaPost(@RequestBody RispostaPost post, @RequestParam int id) {

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        post.setDataPubb(LocalDate.now());

        //SET THE LOGGED USER AS THE CREATOR OF THE POST
        post.setUser(ur.findByUsername(currentPrincipalName));

        post.setPost(pr.findById(id).get());

        RispostaPost p = rp.save(post);

        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User user) {

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User uId = ur.findByUsername(currentPrincipalName);

        id = uId.getId();

        User u = ur.findById(id).get();

        u.setBio(user.getBio());
        u.setImgUrl(user.getImgUrl());

        ur.save(u);

        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping("/like/{id}")
    public ResponseEntity<Post> like(@PathVariable int id, @RequestBody Post post) {

        Post p = pr.findById(id).get();

        p.setMiPiace(p.getMiPiace() + 1);

        pr.save(p);

        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id) {
        Optional<Post> p = pr.findById(id);

        //GET LOGGED USER
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        if(p.get().getUser().getUsername().equals(currentPrincipalName)) {
            pr.deleteById(id);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/risposta")
    public List<RispostaPost> getByPostId(@RequestParam int id) {
        return rp.findAllByUserId(id);
    }

    @GetMapping("/post")
    public List<Post> getAllPost() {
        return pr.findAll();
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
