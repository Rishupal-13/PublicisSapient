package com.example.newsSearch.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class NewsAPIClient {

    private static final String API_KEY = "ccaf5d41cc5140c984818c344edcc14d";
    private static final String BASE_URL = "https://newsapi.org/v2/everything";

    public JSONArray fetchNews(String keyword, boolean useCache) {
        // Fallback to cache if enabled (for offline mode)
        if (useCache) {
            return fetchCachedNews();
        }

        try {
            // Build the API URL
            String apiUrl = String.format("%s?q=%s&apiKey=%s", BASE_URL, keyword, API_KEY);

            // Create HTTP connection
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            // Read response
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getJSONArray("articles");

        } catch (IOException e) {
            // Fallback to cache in case of an error
            return fetchCachedNews();
        }
    }

    private JSONArray fetchCachedNews() {
        // Mocked fallback data (for offline mode)
        return new JSONArray("[{\"title\": \"Sample News\", \"publishedAt\": \"2024-11-20T10:00:00Z\"}]");
    }
}
