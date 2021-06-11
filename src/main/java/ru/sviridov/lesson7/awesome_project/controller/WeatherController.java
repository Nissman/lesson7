package ru.sviridov.lesson7.awesome_project.controller;

import ru.sviridov.lesson7.awesome_project.model.AccuWeatherProvider;
import ru.sviridov.lesson7.awesome_project.model.IWeatherProvider;
import ru.sviridov.lesson7.awesome_project.model.Period;

public class WeatherController implements IWeatherController {

    private IWeatherProvider weatherProvider = new AccuWeatherProvider();

    @Override
    public void onUserInput(int command) {

        switch (command) {
            case 1:
                getCurrentWeather();
                break;
            case 2:
                System.out.println("НЕ РЕАЛИЗОВАНО!");
                System.exit(1);
            default:
                System.out.println("НЕТ ТАКОЙ КОМАНДЫ!");
                System.exit(1);
        }
    }

    private void getCurrentWeather() {
        weatherProvider.getWeather(Period.NOW);
    }
}
