package ru.sviridov.lesson7.awesome_project.controller;

import ru.sviridov.lesson7.awesome_project.model.*;

import java.sql.SQLException;

public class WeatherController implements IWeatherController {

    private IWeatherRepository weatherRepository = new WeatherRepository();

    @Override
    public void onUserInput(int command) {

        switch (command) {
            case 1:
                getCurrentWeather(Period.NOW);
                break;
            case 2:
                getCurrentWeather(Period.FIVE_DAYS);
                break;
            case 3:
                getAllFromDb();
                break;
            default:
                System.out.println("НЕТ ТАКОЙ КОМАНДЫ!");
                System.exit(1);
        }
    }

    private void getCurrentWeather(Period period) {
        weatherRepository.fetchWeatherFromApi(period);
    }

    private void getAllFromDb() {
        try {
            weatherRepository.readWeatherFromDb();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
