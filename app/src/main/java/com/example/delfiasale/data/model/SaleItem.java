package com.example.delfiasale.data.model;

import com.google.gson.annotations.SerializedName;

public class SaleItem {

    @SerializedName("barcode")
    private String barcode;

    @SerializedName("quantity")
    private int quantity;

    @SerializedName("price")
    private double price;

    public SaleItem(String barcode, int quantity, double price) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
