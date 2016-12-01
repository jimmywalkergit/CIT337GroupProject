package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Testorder;

import com.mycompany.myapp.repository.TestorderRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TestorderDTO;
import com.mycompany.myapp.service.mapper.TestorderMapper;
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
 * REST controller for managing Testorder.
 */
@RestController
@RequestMapping("/api")
public class TestorderResource {

    private final Logger log = LoggerFactory.getLogger(TestorderResource.class);
        
    @Inject
    private TestorderRepository testorderRepository;

    @Inject
    private TestorderMapper testorderMapper;

    /**
     * POST  /testorders : Create a new testorder.
     *
     * @param testorderDTO the testorderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testorderDTO, or with status 400 (Bad Request) if the testorder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/testorders")
    @Timed
    public ResponseEntity<TestorderDTO> createTestorder(@RequestBody TestorderDTO testorderDTO) throws URISyntaxException {
        log.debug("REST request to save Testorder : {}", testorderDTO);
        if (testorderDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testorder", "idexists", "A new testorder cannot already have an ID")).body(null);
        }
        Testorder testorder = testorderMapper.testorderDTOToTestorder(testorderDTO);
        testorder = testorderRepository.save(testorder);
        TestorderDTO result = testorderMapper.testorderToTestorderDTO(testorder);
        return ResponseEntity.created(new URI("/api/testorders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testorder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /testorders : Updates an existing testorder.
     *
     * @param testorderDTO the testorderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testorderDTO,
     * or with status 400 (Bad Request) if the testorderDTO is not valid,
     * or with status 500 (Internal Server Error) if the testorderDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/testorders")
    @Timed
    public ResponseEntity<TestorderDTO> updateTestorder(@RequestBody TestorderDTO testorderDTO) throws URISyntaxException {
        log.debug("REST request to update Testorder : {}", testorderDTO);
        if (testorderDTO.getId() == null) {
            return createTestorder(testorderDTO);
        }
        Testorder testorder = testorderMapper.testorderDTOToTestorder(testorderDTO);
        testorder = testorderRepository.save(testorder);
        TestorderDTO result = testorderMapper.testorderToTestorderDTO(testorder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testorder", testorderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /testorders : get all the testorders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testorders in body
     */
    @GetMapping("/testorders")
    @Timed
    public List<TestorderDTO> getAllTestorders() {
        log.debug("REST request to get all Testorders");
        List<Testorder> testorders = testorderRepository.findAllWithEagerRelationships();
        return testorderMapper.testordersToTestorderDTOs(testorders);
    }

    /**
     * GET  /testorders/:id : get the "id" testorder.
     *
     * @param id the id of the testorderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testorderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/testorders/{id}")
    @Timed
    public ResponseEntity<TestorderDTO> getTestorder(@PathVariable Long id) {
        log.debug("REST request to get Testorder : {}", id);
        Testorder testorder = testorderRepository.findOneWithEagerRelationships(id);
        TestorderDTO testorderDTO = testorderMapper.testorderToTestorderDTO(testorder);
        return Optional.ofNullable(testorderDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /testorders/:id : delete the "id" testorder.
     *
     * @param id the id of the testorderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/testorders/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestorder(@PathVariable Long id) {
        log.debug("REST request to delete Testorder : {}", id);
        testorderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testorder", id.toString())).build();
    }

}
