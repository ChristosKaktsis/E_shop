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
public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    TextInputLayout   textInputLayout_1 , textInputLayout_2 , textInputLayout_3 , textInputLayout_4;//Layout Για τα πεδια εισαγογης
    TextInputEditText  textName , textSurname , textEmail , textPassword;//Πεδια εισαγογης Στιχειων Χρηστη
    Button submit ;//κουμι οπου γινεται η εγγραφη

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        textInputLayout_1 = view.findViewById(R.id.textInputLayout_1);//Layout για το ονομα
        textInputLayout_2 = view.findViewById(R.id.textInputLayout_2);//Layout για το επιθετο
        textInputLayout_3 = view.findViewById(R.id.textInputLayout_3);//Layout για το Email
        textInputLayout_4 = view.findViewById(R.id.textInputLayout_4);//Layout για το Password
        //
        //Βρισκουμε τα πεδια απο το View με το id τους
        //
        textName = view.findViewById(R.id.textInputName);
        textSurname = view.findViewById(R.id.textInputSurname);
        textEmail = view.findViewById(R.id.textInputEmail);
        textPassword = view.findViewById(R.id.textInputPassword);

        submit = view.findViewById(R.id.submitButton);
        //
        //Οταν γινεται κλικ στο κουμπι εκτελειται η μεθοδος εγγραφης
        //
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

    //
    //Δημιουργει απο τα πεδια που εισαγει ο χρηστης εναν Customer Στην βαση
    //
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
            //Αμα η μεθοδος επιστρεψει customer τοτε σημαινει οτι υαπρχει ιδι χρηστης με αυτα τα στιχεια
            //
            if(MainActivity.myAppDatabase.myDao().getCustomersWithEmailOrPassword(email,password)!=null){
                Toast.makeText(getActivity(),"EMAIL OR PASSWORD ALREADY EXIST",Toast.LENGTH_LONG).show();
                return;
            }

            //Βαλε τον χρηστη στην βαση
            MainActivity.myAppDatabase.myDao().addCustomer(customers);
            //Βαλε τον χρηστη στην Static μεταβλητη
            MainActivity.logCustomer = MainActivity.myAppDatabase.myDao().getCustomersWithEmailAndPassword(email,password);

            //
            //Εμφανισε τα στιχεια στην οθονη
            //
            Toast.makeText(getActivity(),name+"\n"+surname+"\n"+email,Toast.LENGTH_LONG).show();
            MainActivity.isConnected = true;//Ενημερωσε το συστημα οτι ειναι συνδεδεμενος ο χρηστης μετα την εγραφη
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment(),null).addToBackStack(null).commit();

        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(getActivity(),"SOMETHING WRONG"+"\n"+message,Toast.LENGTH_LONG).show();
        }

    }

}
