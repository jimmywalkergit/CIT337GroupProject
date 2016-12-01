package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TestproductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Testproduct and its DTO TestproductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestproductMapper {

    TestproductDTO testproductToTestproductDTO(Testproduct testproduct);

    List<TestproductDTO> testproductsToTestproductDTOs(List<Testproduct> testproducts);

    Testproduct testproductDTOToTestproduct(TestproductDTO testproductDTO);

    List<Testproduct> testproductDTOsToTestproducts(List<TestproductDTO> testproductDTOs);
}
