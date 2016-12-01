package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Testproduct;

import com.mycompany.myapp.repository.TestproductRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TestproductDTO;
import com.mycompany.myapp.service.mapper.TestproductMapper;
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
 * REST controller for managing Testproduct.
 */
@RestController
@RequestMapping("/api")
public class TestproductResource {

    private final Logger log = LoggerFactory.getLogger(TestproductResource.class);
        
    @Inject
    private TestproductRepository testproductRepository;

    @Inject
    private TestproductMapper testproductMapper;

    /**
     * POST  /testproducts : Create a new testproduct.
     *
     * @param testproductDTO the testproductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testproductDTO, or with status 400 (Bad Request) if the testproduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/testproducts")
    @Timed
    public ResponseEntity<TestproductDTO> createTestproduct(@RequestBody TestproductDTO testproductDTO) throws URISyntaxException {
        log.debug("REST request to save Testproduct : {}", testproductDTO);
        if (testproductDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testproduct", "idexists", "A new testproduct cannot already have an ID")).body(null);
        }
        Testproduct testproduct = testproductMapper.testproductDTOToTestproduct(testproductDTO);
        testproduct = testproductRepository.save(testproduct);
        TestproductDTO result = testproductMapper.testproductToTestproductDTO(testproduct);
        return ResponseEntity.created(new URI("/api/testproducts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testproduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testproducts : Updates an existing testproduct.
     *
     * @param testproductDTO the testproductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testproductDTO,
     * or with status 400 (Bad Request) if the testproductDTO is not valid,
     * or with status 500 (Internal Server Error) if the testproductDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/testproducts")
    @Timed
    public ResponseEntity<TestproductDTO> updateTestproduct(@RequestBody TestproductDTO testproductDTO) throws URISyntaxException {
        log.debug("REST request to update Testproduct : {}", testproductDTO);
        if (testproductDTO.getId() == null) {
            return createTestproduct(testproductDTO);
        }
        Testproduct testproduct = testproductMapper.testproductDTOToTestproduct(testproductDTO);
        testproduct = testproductRepository.save(testproduct);
        TestproductDTO result = testproductMapper.testproductToTestproductDTO(testproduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testproduct", testproductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testproducts : get all the testproducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testproducts in body
     */
    @GetMapping("/testproducts")
    @Timed
    public List<TestproductDTO> getAllTestproducts() {
        log.debug("REST request to get all Testproducts");
        List<Testproduct> testproducts = testproductRepository.findAll();
        return testproductMapper.testproductsToTestproductDTOs(testproducts);
    }

    /**
     * GET  /testproducts/:id : get the "id" testproduct.
     *
     * @param id the id of the testproductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testproductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/testproducts/{id}")
    @Timed
    public ResponseEntity<TestproductDTO> getTestproduct(@PathVariable Long id) {
        log.debug("REST request to get Testproduct : {}", id);
        Testproduct testproduct = testproductRepository.findOne(id);
        TestproductDTO testproductDTO = testproductMapper.testproductToTestproductDTO(testproduct);
        return Optional.ofNullable(testproductDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testproducts/:id : delete the "id" testproduct.
     *
     * @param id the id of the testproductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/testproducts/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestproduct(@PathVariable Long id) {
        log.debug("REST request to delete Testproduct : {}", id);
        testproductRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testproduct", id.toString())).build();
    }

}
