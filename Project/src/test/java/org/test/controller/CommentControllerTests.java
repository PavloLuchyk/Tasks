package org.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.project.configuration.ApplicationConfig;
import org.project.controller.CommentController;
import org.project.model.Comment;
import org.project.service.CommentService;
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
public class CommentControllerTests {

    private MockMvc mockMvc;

    @Mock
    private CommentService service;

    @InjectMocks
    private CommentController commentController;

    private ObjectMapper objectMapper;

    private Comment expected;

    @BeforeAll
    public void init() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @BeforeEach
    public void setup() {
        expected = new Comment().setId(1L).setText("Text");
    }

    @AfterEach
    public void testEnd() {
        expected = null;
    }

    @Test
    public void readAllTests() throws Exception {
        Mockito.when(service.readAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/comment"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAll();
    }

    @Test
    void createTest() throws Exception {
        Mockito.when(service.create(expected))
                .thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/add")
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
        mockMvc.perform(MockMvcRequestBuilders.put("/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).update(expected);
    }

    @Test
    public void deleteTest() throws Exception {
        Mockito.doNothing().when(service).delete(new Comment().setId(1L));
        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).delete(new Comment().setId(1L));
    }

    @Test
    public void readByIdTest() throws Exception {
        Mockito.when(service.readById(1L)).thenReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/comment/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readById(1L);
    }

    @Test
    public void getAllSortedTest() throws Exception {
        Mockito.when(service.readAllSorted(SortingOrder.ASC)).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/comment/sorted/asc"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).readAllSorted(SortingOrder.ASC);
    }

    @Test
    public void getAllInPagesTest() throws Exception {
        Mockito.when(service.getAllInPages(PageSize.SIZE15)).thenReturn(new TreeMap<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/comment/pages/15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllInPages(PageSize.SIZE15);
    }

    @Test
    public void getAllByParentName() throws Exception {
        Mockito.when(service.getAllByParentId(1, "author")).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/comment/author/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).getAllByParentId(1, "author");
    }
}
