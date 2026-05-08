package com.sanosysalvos.bff.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AggregationService {

    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        data.put("lostPets", 0);
        data.put("foundPets", 0);
        data.put("pendingMatches", 0);
        data.put("totalLocations", 0);
        return data;
    }
}