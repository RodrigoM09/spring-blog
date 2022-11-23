package com.codeup.springblog.models;
//This is a POJO: Plain Old Java Object

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String roast;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false, length = 100)
    private String brand;

    @ManyToOne
    @JoinColumn(name="supplier_id")
    private Supplier supplier;

    public Coffee() {
    }

    public Coffee(String brand) {
        this.brand = brand;
    }

    public Coffee(String roast, String brand) {
        this.roast = roast;
        this.brand = brand;
    }

    public Coffee(String roast, String origin, String brand) {
        this.roast = roast;
        this.origin = origin;
        this.brand = brand;
    }

    public String getRoast() {
        return roast;
    }

    public void setRoast(String roast) {
        this.roast = roast;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
