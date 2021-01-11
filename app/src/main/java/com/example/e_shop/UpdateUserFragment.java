package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserFragment extends Fragment {

    public UpdateUserFragment() {
        // Required empty public constructor
    }

    TextInputLayout textInputLayout_1 , textInputLayout_2 , textInputLayout_3 , textInputLayout_4;
    TextInputEditText textName , textSurname , textEmail , textPassword;
    Button submit ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_user, container, false);

        textInputLayout_1 = view.findViewById(R.id.textInputLayout_1);
        textInputLayout_2 = view.findViewById(R.id.textInputLayout_2);
        textInputLayout_3 = view.findViewById(R.id.textInputLayout_3);
        textInputLayout_4 = view.findViewById(R.id.textInputLayout_4);

        //
        //Βρισκουμε τα πεδια απο το View με το id τους
        //
        textName = view.findViewById(R.id.textInputName);
        textSurname = view.findViewById(R.id.textInputSurname);
        textEmail = view.findViewById(R.id.textInputEmail);
        textPassword = view.findViewById(R.id.textInputPassword);

        //
        //Εμφανιζουμε τα στιχεια του χρηστη στο View
        //
        textName.setText(MainActivity.logCustomer.getName());
        textSurname.setText(MainActivity.logCustomer.getSurname());
        textEmail.setText(MainActivity.logCustomer.getEmail());
        textPassword.setText(MainActivity.logCustomer.getPassword());

        //
        //Οταν γινεται κλικ στο κουμπι εκτελειται η μεθοδος εγγραφης
        //
        submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        return view;
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
    private void submitForm(){

        //
        //Ελεγχος πεδιων αμα ειναι κενα
        //
        if (!validate(textInputLayout_1,textName) | !validate(textInputLayout_2,textSurname) | !validate(textInputLayout_3,textEmail) | !validate(textInputLayout_4,textPassword)){
            return;
        }

        //
        //Δηλωση μεταβλητων για την εισαγογη στιχειων του χρηστη
        //
        String name = textName.getText().toString();//Ονομα του χρηστη που θα μπει στην βαση
        String surname = textSurname.getText().toString();//Επιθετο του χρηστη που θα μπει στην βαση
        String email = textEmail.getText().toString();//Email του χρηστη που θα μπει στην βαση
        String password = textPassword.getText().toString();//password του χρηστη που θα μπει στην βαση

        try {
            Customers customers = new Customers();//Δημιουργια αντικειμενου Customer
            customers.setName(name);//Εισαγογη ονοματος
            customers.setSurname(surname);//Εισαγογη Επιθετου
            customers.setEmail(email);//Εισαγογη Email
            customers.setPassword(password);//Εισαγογη Password

            //
            //Ελεγχος αμα υπαρχει ιδη χρηστης με ιδιο Email kai password
            //Αμα βρει Customer Στην βαση που ΔΕΝ εχει Ιδιο Id(δηλαδη Δεν ειναι ο εαυτος του)Και εχει ιδιο Email k Password
            //Σημαινει οτι υπαρχει αλλος χρηστης με υπαρχοντα στιχεια και δεν μπορει να γινει εγγραφη
            //
            if(MainActivity.myAppDatabase.myDao().getCustomersWithEmailOrPassword(email,password).getId()!=MainActivity.logCustomer.getId()){
                Toast.makeText(getActivity(),"EMAIL OR PASSWORD ALREADY EXIST",Toast.LENGTH_LONG).show();
                return;
            }
            customers.setId(MainActivity.logCustomer.getId());//Εισαγογη ID του χρηστη για να κανει update

            MainActivity.myAppDatabase.myDao().UpdateCustomers(customers);//Update τον χρηστη


            //
            //Μηνιμα οτι εγινε το update
            //
            Toast.makeText(getActivity(),"Changed Your Info",Toast.LENGTH_LONG).show();

            MainActivity.logCustomer = customers; //Βαλε τον χρηστη στην Static μεταβλητη

            //
            //Βγαζει το Update Fragment απο την στιβα με τα Fragments για να γιρισει στο αρχικο μενου
            //
            getActivity().getSupportFragmentManager().popBackStackImmediate();

        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(getActivity(),"SOMETHING WRONG"+"\n"+message,Toast.LENGTH_LONG).show();
        }

    }
}
