package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyEmployee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyEmployee entity.
 */
@SuppressWarnings("unused")
public interface MyEmployeeRepository extends JpaRepository<MyEmployee,Long> {

}
