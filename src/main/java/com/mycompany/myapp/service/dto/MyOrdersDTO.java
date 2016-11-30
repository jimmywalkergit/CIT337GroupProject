package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MyOrders entity.
 */
public class MyOrdersDTO implements Serializable {

    private Long id;


    private Long myCustomerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMyCustomerId() {
        return myCustomerId;
    }

    public void setMyCustomerId(Long myCustomerId) {
        this.myCustomerId = myCustomerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyOrdersDTO myOrdersDTO = (MyOrdersDTO) o;

        if ( ! Objects.equals(id, myOrdersDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyOrdersDTO{" +
            "id=" + id +
            '}';
    }
}
