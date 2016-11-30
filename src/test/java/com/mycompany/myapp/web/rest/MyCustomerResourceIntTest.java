package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.MyCustomer;
import com.mycompany.myapp.repository.MyCustomerRepository;
import com.mycompany.myapp.service.dto.MyCustomerDTO;
import com.mycompany.myapp.service.mapper.MyCustomerMapper;

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
 * Test class for the MyCustomerResource REST controller.
 *
 * @see MyCustomerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class MyCustomerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    @Inject
    private MyCustomerRepository myCustomerRepository;

    @Inject
    private MyCustomerMapper myCustomerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyCustomerMockMvc;

    private MyCustomer myCustomer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyCustomerResource myCustomerResource = new MyCustomerResource();
        ReflectionTestUtils.setField(myCustomerResource, "myCustomerRepository", myCustomerRepository);
        ReflectionTestUtils.setField(myCustomerResource, "myCustomerMapper", myCustomerMapper);
        this.restMyCustomerMockMvc = MockMvcBuilders.standaloneSetup(myCustomerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyCustomer createEntity(EntityManager em) {
        MyCustomer myCustomer = new MyCustomer()
                .name(DEFAULT_NAME)
                .phone(DEFAULT_PHONE)
                .email(DEFAULT_EMAIL)
                .address(DEFAULT_ADDRESS);
        return myCustomer;
    }

    @Before
    public void initTest() {
        myCustomer = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyCustomer() throws Exception {
        int databaseSizeBeforeCreate = myCustomerRepository.findAll().size();

        // Create the MyCustomer
        MyCustomerDTO myCustomerDTO = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);

        restMyCustomerMockMvc.perform(post("/api/my-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCustomerDTO)))
                .andExpect(status().isCreated());

        // Validate the MyCustomer in the database
        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        assertThat(myCustomers).hasSize(databaseSizeBeforeCreate + 1);
        MyCustomer testMyCustomer = myCustomers.get(myCustomers.size() - 1);
        assertThat(testMyCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyCustomer.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMyCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMyCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myCustomerRepository.findAll().size();
        // set the field null
        myCustomer.setName(null);

        // Create the MyCustomer, which fails.
        MyCustomerDTO myCustomerDTO = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);

        restMyCustomerMockMvc.perform(post("/api/my-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCustomerDTO)))
                .andExpect(status().isBadRequest());

        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        assertThat(myCustomers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = myCustomerRepository.findAll().size();
        // set the field null
        myCustomer.setPhone(null);

        // Create the MyCustomer, which fails.
        MyCustomerDTO myCustomerDTO = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);

        restMyCustomerMockMvc.perform(post("/api/my-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCustomerDTO)))
                .andExpect(status().isBadRequest());

        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        assertThat(myCustomers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyCustomers() throws Exception {
        // Initialize the database
        myCustomerRepository.saveAndFlush(myCustomer);

        // Get all the myCustomers
        restMyCustomerMockMvc.perform(get("/api/my-customers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myCustomer.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getMyCustomer() throws Exception {
        // Initialize the database
        myCustomerRepository.saveAndFlush(myCustomer);

        // Get the myCustomer
        restMyCustomerMockMvc.perform(get("/api/my-customers/{id}", myCustomer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myCustomer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyCustomer() throws Exception {
        // Get the myCustomer
        restMyCustomerMockMvc.perform(get("/api/my-customers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyCustomer() throws Exception {
        // Initialize the database
        myCustomerRepository.saveAndFlush(myCustomer);
        int databaseSizeBeforeUpdate = myCustomerRepository.findAll().size();

        // Update the myCustomer
        MyCustomer updatedMyCustomer = myCustomerRepository.findOne(myCustomer.getId());
        updatedMyCustomer
                .name(UPDATED_NAME)
                .phone(UPDATED_PHONE)
                .email(UPDATED_EMAIL)
                .address(UPDATED_ADDRESS);
        MyCustomerDTO myCustomerDTO = myCustomerMapper.myCustomerToMyCustomerDTO(updatedMyCustomer);

        restMyCustomerMockMvc.perform(put("/api/my-customers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCustomerDTO)))
                .andExpect(status().isOk());

        // Validate the MyCustomer in the database
        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        assertThat(myCustomers).hasSize(databaseSizeBeforeUpdate);
        MyCustomer testMyCustomer = myCustomers.get(myCustomers.size() - 1);
        assertThat(testMyCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyCustomer.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMyCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMyCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void deleteMyCustomer() throws Exception {
        // Initialize the database
        myCustomerRepository.saveAndFlush(myCustomer);
        int databaseSizeBeforeDelete = myCustomerRepository.findAll().size();

        // Get the myCustomer
        restMyCustomerMockMvc.perform(delete("/api/my-customers/{id}", myCustomer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        assertThat(myCustomers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
