package com.aryan.journalApp.service;


import com.aryan.journalApp.api.response.WeatherResponse;
import com.aryan.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {

        String cacheKey = "weather_of_" + city.toLowerCase();

        // Try Redis first
        WeatherResponse cached =
                redisService.get(cacheKey, WeatherResponse.class);

        if (cached != null) {
            return cached;
        }

        // Build API URL
        String finalAPI = appCache.APP_CACHE
                .get(AppCache.keys.WEATHER_API.toString())
                .replace("<city>", city)
                .replace("<apiKey>", apiKey);

        // Call external API (GET)
        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(
                        finalAPI,
                        HttpMethod.GET,
                        null,
                        WeatherResponse.class
                );

        WeatherResponse body = response.getBody();

        // Cache if response valid
        if (body != null) {
            redisService.set(cacheKey, body, 300L); // 5 min TTL
        }

        return body;
    }
}