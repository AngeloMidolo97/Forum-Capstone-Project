package it.epicode.forum.service;

import it.epicode.forum.entity.Post;
import it.epicode.forum.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepo pr;

    public Optional<List<Post>> filterByNome(String nome){
        return pr.findAllByTitleContaining(nome);
    }

    public Optional<List<Post>> filterByCategoria(String categoria) { return pr.findAllByCategoria(categoria); }

}
