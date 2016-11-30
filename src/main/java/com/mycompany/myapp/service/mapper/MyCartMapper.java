package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MyCartDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyCart and its DTO MyCartDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyCartMapper {

    MyCartDTO myCartToMyCartDTO(MyCart myCart);

    List<MyCartDTO> myCartsToMyCartDTOs(List<MyCart> myCarts);

    @Mapping(target = "containsprods", ignore = true)
    MyCart myCartDTOToMyCart(MyCartDTO myCartDTO);

    List<MyCart> myCartDTOsToMyCarts(List<MyCartDTO> myCartDTOs);
}
