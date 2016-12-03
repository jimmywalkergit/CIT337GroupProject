package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderlogDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Orderlog and its DTO OrderlogDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderproductMapper.class, OrdercustomerMapper.class, })
public interface OrderlogMapper {

    OrderlogDTO orderlogToOrderlogDTO(Orderlog orderlog);

    List<OrderlogDTO> orderlogsToOrderlogDTOs(List<Orderlog> orderlogs);

    Orderlog orderlogDTOToOrderlog(OrderlogDTO orderlogDTO);

    List<Orderlog> orderlogDTOsToOrderlogs(List<OrderlogDTO> orderlogDTOs);

    default Orderproduct orderproductFromId(Long id) {
        if (id == null) {
            return null;
        }
        Orderproduct orderproduct = new Orderproduct();
        orderproduct.setId(id);
        return orderproduct;
    }

    default Ordercustomer ordercustomerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ordercustomer ordercustomer = new Ordercustomer();
        ordercustomer.setId(id);
        return ordercustomer;
    }
}
