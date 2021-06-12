package ru.sviridov.lesson7.awesome_project.entity;

public class WeatherObject {
    private String city;
    private String date;
    private String weatherTextDay;
    private String weatherTextNigh;
    private double temperatureMin;
    private double temperatureMax;

    public WeatherObject() {
    }

    public WeatherObject(String city, String date, String weather_text_day, String weather_text_night, double temperatureMin, double temperatureMax) {
        this.city = city;
        this.date = date;
        this.weatherTextDay = weather_text_day;
        this.weatherTextNigh = weather_text_night;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
    }

    public String getWeatherTextDay() {
        return weatherTextDay;
    }

    public void setWeatherTextDay(String weatherTextDay) {
        this.weatherTextDay = weatherTextDay;
    }

    public String getWeatherTextNigh() {
        return weatherTextNigh;
    }

    public void setWeatherTextNigh(String weatherTextNigh) {
        this.weatherTextNigh = weatherTextNigh;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }


    public double getTemperatureMin() {
        return temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    @Override
    public String toString() {
        return "В городе " +
                city +
                ", на дату " + date +
                ", днем ожидается: " + weatherTextDay +
                ", ночью ожидается: " + weatherTextNigh +
                ", тепература от " + temperatureMin +
                ", до " + temperatureMax +
                " грудусов Цельсия";
    }
}
