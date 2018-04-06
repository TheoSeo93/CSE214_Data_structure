package com.cse214.theo.oilchangemanager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * The fragment that merges one list to the list selected from the spinner.
 * If Donny's list is selected, then the other list, Joe's list will be merged into Donny's list in the style that each other's node alternates one by one.
 * The first node is the head of the selected list.
 *
 *  @author
 *    Theo Seo, SBU ID: 111319497
 *
 *    Homework #2 for CSE 214, fall 2017
 */
public class FragmentMerge extends android.support.v4.app.Fragment {


    /**
     * Inflated view from the xml resource of duplicate fragment.
     */
    private View rootView;


    /**
     * Car list container passed from other fragments.
     */
    private CarListContainer carListContainer;


    /**
     * Array adapter for spinner.
     */
    private ArrayAdapter < String > spinnerArrayAdapter;


    /**
     * The options to be displayed on the spinner
     */
    private String[] spinnerArray = {
            "Destination List",
            "Joe",
            "Donny"
    };

    /**
     * The list to be merged into the destination list.
     */
    private CarList source;

    /**
     * The list to be a result after merge.
     */
    private CarList destination;


    /**
     * Receives and sets the current car list from other fragments.
     */
    public void setCarListContainer(CarListContainer carListContainer) {

        this.carListContainer = carListContainer;

    }

    /**
     * Called to have the fragment instantiate its user interface view.
     * Two spinners will be attached on the activity, and these will give user the options to compare realities.
     * If a reality indicated by either of them chosen has not generated yet, a message will be toasted and go back to the method again.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setCarListFromMerge(carListContainer);

        rootView = inflater.inflate(R.layout.fragment_merge, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.compare_spinner1);

        final Button confirm = (Button) rootView.findViewById(R.id.compare_confirm);

        spinnerArrayAdapter = new ArrayAdapter < String > (rootView.getContext(), (R.layout.spinner_item), spinnerArray);

        spinner.setAdapter(spinnerArrayAdapter);


        AdapterView.OnItemSelectedListener onItemSelectedListener_spinner1 = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                if (position == 0) {
                    source = null;
                    return;
                }

                destination = carListContainer.getCarList(position - 1);


                confirm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        if (destination == null) {
                            Toast.makeText(rootView.getContext(), "Please select options", Toast.LENGTH_LONG).show();
                            return;
                        } else {

                            if (destination == carListContainer.getCarList(0)) {

                                CarList newList = carListContainer.getCarList(1).merge(carListContainer.getCarList(0));
                                carListContainer.setCarList(0, newList);
                                carListContainer.setCarList(1, new CarList());

                                Toast.makeText(getContext(), "Donny's list is merged to Joe's list", Toast.LENGTH_LONG).show();
                            } else {

                                CarList newList = carListContainer.getCarList(0).merge(carListContainer.getCarList(1));
                                carListContainer.setCarList(1, newList);
                                carListContainer.setCarList(0, new CarList());

                                Toast.makeText(getContext(), "Joe's list is merged to Donny's list", Toast.LENGTH_LONG).show();
                            }

                        }

                        ((MainActivity) getActivity()).setCarListFromMerge(carListContainer);

                    }
                });
            }

            /**
             *
             * Callback method to be invoked when the selection disappears from this view.
             * The selection can disappear for instance when touch is activated or when the adapter becomes empty.nitialized as an empty set.
             *
             * @param parentView
             *     The AdapterView that now contains no selected item.
             */
            @Override
            public void onNothingSelected(AdapterView < ? > parentView) {

            }

        };

        spinner.setOnItemSelectedListener(onItemSelectedListener_spinner1);

        return rootView;

    }


}