package com.example.newsSearch.controller;

import com.example.newsSearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<?> getNews(
            @RequestParam String keyword,
            @RequestParam(required = false, defaultValue = "12") int intervalSize,
            @RequestParam(required = false, defaultValue = "hours") String intervalUnit,

            @RequestParam(required = false, defaultValue = "false") boolean useCache) {
        try {
            return ResponseEntity.ok(newsService.getGroupedNews(keyword, intervalSize, intervalUnit, useCache));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching news: " + e.getMessage());
        }
    }
}
