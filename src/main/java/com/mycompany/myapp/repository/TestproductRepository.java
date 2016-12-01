package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Testproduct;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Testproduct entity.
 */
@SuppressWarnings("unused")
public interface TestproductRepository extends JpaRepository<Testproduct,Long> {

}
