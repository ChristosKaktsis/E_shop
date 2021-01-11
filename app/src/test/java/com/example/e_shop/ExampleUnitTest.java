package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Entity(tableName = "sales",
            primaryKeys = {"sales_item_id","sales_customer_id","sales_date"},
            foreignKeys = {
            @ForeignKey(entity = Items.class,
                parentColumns = "item_id",
                childColumns = "sales_item_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
            @ForeignKey(entity = Customers.class,
                parentColumns = "customer_id",
                childColumns = "sales_customer_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)
    })
    public static class Sales {
        @ColumnInfo(name = "sales_customer_id")@NonNull
        private int sale_customer_id;

        @ColumnInfo(name = "sales_item_id")@NonNull
        private int sale_item_id;

        @ColumnInfo(name = "sales_date")@NonNull
        private String sale_day;

        @ColumnInfo(name = "sales_quantity")
        private int sale_quantity;

        public int getSale_customer_id() {
            return sale_customer_id;
        }

        public void setSale_customer_id(int sale_customer_id) {
            this.sale_customer_id = sale_customer_id;
        }

        public int getSale_item_id() {
            return sale_item_id;
        }

        public void setSale_item_id(int sale_item_id) {
            this.sale_item_id = sale_item_id;
        }

        public String getSale_day() {
            return sale_day;
        }

        public void setSale_day(String sale_day) {
            this.sale_day = sale_day;
        }

        public int getSale_quantity() {
            return sale_quantity;
        }

        public void setSale_quantity(int sale_quantity) {
            this.sale_quantity = sale_quantity;
        }
    }
}