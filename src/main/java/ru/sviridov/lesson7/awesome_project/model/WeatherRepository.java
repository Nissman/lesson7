package ru.sviridov.lesson7.awesome_project.model;

import ru.sviridov.lesson7.awesome_project.entity.WeatherObject;

import java.sql.SQLException;
import java.util.List;

public class WeatherRepository implements IWeatherRepository {

    IWeatherProvider apiWeatherProvider = new AccuWeatherProvider();
    ILocalStorageProvider localStorageProvider = new SqLiteWeatherStorage();

    @Override
    public List<WeatherObject> fetchWeatherFromApi(Period period) {
        List<WeatherObject> weather = apiWeatherProvider.getWeather(period);
        saveWeatherInfoInDb(weather);
        return weather;
    }

    @Override
    public List<WeatherObject> readWeatherFromDb() {
        List<WeatherObject> dbResult = null;
        try {
            dbResult = localStorageProvider.getAllWeather();
            for (WeatherObject weatherObject : dbResult) {
                System.out.println(weatherObject.toString());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dbResult;
    }

    @Override
    public void saveWeatherInfoInDb(List<WeatherObject> weatherObjects) {
        for (WeatherObject weatherObject : weatherObjects) {
            try {
                localStorageProvider.saveWeather(weatherObject);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
