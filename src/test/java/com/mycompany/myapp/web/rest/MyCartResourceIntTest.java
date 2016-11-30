package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.MyCart;
import com.mycompany.myapp.repository.MyCartRepository;
import com.mycompany.myapp.service.dto.MyCartDTO;
import com.mycompany.myapp.service.mapper.MyCartMapper;

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
 * Test class for the MyCartResource REST controller.
 *
 * @see MyCartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class MyCartResourceIntTest {

    @Inject
    private MyCartRepository myCartRepository;

    @Inject
    private MyCartMapper myCartMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyCartMockMvc;

    private MyCart myCart;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyCartResource myCartResource = new MyCartResource();
        ReflectionTestUtils.setField(myCartResource, "myCartRepository", myCartRepository);
        ReflectionTestUtils.setField(myCartResource, "myCartMapper", myCartMapper);
        this.restMyCartMockMvc = MockMvcBuilders.standaloneSetup(myCartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyCart createEntity(EntityManager em) {
        MyCart myCart = new MyCart();
        return myCart;
    }

    @Before
    public void initTest() {
        myCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyCart() throws Exception {
        int databaseSizeBeforeCreate = myCartRepository.findAll().size();

        // Create the MyCart
        MyCartDTO myCartDTO = myCartMapper.myCartToMyCartDTO(myCart);

        restMyCartMockMvc.perform(post("/api/my-carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCartDTO)))
                .andExpect(status().isCreated());

        // Validate the MyCart in the database
        List<MyCart> myCarts = myCartRepository.findAll();
        assertThat(myCarts).hasSize(databaseSizeBeforeCreate + 1);
        MyCart testMyCart = myCarts.get(myCarts.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMyCarts() throws Exception {
        // Initialize the database
        myCartRepository.saveAndFlush(myCart);

        // Get all the myCarts
        restMyCartMockMvc.perform(get("/api/my-carts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myCart.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMyCart() throws Exception {
        // Initialize the database
        myCartRepository.saveAndFlush(myCart);

        // Get the myCart
        restMyCartMockMvc.perform(get("/api/my-carts/{id}", myCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myCart.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMyCart() throws Exception {
        // Get the myCart
        restMyCartMockMvc.perform(get("/api/my-carts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyCart() throws Exception {
        // Initialize the database
        myCartRepository.saveAndFlush(myCart);
        int databaseSizeBeforeUpdate = myCartRepository.findAll().size();

        // Update the myCart
        MyCart updatedMyCart = myCartRepository.findOne(myCart.getId());
        MyCartDTO myCartDTO = myCartMapper.myCartToMyCartDTO(updatedMyCart);

        restMyCartMockMvc.perform(put("/api/my-carts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myCartDTO)))
                .andExpect(status().isOk());

        // Validate the MyCart in the database
        List<MyCart> myCarts = myCartRepository.findAll();
        assertThat(myCarts).hasSize(databaseSizeBeforeUpdate);
        MyCart testMyCart = myCarts.get(myCarts.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMyCart() throws Exception {
        // Initialize the database
        myCartRepository.saveAndFlush(myCart);
        int databaseSizeBeforeDelete = myCartRepository.findAll().size();

        // Get the myCart
        restMyCartMockMvc.perform(delete("/api/my-carts/{id}", myCart.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyCart> myCarts = myCartRepository.findAll();
        assertThat(myCarts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
