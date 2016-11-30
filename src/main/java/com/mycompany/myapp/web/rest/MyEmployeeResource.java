package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyEmployee;

import com.mycompany.myapp.repository.MyEmployeeRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MyEmployeeDTO;
import com.mycompany.myapp.service.mapper.MyEmployeeMapper;
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
 * REST controller for managing MyEmployee.
 */
@RestController
@RequestMapping("/api")
public class MyEmployeeResource {

    private final Logger log = LoggerFactory.getLogger(MyEmployeeResource.class);
        
    @Inject
    private MyEmployeeRepository myEmployeeRepository;

    @Inject
    private MyEmployeeMapper myEmployeeMapper;

    /**
     * POST  /my-employees : Create a new myEmployee.
     *
     * @param myEmployeeDTO the myEmployeeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myEmployeeDTO, or with status 400 (Bad Request) if the myEmployee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-employees")
    @Timed
    public ResponseEntity<MyEmployeeDTO> createMyEmployee(@RequestBody MyEmployeeDTO myEmployeeDTO) throws URISyntaxException {
        log.debug("REST request to save MyEmployee : {}", myEmployeeDTO);
        if (myEmployeeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myEmployee", "idexists", "A new myEmployee cannot already have an ID")).body(null);
        }
        MyEmployee myEmployee = myEmployeeMapper.myEmployeeDTOToMyEmployee(myEmployeeDTO);
        myEmployee = myEmployeeRepository.save(myEmployee);
        MyEmployeeDTO result = myEmployeeMapper.myEmployeeToMyEmployeeDTO(myEmployee);
        return ResponseEntity.created(new URI("/api/my-employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myEmployee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-employees : Updates an existing myEmployee.
     *
     * @param myEmployeeDTO the myEmployeeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myEmployeeDTO,
     * or with status 400 (Bad Request) if the myEmployeeDTO is not valid,
     * or with status 500 (Internal Server Error) if the myEmployeeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-employees")
    @Timed
    public ResponseEntity<MyEmployeeDTO> updateMyEmployee(@RequestBody MyEmployeeDTO myEmployeeDTO) throws URISyntaxException {
        log.debug("REST request to update MyEmployee : {}", myEmployeeDTO);
        if (myEmployeeDTO.getId() == null) {
            return createMyEmployee(myEmployeeDTO);
        }
        MyEmployee myEmployee = myEmployeeMapper.myEmployeeDTOToMyEmployee(myEmployeeDTO);
        myEmployee = myEmployeeRepository.save(myEmployee);
        MyEmployeeDTO result = myEmployeeMapper.myEmployeeToMyEmployeeDTO(myEmployee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myEmployee", myEmployeeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-employees : get all the myEmployees.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myEmployees in body
     */
    @GetMapping("/my-employees")
    @Timed
    public List<MyEmployeeDTO> getAllMyEmployees() {
        log.debug("REST request to get all MyEmployees");
        List<MyEmployee> myEmployees = myEmployeeRepository.findAll();
        return myEmployeeMapper.myEmployeesToMyEmployeeDTOs(myEmployees);
    }

    /**
     * GET  /my-employees/:id : get the "id" myEmployee.
     *
     * @param id the id of the myEmployeeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myEmployeeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-employees/{id}")
    @Timed
    public ResponseEntity<MyEmployeeDTO> getMyEmployee(@PathVariable Long id) {
        log.debug("REST request to get MyEmployee : {}", id);
        MyEmployee myEmployee = myEmployeeRepository.findOne(id);
        MyEmployeeDTO myEmployeeDTO = myEmployeeMapper.myEmployeeToMyEmployeeDTO(myEmployee);
        return Optional.ofNullable(myEmployeeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-employees/:id : delete the "id" myEmployee.
     *
     * @param id the id of the myEmployeeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-employees/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyEmployee(@PathVariable Long id) {
        log.debug("REST request to delete MyEmployee : {}", id);
        myEmployeeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myEmployee", id.toString())).build();
    }

}
