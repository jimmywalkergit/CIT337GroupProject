package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MyCustomerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyCustomer and its DTO MyCustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyCustomerMapper {

    @Mapping(source = "ownscart.id", target = "ownscartId")
    MyCustomerDTO myCustomerToMyCustomerDTO(MyCustomer myCustomer);

    List<MyCustomerDTO> myCustomersToMyCustomerDTOs(List<MyCustomer> myCustomers);

    @Mapping(source = "ownscartId", target = "ownscart")
    @Mapping(target = "ownsorders", ignore = true)
    MyCustomer myCustomerDTOToMyCustomer(MyCustomerDTO myCustomerDTO);

    List<MyCustomer> myCustomerDTOsToMyCustomers(List<MyCustomerDTO> myCustomerDTOs);

    default MyCart myCartFromId(Long id) {
        if (id == null) {
            return null;
        }
        MyCart myCart = new MyCart();
        myCart.setId(id);
        return myCart;
    }
}
