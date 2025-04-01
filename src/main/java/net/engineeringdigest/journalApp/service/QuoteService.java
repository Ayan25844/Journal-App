package net.engineeringdigest.journalApp.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.api.response.QuoteResponse;

@Service
public class QuoteService {

    @Value("${quotes.qpi.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;
    @Autowired
    private RestTemplate restTemplate;

    public QuoteResponse getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<QuoteResponse[]> response = restTemplate.exchange(
                appCache.getAPP_CACHE().get(AppCache.keys.QUOTE_API.toString()),
                HttpMethod.GET, httpEntity,
                QuoteResponse[].class);
        return response.getBody()[0];
    }

}
