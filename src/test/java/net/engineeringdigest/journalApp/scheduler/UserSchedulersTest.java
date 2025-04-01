package net.engineeringdigest.journalApp.scheduler;

import java.util.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.*;
import net.engineeringdigest.journalApp.entity.*;
import net.engineeringdigest.journalApp.repository.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserSchedulersTest {

    @Autowired
    private UserScheduler userScheduler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @BeforeAll
    void setup() {
        User user = userRepository.save(User.builder()
                .email("ayan25844@gmail.com")
                .username("Ayan")
                .password("$2a$ajklitrhe")
                .sentimentAnalysis(true)
                .roles(Arrays.asList("USER"))
                .build());

        journalEntryRepository.saveAll(
                List.of(
                        JournalEntry.builder()
                                .title("Angry")
                                .content("I am very angry")
                                .date(LocalDateTime.now())
                                .sentiment(Sentiment.ANGRY)
                                .user(user)
                                .build(),
                        JournalEntry.builder()
                                .title("Sad")
                                .content("I am very sad")
                                .date(LocalDateTime.now())
                                .sentiment(Sentiment.SAD)
                                .user(user)
                                .build(),
                        JournalEntry.builder()
                                .title("Angry")
                                .content("I am very angry")
                                .date(LocalDateTime.now())
                                .sentiment(Sentiment.ANGRY)
                                .user(user)
                                .build()));
    }

    @Test
    @Transactional
    void testFetchUsersAndSendSaMail() {
        userScheduler.fetchUsersAndSendSaMail();
    }

}
