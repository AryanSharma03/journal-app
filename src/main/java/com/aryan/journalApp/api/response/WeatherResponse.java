package com.aryan.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class WeatherResponse{

    private Current current;

    @Getter
    @Setter
    public class Current{
        public int temperature;
        public List<String> weather_descriptions;
        public int feelslike;

    }

}




