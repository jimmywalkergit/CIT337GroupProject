package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.MyEmployee;
import com.mycompany.myapp.repository.MyEmployeeRepository;
import com.mycompany.myapp.service.dto.MyEmployeeDTO;
import com.mycompany.myapp.service.mapper.MyEmployeeMapper;

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
 * Test class for the MyEmployeeResource REST controller.
 *
 * @see MyEmployeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class MyEmployeeResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBB";

    @Inject
    private MyEmployeeRepository myEmployeeRepository;

    @Inject
    private MyEmployeeMapper myEmployeeMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyEmployeeMockMvc;

    private MyEmployee myEmployee;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyEmployeeResource myEmployeeResource = new MyEmployeeResource();
        ReflectionTestUtils.setField(myEmployeeResource, "myEmployeeRepository", myEmployeeRepository);
        ReflectionTestUtils.setField(myEmployeeResource, "myEmployeeMapper", myEmployeeMapper);
        this.restMyEmployeeMockMvc = MockMvcBuilders.standaloneSetup(myEmployeeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyEmployee createEntity(EntityManager em) {
        MyEmployee myEmployee = new MyEmployee()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .email(DEFAULT_EMAIL)
                .phoneNumber(DEFAULT_PHONE_NUMBER);
        return myEmployee;
    }

    @Before
    public void initTest() {
        myEmployee = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyEmployee() throws Exception {
        int databaseSizeBeforeCreate = myEmployeeRepository.findAll().size();

        // Create the MyEmployee
        MyEmployeeDTO myEmployeeDTO = myEmployeeMapper.myEmployeeToMyEmployeeDTO(myEmployee);

        restMyEmployeeMockMvc.perform(post("/api/my-employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myEmployeeDTO)))
                .andExpect(status().isCreated());

        // Validate the MyEmployee in the database
        List<MyEmployee> myEmployees = myEmployeeRepository.findAll();
        assertThat(myEmployees).hasSize(databaseSizeBeforeCreate + 1);
        MyEmployee testMyEmployee = myEmployees.get(myEmployees.size() - 1);
        assertThat(testMyEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMyEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMyEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMyEmployee.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllMyEmployees() throws Exception {
        // Initialize the database
        myEmployeeRepository.saveAndFlush(myEmployee);

        // Get all the myEmployees
        restMyEmployeeMockMvc.perform(get("/api/my-employees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myEmployee.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getMyEmployee() throws Exception {
        // Initialize the database
        myEmployeeRepository.saveAndFlush(myEmployee);

        // Get the myEmployee
        restMyEmployeeMockMvc.perform(get("/api/my-employees/{id}", myEmployee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myEmployee.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyEmployee() throws Exception {
        // Get the myEmployee
        restMyEmployeeMockMvc.perform(get("/api/my-employees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyEmployee() throws Exception {
        // Initialize the database
        myEmployeeRepository.saveAndFlush(myEmployee);
        int databaseSizeBeforeUpdate = myEmployeeRepository.findAll().size();

        // Update the myEmployee
        MyEmployee updatedMyEmployee = myEmployeeRepository.findOne(myEmployee.getId());
        updatedMyEmployee
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .email(UPDATED_EMAIL)
                .phoneNumber(UPDATED_PHONE_NUMBER);
        MyEmployeeDTO myEmployeeDTO = myEmployeeMapper.myEmployeeToMyEmployeeDTO(updatedMyEmployee);

        restMyEmployeeMockMvc.perform(put("/api/my-employees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myEmployeeDTO)))
                .andExpect(status().isOk());

        // Validate the MyEmployee in the database
        List<MyEmployee> myEmployees = myEmployeeRepository.findAll();
        assertThat(myEmployees).hasSize(databaseSizeBeforeUpdate);
        MyEmployee testMyEmployee = myEmployees.get(myEmployees.size() - 1);
        assertThat(testMyEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMyEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMyEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMyEmployee.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void deleteMyEmployee() throws Exception {
        // Initialize the database
        myEmployeeRepository.saveAndFlush(myEmployee);
        int databaseSizeBeforeDelete = myEmployeeRepository.findAll().size();

        // Get the myEmployee
        restMyEmployeeMockMvc.perform(delete("/api/my-employees/{id}", myEmployee.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyEmployee> myEmployees = myEmployeeRepository.findAll();
        assertThat(myEmployees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
