package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MyCart entity.
 */
public class MyCartDTO implements Serializable {

    private Long id;

    private String myProductId;

    public String getMyProductId() {
        return myProductId;
    }

    public void setMyProductId(String myProductId) {
        this.myProductId = myProductId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyCartDTO myCartDTO = (MyCartDTO) o;

        if ( ! Objects.equals(id, myCartDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyCartDTO{" +
            "id=" + id +
            '}';
    }
}
