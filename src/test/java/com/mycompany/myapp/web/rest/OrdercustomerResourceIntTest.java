package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.Ordercustomer;
import com.mycompany.myapp.repository.OrdercustomerRepository;
import com.mycompany.myapp.service.dto.OrdercustomerDTO;
import com.mycompany.myapp.service.mapper.OrdercustomerMapper;

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
 * Test class for the OrdercustomerResource REST controller.
 *
 * @see OrdercustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class OrdercustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    @Inject
    private OrdercustomerRepository ordercustomerRepository;

    @Inject
    private OrdercustomerMapper ordercustomerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrdercustomerMockMvc;

    private Ordercustomer ordercustomer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdercustomerResource ordercustomerResource = new OrdercustomerResource();
        ReflectionTestUtils.setField(ordercustomerResource, "ordercustomerRepository", ordercustomerRepository);
        ReflectionTestUtils.setField(ordercustomerResource, "ordercustomerMapper", ordercustomerMapper);
        this.restOrdercustomerMockMvc = MockMvcBuilders.standaloneSetup(ordercustomerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordercustomer createEntity(EntityManager em) {
        Ordercustomer ordercustomer = new Ordercustomer()
                .name(DEFAULT_NAME)
                .address(DEFAULT_ADDRESS)
                .phone(DEFAULT_PHONE)
                .email(DEFAULT_EMAIL);
        return ordercustomer;
    }

    @Before
    public void initTest() {
        ordercustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdercustomer() throws Exception {
        int databaseSizeBeforeCreate = ordercustomerRepository.findAll().size();

        // Create the Ordercustomer
        OrdercustomerDTO ordercustomerDTO = ordercustomerMapper.ordercustomerToOrdercustomerDTO(ordercustomer);

        restOrdercustomerMockMvc.perform(post("/api/ordercustomers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordercustomerDTO)))
                .andExpect(status().isCreated());

        // Validate the Ordercustomer in the database
        List<Ordercustomer> ordercustomers = ordercustomerRepository.findAll();
        assertThat(ordercustomers).hasSize(databaseSizeBeforeCreate + 1);
        Ordercustomer testOrdercustomer = ordercustomers.get(ordercustomers.size() - 1);
        assertThat(testOrdercustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrdercustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrdercustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrdercustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllOrdercustomers() throws Exception {
        // Initialize the database
        ordercustomerRepository.saveAndFlush(ordercustomer);

        // Get all the ordercustomers
        restOrdercustomerMockMvc.perform(get("/api/ordercustomers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ordercustomer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getOrdercustomer() throws Exception {
        // Initialize the database
        ordercustomerRepository.saveAndFlush(ordercustomer);

        // Get the ordercustomer
        restOrdercustomerMockMvc.perform(get("/api/ordercustomers/{id}", ordercustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordercustomer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdercustomer() throws Exception {
        // Get the ordercustomer
        restOrdercustomerMockMvc.perform(get("/api/ordercustomers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdercustomer() throws Exception {
        // Initialize the database
        ordercustomerRepository.saveAndFlush(ordercustomer);
        int databaseSizeBeforeUpdate = ordercustomerRepository.findAll().size();

        // Update the ordercustomer
        Ordercustomer updatedOrdercustomer = ordercustomerRepository.findOne(ordercustomer.getId());
        updatedOrdercustomer
                .name(UPDATED_NAME)
                .address(UPDATED_ADDRESS)
                .phone(UPDATED_PHONE)
                .email(UPDATED_EMAIL);
        OrdercustomerDTO ordercustomerDTO = ordercustomerMapper.ordercustomerToOrdercustomerDTO(updatedOrdercustomer);

        restOrdercustomerMockMvc.perform(put("/api/ordercustomers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordercustomerDTO)))
                .andExpect(status().isOk());

        // Validate the Ordercustomer in the database
        List<Ordercustomer> ordercustomers = ordercustomerRepository.findAll();
        assertThat(ordercustomers).hasSize(databaseSizeBeforeUpdate);
        Ordercustomer testOrdercustomer = ordercustomers.get(ordercustomers.size() - 1);
        assertThat(testOrdercustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrdercustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrdercustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrdercustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void deleteOrdercustomer() throws Exception {
        // Initialize the database
        ordercustomerRepository.saveAndFlush(ordercustomer);
        int databaseSizeBeforeDelete = ordercustomerRepository.findAll().size();

        // Get the ordercustomer
        restOrdercustomerMockMvc.perform(delete("/api/ordercustomers/{id}", ordercustomer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordercustomer> ordercustomers = ordercustomerRepository.findAll();
        assertThat(ordercustomers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
