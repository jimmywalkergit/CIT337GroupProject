package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyCustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyCustomer entity.
 */
@SuppressWarnings("unused")
public interface MyCustomerRepository extends JpaRepository<MyCustomer,Long> {

}
