package org.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.configuration.ApplicationConfig;
import org.project.controller.AdvertisementController;
import org.project.model.Advertisement;
import org.project.service.AdvertisementService;
import org.project.enums.PageSize;
import org.project.enums.SortingOrder;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.TreeMap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdvertisementControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AdvertisementService service;

    @InjectMocks
    private AdvertisementController advertisementController;

    private ObjectMapper objectMapper;

    private Advertisement expected;

    @BeforeAll
    public void init() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(advertisementController).build();
    }

    @BeforeEach
    public void setup() {
        expected = new Advertisement().setId(1L).setTitle("Title");
    }

    @AfterEach
    public void testEnd() {
        expected = null;
    }

    @Test
    public void readAllTest() throws Exception {
        Mockito.when(service.readAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/advertisement"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAll();
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(service.create(expected))
                .thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.post("/advertisement/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).create(expected);
    }

    @Test
    void updateTest() throws Exception {
        Mockito.when(service.update(expected))
                .thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.put("/advertisement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).update(expected);
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(service).delete(new Advertisement().setId(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/advertisement/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).delete(new Advertisement().setId(1L));
    }

    @Test
    public void readByIdTest() throws Exception {
        Mockito.when(service.readById(1L)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/advertisement/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readById(1L);
    }

    @Test
    public void getAllSortedTest() throws Exception {
        Mockito.when(service.readAllSorted(SortingOrder.ASC)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/advertisement/sorted/asc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAllSorted(SortingOrder.ASC);
    }

    @Test
    public void getAllInPagesTest() throws Exception {
        Mockito.when(service.getAllInPages(PageSize.SIZE15, 0)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/advertisement/pages/15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllInPages(PageSize.SIZE15, 0);
    }
    
    @Test
    public void getAllByParentName() throws Exception {
        Mockito.when(service.getAllByParentId(1, "author")).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/advertisement/author/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllByParentId(1, "author");
    }
}
