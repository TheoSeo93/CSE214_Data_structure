package com.cse214.theo.oilchangemanager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * The main activity class that runs the application
 *
 * @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class MainActivity extends AppCompatActivity {


    /**
     * The home fragment that performs overall functions.
     */
    private FragmentHome fh = new FragmentHome();

    /**
     * The fragment functions to merge two lists.
     */
    private FragmentMerge fm = new FragmentMerge();

    /**
     * The home fragment that pastes cut car to the finished line.
     */
    private FragmentPaste fp = new FragmentPaste();

    /**
     * Passes and sets the car list  changed from home fragment to compare fragment and duplicate fragment.
     * The currently selected car list is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param
     */
    public void setCarListContainerFromHome(CarListContainer carListContainer) {

        fp.setCarListContainer(carListContainer);

        fm.setCarListContainer(carListContainer);


    }

    /**
     * Receives and sets the cut car from other fragments..
     *
     *  @param cutCar
     *
     */
    public void setCutCar(Car cutCar) {
        fp.setCutCar(cutCar);
    }


    public void setCarListFromPaste(CarListContainer carListContainer) {

        fh.setCarListContainer(carListContainer);
        fm.setCarListContainer(carListContainer);

    }
    public void setCarListFromMerge(CarListContainer carListContainer) {

        fp.setCarListContainer(carListContainer);
        fh.setCarListContainer(carListContainer);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the activity content to an explicit view.
        setContentView(R.layout.activity_main);

        //Represents a bottom navigation bar for application
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //Sets OnNavigationItemSelectedListener
        navigation.setOnNavigationItemSelectedListener(newOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Sets the home fragment as the default fragment to be shown when beginning the application.
        transaction.replace(R.id.content, fh).commit();


    }

    /**
     * On navigation view listener for the bottom navigation view that changes the fragment to be shown as clicked accordingly.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener newOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();


            FragmentTransaction transaction = fragmentManager.beginTransaction();


            switch (item.getItemId()) {
                case R.id.display_line:

                    transaction.replace(R.id.content, fh).commit();


                    return true;
                case R.id.merge:

                    transaction.replace(R.id.content, fm).commit();


                    return true;
                case R.id.paste_to_finished:

                    transaction.replace(R.id.content, fp).commit();

                    return true;

            }


            return true;
        }

    };
}