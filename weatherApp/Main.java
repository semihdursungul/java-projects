import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import org.json.*;

class WeatherApp {
    private static final String API_KEY = "ENTER-YOUR-API-KEY-HERE";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    private JFrame frame;
    private JTextField locationField;
    private JTextArea weatherInfoArea;

    public WeatherApp() {
        frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300); // Set frame size

        locationField = new JTextField(20);
        JButton fetchButton = new JButton("Fetch Weather");
        weatherInfoArea = new JTextArea(10, 50); // Adjust text area size
        weatherInfoArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Location:"));
        panel.add(locationField);
        panel.add(fetchButton);

        fetchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchWeather();
            }
        });

        // Trigger "Fetch Weather" on Enter key press
        locationField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchWeather();
            }
        });

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(weatherInfoArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void fetchWeather() {
        String location = locationField.getText().trim();

        try {
            String apiUrl = API_URL + "?q=" + URLEncoder.encode(location, "UTF-8") + "&appid=" + API_KEY;

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse JSON response and update weatherInfoArea with selected fields
            String weatherData = parseWeatherData(response.toString());
            weatherInfoArea.setText(weatherData);

            // Clear the text field after fetching weather
            locationField.setText("");

        } catch (Exception ex) {
            weatherInfoArea.setText("Error fetching weather data: " + ex.getMessage());
        }
    }

    private String parseWeatherData(String jsonResponse) {
        StringBuilder parsedData = new StringBuilder();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            String cityName = jsonObject.getString("name");
            JSONObject main = jsonObject.getJSONObject("main");
            double temperatureKelvin = main.getDouble("temp");
            double temperatureCelsius = temperatureKelvin - 273.15; // Convert Kelvin to Celsius
            double feelsLikeKelvin = main.getDouble("feels_like");
            double feelsLikeCelsius = feelsLikeKelvin - 273.15; // Convert Kelvin to Celsius
            int humidity = main.getInt("humidity");
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weather = weatherArray.getJSONObject(0);
            String description = weather.getString("description");

            parsedData.append("City:\t\t").append(cityName).append("\n");
            parsedData.append("Temperature:\t").append(String.format("%.2f", temperatureCelsius)).append("°C\n");
            parsedData.append("Feels Like:\t").append(String.format("%.2f", feelsLikeCelsius)).append("°C\n");
            parsedData.append("Humidity:\t").append(humidity).append("%\n");
            parsedData.append("Description:\t").append(description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedData.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WeatherApp();
            }
        });
    }
}
