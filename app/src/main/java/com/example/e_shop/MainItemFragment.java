package com.example.e_shop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainItemFragment extends Fragment {

    //
    //Δηλωση μεταβλιτων για την εμφανιση στη Λιστα
    //
    ListView listView;
    ListAdapter listAdapter;
    List<Items> itemList = new ArrayList<>();

    Items item = new Items();//Δηλωση Item για οταν γινει κλικ πανω σε ενα αντικειμενο λιστας



    public MainItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_item, container, false);
        //
        //Ελεγχος για το αμα η λιστα ειναι αδια να εμφανισει τα Items με βαση το Category που το παιρνει απο το MainActivity
        //
        if(itemList.isEmpty()){
            Bundle bundle = getArguments();
            putItemsInList(bundle.getInt("category"));
        }

        //
        //Δημιουργία Λίστας με το ListAdapter
        //
        listView = view.findViewById(R.id.my_list_view);
        listAdapter = new ListAdapter(getActivity(),itemList, R.layout.item_layout);//Δεχεται το Context ,Μια Items λιστα και το Id του layout
        listView.setAdapter(listAdapter);

        //
        //Μεθοδος για κλικ σε ενα αντικειμενο να ανοιξει το SingleItem Fragment
        //
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Items)listView.getItemAtPosition(position);
                //
                //Περναμε τα στιχεια του Item στο SingleItemFragment μεσο μιας Static μεθοδου η οποια χειριζεται καλητερα τα Bundle
                //
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container, SingleItemFragment.newInstance(item.getId(),item.getName(),item.getPrice(),item.getStock(),item.getCategory()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    //
    //Μεθοδος Που Βάζει τα Items στη λιστα αναλογα με την κατηγορια που επιλεγεται απο το MainActivity
    //
    void putItemsInList(int category){

        switch (category){
            case 0:
                itemList = MainActivity.myAppDatabase.myDao().getAllItems();
                break;
            case 1:
                itemList = MainActivity.myAppDatabase.myDao().getGPUItems();
                break;
            case 2:
                itemList = MainActivity.myAppDatabase.myDao().getMotherBoardItems();
                break;
            case 3:
                itemList = MainActivity.myAppDatabase.myDao().getCPUItems();
                break;
            case 4:
                itemList = MainActivity.myAppDatabase.myDao().getPSUItems();
                break;
            case 5:
                itemList = MainActivity.myAppDatabase.myDao().getRAMItems();
                break;
            case 6:
                itemList = MainActivity.myAppDatabase.myDao().getCaseItems();
                break;
        }
    }




}
