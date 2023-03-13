package it.epicode.forum.repo;

import it.epicode.forum.entity.RispostaPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RispostaPostRepo extends JpaRepository<RispostaPost, Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM risposta_post AS r WHERE r.post_id = :c"
    )
    List<RispostaPost> findAllByUserId(@Param("c") int id);
}
