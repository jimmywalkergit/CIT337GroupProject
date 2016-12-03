package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Orderlog;

import com.mycompany.myapp.repository.OrderlogRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrderlogDTO;
import com.mycompany.myapp.service.mapper.OrderlogMapper;
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
 * REST controller for managing Orderlog.
 */
@RestController
@RequestMapping("/api")
public class OrderlogResource {

    private final Logger log = LoggerFactory.getLogger(OrderlogResource.class);
        
    @Inject
    private OrderlogRepository orderlogRepository;

    @Inject
    private OrderlogMapper orderlogMapper;

    /**
     * POST  /orderlogs : Create a new orderlog.
     *
     * @param orderlogDTO the orderlogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderlogDTO, or with status 400 (Bad Request) if the orderlog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orderlogs")
    @Timed
    public ResponseEntity<OrderlogDTO> createOrderlog(@RequestBody OrderlogDTO orderlogDTO) throws URISyntaxException {
        log.debug("REST request to save Orderlog : {}", orderlogDTO);
        if (orderlogDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderlog", "idexists", "A new orderlog cannot already have an ID")).body(null);
        }
        Orderlog orderlog = orderlogMapper.orderlogDTOToOrderlog(orderlogDTO);
        orderlog = orderlogRepository.save(orderlog);
        OrderlogDTO result = orderlogMapper.orderlogToOrderlogDTO(orderlog);
        return ResponseEntity.created(new URI("/api/orderlogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderlog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orderlogs : Updates an existing orderlog.
     *
     * @param orderlogDTO the orderlogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderlogDTO,
     * or with status 400 (Bad Request) if the orderlogDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderlogDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orderlogs")
    @Timed
    public ResponseEntity<OrderlogDTO> updateOrderlog(@RequestBody OrderlogDTO orderlogDTO) throws URISyntaxException {
        log.debug("REST request to update Orderlog : {}", orderlogDTO);
        if (orderlogDTO.getId() == null) {
            return createOrderlog(orderlogDTO);
        }
        Orderlog orderlog = orderlogMapper.orderlogDTOToOrderlog(orderlogDTO);
        orderlog = orderlogRepository.save(orderlog);
        OrderlogDTO result = orderlogMapper.orderlogToOrderlogDTO(orderlog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderlog", orderlogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orderlogs : get all the orderlogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of orderlogs in body
     */
    @GetMapping("/orderlogs")
    @Timed
    public List<OrderlogDTO> getAllOrderlogs() {
        log.debug("REST request to get all Orderlogs");
        List<Orderlog> orderlogs = orderlogRepository.findAllWithEagerRelationships();
        return orderlogMapper.orderlogsToOrderlogDTOs(orderlogs);
    }

    /**
     * GET  /orderlogs/:id : get the "id" orderlog.
     *
     * @param id the id of the orderlogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderlogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orderlogs/{id}")
    @Timed
    public ResponseEntity<OrderlogDTO> getOrderlog(@PathVariable Long id) {
        log.debug("REST request to get Orderlog : {}", id);
        Orderlog orderlog = orderlogRepository.findOneWithEagerRelationships(id);
        OrderlogDTO orderlogDTO = orderlogMapper.orderlogToOrderlogDTO(orderlog);
        return Optional.ofNullable(orderlogDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderlogs/:id : delete the "id" orderlog.
     *
     * @param id the id of the orderlogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orderlogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderlog(@PathVariable Long id) {
        log.debug("REST request to delete Orderlog : {}", id);
        orderlogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderlog", id.toString())).build();
    }

}
