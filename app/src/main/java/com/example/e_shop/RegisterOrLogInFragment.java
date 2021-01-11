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
public class RegisterOrLogInFragment extends Fragment implements View.OnClickListener {

    public RegisterOrLogInFragment() {
        // Required empty public constructor
    }
    //
    //Δήλωση κουμπιων
    //
    Button registerBtn ,logInBtn , addItemButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_or_log_in, container, false);


        registerBtn = view.findViewById(R.id.registerButton);//κουμπι για να ανοιξει το Register Fragment
        logInBtn = view.findViewById(R.id.logInButton);//κουμπι για να ανοιξει το LogIn fragment

        //
        //OnClick Event για τα κουμπια
        //
        registerBtn.setOnClickListener(this);
        logInBtn.setOnClickListener(this);

        //
        //Κουμπι για να ανοιξει το AddItem Fragment
        //
        addItemButton = view.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logInButton ://Ανοιγει την φορμα logIn Χρηστη
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LogInFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.registerButton ://Ανοιγει την φορμα Register Χρηστη
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RegisterFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.addItemButton :////Ανοιγει την φορμα που προσθετει προιόντα
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddItemFragment(),null).addToBackStack(null).commit();
                break;
        }
    }
}
