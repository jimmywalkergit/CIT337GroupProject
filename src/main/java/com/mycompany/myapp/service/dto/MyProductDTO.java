package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MyProduct entity.
 */
public class MyProductDTO implements Serializable {

    private Long id;

    private String name;

    private Double price;

    private String description;


    private Long myCartId;
    
    private Long myOrdersId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMyCartId() {
        return myCartId;
    }

    public void setMyCartId(Long myCartId) {
        this.myCartId = myCartId;
    }

    public Long getMyOrdersId() {
        return myOrdersId;
    }

    public void setMyOrdersId(Long myOrdersId) {
        this.myOrdersId = myOrdersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyProductDTO myProductDTO = (MyProductDTO) o;

        if ( ! Objects.equals(id, myProductDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyProductDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
