package com.cse214.theo.oilchangemanager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
/**
 * The activity class that takes cur car to paste it to the finished line.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class FragmentPaste extends android.support.v4.app.Fragment {

    /**
     * The car object cut from the home fragment.
     */
    private Car cutCar;


    /**
     * Car list container passed from fragment home
     */
    private CarListContainer carListContainer = new CarListContainer();

    /**
     * Inflated view from the xml resource of home fragment.
     */
    private View rootView;

    /**
     * The options to be displayed on the spinner
     */
    private ArrayAdapter < String > spinnerArrayAdapter;

    /**
     * The options to be displayed on the spinner
     */
    private String[] spinnerArray = {
            "Select List",
            "Joe",
            "Donny"
    };


    /**
     * Receives and sets the current car list changed from home fragment.
     *
     *  @param carListContainer
     *
     */
    public void setCarListContainer(CarListContainer carListContainer) {
        this.carListContainer = carListContainer;
    }

    /**
     * Receives and sets the cut car from home fragment.
     *
     *  @param cutCar
     *
     */
    public void setCutCar(Car cutCar) {
        this.cutCar = cutCar;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater
     *      The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container
     *      If non-null, this is the parent view that the fragment's UI should be attached to.
     *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_paste, container, false);

        final Button confirm = (Button) rootView.findViewById(R.id.paste_check);

        ((MainActivity) getActivity()).setCarListFromPaste(carListContainer);

        confirm.setOnClickListener(new View.OnClickListener() {

            /**
             * Called when a button has been clicked.
             * If any one of the two reality has not been selected, this comes back to the method and toasts a message.
             * If they are the same realities or different realities, this toasts messages accordingly.
             *
             * @param view
             *      The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                if (cutCar != null) {
                    carListContainer.getCarList(2).appendToTail(cutCar);
                    Toast.makeText(rootView.getContext(), "Pasted to the finished list", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(rootView.getContext(), "You need to cut a car first in order to paste one to the finished list", Toast.LENGTH_LONG).show();
                }

            }
        });
        return rootView;
    }
}