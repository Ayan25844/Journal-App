package net.engineeringdigest.journalApp.cache;

import java.util.*;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.entity.ConfigJournalApp;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;

@Component
public class AppCache {

    public enum keys {
        QUOTE_API,
        WEATHER_API;
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    private Map<String, String> appCache;

    public Map<String, String> getAPP_CACHE() {
        return appCache;
    }

    @PostConstruct
    public void init() {
        List<ConfigJournalApp> configList = List.of(
                ConfigJournalApp.builder()
                        .configKey(keys.QUOTE_API.toString())
                        .configValue("https://api.api-ninjas.com/v1/quotes")
                        .build(),
                ConfigJournalApp.builder()
                        .configKey(keys.WEATHER_API.toString())
                        .configValue("https://api.weatherstack.com/current?access_key=<apiKey>&query=<city>")
                        .build());
        configJournalAppRepository.saveAll(configList);
        loadConfig();
    }

    public void loadConfig() {
        appCache = new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepository.findAll();
        for (ConfigJournalApp configJournalApp : all) {
            appCache.put(configJournalApp.getConfigKey(), configJournalApp.getConfigValue());
        }
    }

}
