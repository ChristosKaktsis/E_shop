package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "sales",
        primaryKeys = {"sales_items_id","sales_customers_id"},
        foreignKeys = {
                @ForeignKey(entity = Items.class,
                        parentColumns = "item_id",
                        childColumns = "sales_items_id",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Customers.class,
                        parentColumns = "customer_id",
                        childColumns = "sales_customers_id",
                        onUpdate = ForeignKey.CASCADE,
                        onDelete = ForeignKey.CASCADE)})
public class Sales {

    @ColumnInfo(name = "sales_items_id")@NonNull
    private int item_id;

    @ColumnInfo(name = "sales_customers_id")@NonNull
    private int customer_id;

    @ColumnInfo(name = "sales_date")
    private String sale_day;

    @ColumnInfo(name = "sales_quantity")
    private int sale_quantity;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @NonNull
    public String getSale_day() {
        return sale_day;
    }

    public void setSale_day(@NonNull String sale_day) {
        this.sale_day = sale_day;
    }

    public int getSale_quantity() {
        return sale_quantity;
    }

    public void setSale_quantity(int sale_quantity) {
        this.sale_quantity = sale_quantity;
    }
}
