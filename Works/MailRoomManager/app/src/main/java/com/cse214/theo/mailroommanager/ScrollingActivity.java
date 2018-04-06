package com.cse214.theo.mailroommanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * Main activity implemented with scrolling activity.
 * Displays the current state of mail room in the form of six stacks, including the floor.
 * Inside the first sheet, mail room recycler sheet,
 * Since there are two sheets and controlling buttons to be displayed, I thought there needs to be enough space.
 *
 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #3 for CSE 214, fall 2017
 */
public class ScrollingActivity extends AppCompatActivity {

    /**
     * The mail room that contains stacks of packages.
     */
    private MailRoom mailRoom = new MailRoom();

    /**
     * Recycler adapter for showing the mail room status.
     */
    private MailRoomRecyclerAdapter mailRoomRecyclerAdapter;

    /**
     * Recycler view for mail room.
     */
    private RecyclerView mailRoomRecyclerView;

    /**
     * The current day, which is the arrival date of a package when delivered.
     */
    private int arrivalDate;

    /**
     * Spinner for selecting which recipient to show on the sheet.
     */
    private Spinner SPINNER;

    /**
     * Recycler view for a recipient's packages.
     */
    private RecyclerView recipientRecyclerView;

    /**
     * The array adapter of string value, the name of recipients.
     */
    private ArrayAdapter<String> spinnerArrayAdapter;

    /**
     * The recycler view adapter for recipient recycler view.
     */
    private RecipientRecyclerAdapter recipientRecyclerAdapter;


    /**
     * This is the built-in method for activity.
     *
     * @param savedInstanceState
     * If the activity is being re-initialized after previously being shut down. then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        SPINNER = (Spinner) findViewById(R.id.list_spinner);

        FloatingActionButton getPackageButton = (FloatingActionButton) findViewById(R.id.button_get);

        FloatingActionButton moveButton = (FloatingActionButton) findViewById(R.id.button_move);

        FloatingActionButton tomorrowButton = (FloatingActionButton) findViewById(R.id.button_tomorrow);

        FloatingActionButton emptyButton = (FloatingActionButton) findViewById(R.id.button_empty);

        FloatingActionButton findWrongButton = (FloatingActionButton) findViewById(R.id.button_wrong);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.deliver);

        FloatingActionButton exitButton = (FloatingActionButton) findViewById(R.id.exit_button);


        try {
            spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, mailRoom.getRecipients());

        } catch (EmptyStackException | FullStackException e) {
            e.printStackTrace();
        }
        SPINNER.setAdapter(spinnerArrayAdapter);

        SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            //If an item in the spinner is selected, the recycler view will immediately display the recipient's packages.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                try {
                    recipientRecyclerView = (RecyclerView) findViewById(R.id.user_recyclerSheet);
                    recipientRecyclerAdapter = new RecipientRecyclerAdapter(getBaseContext(), mailRoom.listRecipientPackages(mailRoom.getRecipients().get(position)));
                } catch (FullStackException | EmptyStackException e) {
                    e.printStackTrace();
                }
                recipientRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                recipientRecyclerView.setAdapter(recipientRecyclerAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DialogDeliver.class);

                startActivityForResult(intent, 1);
            }
        });
        getPackageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DialogGet.class);

                startActivityForResult(intent, 2);
            }
        });
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DialogMove.class);

                startActivityForResult(intent, 3);
            }
        });
        findWrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailRoom.wrongPackagesToFloor();
                updateMailRecyclerView();
                Toast.makeText(getBaseContext(),
                        " Rearranged packages. Wrong packages are on the floor. ",
                        Toast.LENGTH_LONG).show();
            }
        });
        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrivalDate++;
                mailRoom.updateMailRoom(arrivalDate);
                updateMailRecyclerView();
                Toast.makeText(getBaseContext(),
                        " A day has passed. Now the day is " + arrivalDate,
                        Toast.LENGTH_LONG).show();
            }
        });
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mailRoom.emptyFloor();
                updateMailRecyclerView();
                Toast.makeText(getBaseContext(),
                        " The floor has been cleaned. There is no packages on the floor. ",
                        Toast.LENGTH_LONG).show();
            }

        });


    }

    /**
     * Delivers a package to the next package stack when it is full.
     * Recursively called when a stack is full, with increased index of stack.
     *
     * @param stackIndex
     *      The specific position of package stack.
     * @param pushedPackage
     *      The package to be delivered.
     */
    public void toNextStack(int stackIndex, Package pushedPackage) {


        try {

            mailRoom.getPackage(stackIndex).push(pushedPackage);

        } catch (FullStackException e) {

            toNextStack(++stackIndex, pushedPackage);

        }
    }

    /**
     * Find the specific stack where a recipient name passed is in.
     *
     * @param recipient
     *      Searches the stack where the package with the given recipient name is
     */
    public PackageStack findStack(String recipient) {
        PackageStack result = new PackageStack();

        try {

            for(int i=0;i<mailRoom.size();i++){

                if(mailRoom.getPackage(i).isEmpty())
                    continue;

                PackageStack tempStack = new PackageStack();

                int size=mailRoom.getPackage(i).size();

                for(int j=0;j<size;j++){

                    if(mailRoom.getPackage(i).peek().getRecipient().equals(recipient))
                        result=mailRoom.getPackage(i);

                    tempStack.push(mailRoom.getPackage(i).pop());

                }

                for(int j=0;j<size;j++){
                    mailRoom.getPackage(i).push(tempStack.pop());
                }
            }

        } catch (EmptyStackException | FullStackException e) {
            e.printStackTrace();
        }
        return  result;
    }



    /**
     * Delivers a package according to the index written on the package stack.
     * The recipient name, arrival date and the weight of the package are passed to instantiate a new package.
     *
     * @param name
     *      The recipient name
     *
     * @param arrivalDate
     *      The date when the package has delivered.
     *
     * @param weight
     *      The weight of the package.
     *
     */
    public void deliver(String name, int arrivalDate, double weight) {

        if (!Character.isAlphabetic(name.charAt(0))) {

            Toast.makeText(this,
                    "Please enter alphabets only",
                    Toast.LENGTH_LONG).show();

            return;
        }

        char firstLetter = Character.toLowerCase((name.charAt(0)));

        if (Character.compare(firstLetter, 'a') >= 0 && Character.compare(firstLetter, 'g') <= 0)
            toNextStack(0, new Package(name, arrivalDate, weight));

        else if (Character.compare(firstLetter, 'h') >= 0 && Character.compare(firstLetter, 'j') <= 0) {
            toNextStack(1, new Package(name, arrivalDate, weight));

        } else if (Character.compare(firstLetter, 'k') >= 0 && Character.compare(firstLetter, 'm') <= 0) {
            toNextStack(2, new Package(name, arrivalDate, weight));

        } else if (Character.compare(firstLetter, 'n') >= 0 && Character.compare(firstLetter, 'r') <= 0) {
            toNextStack(3, new Package(name, arrivalDate, weight));

        } else
            toNextStack(4, new Package(name, arrivalDate, weight));


    }


    /**
     * Starts an activity according to the request code.
     *
     * @paramn requestCode
     *      The request code assigned from start activity method.
     *
     * @param resultCode
     *      If the operation is successful, start an activity.
     *
     * @param data
     *      Intent to be started.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {

                String name = data.getExtras().getString("DELIVER_NAME");

                double weight = data.getExtras().getDouble("DELIVER_WEIGHT");

                deliver(name, arrivalDate, weight);
                Toast.makeText(this,
                        "A " + weight+ "kg package is awaiting pickup by "+ name,
                        Toast.LENGTH_LONG).show();
                updateMailRecyclerView();
            } else if (requestCode == 2) {


                final String name = data.getExtras().getString("RECIPIENT_NAME");

                try {

                    //If there is no package for the recipient name given, toast a message.
                    if (!mailRoom.getRecipients().contains(name)) {
                        Toast.makeText(this,
                                "There is no package for that person",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                } catch (EmptyStackException | FullStackException e){
                    e.printStackTrace();
                }

                final PackageStack toSearch = findStack(name);

                try {
                    while (!toSearch.isEmpty()) {
                        if (toSearch.peek().getRecipient().equalsIgnoreCase(name)) {
                            break;
                        }

                        mailRoom.getPackage(5).push(toSearch.pop());

                    }



                    updateMailRecyclerView();



                    mailRoomRecyclerView.setOnTouchListener(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            try {
                            toSearch.pop();

                            while (!mailRoom.getPackage(5).isEmpty())



                                    toSearch.push(mailRoom.getPackage(5).pop());
                                } catch (FullStackException | EmptyStackException e) {
                                    e.printStackTrace();
                                }

                            updateMailRecyclerView();

                            Toast.makeText(getBaseContext(),
                                    name + " has picked up the package.",
                                    Toast.LENGTH_LONG).show();

                            mailRoomRecyclerView.setOnTouchListener(null);


                            return false;
                        }

                    });






                } catch (EmptyStackException e) {
                    e.printStackTrace();
                } catch (FullStackException e) {
                    e.printStackTrace();
                }




            } else if (requestCode == 3) {

                int source = data.getExtras().getInt("MOVE_SOURCE");
                int destination = data.getExtras().getInt("MOVE_DESTINATION");
                if (mailRoom.getPackage(source).isEmpty()) {
                    Toast.makeText(this,
                            "There is no package to move from that stack. Please select the options again.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (destination == 5)
                    try {
                        mailRoom.getPackage(destination).push(mailRoom.getPackage(source).pop());
                    } catch (EmptyStackException | FullStackException e) {
                        e.printStackTrace();
                    }
                else
                    try {
                        mailRoom.getPackage(destination).push(mailRoom.getPackage(source).pop());
                    } catch (FullStackException e) {
                        e.printStackTrace();
                    } catch (EmptyStackException e) {
                        e.printStackTrace();
                    }

                updateMailRecyclerView();
                Toast.makeText(this,
                        "The top package from stack" + source + " has been moved to the stack " + destination,
                        Toast.LENGTH_LONG).show();

            }
        }
    }

    /**
     * Built-in method for scrolling activity.
     *  Initialize the contents of the Activity's standard options menu. You should place your menu items in to menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    /**
     * Built-in method for scrolling activity.
     * This hook is called whenever an item in your options menu is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Updates the mail recycler view whenever the data set has been changed.
     * At the same time, since the data set has been changed, the adapter for the spinner should be changed too.
     */
    public void updateMailRecyclerView() {

        mailRoomRecyclerView = (RecyclerView) this.findViewById(R.id.mail_recycler_sheet);

        try {
            spinnerArrayAdapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, mailRoom.getRecipients());
        } catch (EmptyStackException | FullStackException e){
            e.printStackTrace();
        }

        SPINNER.setAdapter(spinnerArrayAdapter);

        mailRoomRecyclerAdapter = new MailRoomRecyclerAdapter(this, mailRoom);


        mailRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mailRoomRecyclerView.setAdapter(mailRoomRecyclerAdapter);


    }
}
