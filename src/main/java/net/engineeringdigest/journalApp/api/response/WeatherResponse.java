package net.engineeringdigest.journalApp.api.response;

import java.util.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class WeatherResponse {

    private Current current;

    @Data
    public class Current {
        private int temperature;
        @JsonProperty("weather_description")
        private List<String> weatherDescriptions;
        private int feelslike;
    }

}