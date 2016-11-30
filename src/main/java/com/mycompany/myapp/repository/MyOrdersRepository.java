package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MyOrders;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MyOrders entity.
 */
@SuppressWarnings("unused")
public interface MyOrdersRepository extends JpaRepository<MyOrders,Long> {

}
