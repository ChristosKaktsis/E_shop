package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {

    //
    //Δηλοση Μεταβλητων
    //
    public static FragmentManager fragmentManager;//Για τον Χειρισμο Fragment
    DrawerLayout drawerLayout; // Δηλωση του DrawerLayout
    NavigationView navigationView; // Δηλωση Navigation View
    Toolbar toolbar ; // Δηλωση Toolbar
    public static MyAppDatabase myAppDatabase;//Δηλωση MyappDatabase
    public  static boolean isConnected = false; // Μεταβλητη οπου θα βλέπει αμα είναι συνδεδεμένος ο χρήστης
    public static Customers logCustomer ; // Μεταβλητη Εχει ολα τα στιχεια του Χρηστη που ειναι συνδεδεμενος
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        //Δημιουργεία Βάσης
        //
        myAppDatabase = Room.databaseBuilder(getApplicationContext(),MyAppDatabase.class,"eshopDB").allowMainThreadQueries().build();
        //
        //Δημιουργεία Custom ToolBar
        //
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        //Δημιουργεια DrawerLayout
        //
        drawerLayout = findViewById(R.id.drawer_layout);

        //
        //Δημιουργεία drawerLayout button για να ανοιγει το drawer Και με κουμπί
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //
        //Ελεγχος για το fragmentcontainer
        //και ανοιγμα mainItemFragment για να εμφανισει τα προιοντα
        //
        fragmentManager = getSupportFragmentManager();
        if(findViewById(R.id.fragment_container)!=null){
            if (savedInstanceState!=null){
                return;
            }
            //
            //Στελνει στην μεθοδο τη Default τιμη για να ανοιξει το itemsFragment με ολα τα Items
            //
            openMainItemFragment(0);
        }

        //
        //Tο Navigation view Ταξινομεί τα αντικοιμενα με βάση την κατιγορία τους
        //
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.category_gpu:
                        menuItem.setChecked(true);
                        openMainItemFragment(1);//Στελνει στην μεθοδο τη τιμη 1 για να ανοιξει το itemsFragment με Κατηγορια GPU
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.category_motherboard:
                        menuItem.setChecked(true);
                        openMainItemFragment(2);//Στελνει στην μεθοδο τη τιμη 2 για να ανοιξει το itemsFragment με Κατηγορια Motherboard
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.category_CPU:
                        menuItem.setChecked(true);
                        openMainItemFragment(3);//Στελνει στην μεθοδο τη τιμη 3 για να ανοιξει το itemsFragment με Κατηγορια CPU
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.category_psu:
                        menuItem.setChecked(true);
                        openMainItemFragment(4);//Στελνει στην μεθοδο τη τιμη 4 για να ανοιξει το itemsFragment με Κατηγορια PSU
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.category_ram:
                        menuItem.setChecked(true);
                        openMainItemFragment(5);//Στελνει στην μεθοδο τη τιμη 5 για να ανοιξει το itemsFragment με Κατηγορια RAM
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.category_case:
                        menuItem.setChecked(true);
                        openMainItemFragment(6);//Στελνει στην μεθοδο τη τιμη 6 για να ανοιξει το itemsFragment με Κατηγορια case
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });
    }

    //
    //Ανοιγει το MainItemFragment αναλογα με το Info εμφανίζει τα Items
    //
    public void openMainItemFragment(int info){
        MainItemFragment mainItemFragment = new MainItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category",info);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainItemFragment,null).addToBackStack(null);
        mainItemFragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    //
    //Here is the app bar menu created
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    //
    //Here is the button action in the app bar menu
    //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile://Αμα γίνει κλικ στο κουμπι με το εικονίδιο Προφιλ τοτε...
                if(isConnected){//αμα ειναι ηδη συνδεδεμένος ανοιξε το Profile Fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment(),null).addToBackStack(null).commit();
                }else {//αλλιως ανοιξε το RegisterorLogin fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new RegisterOrLogInFragment(),null).addToBackStack(null).commit();
                }

                break;
            case R.id.action_cart://Αμα γίνει κλικ στο κουμπι με το εικονίδιο Καροτσι τοτε...
                if(!isConnected){//αμα δεν ειναι συνδεδεμένος Εμφανισε..
                    Toast.makeText(this, "You Need A profile", Toast.LENGTH_LONG).show();
                    return false;
                }
                //
                //Ανοιξε το CartFragment
                //
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CartFragment(),null).addToBackStack(null).commit();
                break;
            case R.id.action_search://Αμα γίνει κλικ στο κουμπι με το εικονίδιο Search τοτε...
                openMainItemFragment(0);//Ανοιξε ξανα ολα τα Items //δεν εβαλα να μπορει να κανει αναζητιση//
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
