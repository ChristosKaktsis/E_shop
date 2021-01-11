package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends Fragment implements View.OnClickListener {

    public LogInFragment() {
        // Required empty public constructor
    }

    Button logInButton;//κουμπι οπου γίνεται το logIn
    TextInputLayout textInputLayout1 , textInputLayout2 ;//Δήλωση πεδίων
    TextInputEditText emailText , passwordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        logInButton = view.findViewById(R.id.logInButton);
        emailText = view.findViewById(R.id.textEmail);//Πεδιο Εισαγογης Email
        passwordText = view.findViewById(R.id.textPassword);//Πεδιο εισαγογης κοδικου
        //Layouts Με τα οποια θα μπορουμε να εμφανιζουμε τυχον λαθη στα πεδια εισαγογης
        textInputLayout1 = view.findViewById(R.id.textInputLayout1);//Layout for email
        textInputLayout2 = view.findViewById(R.id.textInputLayout2);//Layout for password

        logInButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //
        //Ελεγχος αμα το πεδιο εισαγογης ειναι αδιο
        //Δινουμε στην μεθοδο το Layout και το textView
        //
        if (!validate(textInputLayout1,emailText)||!validate(textInputLayout2,passwordText)){
            return;
        }
        //
        //Παιρνουμε τις τιμες απο τα Textview
        //
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        try {
            //
            //Παιρνουμε τον πελατη απο την βαση με τα στοιχια Email , Password
            //
            Customers customers = MainActivity.myAppDatabase.myDao().getCustomersWithEmailAndPassword(email,password);
            //
            //αμα δεν επιστρεψει τιποτα η μεθοδος δεν υπαρχει ο χρηστης
            //
            if(customers==null){
                Toast.makeText(getActivity(),"NOT A USER",Toast.LENGTH_LONG).show();
                return;
            }
            //Μηνιμα οτι εγινε η εισοδος Εμφανισε το οναμα του
            Toast.makeText(getActivity(),customers.getName()+"\n"+customers.getSurname()+"\n"+email,Toast.LENGTH_LONG).show();
            MainActivity.isConnected = true;//Μεταβλητη για να βλεπει το συστιμα οτι ειναι συνδεδεμενος ο χρηστης
            MainActivity.logCustomer = customers;//Περασμα του χρηστη στην Static μεταβλιτη για να μπορει να το χειριζεται το συστημα
            //
            //Ανοιγμα του Profile Fragment μετα την εισοδο του χρηστη
            //
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment(),null).addToBackStack(null).commit();

        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(getActivity(),"SOMETHING WRONG"+"\n"+message,Toast.LENGTH_LONG).show();
        }
    }
    //
    //Ελεγχει αμα ειναι αδια τα πεδια εισαγογης και εμφανιζει καταληλο μηνιμα
    //και επιστρεφει true h false σε καθε περιπτοση
    //
    private boolean validate(TextInputLayout textLayout, TextInputEditText textEdit){
        if(textEdit.getText().toString().trim().isEmpty()) {
            textLayout.setError("Enter "+textEdit.getHint().toString());
            return false;
        } else {
            textLayout.setErrorEnabled(false);
        }
        return true;
    }
}
