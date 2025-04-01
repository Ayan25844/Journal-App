package net.engineeringdigest.journalApp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM users u WHERE u.email IS NOT NULL AND u.sentimentAnalysis = true")
    public List<User> getUserForSA();

    public User findByUsername(String username);

    @Modifying
    @Transactional
    public void deleteByUsername(String username);

}
