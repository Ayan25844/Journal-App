package net.engineeringdigest.journalApp.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.PlaceHolders;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        String finalAPI = appCache.getAPP_CACHE().get(AppCache.keys.WEATHER_API.toString())
                .replace(PlaceHolders.API_KEY, apiKey)
                .replace(PlaceHolders.CITY,
                        city);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null,
                WeatherResponse.class);
        WeatherResponse body = response.getBody();
        if (body != null) {
            redisService.set("weather_of_" + city, body, 300l);
        }
        return body;
    }

}
