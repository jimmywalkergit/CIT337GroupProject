package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Cit337ProjectApp;

import com.mycompany.myapp.domain.MyProduct;
import com.mycompany.myapp.repository.MyProductRepository;
import com.mycompany.myapp.service.dto.MyProductDTO;
import com.mycompany.myapp.service.mapper.MyProductMapper;

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
 * Test class for the MyProductResource REST controller.
 *
 * @see MyProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Cit337ProjectApp.class)
public class MyProductResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private MyProductRepository myProductRepository;

    @Inject
    private MyProductMapper myProductMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restMyProductMockMvc;

    private MyProduct myProduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyProductResource myProductResource = new MyProductResource();
        ReflectionTestUtils.setField(myProductResource, "myProductRepository", myProductRepository);
        ReflectionTestUtils.setField(myProductResource, "myProductMapper", myProductMapper);
        this.restMyProductMockMvc = MockMvcBuilders.standaloneSetup(myProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyProduct createEntity(EntityManager em) {
        MyProduct myProduct = new MyProduct()
                .name(DEFAULT_NAME)
                .price(DEFAULT_PRICE)
                .description(DEFAULT_DESCRIPTION);
        return myProduct;
    }

    @Before
    public void initTest() {
        myProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyProduct() throws Exception {
        int databaseSizeBeforeCreate = myProductRepository.findAll().size();

        // Create the MyProduct
        MyProductDTO myProductDTO = myProductMapper.myProductToMyProductDTO(myProduct);

        restMyProductMockMvc.perform(post("/api/my-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myProductDTO)))
                .andExpect(status().isCreated());

        // Validate the MyProduct in the database
        List<MyProduct> myProducts = myProductRepository.findAll();
        assertThat(myProducts).hasSize(databaseSizeBeforeCreate + 1);
        MyProduct testMyProduct = myProducts.get(myProducts.size() - 1);
        assertThat(testMyProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testMyProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMyProducts() throws Exception {
        // Initialize the database
        myProductRepository.saveAndFlush(myProduct);

        // Get all the myProducts
        restMyProductMockMvc.perform(get("/api/my-products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myProduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getMyProduct() throws Exception {
        // Initialize the database
        myProductRepository.saveAndFlush(myProduct);

        // Get the myProduct
        restMyProductMockMvc.perform(get("/api/my-products/{id}", myProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myProduct.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyProduct() throws Exception {
        // Get the myProduct
        restMyProductMockMvc.perform(get("/api/my-products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyProduct() throws Exception {
        // Initialize the database
        myProductRepository.saveAndFlush(myProduct);
        int databaseSizeBeforeUpdate = myProductRepository.findAll().size();

        // Update the myProduct
        MyProduct updatedMyProduct = myProductRepository.findOne(myProduct.getId());
        updatedMyProduct
                .name(UPDATED_NAME)
                .price(UPDATED_PRICE)
                .description(UPDATED_DESCRIPTION);
        MyProductDTO myProductDTO = myProductMapper.myProductToMyProductDTO(updatedMyProduct);

        restMyProductMockMvc.perform(put("/api/my-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myProductDTO)))
                .andExpect(status().isOk());

        // Validate the MyProduct in the database
        List<MyProduct> myProducts = myProductRepository.findAll();
        assertThat(myProducts).hasSize(databaseSizeBeforeUpdate);
        MyProduct testMyProduct = myProducts.get(myProducts.size() - 1);
        assertThat(testMyProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testMyProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteMyProduct() throws Exception {
        // Initialize the database
        myProductRepository.saveAndFlush(myProduct);
        int databaseSizeBeforeDelete = myProductRepository.findAll().size();

        // Get the myProduct
        restMyProductMockMvc.perform(delete("/api/my-products/{id}", myProduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyProduct> myProducts = myProductRepository.findAll();
        assertThat(myProducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
