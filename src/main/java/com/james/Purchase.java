package com.james;

import javax.persistence.*;

/**
 * Created by jamesyburr on 6/22/16.
 */
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String category;

    @Column(nullable = false)
    String creditCard;

    @Column(nullable = false)
    String cvv;

    @Column(nullable = false)
    String date;

    @ManyToOne
    Customer customer;

    public Purchase() {
    }

    public Purchase(String category, String creditCard, String cvv, String date, Customer customer) {
        this.category = category;
        this.creditCard = creditCard;
        this.cvv = cvv;
        this.date = date;
        this.customer = customer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
