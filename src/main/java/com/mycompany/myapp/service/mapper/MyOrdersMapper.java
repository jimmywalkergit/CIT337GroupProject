package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MyOrdersDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyOrders and its DTO MyOrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyOrdersMapper {

    @Mapping(source = "myCustomer.id", target = "myCustomerId")
    MyOrdersDTO myOrdersToMyOrdersDTO(MyOrders myOrders);

    List<MyOrdersDTO> myOrdersToMyOrdersDTOs(List<MyOrders> myOrders);

    @Mapping(source = "myCustomerId", target = "myCustomer")
    @Mapping(target = "containsorderprods", ignore = true)
    MyOrders myOrdersDTOToMyOrders(MyOrdersDTO myOrdersDTO);

    List<MyOrders> myOrdersDTOsToMyOrders(List<MyOrdersDTO> myOrdersDTOs);

    default MyCustomer myCustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        MyCustomer myCustomer = new MyCustomer();
        myCustomer.setId(id);
        return myCustomer;
    }
}
