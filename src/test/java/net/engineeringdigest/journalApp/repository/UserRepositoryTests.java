package net.engineeringdigest.journalApp.repository;

import java.util.*;
import org.junit.jupiter.api.*;
import net.engineeringdigest.journalApp.entity.User;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void setup() {
        userRepository.save(User.builder().username("ram").password("$2a$ajklitrhe").email("ayan25844@gmail.com")
                .sentimentAnalysis(true).roles(Arrays.asList("USER")).build());
    }

    @Test
    void testSaveNewUser() {
        assertFalse(userRepository.getUserForSA().isEmpty());
    }

}
