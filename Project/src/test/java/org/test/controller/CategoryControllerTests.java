package org.test.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.configuration.ApplicationConfig;
import org.project.controller.CategoryController;
import org.project.model.Category;
import org.project.service.CategoryService;
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
public class CategoryControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CategoryService service;

    @InjectMocks
    private CategoryController categoryController;

    private ObjectMapper objectMapper;

    private Category expected;

    @BeforeAll
    public void init() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @BeforeEach
    public void setup() throws Exception {
        expected = new Category().setId(1L).setDescription("desc");
    }

    @AfterEach
    public void testEnd() {
        expected = null;
    }

    @Test
    public void readAllTests() throws Exception {
        Mockito.when(service.readAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/category"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAll();
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(service.create(expected))
                        .thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.post("/category/add")
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
        mockMvc.perform(MockMvcRequestBuilders.put("/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).update(expected);
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(service).delete(new Category().setId(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/category/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).delete(new Category().setId(1L));
    }

    @Test
    public void readByIdTest() throws Exception {
        Mockito.when(service.readById(1L)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/category/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readById(1L);
    }

    @Test
    public void getAllSortedTest() throws Exception {
        Mockito.when(service.readAllSorted(SortingOrder.ASC)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/category/sorted/asc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAllSorted(SortingOrder.ASC);
    }

    @Test
    public void getAllInPagesTest() throws Exception {
        Mockito.when(service.getAllInPages(PageSize.SIZE15)).thenReturn(new TreeMap<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/category/pages/15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllInPages(PageSize.SIZE15);
    }
}
