package com.example.e_shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleItemFragment extends Fragment implements View.OnClickListener {
    //
    //Μεταβλητες αυτες χρησιμοποιουντε για κλειδια μεταβλητων στο bundle
    //
    private static final String ARG_ITEM_TITLE = "argItemTitle";
    private static final String ARG_ITEM_PRICE = "argItemPrice";
    private static final String ARG_ITEM_ID = "argItemId";
    private static final String ARG_ITEM_STOCK = "argItemStock";
    private static final String ARG_ITEM_CATEGORY = "argItemCategory";

    //
    //Με αυτη τη μεθοδο μπορουμε να χειριστουμε καλητερα τα bundles Σαν να τα δηλωνουμε σε Δομητη Κλασης
    //Οποια Κλαση Θελει να ανοιξει το SingleItemFragment Χρησημοποιει αυτη τη μεθοδο η οποια κανει return το activity και βαζει
    //τις καταλληλες τιμες στο Bundle
    //
    public static SingleItemFragment newInstance(int id , String title , double price, int stock ,String category){
        SingleItemFragment fragment = new SingleItemFragment();//Οριζουμε το Fragment
        Bundle bundle = new Bundle();//Οριζουμε το Bundle
        //
        //Βαζουμε στο Bundle τις μεταβλητες ενος Item χρησημοποιοντας τα Strings Που δηλοσαμε παραπάνω
        //
        bundle.putInt(ARG_ITEM_ID,id);
        bundle.putString(ARG_ITEM_TITLE,title);
        bundle.putDouble(ARG_ITEM_PRICE,price);
        bundle.putInt(ARG_ITEM_STOCK,stock);
        bundle.putString(ARG_ITEM_CATEGORY,category);
        fragment.setArguments(bundle);
        return fragment;
    }

    //
    //Οριζουμε Μεταβλητες Που Υπαρχουν στο View
    //
    TextView itemTitleTextView;//Τιτλος Item
    TextView itemPriceTextView;//Τιμη Item
    TextView itemInStock;//Ποσα εχει αποθεμα
    Button buyButton;//Κουμπι αγορας

    //
    //Οριζουμε Μεταβλητες Ενος Item
    //
    private int itemId;
    private String itemTitle;
    private double itemPrice;
    private int itemStock;
    private String itemCategory;

    boolean itExists = false;//Αυτη η μεταβλητη διχνει αμα υπαρχει στο καλαθι το συγκεκριμενο Item

    public SingleItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_item, container, false);
        //
        //Δηλωνουμε Τα Views
        //
        itemTitleTextView = view.findViewById(R.id.itemTitleText);
        itemPriceTextView = view.findViewById(R.id.priceTextView);
        itemInStock = view.findViewById(R.id.inStockText);
        buyButton = view.findViewById(R.id.buyButton);
        buyButton.setOnClickListener(this);//Onclick για το κουμπι αγορας

        if(getArguments()!= null){
            //
            //Παιρνουμε τις τιμες που εχει το Bundle Χρησιμοποιοντασ τα Strings που εχουν οριστει παραπανω
            //
            itemId = getArguments().getInt(ARG_ITEM_ID);
            itemTitle = getArguments().getString(ARG_ITEM_TITLE);
            itemPrice = getArguments().getDouble(ARG_ITEM_PRICE);
            itemStock = getArguments().getInt(ARG_ITEM_STOCK);
            itemCategory = getArguments().getString(ARG_ITEM_CATEGORY);
        }
        //
        //Βαζουμε στα TextViews τις μεταβλητες του Item
        //
        itemTitleTextView.setText(itemTitle);
        itemPriceTextView.setText(itemPrice+"");
        itemInStock.setText(""+itemStock);

        return view;
    }

    @Override
    public void onClick(View v) {
        //
        //Αμα Δεν ειναι συνδεδεμενος ο χρηστης δεν τον αφηνει να κανει αγορα
        //
        if (!MainActivity.isConnected){
            Toast.makeText(getActivity(),"NOT LOGGED IN"+"\n"+"LOG IN OR CREATE A PROFILE TO BUY!", Toast.LENGTH_LONG).show();
            return;
        }
        //
        //Ελεγχος Αμα το αποθεμα ειναι 0 τοτε δεν γινεται η αγορα
        //
        if (itemStock==0){
            return;
        }

        Items itemToAdd = MainActivity.myAppDatabase.myDao().getItemId(itemId);//Το Item Που προστεθει στο καλαθι

        //
        //Περναμε απο ολα τα Items που εχει το καλαθι για να δουμε αμα το itemToAdd υπαρχει ηδη μεσα
        //Προσθετουμε στην ποσοτητα του συγκεκριμενου Item που υπαρχει στο καλαθι
        //Δεν επιτρεπουμε στον χρηστη να βαλει παραπανω Items απο την ποσοτητα στο αποθεμα
        //
        for (Items item : CartFragment.cartitemList){
            if(item.getId()==itemToAdd.getId()){//συγκριση για να δουμε αμα υπαρχει
                if(item.getStock()<itemToAdd.getStock()){//ελεγχος για να μην ξεπερασει το αποθεμα και βαλει παραπανω Items στο καλαθι
                    item.setStock(item.getStock()+1);//Αμα υπαρει απλα προσθεσε +1 στο ιδι υαπρχον item στο καλαθι
                    Toast.makeText(getActivity(),"Added To Cart", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getActivity(),"You can't add more", Toast.LENGTH_LONG).show();
                }
                itExists = true;//Βαλε οτι υπαρχει
            }
        }
        //
        //Αμα δεν υπαρχει το προσθετει στη λιστα του καλαθιου
        //
        if (!itExists){
            itemToAdd.setStock(1);//Το Stock Εδω ειναι διαφορετικο απο αυτο που υπαρχει στη βαση. Το item αυτο μπαινει στο καλαθι με ποσοτητα 1 και αμα το
                                //παρει ξανα ο χρηστης 8α παρει Stock+1
            CartFragment.cartitemList.add(itemToAdd);
            Toast.makeText(getActivity(),"Added To Cart", Toast.LENGTH_LONG).show();
        }




    }
}
