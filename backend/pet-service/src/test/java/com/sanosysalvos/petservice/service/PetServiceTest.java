package com.sanosysalvos.petservice.service;

import com.sanosysalvos.petservice.model.Pet;
import com.sanosysalvos.petservice.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private ContactRepository contactRepository;

    private PetService petService;
    private PetFactory petFactory;

    @BeforeEach
    void setUp() {
        petFactory = new PetFactory();
        petService = new PetService(petRepository, contactRepository, petFactory);
    }

    @Test
    void testGetAllPets() {
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Max");
        pet1.setStatus("LOST");

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Luna");
        pet2.setStatus("FOUND");

        when(petRepository.findAll()).thenReturn(Arrays.asList(pet1, pet2));

        List<Pet> pets = petService.getAllPets();

        assertEquals(2, pets.size());
        assertEquals("Max", pets.get(0).getName());
        verify(petRepository, times(1)).findAll();
    }

    @Test
    void testGetPetById() {
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Max");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Optional<Pet> result = petService.getPetById(1L);

        assertTrue(result.isPresent());
        assertEquals("Max", result.get().getName());
    }

    @Test
    void testGetPetByIdNotFound() {
        when(petRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Pet> result = petService.getPetById(999L);

        assertFalse(result.isPresent());
    }

    @Test
    void testCreatePet() {
        Pet pet = new Pet();
        pet.setName("Max");
        pet.setRace("Golden Retriever");
        pet.setStatus("LOST");

        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet created = petService.createPet(pet);

        assertNotNull(created);
        assertEquals("Max", created.getName());
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void testUpdatePet() {
        Pet existingPet = new Pet();
        existingPet.setId(1L);
        existingPet.setName("Max");

        Pet updatedDetails = new Pet();
        updatedDetails.setName("Max Updated");
        updatedDetails.setRace("Labrador");

        when(petRepository.findById(1L)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any(Pet.class))).thenReturn(existingPet);

        Pet result = petService.updatePet(1L, updatedDetails);

        assertNotNull(result);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void testDeletePet() {
        Pet pet = new Pet();
        pet.setId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).delete(pet);

        petService.deletePet(1L);

        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    void testGetPetsByRace() {
        Pet pet = new Pet();
        pet.setName("Max");
        pet.setRace("Golden Retriever");

        when(petRepository.findByRace("Golden Retriever")).thenReturn(Arrays.asList(pet));

        List<Pet> pets = petService.getPetsByRace("Golden Retriever");

        assertEquals(1, pets.size());
        assertEquals("Golden Retriever", pets.get(0).getRace());
    }

    @Test
    void testCountPetsByStatus() {
        when(petRepository.countByStatus("LOST")).thenReturn(5L);

        long count = petService.countPetsByStatus("LOST");

        assertEquals(5L, count);
    }

    @Test
    void testCreatePetFromFactory() {
        Pet pet = petService.createPetFromFactory("Max", "Golden", "Dorado", "Grande", "LOST");

        assertNotNull(pet);
        assertEquals("Max", pet.getName());
        assertEquals("Golden", pet.getRace());
    }
}