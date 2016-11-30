package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyProduct;

import com.mycompany.myapp.repository.MyProductRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MyProductDTO;
import com.mycompany.myapp.service.mapper.MyProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MyProduct.
 */
@RestController
@RequestMapping("/api")
public class MyProductResource {

    private final Logger log = LoggerFactory.getLogger(MyProductResource.class);
        
    @Inject
    private MyProductRepository myProductRepository;

    @Inject
    private MyProductMapper myProductMapper;

    /**
     * POST  /my-products : Create a new myProduct.
     *
     * @param myProductDTO the myProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myProductDTO, or with status 400 (Bad Request) if the myProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-products")
    @Timed
    public ResponseEntity<MyProductDTO> createMyProduct(@RequestBody MyProductDTO myProductDTO) throws URISyntaxException {
        log.debug("REST request to save MyProduct : {}", myProductDTO);
        if (myProductDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myProduct", "idexists", "A new myProduct cannot already have an ID")).body(null);
        }
        MyProduct myProduct = myProductMapper.myProductDTOToMyProduct(myProductDTO);
        myProduct = myProductRepository.save(myProduct);
        MyProductDTO result = myProductMapper.myProductToMyProductDTO(myProduct);
        return ResponseEntity.created(new URI("/api/my-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myProduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-products : Updates an existing myProduct.
     *
     * @param myProductDTO the myProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myProductDTO,
     * or with status 400 (Bad Request) if the myProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the myProductDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-products")
    @Timed
    public ResponseEntity<MyProductDTO> updateMyProduct(@RequestBody MyProductDTO myProductDTO) throws URISyntaxException {
        log.debug("REST request to update MyProduct : {}", myProductDTO);
        if (myProductDTO.getId() == null) {
            return createMyProduct(myProductDTO);
        }
        MyProduct myProduct = myProductMapper.myProductDTOToMyProduct(myProductDTO);
        myProduct = myProductRepository.save(myProduct);
        MyProductDTO result = myProductMapper.myProductToMyProductDTO(myProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myProduct", myProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-products : get all the myProducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myProducts in body
     */
    @GetMapping("/my-products")
    @Timed
    public List<MyProductDTO> getAllMyProducts() {
        log.debug("REST request to get all MyProducts");
        List<MyProduct> myProducts = myProductRepository.findAll();
        return myProductMapper.myProductsToMyProductDTOs(myProducts);
    }

    /**
     * GET  /my-products/:id : get the "id" myProduct.
     *
     * @param id the id of the myProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-products/{id}")
    @Timed
    public ResponseEntity<MyProductDTO> getMyProduct(@PathVariable Long id) {
        log.debug("REST request to get MyProduct : {}", id);
        MyProduct myProduct = myProductRepository.findOne(id);
        MyProductDTO myProductDTO = myProductMapper.myProductToMyProductDTO(myProduct);
        return Optional.ofNullable(myProductDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-products/:id : delete the "id" myProduct.
     *
     * @param id the id of the myProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyProduct(@PathVariable Long id) {
        log.debug("REST request to delete MyProduct : {}", id);
        myProductRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myProduct", id.toString())).build();
    }

}
