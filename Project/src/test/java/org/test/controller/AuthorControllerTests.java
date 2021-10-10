package org.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.configuration.ApplicationConfig;
import org.project.controller.AuthorController;
import org.project.model.Author;
import org.project.service.AuthorService;
import org.project.util.PageSize;
import org.project.util.SortingOrder;
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
public class AuthorControllerTests {

    private MockMvc mockMvc;

    @Mock
    private AuthorService service;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper;

    private Author expected;

    @BeforeAll
    public void init() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @BeforeEach
    public void setup() throws Exception {
        expected = new Author().setId(1L).setFirstName("Name");
    }

    @AfterEach
    public void testEnd() {
        expected = null;
    }

    @Test
    public void readAllTests() throws Exception {
        Mockito.when(service.readAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/author"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAll();
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(service.create(expected))
                .thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.post("/author/add")
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
        mockMvc.perform(MockMvcRequestBuilders.put("/author/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).update(expected);
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(service).delete(new Author().setId(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/author/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).delete(new Author().setId(1L));
    }

    @Test
    public void readByIdTest() throws Exception {
        Mockito.when(service.readById(1L)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/author/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readById(1L);
    }

    @Test
    public void getAllSortedTest() throws Exception {
        Mockito.when(service.readAllSorted(SortingOrder.ASC)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/author/sorted/asc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAllSorted(SortingOrder.ASC);
    }

    @Test
    public void getAllInPagesTest() throws Exception {
        Mockito.when(service.getAllInPages(PageSize.SIZE15)).thenReturn(new TreeMap<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/author/pages/15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllInPages(PageSize.SIZE15);
    }
}
