package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MyCustomer entity.
 */
public class MyCustomerDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    private String email;

    private String address;


    private Long ownscartId;
    
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
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOwnscartId() {
        return ownscartId;
    }

    public void setOwnscartId(Long myCartId) {
        this.ownscartId = myCartId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyCustomerDTO myCustomerDTO = (MyCustomerDTO) o;

        if ( ! Objects.equals(id, myCustomerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyCustomerDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
