package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Cart;

import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.service.mapper.CartMapper;
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
 * REST controller for managing Cart.
 */
@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);
        
    @Inject
    private CartRepository cartRepository;

    @Inject
    private CartMapper cartMapper;

    /**
     * POST  /carts : Create a new cart.
     *
     * @param cartDTO the cartDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cartDTO, or with status 400 (Bad Request) if the cart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carts")
    @Timed
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to save Cart : {}", cartDTO);
        if (cartDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cart", "idexists", "A new cart cannot already have an ID")).body(null);
        }
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        cart = cartRepository.save(cart);
        CartDTO result = cartMapper.cartToCartDTO(cart);
        return ResponseEntity.created(new URI("/api/carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cart", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carts : Updates an existing cart.
     *
     * @param cartDTO the cartDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cartDTO,
     * or with status 400 (Bad Request) if the cartDTO is not valid,
     * or with status 500 (Internal Server Error) if the cartDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carts")
    @Timed
    public ResponseEntity<CartDTO> updateCart(@RequestBody CartDTO cartDTO) throws URISyntaxException {
        log.debug("REST request to update Cart : {}", cartDTO);
        if (cartDTO.getId() == null) {
            return createCart(cartDTO);
        }
        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        cart = cartRepository.save(cart);
        CartDTO result = cartMapper.cartToCartDTO(cart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cart", cartDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carts : get all the carts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of carts in body
     */
    @GetMapping("/carts")
    @Timed
    public List<CartDTO> getAllCarts() {
        log.debug("REST request to get all Carts");
        List<Cart> carts = cartRepository.findAll();
        return cartMapper.cartsToCartDTOs(carts);
    }

    /**
     * GET  /carts/:id : get the "id" cart.
     *
     * @param id the id of the cartDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cartDTO, or with status 404 (Not Found)
     */
    @GetMapping("/carts/{id}")
    @Timed
    public ResponseEntity<CartDTO> getCart(@PathVariable Long id) {
        log.debug("REST request to get Cart : {}", id);
        Cart cart = cartRepository.findOne(id);
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        return Optional.ofNullable(cartDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carts/:id : delete the "id" cart.
     *
     * @param id the id of the cartDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        log.debug("REST request to delete Cart : {}", id);
        cartRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cart", id.toString())).build();
    }

}
