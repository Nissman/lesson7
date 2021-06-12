package ru.sviridov.lesson7.awesome_project.model;

import ru.sviridov.lesson7.awesome_project.entity.WeatherObject;

import java.sql.SQLException;
import java.util.List;

public interface IWeatherRepository {
    List<WeatherObject> fetchWeatherFromApi(Period period);

    List<WeatherObject> readWeatherFromDb() throws SQLException;

    void saveWeatherInfoInDb(List<WeatherObject> weatherObjects);
}
