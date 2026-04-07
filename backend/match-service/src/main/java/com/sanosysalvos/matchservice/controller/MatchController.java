package com.sanosysalvos.matchservice.controller;

import com.sanosysalvos.matchservice.service.PetServiceConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class MatchController {

    @Autowired
    private PetServiceConsumer consumer;

    @GetMapping("/health")
    public String health() {
        return "Match Service is running";
    }

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyze() {
        List<Map<String, Object>> reports = consumer.getPetReports();

        if (reports != null && !reports.isEmpty() && reports.get(0).containsKey("error")) {
            // Evaluamos si el fallback se ejecutó
            Map<String, Object> errResponse = new HashMap<>();
            errResponse.put("error", reports.get(0).get("error"));
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errResponse);
        }

        if (reports == null) {
            reports = new ArrayList<>();
        }

        List<Map<String, Object>> lost = reports.stream()
                .filter(r -> "LOST".equals(r.get("type")))
                .collect(Collectors.toList());

        List<Map<String, Object>> found = reports.stream()
                .filter(r -> "FOUND".equals(r.get("type")))
                .collect(Collectors.toList());

        List<Map<String, Object>> potentialMatches = new ArrayList<>();

        for (Map<String, Object> l : lost) {
            for (Map<String, Object> f : found) {
                if (l.getOrDefault("species", "L").equals(f.getOrDefault("species", "F")) &&
                    l.getOrDefault("color", "L").equals(f.getOrDefault("color", "F"))) {
                    
                    Map<String, Object> match = new HashMap<>();
                    match.put("lost", l);
                    match.put("found", f);
                    match.put("message", "Alta probabilidad");
                    potentialMatches.add(match);
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("potentialMatches", potentialMatches);
        
        return ResponseEntity.ok(response);
    }
}
