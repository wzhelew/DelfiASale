package com.example.delfiasale.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleRequest {

    @SerializedName("items")
    private List<SaleItem> items;

    public SaleRequest(List<SaleItem> items) {
        this.items = items;
    }

    public List<SaleItem> getItems() {
        return items;
    }
}
