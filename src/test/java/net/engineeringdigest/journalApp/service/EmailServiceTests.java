package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail() {
        emailService.sendEmail("ayan25844@gmail.com",
                "Testing Java mail sender",
                "Hi, aap kaise hain ?");
    }
}
