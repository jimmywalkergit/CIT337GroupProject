package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyCustomer;

import com.mycompany.myapp.repository.MyCustomerRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MyCustomerDTO;
import com.mycompany.myapp.service.mapper.MyCustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MyCustomer.
 */
@RestController
@RequestMapping("/api")
public class MyCustomerResource {

    private final Logger log = LoggerFactory.getLogger(MyCustomerResource.class);
        
    @Inject
    private MyCustomerRepository myCustomerRepository;

    @Inject
    private MyCustomerMapper myCustomerMapper;

    /**
     * POST  /my-customers : Create a new myCustomer.
     *
     * @param myCustomerDTO the myCustomerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myCustomerDTO, or with status 400 (Bad Request) if the myCustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-customers")
    @Timed
    public ResponseEntity<MyCustomerDTO> createMyCustomer(@Valid @RequestBody MyCustomerDTO myCustomerDTO) throws URISyntaxException {
        log.debug("REST request to save MyCustomer : {}", myCustomerDTO);
        if (myCustomerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myCustomer", "idexists", "A new myCustomer cannot already have an ID")).body(null);
        }
        MyCustomer myCustomer = myCustomerMapper.myCustomerDTOToMyCustomer(myCustomerDTO);
        myCustomer = myCustomerRepository.save(myCustomer);
        MyCustomerDTO result = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);
        return ResponseEntity.created(new URI("/api/my-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myCustomer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-customers : Updates an existing myCustomer.
     *
     * @param myCustomerDTO the myCustomerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myCustomerDTO,
     * or with status 400 (Bad Request) if the myCustomerDTO is not valid,
     * or with status 500 (Internal Server Error) if the myCustomerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-customers")
    @Timed
    public ResponseEntity<MyCustomerDTO> updateMyCustomer(@Valid @RequestBody MyCustomerDTO myCustomerDTO) throws URISyntaxException {
        log.debug("REST request to update MyCustomer : {}", myCustomerDTO);
        if (myCustomerDTO.getId() == null) {
            return createMyCustomer(myCustomerDTO);
        }
        MyCustomer myCustomer = myCustomerMapper.myCustomerDTOToMyCustomer(myCustomerDTO);
        myCustomer = myCustomerRepository.save(myCustomer);
        MyCustomerDTO result = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myCustomer", myCustomerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-customers : get all the myCustomers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myCustomers in body
     */
    @GetMapping("/my-customers")
    @Timed
    public List<MyCustomerDTO> getAllMyCustomers() {
        log.debug("REST request to get all MyCustomers");
        List<MyCustomer> myCustomers = myCustomerRepository.findAll();
        return myCustomerMapper.myCustomersToMyCustomerDTOs(myCustomers);
    }

    /**
     * GET  /my-customers/:id : get the "id" myCustomer.
     *
     * @param id the id of the myCustomerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myCustomerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-customers/{id}")
    @Timed
    public ResponseEntity<MyCustomerDTO> getMyCustomer(@PathVariable Long id) {
        log.debug("REST request to get MyCustomer : {}", id);
        MyCustomer myCustomer = myCustomerRepository.findOne(id);
        MyCustomerDTO myCustomerDTO = myCustomerMapper.myCustomerToMyCustomerDTO(myCustomer);
        return Optional.ofNullable(myCustomerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-customers/:id : delete the "id" myCustomer.
     *
     * @param id the id of the myCustomerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-customers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyCustomer(@PathVariable Long id) {
        log.debug("REST request to delete MyCustomer : {}", id);
        myCustomerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myCustomer", id.toString())).build();
    }

}
