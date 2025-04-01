package net.engineeringdigest.journalApp.service;

import java.util.*;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.core.userdetails.UserDetails;
import net.engineeringdigest.journalApp.repository.UserRepository;

@ActiveProfiles("dev")
class UserDetailsServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(
                        User.builder().username("ram").password("$2a$ajklitrhe").roles(Arrays.asList("USER")).build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        assertNotNull(user);
    }
}
