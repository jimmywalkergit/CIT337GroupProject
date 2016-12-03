package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrdercustomerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Ordercustomer and its DTO OrdercustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdercustomerMapper {

    OrdercustomerDTO ordercustomerToOrdercustomerDTO(Ordercustomer ordercustomer);

    List<OrdercustomerDTO> ordercustomersToOrdercustomerDTOs(List<Ordercustomer> ordercustomers);

    Ordercustomer ordercustomerDTOToOrdercustomer(OrdercustomerDTO ordercustomerDTO);

    List<Ordercustomer> ordercustomerDTOsToOrdercustomers(List<OrdercustomerDTO> ordercustomerDTOs);
}
