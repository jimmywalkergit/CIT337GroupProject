package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.Testproduct;
import com.mycompany.myapp.repository.TestproductRepository;
import com.mycompany.myapp.service.dto.TestproductDTO;
import com.mycompany.myapp.service.mapper.TestproductMapper;

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
 * Test class for the TestproductResource REST controller.
 *
 * @see TestproductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class TestproductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_PRICE = "AAAAA";
    private static final String UPDATED_PRICE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";

    @Inject
    private TestproductRepository testproductRepository;

    @Inject
    private TestproductMapper testproductMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTestproductMockMvc;

    private Testproduct testproduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestproductResource testproductResource = new TestproductResource();
        ReflectionTestUtils.setField(testproductResource, "testproductRepository", testproductRepository);
        ReflectionTestUtils.setField(testproductResource, "testproductMapper", testproductMapper);
        this.restTestproductMockMvc = MockMvcBuilders.standaloneSetup(testproductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Testproduct createEntity(EntityManager em) {
        Testproduct testproduct = new Testproduct()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .description(DEFAULT_DESCRIPTION)
                .image(DEFAULT_IMAGE);
        return testproduct;
    }

    @Before
    public void initTest() {
        testproduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createTestproduct() throws Exception {
        int databaseSizeBeforeCreate = testproductRepository.findAll().size();

        // Create the Testproduct
        TestproductDTO testproductDTO = testproductMapper.testproductToTestproductDTO(testproduct);

        restTestproductMockMvc.perform(post("/api/testproducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testproductDTO)))
                .andExpect(status().isCreated());

        // Validate the Testproduct in the database
        List<Testproduct> testproducts = testproductRepository.findAll();
        assertThat(testproducts).hasSize(databaseSizeBeforeCreate + 1);
        Testproduct testTestproduct = testproducts.get(testproducts.size() - 1);
        assertThat(testTestproduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTestproduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTestproduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTestproduct.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void getAllTestproducts() throws Exception {
        // Initialize the database
        testproductRepository.saveAndFlush(testproduct);

        // Get all the testproducts
        restTestproductMockMvc.perform(get("/api/testproducts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(testproduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getTestproduct() throws Exception {
        // Initialize the database
        testproductRepository.saveAndFlush(testproduct);

        // Get the testproduct
        restTestproductMockMvc.perform(get("/api/testproducts/{id}", testproduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(testproduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTestproduct() throws Exception {
        // Get the testproduct
        restTestproductMockMvc.perform(get("/api/testproducts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTestproduct() throws Exception {
        // Initialize the database
        testproductRepository.saveAndFlush(testproduct);
        int databaseSizeBeforeUpdate = testproductRepository.findAll().size();

        // Update the testproduct
        Testproduct updatedTestproduct = testproductRepository.findOne(testproduct.getId());
        updatedTestproduct
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .description(UPDATED_DESCRIPTION)
                .image(UPDATED_IMAGE);
        TestproductDTO testproductDTO = testproductMapper.testproductToTestproductDTO(updatedTestproduct);

        restTestproductMockMvc.perform(put("/api/testproducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(testproductDTO)))
                .andExpect(status().isOk());

        // Validate the Testproduct in the database
        List<Testproduct> testproducts = testproductRepository.findAll();
        assertThat(testproducts).hasSize(databaseSizeBeforeUpdate);
        Testproduct testTestproduct = testproducts.get(testproducts.size() - 1);
        assertThat(testTestproduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTestproduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTestproduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTestproduct.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteTestproduct() throws Exception {
        // Initialize the database
        testproductRepository.saveAndFlush(testproduct);
        int databaseSizeBeforeDelete = testproductRepository.findAll().size();

        // Get the testproduct
        restTestproductMockMvc.perform(delete("/api/testproducts/{id}", testproduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Testproduct> testproducts = testproductRepository.findAll();
        assertThat(testproducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
