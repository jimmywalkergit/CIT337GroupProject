package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyCart;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyCart entity.
 */
@SuppressWarnings("unused")
public interface MyCartRepository extends JpaRepository<MyCart,Long> {

}
