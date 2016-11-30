package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MyEmployeeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyEmployee and its DTO MyEmployeeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MyEmployeeMapper {

    MyEmployeeDTO myEmployeeToMyEmployeeDTO(MyEmployee myEmployee);

    List<MyEmployeeDTO> myEmployeesToMyEmployeeDTOs(List<MyEmployee> myEmployees);

    MyEmployee myEmployeeDTOToMyEmployee(MyEmployeeDTO myEmployeeDTO);

    List<MyEmployee> myEmployeeDTOsToMyEmployees(List<MyEmployeeDTO> myEmployeeDTOs);
}
