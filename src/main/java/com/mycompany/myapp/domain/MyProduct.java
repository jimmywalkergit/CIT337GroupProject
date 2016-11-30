package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MyProduct.
 */
@Entity
@Table(name = "my_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private MyCart myCart;

    @ManyToOne
    private MyOrders myOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public MyProduct name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public MyProduct price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public MyProduct description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyCart getMyCart() {
        return myCart;
    }

    public MyProduct myCart(MyCart myCart) {
        this.myCart = myCart;
        return this;
    }

    public void setMyCart(MyCart myCart) {
        this.myCart = myCart;
    }

    public MyOrders getMyOrders() {
        return myOrders;
    }

    public MyProduct myOrders(MyOrders myOrders) {
        this.myOrders = myOrders;
        return this;
    }

    public void setMyOrders(MyOrders myOrders) {
        this.myOrders = myOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyProduct myProduct = (MyProduct) o;
        if(myProduct.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyProduct{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", price='" + price + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
