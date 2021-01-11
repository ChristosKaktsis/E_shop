package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SeeCustomersFragment extends Fragment {

    public SeeCustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_customers, container, false);
        String display = "";//Μεσα σε αυτο το String θα εμφανιστουν ολοι οι χρηστες
        try {
            //
            //Παιρνουμε ολους τους Χρηστες απο την βαση
            //και τουσ βαζουμε σε μια λιστα
            //
            List<Customers> allCustomers = MainActivity.myAppDatabase.myDao().getCustomers();
            //
            //Για καθε χρηση βαλε τα στοιχια του στο String
            //
            for (Customers customer :allCustomers){
                display += "Customer Id :"+customer.getId()+"\n"+"Customer Name:"+customer.getName()+"\n"+"Customer Surname:"+customer.getSurname()+"\n"+"Customer Email:"+customer.getEmail()+"\n"+"\n";
            }
            //
            //TextView οπου εμφανιζονται ολοι οι χρηστες
            //
            TextView textView = view.findViewById(R.id.customerDisplayText);
            textView.setText(display);
        } catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return view;
    }
}
