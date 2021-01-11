package com.example.e_shop;

import android.icu.util.ValueIterator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeeSalesFragment extends Fragment {

    public SeeSalesFragment() {
        // Required empty public constructor
    }

    //
    //Μεταβλητες για την δημιουργια Listview
    //
    ListView listView;
    ListAdapter listAdapter;
    List<Items> allItems;//Εχει ολα τα προιοντα της Βάσης

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_sales, container, false);

        TextView totalSalesText = view.findViewById(R.id.totalSalesText);//Εδω θα εμφανισει τις συνολικες πολισεις

        try {
            allItems = MainActivity.myAppDatabase.myDao().getAllItems();//Γεμίζουμε τη λιστα με ολα τα προιοντα της βάσης
            listView = view.findViewById(R.id.my_list_view);
            listAdapter = new ListAdapter(getActivity(),allItems,R.layout.item_layout2);
            listView.setAdapter(listAdapter);
        } catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }
        int total=0;//Μετραει τις συνολικες πολισεις
        //
        //Για καθε προιον θα προσθετει στο total το ποσες φορες εχει αγοραστει
        //
        for(Items item :allItems){
            total = total+item.getTimesSold();
        }
        //
        //Εμφανισε στο View Συνολικες πολισεις απο ολα τα ποριοντα
        //
        totalSalesText.setText(total+"");
        return view;
    }
}
