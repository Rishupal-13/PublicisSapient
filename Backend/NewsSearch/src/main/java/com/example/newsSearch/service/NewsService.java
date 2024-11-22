package com.example.newsSearch.service;

import com.example.newsSearch.util.NewsAPIClient;
import com.example.newsSearch.util.NewsGrouper;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service // Ensures that Spring will manage this class as a service bean
public class NewsService {
    private final NewsAPIClient newsAPIClient = new NewsAPIClient();
    private final NewsGrouper newsGrouper = new NewsGrouper();

    public Map<String, Object> getGroupedNews(String keyword, int intervalSize, String intervalUnit, boolean useCache) {
        // Fetch raw news data
        var newsArticles = newsAPIClient.fetchNews(keyword, useCache);

        // Group news articles into intervals
        return newsGrouper.groupNewsByInterval(newsArticles, intervalSize, intervalUnit);
    }
}
