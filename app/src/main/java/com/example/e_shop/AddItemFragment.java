package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public AddItemFragment() {
        // Required empty public constructor
    }

    TextInputLayout textInputLayout_1 , textInputLayout_2 , textInputLayout_3 ;//Δηλωση Layout για τα TextView
    TextInputEditText textName , textPice , textStock;//Δηλωση textView για την εισαγωγη μεταβλητων ενος Item
    Button submit ;//Δηλωση button για την εγγραφη του Item
    String itemCategory;//Δηλωση μεταβλητης οπου παιρνει την κατηγορια ενος Item απο το spinner

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        //
        //create spinner
        //
        Spinner spinner = view.findViewById(R.id.item_category_Spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity() ,R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //
        //Create Input fields
        //
        textInputLayout_1 = view.findViewById(R.id.textInputLayout_1);
        textInputLayout_2 = view.findViewById(R.id.textInputLayout_2);
        textInputLayout_3 = view.findViewById(R.id.textInputLayout_3);

        textName = view.findViewById(R.id.textInputName);
        textPice = view.findViewById(R.id.textInputPrice);
        textStock = view.findViewById(R.id.textInputStock);

        submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //
        //Αναλογα με το που θα γινει κλικ στο spinner θα βαλει την τιμη στην μεταβλητη itemCategory
        //
        itemCategory = parent.getItemAtPosition(position).toString();
    }

    //
    //Αυτη η μεθοδος δεν χρειαζετε ειναι απλα για αλλη λειτουργεια του spinner
    //
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //
    //Ελεγχουμε αμα τα πεδια ειναι αδια και εμφανιζουμε καταληλο μηνιμα
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
        //Αμα ενα απο τα πεδια ειναι αδια δεν γινεται η εγγραφη
        //
        if (!validate(textInputLayout_1,textName) | !validate(textInputLayout_2,textPice) | !validate(textInputLayout_3,textStock)){
            return;
        }

        try {
            String name = textName.getText().toString();//περνουμε το ονομα του Item
            double price = Double.parseDouble(textPice.getText().toString().trim());//περνουμε τη τιμη του Item
            int stock = Integer.parseInt(textStock.getText().toString().trim());//περνουμε την ποσοτητα του Item

            Items item = new Items();//Δημιουργουμε καινουριο Item
            item.setName(name);//Δινουμε το ονομα που πηραμε πραπανω
            item.setPrice(price);//Δινουμε τη τιμη που πηραμε πραπανω
            item.setStock(stock);//Δινουμε τη ποσοτητα που πηραμε πραπανω
            item.setCategory(itemCategory);//Δινουμε τη κατηγορια που πηραμε απο το Spinner
            MainActivity.myAppDatabase.myDao().addItem(item);//Βαζουμε το Item στη βαση

        } catch (Exception e) {
            String message = e.getMessage();
            Toast.makeText(getActivity(),"SOMETHING WRONG"+"\n"+message,Toast.LENGTH_LONG).show();
        }

    }
}
