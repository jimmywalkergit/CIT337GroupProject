package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyOrders;

import com.mycompany.myapp.repository.MyOrdersRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MyOrdersDTO;
import com.mycompany.myapp.service.mapper.MyOrdersMapper;
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
 * REST controller for managing MyOrders.
 */
@RestController
@RequestMapping("/api")
public class MyOrdersResource {

    private final Logger log = LoggerFactory.getLogger(MyOrdersResource.class);
        
    @Inject
    private MyOrdersRepository myOrdersRepository;

    @Inject
    private MyOrdersMapper myOrdersMapper;

    /**
     * POST  /my-orders : Create a new myOrders.
     *
     * @param myOrdersDTO the myOrdersDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myOrdersDTO, or with status 400 (Bad Request) if the myOrders has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-orders")
    @Timed
    public ResponseEntity<MyOrdersDTO> createMyOrders(@RequestBody MyOrdersDTO myOrdersDTO) throws URISyntaxException {
        log.debug("REST request to save MyOrders : {}", myOrdersDTO);
        if (myOrdersDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myOrders", "idexists", "A new myOrders cannot already have an ID")).body(null);
        }
        MyOrders myOrders = myOrdersMapper.myOrdersDTOToMyOrders(myOrdersDTO);
        myOrders = myOrdersRepository.save(myOrders);
        MyOrdersDTO result = myOrdersMapper.myOrdersToMyOrdersDTO(myOrders);
        return ResponseEntity.created(new URI("/api/my-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myOrders", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-orders : Updates an existing myOrders.
     *
     * @param myOrdersDTO the myOrdersDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myOrdersDTO,
     * or with status 400 (Bad Request) if the myOrdersDTO is not valid,
     * or with status 500 (Internal Server Error) if the myOrdersDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-orders")
    @Timed
    public ResponseEntity<MyOrdersDTO> updateMyOrders(@RequestBody MyOrdersDTO myOrdersDTO) throws URISyntaxException {
        log.debug("REST request to update MyOrders : {}", myOrdersDTO);
        if (myOrdersDTO.getId() == null) {
            return createMyOrders(myOrdersDTO);
        }
        MyOrders myOrders = myOrdersMapper.myOrdersDTOToMyOrders(myOrdersDTO);
        myOrders = myOrdersRepository.save(myOrders);
        MyOrdersDTO result = myOrdersMapper.myOrdersToMyOrdersDTO(myOrders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myOrders", myOrdersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-orders : get all the myOrders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myOrders in body
     */
    @GetMapping("/my-orders")
    @Timed
    public List<MyOrdersDTO> getAllMyOrders() {
        log.debug("REST request to get all MyOrders");
        List<MyOrders> myOrders = myOrdersRepository.findAll();
        return myOrdersMapper.myOrdersToMyOrdersDTOs(myOrders);
    }

    /**
     * GET  /my-orders/:id : get the "id" myOrders.
     *
     * @param id the id of the myOrdersDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myOrdersDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-orders/{id}")
    @Timed
    public ResponseEntity<MyOrdersDTO> getMyOrders(@PathVariable Long id) {
        log.debug("REST request to get MyOrders : {}", id);
        MyOrders myOrders = myOrdersRepository.findOne(id);
        MyOrdersDTO myOrdersDTO = myOrdersMapper.myOrdersToMyOrdersDTO(myOrders);
        return Optional.ofNullable(myOrdersDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-orders/:id : delete the "id" myOrders.
     *
     * @param id the id of the myOrdersDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-orders/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyOrders(@PathVariable Long id) {
        log.debug("REST request to delete MyOrders : {}", id);
        myOrdersRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myOrders", id.toString())).build();
    }

}
