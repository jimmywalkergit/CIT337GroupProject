package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.Testorder;
import com.mycompany.myapp.repository.TestorderRepository;
import com.mycompany.myapp.service.dto.TestorderDTO;
import com.mycompany.myapp.service.mapper.TestorderMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TestorderResource REST controller.
 *
 * @see TestorderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class TestorderResourceIntTest {

    @Inject
    private TestorderRepository testorderRepository;

    @Inject
    private TestorderMapper testorderMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTestorderMockMvc;

    private Testorder testorder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestorderResource testorderResource = new TestorderResource();
        ReflectionTestUtils.setField(testorderResource, "testorderRepository", testorderRepository);
        ReflectionTestUtils.setField(testorderResource, "testorderMapper", testorderMapper);
        this.restTestorderMockMvc = MockMvcBuilders.standaloneSetup(testorderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testorder createEntity(EntityManager em) {
        Testorder testorder = new Testorder();
        return testorder;
    }

    @Before
    public void initTest() {
        testorder = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestorder() throws Exception {
        int databaseSizeBeforeCreate = testorderRepository.findAll().size();

        // Create the Testorder
        TestorderDTO testorderDTO = testorderMapper.testorderToTestorderDTO(testorder);

        restTestorderMockMvc.perform(post("/api/testorders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testorderDTO)))
                .andExpect(status().isCreated());

        // Validate the Testorder in the database
        List<Testorder> testorders = testorderRepository.findAll();
        assertThat(testorders).hasSize(databaseSizeBeforeCreate + 1);
        Testorder testTestorder = testorders.get(testorders.size() - 1);
    }

    @Test
    @Transactional
    public void getAllTestorders() throws Exception {
        // Initialize the database
        testorderRepository.saveAndFlush(testorder);

        // Get all the testorders
        restTestorderMockMvc.perform(get("/api/testorders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(testorder.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTestorder() throws Exception {
        // Initialize the database
        testorderRepository.saveAndFlush(testorder);

        // Get the testorder
        restTestorderMockMvc.perform(get("/api/testorders/{id}", testorder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testorder.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTestorder() throws Exception {
        // Get the testorder
        restTestorderMockMvc.perform(get("/api/testorders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestorder() throws Exception {
        // Initialize the database
        testorderRepository.saveAndFlush(testorder);
        int databaseSizeBeforeUpdate = testorderRepository.findAll().size();

        // Update the testorder
        Testorder updatedTestorder = testorderRepository.findOne(testorder.getId());
        TestorderDTO testorderDTO = testorderMapper.testorderToTestorderDTO(updatedTestorder);

        restTestorderMockMvc.perform(put("/api/testorders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testorderDTO)))
                .andExpect(status().isOk());

        // Validate the Testorder in the database
        List<Testorder> testorders = testorderRepository.findAll();
        assertThat(testorders).hasSize(databaseSizeBeforeUpdate);
        Testorder testTestorder = testorders.get(testorders.size() - 1);
    }

    @Test
    @Transactional
    public void deleteTestorder() throws Exception {
        // Initialize the database
        testorderRepository.saveAndFlush(testorder);
        int databaseSizeBeforeDelete = testorderRepository.findAll().size();

        // Get the testorder
        restTestorderMockMvc.perform(delete("/api/testorders/{id}", testorder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Testorder> testorders = testorderRepository.findAll();
        assertThat(testorders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
