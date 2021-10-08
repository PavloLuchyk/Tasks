package org.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.model.Advertisement;
import org.project.repository.AdvertisementRepository;
import org.project.service.impl.AdvertisementServiceImpl;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdvertisementServiceTests {

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private AdvertisementServiceImpl advertisementService;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createTest() {
        Advertisement expected = new Advertisement();
        Mockito.when(advertisementRepository.create(expected)).thenReturn(1L);
        Advertisement actual = advertisementService.create(expected);
        assertEquals(expected, actual);
        Mockito.verify(advertisementRepository, Mockito.times(1)).create(expected);
    }

    @Test
    public void createTestNegative() {
        assertThrows(IllegalArgumentException.class, () -> advertisementService.create(null));
    }

    @Test
    public void getByParentId() {
        List<Advertisement> expected = List.of(new Advertisement(), new Advertisement());
        Mockito.when(advertisementRepository.getAllByParentId(1, "author")).thenReturn(expected);
        List<Advertisement> actual = advertisementService.getAllByParentId(1, "author");
        assertEquals(expected, actual);
        Mockito.verify(advertisementRepository, Mockito.times(1)).getAllByParentId(1, "author");
    }
}
