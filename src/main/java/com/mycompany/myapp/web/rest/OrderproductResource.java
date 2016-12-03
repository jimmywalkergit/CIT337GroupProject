package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Orderproduct;

import com.mycompany.myapp.repository.OrderproductRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrderproductDTO;
import com.mycompany.myapp.service.mapper.OrderproductMapper;
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
 * REST controller for managing Orderproduct.
 */
@RestController
@RequestMapping("/api")
public class OrderproductResource {

    private final Logger log = LoggerFactory.getLogger(OrderproductResource.class);
        
    @Inject
    private OrderproductRepository orderproductRepository;

    @Inject
    private OrderproductMapper orderproductMapper;

    /**
     * POST  /orderproducts : Create a new orderproduct.
     *
     * @param orderproductDTO the orderproductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderproductDTO, or with status 400 (Bad Request) if the orderproduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orderproducts")
    @Timed
    public ResponseEntity<OrderproductDTO> createOrderproduct(@RequestBody OrderproductDTO orderproductDTO) throws URISyntaxException {
        log.debug("REST request to save Orderproduct : {}", orderproductDTO);
        if (orderproductDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderproduct", "idexists", "A new orderproduct cannot already have an ID")).body(null);
        }
        Orderproduct orderproduct = orderproductMapper.orderproductDTOToOrderproduct(orderproductDTO);
        orderproduct = orderproductRepository.save(orderproduct);
        OrderproductDTO result = orderproductMapper.orderproductToOrderproductDTO(orderproduct);
        return ResponseEntity.created(new URI("/api/orderproducts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderproduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orderproducts : Updates an existing orderproduct.
     *
     * @param orderproductDTO the orderproductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderproductDTO,
     * or with status 400 (Bad Request) if the orderproductDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderproductDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orderproducts")
    @Timed
    public ResponseEntity<OrderproductDTO> updateOrderproduct(@RequestBody OrderproductDTO orderproductDTO) throws URISyntaxException {
        log.debug("REST request to update Orderproduct : {}", orderproductDTO);
        if (orderproductDTO.getId() == null) {
            return createOrderproduct(orderproductDTO);
        }
        Orderproduct orderproduct = orderproductMapper.orderproductDTOToOrderproduct(orderproductDTO);
        orderproduct = orderproductRepository.save(orderproduct);
        OrderproductDTO result = orderproductMapper.orderproductToOrderproductDTO(orderproduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderproduct", orderproductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orderproducts : get all the orderproducts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderproducts in body
     */
    @GetMapping("/orderproducts")
    @Timed
    public List<OrderproductDTO> getAllOrderproducts() {
        log.debug("REST request to get all Orderproducts");
        List<Orderproduct> orderproducts = orderproductRepository.findAll();
        return orderproductMapper.orderproductsToOrderproductDTOs(orderproducts);
    }

    /**
     * GET  /orderproducts/:id : get the "id" orderproduct.
     *
     * @param id the id of the orderproductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderproductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orderproducts/{id}")
    @Timed
    public ResponseEntity<OrderproductDTO> getOrderproduct(@PathVariable Long id) {
        log.debug("REST request to get Orderproduct : {}", id);
        Orderproduct orderproduct = orderproductRepository.findOne(id);
        OrderproductDTO orderproductDTO = orderproductMapper.orderproductToOrderproductDTO(orderproduct);
        return Optional.ofNullable(orderproductDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderproducts/:id : delete the "id" orderproduct.
     *
     * @param id the id of the orderproductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orderproducts/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderproduct(@PathVariable Long id) {
        log.debug("REST request to delete Orderproduct : {}", id);
        orderproductRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderproduct", id.toString())).build();
    }

}
