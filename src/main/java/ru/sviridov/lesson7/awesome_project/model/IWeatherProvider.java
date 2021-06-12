package ru.sviridov.lesson7.awesome_project.model;

import ru.sviridov.lesson7.awesome_project.entity.WeatherObject;

import java.util.List;

public interface IWeatherProvider {
    List<WeatherObject> getWeather(Period period);
}
