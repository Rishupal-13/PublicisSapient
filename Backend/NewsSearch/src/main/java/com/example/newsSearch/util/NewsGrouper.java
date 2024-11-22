package com.example.newsSearch.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class NewsGrouper {

    public Map<String, Object> groupNewsByInterval(JSONArray newsArticles, int intervalSize, String intervalUnit) {
        // Determine interval unit (e.g., minutes, hours, days)
        ChronoUnit chronoUnit = ChronoUnit.valueOf(intervalUnit.toUpperCase());

        // Group articles
        Map<String, List<JSONObject>> groupedNews = new TreeMap<>();
        Instant now = Instant.now();

        for (int i = 0; i < newsArticles.length(); i++) {
            JSONObject article = newsArticles.getJSONObject(i);
            Instant publishedAt = Instant.parse(article.getString("publishedAt"));

            long intervalKey = ChronoUnit.SECONDS.between(publishedAt, now) / chronoUnit.getDuration().getSeconds()
                    / intervalSize;
            String intervalLabel = String.format("%d %s ago", intervalKey * intervalSize, intervalUnit);

            groupedNews.computeIfAbsent(intervalLabel, k -> new ArrayList<>()).add(article);
        }

        // Prepare final response
        Map<String, Object> response = new HashMap<>();
        groupedNews.forEach((key, value) -> {
            response.put(key, Map.of(
                    "count", value.size(),
                    "articles", value));
        });

        return response;
    }
}
