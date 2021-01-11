package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    public ProfileFragment() {
        // Required empty public constructor
    }

    //
    //Δηλωση κουμπιων
    //το καθε ενα ανοιγει το καταληλο Fragment
    //
    Button profileButton ,seeItemsButton , seeCustomersButton , seeSalesButton ,addItemButton ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //
        //Το κουμπι πηγαινει στο Update User
        profileButton = view.findViewById(R.id.profileButton);
        profileButton.setOnClickListener(this);
        //
        //Το κουμπι πηγαινει στο SeeItems Fragment για να δει ο χρηστης
        //τα προιοντα που εχει αγορασει
        seeItemsButton = view.findViewById(R.id.seeItemsButton);
        seeItemsButton.setOnClickListener(this);
        //
        //Το κουμπι αυτο σε πηγαινει στο SeeCustomers fragment
        //Εμφανιζει ολους τους χρηστες
        seeCustomersButton = view.findViewById(R.id.seeCustomersButton);
        seeCustomersButton.setOnClickListener(this);
        //
        //Το κουμι αυτο σε πηγαινει στο SeeSales Fragment
        //Εμφανιζει τις πολισεις ανα προιον και το συνολο των πολισεων
        seeSalesButton = view.findViewById(R.id.seeSalesButton);
        seeSalesButton.setOnClickListener(this);

        //
        //Το κουμπι αυτο σε πηγαινει στο AddItem Fragment
        //Προσθετεις ενα προιον στη βαση
        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seeCustomersButton :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SeeCustomersFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.seeItemsButton :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SeeItemsFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.seeSalesButton :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SeeSalesFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.profileButton :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UpdateUserFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.addItemButton :
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddItemFragment(),null).addToBackStack(null).commit();
                break;
        }
    }
}
