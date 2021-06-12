package ru.sviridov.lesson7.awesome_project.model;

import ru.sviridov.lesson7.awesome_project.GlobalState;
import ru.sviridov.lesson7.awesome_project.entity.WeatherObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SqLiteWeatherStorage implements ILocalStorageProvider {

    private PreparedStatement preparedStatement;

    static {
        try {
            GlobalState.getInstance()
                    .getConnection()
                    .createStatement()
                    .executeUpdate("CREATE TABLE IF NOT EXISTS weather (id INTEGER PRIMARY KEY AUTOINCREMENT,city TEXT, date TEXT, weatherTextDay TEXT, weatherTextNigh TEXT, temperatureMin REAL, temperatureMax REAL)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveWeather(WeatherObject weatherObject) {

        String query = String.format(Locale.US, "INSERT INTO weather (city, date, weatherTextDay, weatherTextNigh, temperatureMin, temperatureMax) VALUES('%s','%s','%s','%s','%s','%s')"
                , weatherObject.getCity()
                , weatherObject.getDate()
                , weatherObject.getWeatherTextDay()
                , weatherObject.getWeatherTextNigh()
                , weatherObject.getTemperatureMin()
                , weatherObject.getTemperatureMax());
        try {
            GlobalState.getInstance()
                    .getConnection()
                    .createStatement()
                    .executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<WeatherObject> getAllWeather() throws SQLException {
        List<WeatherObject> weatherObjectList = new ArrayList<>();
        ResultSet resultSet = GlobalState.getInstance()
                .getConnection()
                .createStatement()
                .executeQuery("SELECT * FROM weather");
        while (resultSet.next()) {
            String city = resultSet.getString(2);
            String date = resultSet.getString(3);
            String weather_text_day = resultSet.getString(4);
            String weather_text_night = resultSet.getString(5);
            double temperatureMin = resultSet.getDouble(6);
            double temperatureMax = resultSet.getDouble(7);
            WeatherObject weatherObject = new WeatherObject(city, date, weather_text_day, weather_text_night, temperatureMin, temperatureMax);
            weatherObjectList.add(weatherObject);
        }
        return weatherObjectList;
    }
}
