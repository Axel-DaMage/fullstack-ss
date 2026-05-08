package com.sanosysalvos.bff;

import com.sanosysalvos.bff.service.AggregationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AggregationServiceTest {

    @Autowired
    private AggregationService aggregationService;

    @Test
    void getDashboardDataReturnsMap() {
        Map<String, Object> data = aggregationService.getDashboardData();
        assertNotNull(data);
        assertTrue(data.containsKey("lostPets"));
        assertTrue(data.containsKey("foundPets"));
    }
}