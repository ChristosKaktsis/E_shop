package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements View.OnClickListener {

    public CartFragment() {
        // Required empty public constructor
    }

    TextView sumText;//Εχει την συνολικη τιμη των προιοντον στο καλαθι
    Button button ;//Κουμπι αγορας

    ListView listView;//ορισμος λιστας για να τα εμφανισει τα προιοντα
    ListAdapter listAdapter;
    //
    //Ορισμος λιστασ καλαθιου
    //Ειναι Static για να μπορει να γεμιζει απο τo SingleItemFragment
    //Και να παραμενουν τα προιοντα μεχρι να τα αγορασει
    //
    public static List<Items> cartitemList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        sumText = view.findViewById(R.id.sum);
        button = view.findViewById(R.id.commitButton);
        button.setOnClickListener(this);

        listView = view.findViewById(R.id.my_list_view);
        listAdapter = new ListAdapter(getActivity(),cartitemList,R.layout.item_layout);
        listView.setAdapter(listAdapter);

        //
        //Οταν γινει κλικ πανω σε ενα προιον  να διχνει την ποσοτητα του συγκεκριμενου αντικειμενο στο καλαθι
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Items item = (Items)listView.getItemAtPosition(position);
               Toast.makeText(getActivity(),"QUANTITY "+item.getStock(), Toast.LENGTH_LONG).show();

            }
        });

        getSum();//Οριζει το συνολο των τιμων στο καλαθι

        return view;
    }
    //
    //Υπολογισμος και εμφανιση συνολικου ποσου
    //
    public void getSum(){
        double sum = 0.0;//Μετραει το συνολικο ποσο
        //
        //Για καθε προιον στο καλαθι προσθετει την τιμη στο Sum
        //
        for(Items item:cartitemList){
            sum+=item.getPrice()*item.getStock();
        }
        String sumRound = String.format("%.2f",sum);//Βαζει σε String το sum και το δειχνει μεχρι 2 δεκαδικα ψηφια
        sumText.setText(sumRound+" €");//Εμφανιση SUM στο textView
    }

    @Override
    public void onClick(View v) {
        Date currentTime = Calendar.getInstance().getTime();//Μεταβλητη με την ημερομινια αγορας
        String date = currentTime.toString();//τοποθετιση ημερομινιας σε String
        //
        //Για καθε προιον στο καλαθι θα γινει μια Πωληση με Item Id και Customer Id που κανει την αγορα
        //
        for (Items item :cartitemList){
            try {
                Sales sale = new Sales();//Δημιουργεια αντικειμενου Πωλισεων
                sale.setItem_id(item.getId());//εισαγογη Item Id
                sale.setCustomer_id(MainActivity.logCustomer.getId());//Εισαγογη Customer Id που ειναι συνδεδεμενος στο συστιμα
                sale.setSale_day(date);//Εισαγογη ημερομινιας
                sale.setSale_quantity(item.getStock());//Εισαγογη ποσοτητας του συγκεκριμενου προιον (Ποσες φορες ειναι στο ΚΑΛΑΘΙ)
                //
                //Ευρεση Πολισεων που υπαρχουν στην βαση
                //Αμα υπαρχει ιδη τοτε θα γινει Update αλλιως θα την προσθεση στη βαση
                //
                Sales oldSale = MainActivity.myAppDatabase.myDao().getSaleWithId(item.getId(),MainActivity.logCustomer.getId());
                if(oldSale==null){
                    MainActivity.myAppDatabase.myDao().addSale(sale);
                }else {
                    //
                    //Εφοσον υπαρχει προσθεσε την ποσοτητα του προιον στην παλια αγορα
                    //
                    sale.setSale_quantity(item.getStock()+oldSale.getSale_quantity());
                    MainActivity.myAppDatabase.myDao().UpdateSales(sale);
                }
                //
                //Ενημεροση αποθεματος του προιον που εγινε η αγορα
                //
                updateItemWhenBought(item);

                //
                //μηνιμα οτι εγινε η αγορα
                //
               Toast.makeText(getActivity(),"You Bought THe Items", Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                String message = e.getMessage();
                Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
            }
        }

        cartitemList.clear();//Αδιασμα λιστας καλαθιου εφοσον εχει γινει η αγορα
    }
    public void updateItemWhenBought(Items item){
        Items oldItem = MainActivity.myAppDatabase.myDao().getItemId(item.getId());//Ευρεση προιοντος με το id
        Items newItem = new Items();//Δημιουργια καινουργιου Item για να γινει update
        newItem.setId(item.getId());//Εισαγογη Id του προιον που θα γινει αγορα
        newItem.setName(item.getName());//Εισαγογη ονοματος του προιον που θα γινει αγορα
        newItem.setPrice(item.getPrice());//Εισαγογη τιμης του προιον που θα γινει αγορα
        newItem.setCategory(item.getCategory());//Εισαγογη κατηγοριας του προιον που θα γινει αγορα
        newItem.setStock(oldItem.getStock()-item.getStock());//Αφαιρεση ποσοτητας αγορας απο το αποθεμα στην βαση
        newItem.setTimesSold(oldItem.getTimesSold()+item.getStock());//Προσθεσε ποσες φορες εχει αγοραστει το προιον γιατι το εμφανιζουμε στο SeeSales Fragment
        MainActivity.myAppDatabase.myDao().UpdateItem(newItem);//Ενημεροσε το Item
    }
}
