package net.company.unique.Service;

import net.company.unique.ApiResponse.WeatherResponse;
import net.company.unique.Cache.AppCache;
import net.company.unique.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class WeatherService
{
private static final String apiKey ="fc9a451f740108b8d6aec407770876f3";


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AppCache appCache;

    @Autowired
    private redisService Service;

    public WeatherResponse getWeather(String city) {
       WeatherResponse weatherResponse = Service.get("Weather_of_"+city, WeatherResponse.class);
        if (weatherResponse!=null)
        {
            return weatherResponse;
        }
        else {
            String finalApi = appCache.appCache.get(Constants.apiName).replace(Constants.apiKey,apiKey).replace(Constants.city,city);
            ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = responseEntity.getBody();
            if (body!=null)
            {
                Service.set("weather_of_"+city,body,300l);
            }
            return body;
        }
    }
}
