package it.epicode.forum.repo;

import it.epicode.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findByUsernameIgnoreCase(String username);

    public User findByUsername(@Param("u") String username);

}
