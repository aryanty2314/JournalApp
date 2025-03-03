package net.company.unique.ApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherResponse {
    private Request request;
    private Location location;
    private Current current;

    @Data
    public static class Current {
        @JsonProperty("observation_time")
        private String observationTime;
        private Integer temperature;

        @JsonProperty("weather_code")
        private Integer weatherCode;

        @JsonProperty("weather_time")
        private List<String> weatherIcons = new ArrayList<>();

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions = new ArrayList<>();

        @JsonProperty("wind_speed")
        private Integer windSpeed;

        @JsonProperty("wind_degree") // Duplicate key fix
        private Integer windDegree;

        @JsonProperty("wind_dir")
        private String windDir;

        private Integer pressure;
        private Integer precip;
        private Integer humidity;
        private Integer cloudcover;
        private Integer feelslike;

        @JsonProperty("uv_index")
        private Integer uvIndex;

        private Integer visibility;

        @JsonProperty("is_day")
        private String isDay;
    }

    @Data
    public static class Location {
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        private String timezoneId;
        private String localtime;
        private Integer localtimeEpoch;

        @JsonProperty("utc_offset")
        private String utcOffset;
    }

    @Data
    public static class Request {
        private String type;
        private String query;
        private String language;
        private String unit;
    }
}
