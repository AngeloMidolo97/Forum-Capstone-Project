package it.epicode.forum.repo;

import it.epicode.forum.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM posts AS p WHERE p.id_user_ = :p ORDER BY p.data_pubb DESC"
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

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts ORDER BY posts.mi_piace DESC LIMIT 3"
    )
    Optional<List<Post>> findTop3();

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts ORDER BY posts.data_pubb DESC"
    )
    Page<Post> findAllByIdDesc(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts ORDER BY posts.title"
    )
    Page<Post> findAllByTitle(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM posts ORDER BY categoria ASC"
    )
    Page<Post> findAllByCategoriaASC(Pageable pageable);

}
