package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.repository.MyOrdersRepository;
import com.mycompany.myapp.repository.TestproductRepository;
import com.mycompany.myapp.service.dto.MyCartDTO;

import org.mapstruct.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.inject.Inject;
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


    default Testproduct testproductFromId(Long id) {
        if (id == null) {
            return null;
        }
        Testproduct testproduct = new Testproduct();
        testproduct.setId(id);
        return testproduct;
    }
}
