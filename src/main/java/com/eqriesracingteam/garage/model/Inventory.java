package com.eqriesracingteam.garage.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "auto_onderdelen")
public class Inventory {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String itemDescription;
    private BigDecimal price;
    private int stock;
    private int usedParts;

    // TODO: 5-1-2022 many to many relation
    @JsonIgnoreProperties("items")
    @ManyToOne
    @JoinColumn(name = "repair_id", referencedColumnName = "id")
    private Repair item;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUsedParts() {
        return usedParts;
    }

    public void setUsedParts(int usedParts) {
        this.usedParts = usedParts;
    }

    public Repair getItem() {
        return item;
    }

    public void setItem(Repair item) {
        this.item = item;
    }
}
