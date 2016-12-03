package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Ordercustomer entity.
 */
public class OrdercustomerDTO implements Serializable {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;


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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrdercustomerDTO ordercustomerDTO = (OrdercustomerDTO) o;

        if ( ! Objects.equals(id, ordercustomerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OrdercustomerDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
