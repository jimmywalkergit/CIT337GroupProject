package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Ordercustomer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Ordercustomer entity.
 */
@SuppressWarnings("unused")
public interface OrdercustomerRepository extends JpaRepository<Ordercustomer,Long> {

}
