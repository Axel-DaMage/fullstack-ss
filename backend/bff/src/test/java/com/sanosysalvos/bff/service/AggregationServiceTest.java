package com.sanosysalvos.bff.service;

import com.sanosysalvos.bff.client.LocationServiceClient;
import com.sanosysalvos.bff.client.MatchServiceClient;
import com.sanosysalvos.bff.client.PetServiceClient;
import com.sanosysalvos.bff.model.LocationDto;
import com.sanosysalvos.bff.model.MatchDto;
import com.sanosysalvos.bff.model.PetDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AggregationServiceTest {

    @Mock
    private PetServiceClient petServiceClient;

    @Mock
    private LocationServiceClient locationServiceClient;

    @Mock
    private MatchServiceClient matchServiceClient;

    private AggregationService aggregationService;

    @BeforeEach
    void setUp() {
        aggregationService = new AggregationService(
            petServiceClient, 
            locationServiceClient, 
            matchServiceClient
        );
    }

    @Test
    void testGetAllPets() {
        PetDto pet1 = new PetDto();
        pet1.setId(1L);
        pet1.setName("Max");

        PetDto pet2 = new PetDto();
        pet2.setId(2L);
        pet2.setName("Luna");

        when(petServiceClient.getAllPets()).thenReturn(Arrays.asList(pet1, pet2));

        List<PetDto> pets = aggregationService.getAllPets();

        assertEquals(2, pets.size());
        assertEquals("Max", pets.get(0).getName());
    }

    @Test
    void testGetPetById() {
        PetDto pet = new PetDto();
        pet.setId(1L);
        pet.setName("Max");

        when(petServiceClient.getPetById(1L)).thenReturn(pet);

        PetDto result = aggregationService.getPetById(1L);

        assertNotNull(result);
        assertEquals("Max", result.getName());
    }

    @Test
    void testGetAllLocations() {
        LocationDto location = new LocationDto();
        location.setId(1L);
        location.setZone("Las Condes");

        when(locationServiceClient.getAllLocations()).thenReturn(Arrays.asList(location));

        List<LocationDto> locations = aggregationService.getAllLocations();

        assertEquals(1, locations.size());
        assertEquals("Las Condes", locations.get(0).getZone());
    }

    @Test
    void testGetAllMatches() {
        MatchDto match = new MatchDto();
        match.setId(1L);
        match.setStatus("PENDING");

        when(matchServiceClient.getAllMatches()).thenReturn(Arrays.asList(match));

        List<MatchDto> matches = aggregationService.getAllMatches();

        assertEquals(1, matches.size());
        assertEquals("PENDING", matches.get(0).getStatus());
    }

    @Test
    void testGetDashboard() {
        PetDto lostPet = new PetDto();
        lostPet.setStatus("LOST");

        PetDto foundPet = new PetDto();
        foundPet.setStatus("FOUND");

        MatchDto pendingMatch = new MatchDto();
        pendingMatch.setStatus("PENDING");

        LocationDto location = new LocationDto();
        location.setZone("Las Condes");

        when(petServiceClient.getPetsByStatus("LOST")).thenReturn(Arrays.asList(lostPet));
        when(petServiceClient.getPetsByStatus("FOUND")).thenReturn(Arrays.asList(foundPet));
        when(matchServiceClient.getMatchesByStatus("PENDING")).thenReturn(Arrays.asList(pendingMatch));
        when(locationServiceClient.getAllLocations()).thenReturn(Arrays.asList(location));

        Map<String, Object> dashboard = aggregationService.getDashboard();

        assertNotNull(dashboard);
        assertEquals(1, dashboard.get("lostPets"));
        assertEquals(1, dashboard.get("foundPets"));
        assertEquals(1, dashboard.get("pendingMatches"));
        assertEquals(1, dashboard.get("totalLocations"));
    }

    @Test
    void testConfirmMatch() {
        MatchDto match = new MatchDto();
        match.setId(1L);
        match.setStatus("PENDING");

        when(matchServiceClient.updateMatchStatus(1L, "CONFIRMED")).thenReturn(match);

        MatchDto result = aggregationService.confirmMatch(1L);

        assertNotNull(result);
        verify(matchServiceClient).updateMatchStatus(1L, "CONFIRMED");
    }

    @Test
    void testCreatePet() {
        PetDto pet = new PetDto();
        pet.setName("Max");
        pet.setStatus("LOST");

        when(petServiceClient.createPet(pet)).thenReturn(pet);

        PetDto result = aggregationService.createPet(pet);

        assertNotNull(result);
        verify(petServiceClient).createPet(pet);
    }

    @Test
    void testDeletePet() {
        doNothing().when(petServiceClient).deletePet(1L);

        aggregationService.deletePet(1L);

        verify(petServiceClient).deletePet(1L);
    }
}