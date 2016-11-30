package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MyCustomer.
 */
@Entity
@Table(name = "my_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(unique = true)
    private MyCart ownscart;

    @OneToMany(mappedBy = "myCustomer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MyOrders> ownsorders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MyCustomer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public MyCustomer phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public MyCustomer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public MyCustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MyCart getOwnscart() {
        return ownscart;
    }

    public MyCustomer ownscart(MyCart myCart) {
        this.ownscart = myCart;
        return this;
    }

    public void setOwnscart(MyCart myCart) {
        this.ownscart = myCart;
    }

    public Set<MyOrders> getOwnsorders() {
        return ownsorders;
    }

    public MyCustomer ownsorders(Set<MyOrders> myOrders) {
        this.ownsorders = myOrders;
        return this;
    }

    public MyCustomer addOwnsorder(MyOrders myOrders) {
        ownsorders.add(myOrders);
        myOrders.setMyCustomer(this);
        return this;
    }

    public MyCustomer removeOwnsorder(MyOrders myOrders) {
        ownsorders.remove(myOrders);
        myOrders.setMyCustomer(null);
        return this;
    }

    public void setOwnsorders(Set<MyOrders> myOrders) {
        this.ownsorders = myOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyCustomer myCustomer = (MyCustomer) o;
        if(myCustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myCustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyCustomer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            '}';
    }
}
