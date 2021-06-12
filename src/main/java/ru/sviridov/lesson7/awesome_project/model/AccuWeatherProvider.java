package ru.sviridov.lesson7.awesome_project.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import ru.sviridov.lesson7.awesome_project.GlobalState;
import ru.sviridov.lesson7.awesome_project.entity.WeatherObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccuWeatherProvider implements IWeatherProvider {

    private final String BASE_HOST = "dataservice.accuweather.com";
    private final String VERSION = "v1";
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
/*
Реализовать вывод погоды на следующие 5 дней в формате

В городе CITY на дату DATE ожидается WEATHER_TEXT, температура - TEMPERATURE

где CITY, DATE, WEATHER_TEXT и TEMPERATURE - уникальные значения для каждого дня.

 */

    @Override
    public List<WeatherObject> getWeather(Period period) {
        List<WeatherObject> weatherObjectArrayList;
        String key = detectCityKeyByName();
        HttpUrl url = getHttpUrl(key, period.getValue());
        Request request = getRequest(url);
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new RuntimeException("Сервер ответил " + response.code());
            }
            String responseBody = response.body().string();
            weatherObjectArrayList = getInfo(responseBody);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherObjectArrayList;
    }

    private String detectCityKeyByName() {
        String selectedCity = GlobalState.getInstance().getSelectedCity();
        HttpUrl detectLocationURL = getHttpUrl(selectedCity);
        Request request = getRequest(detectLocationURL);
        Response locationResponse = null;
        try {
            locationResponse = okHttpClient.newCall(request).execute();
            if (!locationResponse.isSuccessful()) {
                throw new RuntimeException("Сервер ответил " + locationResponse.code());
            }
            String jsonResponse = locationResponse.body().string();
            if (objectMapper.readTree(jsonResponse).size() > 0) {
                String code = objectMapper.readTree(jsonResponse).get(0).at("/Key").asText();
                String cityName = objectMapper.readTree(jsonResponse).get(0).at("/LocalizedName").asText();
                String countryName = objectMapper.readTree(jsonResponse).get(0).at("/Country/LocalizedName").asText();
                System.out.printf("Найден город %s в стране %s, код - %s\n", cityName, countryName, code);
                return code;
            } else {
                throw new RuntimeException(selectedCity + " - такой город не найден");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private HttpUrl getHttpUrl(String selectedCity) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("locations")
                .addPathSegment(VERSION)
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();
    }

    @NotNull
    private HttpUrl getHttpUrl(String key, int i) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("forecasts")
                .addPathSegment(VERSION)
                .addPathSegment("daily")
                .addPathSegment(i + "day")
                .addPathSegment(key)
                .addQueryParameter("apikey", GlobalState.getInstance().API_KEY)
                .addQueryParameter("language", "ru-ru")
                .build();
    }

    private Request getRequest(HttpUrl url) {
        return new Request.Builder()
                .addHeader("accept", "application/json")
                .url(url)
                .build();
    }

    private List<WeatherObject> getInfo(String responseBody) throws JsonProcessingException {
        List<WeatherObject> weatherObjectArrayList = new ArrayList<>();
        if (objectMapper.readTree(responseBody).size() > 0) {
            String cityName = GlobalState.getInstance().getSelectedCity();
            for (int i = 0; i < objectMapper.readTree(responseBody).at("/DailyForecasts").size(); i++) {
                String date = objectMapper.readTree(responseBody).at("/DailyForecasts").get(i).at("/Date").asText().split("T")[0];
                String weather_text_day = objectMapper.readTree(responseBody).at("/DailyForecasts").get(i).at("/Day/IconPhrase").asText();
                String weather_text_night = objectMapper.readTree(responseBody).at("/DailyForecasts").get(i).at("/Night/IconPhrase").asText();
                double temperatureMin = ((objectMapper.readTree(responseBody).at("/DailyForecasts").get(i).at("/Temperature/Minimum/Value").asInt() - 32) * 5 / 9);
                double temperatureMax = ((objectMapper.readTree(responseBody).at("/DailyForecasts").get(i).at("/Temperature/Maximum/Value").asInt() - 32) * 5 / 9);
                System.out.printf("В городе %s на дату %s, днем ожидается: %s, ночью ожидается: %s, тепература от %s до %s грудусов Цельсия\n", cityName, date,
                        weather_text_day, weather_text_night, temperatureMin, temperatureMax);
                weatherObjectArrayList.add(new WeatherObject(cityName, date, weather_text_day, weather_text_night, temperatureMin, temperatureMax));
            }
        }
        return weatherObjectArrayList;
    }

/*
Пример вывода по городу Санкт-Петербург
Найден город Saint Petersburg в стране Russia, код - 295212
В городе Санкт-Петербург на дату 2021-06-11, днем ожидается: Небольшая облачность с дождями, ночью ожидается: Ливни, тепература от 12 до 24 грудусов Цельсия
В городе Санкт-Петербург на дату 2021-06-12, днем ожидается: Ливни, ночью ожидается: Преимущественно ясно, тепература от 11 до 20 грудусов Цельсия
В городе Санкт-Петербург на дату 2021-06-13, днем ожидается: Преимущественно облачно с ливнями, ночью ожидается: Ливни, тепература от 12 до 21 грудусов Цельсия
В городе Санкт-Петербург на дату 2021-06-14, днем ожидается: Преимущественно ясно, ночью ожидается: Облачно с прояснениями, тепература от 11 до 21 грудусов Цельсия
В городе Санкт-Петербург на дату 2021-06-15, днем ожидается: Преимущественно облачно, ночью ожидается: Преимущественно ясно, тепература от 10 до 21 грудусов Цельсия
 */

}
