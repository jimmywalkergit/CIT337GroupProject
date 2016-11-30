package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MyOrders.
 */
@Entity
@Table(name = "my_orders")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private MyCustomer myCustomer;

    @OneToMany(mappedBy = "myOrders")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MyProduct> containsorderprods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyCustomer getMyCustomer() {
        return myCustomer;
    }

    public MyOrders myCustomer(MyCustomer myCustomer) {
        this.myCustomer = myCustomer;
        return this;
    }

    public void setMyCustomer(MyCustomer myCustomer) {
        this.myCustomer = myCustomer;
    }

    public Set<MyProduct> getContainsorderprods() {
        return containsorderprods;
    }

    public MyOrders containsorderprods(Set<MyProduct> myProducts) {
        this.containsorderprods = myProducts;
        return this;
    }

    public MyOrders addContainsorderprod(MyProduct myProduct) {
        containsorderprods.add(myProduct);
        myProduct.setMyOrders(this);
        return this;
    }

    public MyOrders removeContainsorderprod(MyProduct myProduct) {
        containsorderprods.remove(myProduct);
        myProduct.setMyOrders(null);
        return this;
    }

    public void setContainsorderprods(Set<MyProduct> myProducts) {
        this.containsorderprods = myProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyOrders myOrders = (MyOrders) o;
        if(myOrders.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myOrders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyOrders{" +
            "id=" + id +
            '}';
    }
}
