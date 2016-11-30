package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.MyCart;

import com.mycompany.myapp.repository.MyCartRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.MyCartDTO;
import com.mycompany.myapp.service.mapper.MyCartMapper;
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
 * REST controller for managing MyCart.
 */
@RestController
@RequestMapping("/api")
public class MyCartResource {

    private final Logger log = LoggerFactory.getLogger(MyCartResource.class);
        
    @Inject
    private MyCartRepository myCartRepository;

    @Inject
    private MyCartMapper myCartMapper;

    /**
     * POST  /my-carts : Create a new myCart.
     *
     * @param myCartDTO the myCartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myCartDTO, or with status 400 (Bad Request) if the myCart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-carts")
    @Timed
    public ResponseEntity<MyCartDTO> createMyCart(@RequestBody MyCartDTO myCartDTO) throws URISyntaxException {
        log.debug("REST request to save MyCart : {}", myCartDTO);
        if (myCartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("myCart", "idexists", "A new myCart cannot already have an ID")).body(null);
        }
        MyCart myCart = myCartMapper.myCartDTOToMyCart(myCartDTO);
        myCart = myCartRepository.save(myCart);
        MyCartDTO result = myCartMapper.myCartToMyCartDTO(myCart);
        return ResponseEntity.created(new URI("/api/my-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("myCart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-carts : Updates an existing myCart.
     *
     * @param myCartDTO the myCartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myCartDTO,
     * or with status 400 (Bad Request) if the myCartDTO is not valid,
     * or with status 500 (Internal Server Error) if the myCartDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-carts")
    @Timed
    public ResponseEntity<MyCartDTO> updateMyCart(@RequestBody MyCartDTO myCartDTO) throws URISyntaxException {
        log.debug("REST request to update MyCart : {}", myCartDTO);
        if (myCartDTO.getId() == null) {
            return createMyCart(myCartDTO);
        }
        MyCart myCart = myCartMapper.myCartDTOToMyCart(myCartDTO);
        myCart = myCartRepository.save(myCart);
        MyCartDTO result = myCartMapper.myCartToMyCartDTO(myCart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("myCart", myCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-carts : get all the myCarts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of myCarts in body
     */
    @GetMapping("/my-carts")
    @Timed
    public List<MyCartDTO> getAllMyCarts() {
        log.debug("REST request to get all MyCarts");
        List<MyCart> myCarts = myCartRepository.findAll();
        return myCartMapper.myCartsToMyCartDTOs(myCarts);
    }

    /**
     * GET  /my-carts/:id : get the "id" myCart.
     *
     * @param id the id of the myCartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myCartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/my-carts/{id}")
    @Timed
    public ResponseEntity<MyCartDTO> getMyCart(@PathVariable Long id) {
        log.debug("REST request to get MyCart : {}", id);
        MyCart myCart = myCartRepository.findOne(id);
        MyCartDTO myCartDTO = myCartMapper.myCartToMyCartDTO(myCart);
        return Optional.ofNullable(myCartDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /my-carts/:id : delete the "id" myCart.
     *
     * @param id the id of the myCartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyCart(@PathVariable Long id) {
        log.debug("REST request to delete MyCart : {}", id);
        myCartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("myCart", id.toString())).build();
    }

}
