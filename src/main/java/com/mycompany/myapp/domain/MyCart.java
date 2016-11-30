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
 * A MyCart.
 */
@Entity
@Table(name = "my_cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MyCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "myCart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MyProduct> containsprods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MyProduct> getContainsprods() {
        return containsprods;
    }

    public MyCart containsprods(Set<MyProduct> myProducts) {
        this.containsprods = myProducts;
        return this;
    }

    public MyCart addContainsprod(MyProduct myProduct) {
        containsprods.add(myProduct);
        myProduct.setMyCart(this);
        return this;
    }

    public MyCart removeContainsprod(MyProduct myProduct) {
        containsprods.remove(myProduct);
        myProduct.setMyCart(null);
        return this;
    }

    public void setContainsprods(Set<MyProduct> myProducts) {
        this.containsprods = myProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyCart myCart = (MyCart) o;
        if(myCart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, myCart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MyCart{" +
            "id=" + id +
            '}';
    }
}
