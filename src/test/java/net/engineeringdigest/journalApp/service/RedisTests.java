package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class RedisTests {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("unchecked")
    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("email", "ayan25844@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
        assertEquals("ayan25844@gmail.com", email);
    }

}
