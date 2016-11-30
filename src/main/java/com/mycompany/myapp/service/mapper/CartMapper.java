package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Cart and its DTO CartDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CartMapper {

    CartDTO cartToCartDTO(Cart cart);

    List<CartDTO> cartsToCartDTOs(List<Cart> carts);

    @Mapping(target = "have", ignore = true)
    Cart cartDTOToCart(CartDTO cartDTO);

    List<Cart> cartDTOsToCarts(List<CartDTO> cartDTOs);
}
