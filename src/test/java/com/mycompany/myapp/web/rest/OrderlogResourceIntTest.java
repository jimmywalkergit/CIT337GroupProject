package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.Orderlog;
import com.mycompany.myapp.repository.OrderlogRepository;
import com.mycompany.myapp.service.dto.OrderlogDTO;
import com.mycompany.myapp.service.mapper.OrderlogMapper;

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
 * Test class for the OrderlogResource REST controller.
 *
 * @see OrderlogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class OrderlogResourceIntTest {

    @Inject
    private OrderlogRepository orderlogRepository;

    @Inject
    private OrderlogMapper orderlogMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrderlogMockMvc;

    private Orderlog orderlog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderlogResource orderlogResource = new OrderlogResource();
        ReflectionTestUtils.setField(orderlogResource, "orderlogRepository", orderlogRepository);
        ReflectionTestUtils.setField(orderlogResource, "orderlogMapper", orderlogMapper);
        this.restOrderlogMockMvc = MockMvcBuilders.standaloneSetup(orderlogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orderlog createEntity(EntityManager em) {
        Orderlog orderlog = new Orderlog();
        return orderlog;
    }

    @Before
    public void initTest() {
        orderlog = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderlog() throws Exception {
        int databaseSizeBeforeCreate = orderlogRepository.findAll().size();

        // Create the Orderlog
        OrderlogDTO orderlogDTO = orderlogMapper.orderlogToOrderlogDTO(orderlog);

        restOrderlogMockMvc.perform(post("/api/orderlogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderlogDTO)))
                .andExpect(status().isCreated());

        // Validate the Orderlog in the database
        List<Orderlog> orderlogs = orderlogRepository.findAll();
        assertThat(orderlogs).hasSize(databaseSizeBeforeCreate + 1);
        Orderlog testOrderlog = orderlogs.get(orderlogs.size() - 1);
    }

    @Test
    @Transactional
    public void getAllOrderlogs() throws Exception {
        // Initialize the database
        orderlogRepository.saveAndFlush(orderlog);

        // Get all the orderlogs
        restOrderlogMockMvc.perform(get("/api/orderlogs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderlog.getId().intValue())));
    }

    @Test
    @Transactional
    public void getOrderlog() throws Exception {
        // Initialize the database
        orderlogRepository.saveAndFlush(orderlog);

        // Get the orderlog
        restOrderlogMockMvc.perform(get("/api/orderlogs/{id}", orderlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderlog.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderlog() throws Exception {
        // Get the orderlog
        restOrderlogMockMvc.perform(get("/api/orderlogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderlog() throws Exception {
        // Initialize the database
        orderlogRepository.saveAndFlush(orderlog);
        int databaseSizeBeforeUpdate = orderlogRepository.findAll().size();

        // Update the orderlog
        Orderlog updatedOrderlog = orderlogRepository.findOne(orderlog.getId());
        OrderlogDTO orderlogDTO = orderlogMapper.orderlogToOrderlogDTO(updatedOrderlog);

        restOrderlogMockMvc.perform(put("/api/orderlogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderlogDTO)))
                .andExpect(status().isOk());

        // Validate the Orderlog in the database
        List<Orderlog> orderlogs = orderlogRepository.findAll();
        assertThat(orderlogs).hasSize(databaseSizeBeforeUpdate);
        Orderlog testOrderlog = orderlogs.get(orderlogs.size() - 1);
    }

    @Test
    @Transactional
    public void deleteOrderlog() throws Exception {
        // Initialize the database
        orderlogRepository.saveAndFlush(orderlog);
        int databaseSizeBeforeDelete = orderlogRepository.findAll().size();

        // Get the orderlog
        restOrderlogMockMvc.perform(delete("/api/orderlogs/{id}", orderlog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Orderlog> orderlogs = orderlogRepository.findAll();
        assertThat(orderlogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
