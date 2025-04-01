package net.engineeringdigest.journalApp.service;

import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.repository.UserRepository;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    // @Disabled
    void test() {
        assertEquals(4, 2 + 2);
    }

    @ParameterizedTest
    @CsvSource({ "2,1,1", "12,10,2", "9,6,3" })
    void test(int expected, int a, int b) {
        assertEquals(expected, a + b);
    }

    @BeforeAll
    void setup() {
        userRepository.saveAll(
                List.of(User.builder().username("ram").password("$2a$ajklitrhe").roles(Arrays.asList("USER")).build(),
                        User.builder().username("shyam").password("$2a$idgeobg").roles(Arrays.asList("USER")).build()));
    }

    @ParameterizedTest
    @CsvSource({ "ram", "shyam" })
    void testFindByUsername(String username) {
        assertNotNull(userRepository.findByUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = { "ram", "shyam" })
    void testFindByUsername_2(String username) {
        assertNotNull(userRepository.findByUsername(username));
    }

    @ParameterizedTest
    @ArgumentsSource(value = UserArgumentsProvider.class)
    void testSaveNewUser(User user) {
        assertTrue(userService.saveEntry(user));
        assertNotNull(userRepository.findByUsername(user.getUsername()));
    }

}
