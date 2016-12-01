package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TestorderDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Testorder and its DTO TestorderDTO.
 */
@Mapper(componentModel = "spring", uses = {TestproductMapper.class, })
public interface TestorderMapper {

    TestorderDTO testorderToTestorderDTO(Testorder testorder);

    List<TestorderDTO> testordersToTestorderDTOs(List<Testorder> testorders);

    Testorder testorderDTOToTestorder(TestorderDTO testorderDTO);

    List<Testorder> testorderDTOsToTestorders(List<TestorderDTO> testorderDTOs);

    default Testproduct testproductFromId(Long id) {
        if (id == null) {
            return null;
        }
        Testproduct testproduct = new Testproduct();
        testproduct.setId(id);
        return testproduct;
    }
}
