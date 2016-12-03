package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.Orderproduct;
import com.mycompany.myapp.repository.OrderproductRepository;
import com.mycompany.myapp.service.dto.OrderproductDTO;
import com.mycompany.myapp.service.mapper.OrderproductMapper;

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
 * Test class for the OrderproductResource REST controller.
 *
 * @see OrderproductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class OrderproductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_PRICE = "AAAAA";
    private static final String UPDATED_PRICE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_IMAGE = "AAAAA";
    private static final String UPDATED_IMAGE = "BBBBB";

    @Inject
    private OrderproductRepository orderproductRepository;

    @Inject
    private OrderproductMapper orderproductMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restOrderproductMockMvc;

    private Orderproduct orderproduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderproductResource orderproductResource = new OrderproductResource();
        ReflectionTestUtils.setField(orderproductResource, "orderproductRepository", orderproductRepository);
        ReflectionTestUtils.setField(orderproductResource, "orderproductMapper", orderproductMapper);
        this.restOrderproductMockMvc = MockMvcBuilders.standaloneSetup(orderproductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orderproduct createEntity(EntityManager em) {
        Orderproduct orderproduct = new Orderproduct()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .description(DEFAULT_DESCRIPTION)
                .image(DEFAULT_IMAGE);
        return orderproduct;
    }

    @Before
    public void initTest() {
        orderproduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderproduct() throws Exception {
        int databaseSizeBeforeCreate = orderproductRepository.findAll().size();

        // Create the Orderproduct
        OrderproductDTO orderproductDTO = orderproductMapper.orderproductToOrderproductDTO(orderproduct);

        restOrderproductMockMvc.perform(post("/api/orderproducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderproductDTO)))
                .andExpect(status().isCreated());

        // Validate the Orderproduct in the database
        List<Orderproduct> orderproducts = orderproductRepository.findAll();
        assertThat(orderproducts).hasSize(databaseSizeBeforeCreate + 1);
        Orderproduct testOrderproduct = orderproducts.get(orderproducts.size() - 1);
        assertThat(testOrderproduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderproduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrderproduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrderproduct.getImage()).isEqualTo(DEFAULT_IMAGE);
    }

    @Test
    @Transactional
    public void getAllOrderproducts() throws Exception {
        // Initialize the database
        orderproductRepository.saveAndFlush(orderproduct);

        // Get all the orderproducts
        restOrderproductMockMvc.perform(get("/api/orderproducts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderproduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getOrderproduct() throws Exception {
        // Initialize the database
        orderproductRepository.saveAndFlush(orderproduct);

        // Get the orderproduct
        restOrderproductMockMvc.perform(get("/api/orderproducts/{id}", orderproduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderproduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderproduct() throws Exception {
        // Get the orderproduct
        restOrderproductMockMvc.perform(get("/api/orderproducts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderproduct() throws Exception {
        // Initialize the database
        orderproductRepository.saveAndFlush(orderproduct);
        int databaseSizeBeforeUpdate = orderproductRepository.findAll().size();

        // Update the orderproduct
        Orderproduct updatedOrderproduct = orderproductRepository.findOne(orderproduct.getId());
        updatedOrderproduct
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .description(UPDATED_DESCRIPTION)
                .image(UPDATED_IMAGE);
        OrderproductDTO orderproductDTO = orderproductMapper.orderproductToOrderproductDTO(updatedOrderproduct);

        restOrderproductMockMvc.perform(put("/api/orderproducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderproductDTO)))
                .andExpect(status().isOk());

        // Validate the Orderproduct in the database
        List<Orderproduct> orderproducts = orderproductRepository.findAll();
        assertThat(orderproducts).hasSize(databaseSizeBeforeUpdate);
        Orderproduct testOrderproduct = orderproducts.get(orderproducts.size() - 1);
        assertThat(testOrderproduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderproduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrderproduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrderproduct.getImage()).isEqualTo(UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void deleteOrderproduct() throws Exception {
        // Initialize the database
        orderproductRepository.saveAndFlush(orderproduct);
        int databaseSizeBeforeDelete = orderproductRepository.findAll().size();

        // Get the orderproduct
        restOrderproductMockMvc.perform(delete("/api/orderproducts/{id}", orderproduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Orderproduct> orderproducts = orderproductRepository.findAll();
        assertThat(orderproducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
