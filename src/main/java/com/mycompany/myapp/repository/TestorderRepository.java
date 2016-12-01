package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Testorder;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Testorder entity.
 */
@SuppressWarnings("unused")
public interface TestorderRepository extends JpaRepository<Testorder,Long> {

    @Query("select distinct testorder from Testorder testorder left join fetch testorder.ownsorders")
    List<Testorder> findAllWithEagerRelationships();

    @Query("select testorder from Testorder testorder left join fetch testorder.ownsorders where testorder.id =:id")
    Testorder findOneWithEagerRelationships(@Param("id") Long id);

}
