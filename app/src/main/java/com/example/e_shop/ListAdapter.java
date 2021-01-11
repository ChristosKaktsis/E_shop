package com.example.e_shop;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Items> {
    List<Items> items;//Λιστα με τα προιοντα που θα εμφανιστουν
    Context context;
    int layout;//Το Id απο το layout ενος προιοντος που θα εμφανιστει στη λιστα

    public ListAdapter(@NonNull Context context, List<Items> items , int layout) {
        super(context, layout, items);
        this.items = items;
        this.context = context;
        this.layout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View row = inflater.inflate(layout, parent, false);
        //
        //Ονομα του Προιοντος
        //Υπαρχει σε ολα τα layout οποτε δεν χρειαζετε ελεγχος
        //
        TextView itemName = row.findViewById(R.id.fragment_item_text);
        itemName.setText(items.get(position).getName());

        //
        //Αναλογα με την περιπτοση καλουμε το list adapter με διαφορετικο Layout
        //Στην αρχηκη σελιδα τα προιοντα εχουν αλλο layout οπου μας λεει αμα το προιον ειναι διαθεσημο
        //Στο seeItems Fragment το layout δειχνει ποσεςς φορες το εχει αγορασει ο χρηστης
        //Στο SeeSales Fragment το layout δειχνει τις πολισεις του προιοντος
        //Κανουμε ελεγχο για ποιο layout στελνουμε στον Adapter
        //Οριζουμε τις αναλογες μεταβλητες
        if ((layout==R.layout.item_layout)){//Αυτο ειναι το layout του MainItemFragment
            TextView itemPrice = row.findViewById(R.id.item_price);//Οριζουμε την τιμη
            TextView itemInfo = row.findViewById(R.id.infoText);//Οριζουμε την πληροφορια αμα ειναι διαθεσημο η οχι

            itemPrice.setText(""+items.get(position).getPrice()+"€");//Βαζουμε την τιμη
            //
            //Αμα η ποσοτητα ειναι 0 εμφανισε στο itemInfo Οτι δεν υπαρχει
            //
            if(items.get(position).getStock()==0){
                itemInfo.setText("NOT IN STOCK");
            }else{
                itemInfo.setText("AVAILABLE");
            }

        }

        if(layout==R.layout.item_layout2){//Αυτο ειναι το layout του SeeSales Fragment
            TextView itemSold = row.findViewById(R.id.item_sold);//Ορισε συνολικες πολισεις για το συγκεκριμενο προιον
            itemSold.setText(items.get(position).getTimesSold()+"");
        }

        if(layout==R.layout.inventory_item_layout){//Αυτο ειναι το layout του SeeItemsFragment
            TextView itemQuantity = row.findViewById(R.id.item_quantity);//Συνολο ποσοτητας που εχει αγορασει ο Χρηστης
            TextView itemid = row.findViewById(R.id.item_Id);//Id του συγκεκριμενου προιοντος
            itemid.setText(items.get(position).getId()+"");
            //
            //Βρες απο τις πολισεις τις αγορες που εχει κανει ο χρηστης για το συγκεκριμενο προιον
            Sales sale = MainActivity.myAppDatabase.myDao().getSaleWithId(items.get(position).getId(),MainActivity.logCustomer.getId());
            itemQuantity.setText(sale.getSale_quantity()+"");//Εμφανισε το συνολο ποσοτητας που εχει αγορασει ο Χρηστης
        }
        return row;
    }
}
