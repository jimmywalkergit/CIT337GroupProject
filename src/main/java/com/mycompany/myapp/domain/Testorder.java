package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Testorder.
 */
@Entity
@Table(name = "testorder")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Testorder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "testorder_ownsorder",
               joinColumns = @JoinColumn(name="testorders_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="ownsorders_id", referencedColumnName="ID"))
    private Set<Testproduct> ownsorders = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Testproduct> getOwnsorders() {
        return ownsorders;
    }

    public Testorder ownsorders(Set<Testproduct> testproducts) {
        this.ownsorders = testproducts;
        return this;
    }

    public Testorder addOwnsorder(Testproduct testproduct) {
        ownsorders.add(testproduct);
        return this;
    }

    public Testorder removeOwnsorder(Testproduct testproduct) {
        ownsorders.remove(testproduct);
        return this;
    }

    public void setOwnsorders(Set<Testproduct> testproducts) {
        this.ownsorders = testproducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Testorder testorder = (Testorder) o;
        if(testorder.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, testorder.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Testorder{" +
            "id=" + id +
            '}';
    }
}
