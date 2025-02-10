package com.example.cumparaturi;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 7141928884080910006L; // Same as the stream classdesc serialVersionUID

    private int id;
    private String marca; // Brand
    private String nume; // Name
    private int cantitate; // Quantity

    // Constructor
    public Product(int id, String marca, String nume, int cantitate) {
        if (id <= 0 || marca == null || marca.trim().isEmpty() || nume == null || nume.trim().isEmpty() || cantitate < 0) {
            throw new IllegalArgumentException("Invalid product details.");
        }
        this.id = id;
        this.marca = marca;
        this.nume = nume;
        this.cantitate = cantitate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getNume() {
        return nume;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    // Override toString for better readability in ListView
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", nume='" + nume + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}