package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ordercustomer;

import com.mycompany.myapp.repository.OrdercustomerRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.OrdercustomerDTO;
import com.mycompany.myapp.service.mapper.OrdercustomerMapper;
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
 * REST controller for managing Ordercustomer.
 */
@RestController
@RequestMapping("/api")
public class OrdercustomerResource {

    private final Logger log = LoggerFactory.getLogger(OrdercustomerResource.class);
        
    @Inject
    private OrdercustomerRepository ordercustomerRepository;

    @Inject
    private OrdercustomerMapper ordercustomerMapper;

    /**
     * POST  /ordercustomers : Create a new ordercustomer.
     *
     * @param ordercustomerDTO the ordercustomerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordercustomerDTO, or with status 400 (Bad Request) if the ordercustomer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordercustomers")
    @Timed
    public ResponseEntity<OrdercustomerDTO> createOrdercustomer(@RequestBody OrdercustomerDTO ordercustomerDTO) throws URISyntaxException {
        log.debug("REST request to save Ordercustomer : {}", ordercustomerDTO);
        if (ordercustomerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ordercustomer", "idexists", "A new ordercustomer cannot already have an ID")).body(null);
        }
        Ordercustomer ordercustomer = ordercustomerMapper.ordercustomerDTOToOrdercustomer(ordercustomerDTO);
        ordercustomer = ordercustomerRepository.save(ordercustomer);
        OrdercustomerDTO result = ordercustomerMapper.ordercustomerToOrdercustomerDTO(ordercustomer);
        return ResponseEntity.created(new URI("/api/ordercustomers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ordercustomer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordercustomers : Updates an existing ordercustomer.
     *
     * @param ordercustomerDTO the ordercustomerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordercustomerDTO,
     * or with status 400 (Bad Request) if the ordercustomerDTO is not valid,
     * or with status 500 (Internal Server Error) if the ordercustomerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordercustomers")
    @Timed
    public ResponseEntity<OrdercustomerDTO> updateOrdercustomer(@RequestBody OrdercustomerDTO ordercustomerDTO) throws URISyntaxException {
        log.debug("REST request to update Ordercustomer : {}", ordercustomerDTO);
        if (ordercustomerDTO.getId() == null) {
            return createOrdercustomer(ordercustomerDTO);
        }
        Ordercustomer ordercustomer = ordercustomerMapper.ordercustomerDTOToOrdercustomer(ordercustomerDTO);
        ordercustomer = ordercustomerRepository.save(ordercustomer);
        OrdercustomerDTO result = ordercustomerMapper.ordercustomerToOrdercustomerDTO(ordercustomer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ordercustomer", ordercustomerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordercustomers : get all the ordercustomers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordercustomers in body
     */
    @GetMapping("/ordercustomers")
    @Timed
    public List<OrdercustomerDTO> getAllOrdercustomers() {
        log.debug("REST request to get all Ordercustomers");
        List<Ordercustomer> ordercustomers = ordercustomerRepository.findAll();
        return ordercustomerMapper.ordercustomersToOrdercustomerDTOs(ordercustomers);
    }

    /**
     * GET  /ordercustomers/:id : get the "id" ordercustomer.
     *
     * @param id the id of the ordercustomerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordercustomerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ordercustomers/{id}")
    @Timed
    public ResponseEntity<OrdercustomerDTO> getOrdercustomer(@PathVariable Long id) {
        log.debug("REST request to get Ordercustomer : {}", id);
        Ordercustomer ordercustomer = ordercustomerRepository.findOne(id);
        OrdercustomerDTO ordercustomerDTO = ordercustomerMapper.ordercustomerToOrdercustomerDTO(ordercustomer);
        return Optional.ofNullable(ordercustomerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ordercustomers/:id : delete the "id" ordercustomer.
     *
     * @param id the id of the ordercustomerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordercustomers/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdercustomer(@PathVariable Long id) {
        log.debug("REST request to delete Ordercustomer : {}", id);
        ordercustomerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ordercustomer", id.toString())).build();
    }

}
