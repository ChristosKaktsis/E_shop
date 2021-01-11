package com.example.e_shop;

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
public class SeeItemsFragment extends Fragment {



    public SeeItemsFragment() {
        // Required empty public constructor
    }

    //
    //Μεταβλητες για την δημιουργια Listview
    //
    ListView listView;
    ListAdapter listAdapter;
    List<Items> allItems;//Λιστα με items που αγορασε ο χρηστης

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_items, container, false);

        try {
            //
            //Γεμιζουμε τη λιστα με τα προιοντα που εχει αγορασει ο χρηστης που ειναι συνδεδεμενος αυτη τη στιγμη
            //
            allItems = MainActivity.myAppDatabase.myDao().getItemsFromSalesWithCustomerId(MainActivity.logCustomer.getId());
            listView = view.findViewById(R.id.my_list_view);
            //
            //Στελνουμε στον Adapter την λιστα με τα προιοντα και το layout
            //
            listAdapter = new ListAdapter(getActivity(),allItems,R.layout.inventory_item_layout);
            listView.setAdapter(listAdapter);
        } catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }


        return view;
    }
}
