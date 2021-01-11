package com.example.e_shop;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class Customers {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "customer_id")
    private int id;

    @ColumnInfo(name = "customer_name")
    private String name;

    @ColumnInfo(name = "customer_surname")
    private String surname;

    @ColumnInfo(name = "customer_email")
    private String email;

    @ColumnInfo(name = "customer_password")
    private String password;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
