package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyProduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyProduct entity.
 */
@SuppressWarnings("unused")
public interface MyProductRepository extends JpaRepository<MyProduct,Long> {

}
