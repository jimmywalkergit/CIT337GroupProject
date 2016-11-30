package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.MyOrders;
import com.mycompany.myapp.repository.MyOrdersRepository;
import com.mycompany.myapp.service.dto.MyOrdersDTO;
import com.mycompany.myapp.service.mapper.MyOrdersMapper;

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
 * Test class for the MyOrdersResource REST controller.
 *
 * @see MyOrdersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class MyOrdersResourceIntTest {

    @Inject
    private MyOrdersRepository myOrdersRepository;

    @Inject
    private MyOrdersMapper myOrdersMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyOrdersMockMvc;

    private MyOrders myOrders;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyOrdersResource myOrdersResource = new MyOrdersResource();
        ReflectionTestUtils.setField(myOrdersResource, "myOrdersRepository", myOrdersRepository);
        ReflectionTestUtils.setField(myOrdersResource, "myOrdersMapper", myOrdersMapper);
        this.restMyOrdersMockMvc = MockMvcBuilders.standaloneSetup(myOrdersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyOrders createEntity(EntityManager em) {
        MyOrders myOrders = new MyOrders();
        return myOrders;
    }

    @Before
    public void initTest() {
        myOrders = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyOrders() throws Exception {
        int databaseSizeBeforeCreate = myOrdersRepository.findAll().size();

        // Create the MyOrders
        MyOrdersDTO myOrdersDTO = myOrdersMapper.myOrdersToMyOrdersDTO(myOrders);

        restMyOrdersMockMvc.perform(post("/api/my-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrdersDTO)))
                .andExpect(status().isCreated());

        // Validate the MyOrders in the database
        List<MyOrders> myOrders = myOrdersRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeCreate + 1);
        MyOrders testMyOrders = myOrders.get(myOrders.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMyOrders() throws Exception {
        // Initialize the database
        myOrdersRepository.saveAndFlush(myOrders);

        // Get all the myOrders
        restMyOrdersMockMvc.perform(get("/api/my-orders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myOrders.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMyOrders() throws Exception {
        // Initialize the database
        myOrdersRepository.saveAndFlush(myOrders);

        // Get the myOrders
        restMyOrdersMockMvc.perform(get("/api/my-orders/{id}", myOrders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myOrders.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMyOrders() throws Exception {
        // Get the myOrders
        restMyOrdersMockMvc.perform(get("/api/my-orders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyOrders() throws Exception {
        // Initialize the database
        myOrdersRepository.saveAndFlush(myOrders);
        int databaseSizeBeforeUpdate = myOrdersRepository.findAll().size();

        // Update the myOrders
        MyOrders updatedMyOrders = myOrdersRepository.findOne(myOrders.getId());
        MyOrdersDTO myOrdersDTO = myOrdersMapper.myOrdersToMyOrdersDTO(updatedMyOrders);

        restMyOrdersMockMvc.perform(put("/api/my-orders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myOrdersDTO)))
                .andExpect(status().isOk());

        // Validate the MyOrders in the database
        List<MyOrders> myOrders = myOrdersRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeUpdate);
        MyOrders testMyOrders = myOrders.get(myOrders.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMyOrders() throws Exception {
        // Initialize the database
        myOrdersRepository.saveAndFlush(myOrders);
        int databaseSizeBeforeDelete = myOrdersRepository.findAll().size();

        // Get the myOrders
        restMyOrdersMockMvc.perform(delete("/api/my-orders/{id}", myOrders.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyOrders> myOrders = myOrdersRepository.findAll();
        assertThat(myOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
