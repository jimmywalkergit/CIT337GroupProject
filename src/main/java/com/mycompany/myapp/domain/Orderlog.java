package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Orderlog.
 */
@Entity
@Table(name = "orderlog")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orderlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "orderlog_hasproducts",
               joinColumns = @JoinColumn(name="orderlogs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="hasproducts_id", referencedColumnName="ID"))
    private Set<Orderproduct> hasproducts = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "orderlog_hascustomers",
               joinColumns = @JoinColumn(name="orderlogs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="hascustomers_id", referencedColumnName="ID"))
    private Set<Ordercustomer> hascustomers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Orderproduct> getHasproducts() {
        return hasproducts;
    }

    public Orderlog hasproducts(Set<Orderproduct> orderproducts) {
        this.hasproducts = orderproducts;
        return this;
    }

    public Orderlog addHasproducts(Orderproduct orderproduct) {
        hasproducts.add(orderproduct);
        return this;
    }

    public Orderlog removeHasproducts(Orderproduct orderproduct) {
        hasproducts.remove(orderproduct);
        return this;
    }

    public void setHasproducts(Set<Orderproduct> orderproducts) {
        this.hasproducts = orderproducts;
    }

    public Set<Ordercustomer> getHascustomers() {
        return hascustomers;
    }

    public Orderlog hascustomers(Set<Ordercustomer> ordercustomers) {
        this.hascustomers = ordercustomers;
        return this;
    }

    public Orderlog addHascustomers(Ordercustomer ordercustomer) {
        hascustomers.add(ordercustomer);
        return this;
    }

    public Orderlog removeHascustomers(Ordercustomer ordercustomer) {
        hascustomers.remove(ordercustomer);
        return this;
    }

    public void setHascustomers(Set<Ordercustomer> ordercustomers) {
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
        Orderlog orderlog = (Orderlog) o;
        if(orderlog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, orderlog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Orderlog{" +
            "id=" + id +
            '}';
    }
}
