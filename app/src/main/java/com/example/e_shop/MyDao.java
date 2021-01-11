package com.example.e_shop;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    public void addItem(Items items);

    @Insert
    public void addCustomer(Customers customers);

    @Insert
    public void addSale(Sales sales);

    @Query("select * from items")
    public List<Items> getAllItems();

    @Query("select * from items where item_category ='GPU'")
    public List<Items> getGPUItems();

    @Query("select * from items where item_category ='MotherBoard'")
    public List<Items> getMotherBoardItems();

    @Query("select * from items where item_category ='CPU'")
    public List<Items> getCPUItems();

    @Query("select * from items where item_category ='PSU'")
    public List<Items> getPSUItems();

    @Query("select * from items where item_category ='RAM'")
    public List<Items> getRAMItems();

    @Query("select * from items where item_category ='Case'")
    public List<Items> getCaseItems();

    @Query("select * from items where item_id = :id ")
    public Items getItemId(int id);

    @Query("select * from customers")
    public List<Customers> getCustomers();

    @Query("select * from customers where customer_email = :email or customer_password = :password")
    public Customers getCustomersWithEmailOrPassword(String email,String password);

    @Query("select * from customers where customer_email = :email and customer_password = :password")
    public Customers getCustomersWithEmailAndPassword(String email,String password);


    @Query("select * from sales")
    public List<Sales> getSales();

    @Query("select * from sales  where sales_items_id = :items_id")
    public List<Sales> getSalesWithItemsId(int items_id);

    @Query("select * from items Inner Join sales On items.item_id = sales.sales_items_id where sales_customers_id = :customer_id")
    public List<Items> getItemsFromSalesWithCustomerId(int customer_id);

    @Query("select * from sales where sales_items_id = :item_id and sales_customers_id = :customer_id")
    public Sales getSaleWithId(int item_id,int customer_id);

    @Delete
    public void deleteItem(Items items);

    @Delete
    public void deleteCustomers(Customers customers);

    @Delete
    public void deleteSales(Sales sales);

    @Update
    public void UpdateItem(Items items);

    @Update
    public void UpdateCustomers(Customers customers);

    @Update
    public void UpdateSales(Sales sales);
}
