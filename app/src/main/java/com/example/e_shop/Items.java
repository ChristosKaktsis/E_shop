package com.example.e_shop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Items {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "item_id")
    private int id;

    @ColumnInfo(name = "item_name")
    private String name;

    @ColumnInfo(name = "item_price")
    private double price;

    @ColumnInfo(name = "item_stock")
    private int stock;

    @ColumnInfo(name = "item_category")
    private String category;

    @ColumnInfo(name = "item_timesold")
    private int timesSold = 0;



    public int getTimesSold() {
        return timesSold;
    }

    public void setTimesSold(int timesSold) {
        this.timesSold = timesSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
