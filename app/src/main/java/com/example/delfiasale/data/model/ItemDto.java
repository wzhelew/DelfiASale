package com.example.delfiasale.data.model;

import com.google.gson.annotations.SerializedName;

public class ItemDto {

    @SerializedName("id")
    private long id;

    @SerializedName("group_id")
    private long groupId;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("barcode")
    private String barcode;

    public long getId() {
        return id;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getBarcode() {
        return barcode;
    }
}
