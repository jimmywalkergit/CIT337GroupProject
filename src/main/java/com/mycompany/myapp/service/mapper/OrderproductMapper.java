package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderproductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Orderproduct and its DTO OrderproductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderproductMapper {

    OrderproductDTO orderproductToOrderproductDTO(Orderproduct orderproduct);

    List<OrderproductDTO> orderproductsToOrderproductDTOs(List<Orderproduct> orderproducts);

    Orderproduct orderproductDTOToOrderproduct(OrderproductDTO orderproductDTO);

    List<Orderproduct> orderproductDTOsToOrderproducts(List<OrderproductDTO> orderproductDTOs);
}
