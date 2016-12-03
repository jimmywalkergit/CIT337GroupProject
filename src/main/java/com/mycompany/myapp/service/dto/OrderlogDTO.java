package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Orderlog entity.
 */
public class OrderlogDTO implements Serializable {

    private Long id;


    private Set<OrderproductDTO> hasproducts = new HashSet<>();

    private Set<OrdercustomerDTO> hascustomers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<OrderproductDTO> getHasproducts() {
        return hasproducts;
    }

    public void setHasproducts(Set<OrderproductDTO> orderproducts) {
        this.hasproducts = orderproducts;
    }

    public Set<OrdercustomerDTO> getHascustomers() {
        return hascustomers;
    }

    public void setHascustomers(Set<OrdercustomerDTO> ordercustomers) {
        this.hascustomers = ordercustomers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderlogDTO orderlogDTO = (OrderlogDTO) o;

        if ( ! Objects.equals(id, orderlogDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrderlogDTO{" +
            "id=" + id +
            '}';
    }
}
