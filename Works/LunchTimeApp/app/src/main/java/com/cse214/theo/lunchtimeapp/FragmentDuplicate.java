package com.cse214.theo.lunchtimeapp;
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
     * The class for duplicate fragment.
     *
     *
     *  @author
     *    Theo Seo, SBU ID: 111319497
     *
     *    Homework #1 for CSE 214, fall 2017
     */
public class FragmentDuplicate extends android.support.v4.app.Fragment {

    /**
     * Student line container passed from fragment home
     */
    private StudentLineContainer studentLineContainer = new StudentLineContainer();

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
            "Select Reality",
            "A",
            "B"
    };
    /**
     * String to be shown in the toast.
     * When duplicate is done, this will tell you where the reality has been duplicated
     */
    private String endName;

    /**
     * String to be shown in the toast
     * When duplicate is done, this will tell you which reality was duplicated
     */
    private String realityName;

    /**
     * Receives and sets the current student line changed from home fragment.
     * The currently selected student line is shared and updated among fragments dynamically whenever it's changed.
     *
     * @param studentLineContainer Currently selected student line passed from the home fragment
     */
    public void setStudentLineContainer(StudentLineContainer studentLineContainer) {
        this.studentLineContainer = studentLineContainer;
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


        rootView = inflater.inflate(R.layout.fragment_duplicate, container, false);

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.duplicate_spinner);

        final Button confirm = (Button) rootView.findViewById(R.id.duplicate_confirm);

        spinnerArrayAdapter = new ArrayAdapter < String > (rootView.getContext(), R.layout.spinner_item, spinnerArray);

        spinner.setAdapter(spinnerArrayAdapter);

        ((MainActivity) getActivity()).setStudentLineContainerFromDuplicate(studentLineContainer);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             *  Sets a reality to be duplicated.
             *  Toasts a message if the reality that clicked item indicates does not exist.
             *  Callback method to be invoked when an item in this view has been selected.
             *  This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
             *
             * @param parentView
             *      The AdapterView where the selection happened
             * @param selectedItemView
             *      If non-null, this is the parent view that the fragment's UI should be attached to.
             *      The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
             * @param position
             *      The position of the view in the adapter
             * @param id
             *      The row id of the item that is selected
             */
            @Override
            public void onItemSelected(AdapterView < ? > parentView, View selectedItemView, int position, long id) {

                final int spinnerPosition = position;

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(spinnerPosition==0) {

                            Toast.makeText(rootView.getContext(), " Please select the option. ", Toast.LENGTH_LONG).show();

                            return;
                        }

                        else if (spinnerPosition == 1) {
                            try {

                                studentLineContainer.setStudentLine(1,studentLineContainer.getStudentLine(0).clone());

                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                return;
                            }  catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                                return;
                            }

                            Toast.makeText(rootView.getContext(), "The reality A has been duplicated to reality B" , Toast.LENGTH_LONG).show();

                        } else if (spinnerPosition == 2) {
                            try {

                                studentLineContainer.setStudentLine(0,studentLineContainer.getStudentLine(1).clone());

                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                                return;
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                                return;
                            }

                            Toast.makeText(rootView.getContext(), "The reality B has been duplicated to reality A" , Toast.LENGTH_LONG).show();

                        }

                        ((MainActivity) getActivity()).setStudentLineContainerFromDuplicate(studentLineContainer);

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

        });

        return rootView;

    }


}