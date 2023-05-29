import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter base currency code (e.g., USD):");
            String baseCurrency = reader.readLine().toUpperCase();

            System.out.println("Enter target currency code (e.g., EUR):");
            String targetCurrency = reader.readLine().toUpperCase();

            System.out.println("Enter amount:");
            double amount = Double.parseDouble(reader.readLine());

            double convertedAmount = convertCurrency(baseCurrency, targetCurrency, amount);
            System.out.printf("%.2f %s = %.2f %s", amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double convertCurrency(String baseCurrency, String targetCurrency, double amount) throws IOException {
        String apiUrl = API_URL + baseCurrency;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            reader.close();

            // Parse the JSON response
            double rate = parseExchangeRate(response, targetCurrency);
            return amount * rate;
        } else {
            throw new IOException("Failed to retrieve exchange rate API data. Response code: " + responseCode);
        }
    }

    private static double parseExchangeRate(String json, String targetCurrency) {
        // Parse the JSON response to get the exchange rate for the target currency
        // You can use a JSON parsing library like Gson or Jackson for a more robust implementation

        // Example implementation with basic string manipulation (not recommended for production use)
        String rateKey = "\"" + targetCurrency + "\":";
        int rateIndex = json.indexOf(rateKey) + rateKey.length();
        int endIndex = json.indexOf(",", rateIndex);
        String rateString = json.substring(rateIndex, endIndex);
        return Double.parseDouble(rateString);
    }
}
