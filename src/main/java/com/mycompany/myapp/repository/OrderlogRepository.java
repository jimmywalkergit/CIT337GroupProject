package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Orderlog;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Orderlog entity.
 */
@SuppressWarnings("unused")
public interface OrderlogRepository extends JpaRepository<Orderlog,Long> {

    @Query("select distinct orderlog from Orderlog orderlog left join fetch orderlog.hasproducts left join fetch orderlog.hascustomers")
    List<Orderlog> findAllWithEagerRelationships();

    @Query("select orderlog from Orderlog orderlog left join fetch orderlog.hasproducts left join fetch orderlog.hascustomers where orderlog.id =:id")
    Orderlog findOneWithEagerRelationships(@Param("id") Long id);

}
