package com.example.delfiasale.ui.model;

public class ReceiptLine {

    private final String name;
    private final String barcode;
    private final double unitPrice;
    private int quantity = 1;

    public ReceiptLine(String name, String barcode, double unitPrice) {
        this.name = name;
        this.barcode = barcode;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity += 1;
    }

    public double getTotal() {
        return quantity * unitPrice;
    }
}
