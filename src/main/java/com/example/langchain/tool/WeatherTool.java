package com.example.langchain.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * Weather Tool — provides simulated weather information for any city.
 * The AI agent can invoke this tool when users ask about weather conditions.
 */
@Slf4j
@Component
public class WeatherTool {

    private static final Map<String, String[]> WEATHER_CONDITIONS = Map.of(
            "sunny", new String[] { "Clear skies", "☀️" },
            "cloudy", new String[] { "Overcast clouds", "☁️" },
            "rainy", new String[] { "Light rain showers", "🌧️" },
            "stormy", new String[] { "Thunderstorms expected", "⛈️" },
            "snowy", new String[] { "Light snowfall", "❄️" },
            "windy", new String[] { "Strong winds", "💨" },
            "foggy", new String[] { "Dense fog", "🌫️" });

    private static final String[] CONDITION_KEYS = WEATHER_CONDITIONS.keySet().toArray(new String[0]);

    @Tool("Get the current weather information for a given city. Returns temperature, conditions, humidity, and wind speed.")
    public String getWeather(@P("The name of the city to get weather for") String city) {
        log.info("🌤️ WeatherTool invoked for city: {}", city);

        Random random = new Random(city.toLowerCase().hashCode());
        int temperature = random.nextInt(35) - 5; // -5 to 30°C
        int humidity = random.nextInt(60) + 30; // 30% to 90%
        int windSpeed = random.nextInt(40) + 5; // 5 to 45 km/h
        String conditionKey = CONDITION_KEYS[random.nextInt(CONDITION_KEYS.length)];
        String[] condition = WEATHER_CONDITIONS.get(conditionKey);

        String result = String.format(
                """
                        Weather Report for %s
                        ━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                        %s Condition: %s (%s)
                        🌡️ Temperature: %d°C
                        💧 Humidity: %d%%
                        💨 Wind Speed: %d km/h
                        📅 Forecast Time: %s
                        """,
                city,
                condition[1], condition[0], conditionKey,
                temperature,
                humidity,
                windSpeed,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        log.info("WeatherTool result: {}", result);
        return result;
    }
}
