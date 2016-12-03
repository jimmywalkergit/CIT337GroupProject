package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Orderproduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Orderproduct entity.
 */
@SuppressWarnings("unused")
public interface OrderproductRepository extends JpaRepository<Orderproduct,Long> {

}
