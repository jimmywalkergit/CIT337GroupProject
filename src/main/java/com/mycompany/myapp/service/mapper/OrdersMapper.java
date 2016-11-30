package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrdersDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "employee.id", target = "employeeId")
    OrdersDTO ordersToOrdersDTO(Orders orders);

    List<OrdersDTO> ordersToOrdersDTOs(List<Orders> orders);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(target = "have", ignore = true)
    @Mapping(source = "employeeId", target = "employee")
    Orders ordersDTOToOrders(OrdersDTO ordersDTO);

    List<Orders> ordersDTOsToOrders(List<OrdersDTO> ordersDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    default Employee employeeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
