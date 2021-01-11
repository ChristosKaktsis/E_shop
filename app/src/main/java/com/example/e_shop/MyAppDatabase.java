package com.example.e_shop;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Items.class , Customers.class , Sales.class}, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
