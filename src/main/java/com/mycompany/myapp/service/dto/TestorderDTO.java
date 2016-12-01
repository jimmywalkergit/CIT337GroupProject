package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Testorder entity.
 */
public class TestorderDTO implements Serializable {

    private Long id;


    private Set<TestproductDTO> ownsorders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TestproductDTO> getOwnsorders() {
        return ownsorders;
    }

    public void setOwnsorders(Set<TestproductDTO> testproducts) {
        this.ownsorders = testproducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestorderDTO testorderDTO = (TestorderDTO) o;

        if ( ! Objects.equals(id, testorderDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestorderDTO{" +
            "id=" + id +
            '}';
    }
}
