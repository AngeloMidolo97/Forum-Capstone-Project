package it.epicode.forum.repo;

import it.epicode.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM posts AS p WHERE p.user_id = :p"
    )
    public Optional<List<Post>> getPostByUser(@Param("p") int id);


    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts AS c WHERE c.title LIKE %:m" + "%"
    )
    Optional<List<Post>> findAllByTitleContaining(@Param("m") String nome);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts WHERE posts.categoria = :c"
    )
    Optional<List<Post>> findAllByCategoria(@Param("c") String categoria);

}
