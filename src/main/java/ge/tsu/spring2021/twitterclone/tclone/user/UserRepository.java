package ge.tsu.spring2021.twitterclone.tclone.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    User findById(long id);
}