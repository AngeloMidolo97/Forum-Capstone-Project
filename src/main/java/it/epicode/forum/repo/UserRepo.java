package it.epicode.forum.repo;

import it.epicode.forum.entity.Post;
import it.epicode.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findByUsernameIgnoreCase(String username);

    public User findByUsername(@Param("u") String username);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM users ORDER BY users.data_registrazione DESC LIMIT 1"
    )
    Optional<List<User>> findLastUser();

}
