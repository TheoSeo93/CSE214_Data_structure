package com.cse214.theo.sevenflags;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.ArrayList;

/**
 *This is the main activity that runs the application.
 *First of all, I used an external library from https://github.com/KeepSafe/TapTargetView in order to show the steps to introduce how this application works.
 *After the show case steps and initiating process are done, each three queues for a ride will be displayed as horizontal text views.
 *Below the rides, there are three other recycler views that displays current status of gold, silver and regular customers.
 *Lastly, as the simulation is done, the dialog will pop up to inform statistics of the rides, such as how many rides that gold customers has ridden or how many people has taken a ride for a specific ride.

 * @author Theo Seo, SBU ID: 111319497
 *
 *         Homework #4 for CSE 214, fall 2017
 */
public class ScrollingActivity extends AppCompatActivity {

    /**
     * Random generator that randomly selects a ride
     */
    private RandomGenerator randomGenerator;

    /**
     * Belows are horizontal recycler views that show each lines.
     */
    private RecyclerView bsodOnRide;
    private RecyclerView bsodHoldingQueue;
    private RecyclerView bsodVirtualQueue;

    private RecyclerView kkOnRide;
    private RecyclerView kkHoldingQueue;
    private RecyclerView kkVirtualQueue;

    private RecyclerView totOnRide;
    private RecyclerView totHoldingQueue;
    private RecyclerView totVirtualQueue;

    private RecyclerView gfOnRide;
    private RecyclerView gfHoldingQueue;
    private RecyclerView gfVirtualQueue;

    private RecyclerView regularRecyclerView;
    private RecyclerView silverRecyclerView;
    private RecyclerView goldRecyclerView;


    /**
     * Recycler adapter to show the lines.
     */
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter1;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter2;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter3;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter4;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter5;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter6;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter7;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter8;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter9;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter10;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter11;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter12;


    /**
     * Text views to show how many minutes are left for each ride.
     */
    private TextView remaining1;
    private TextView remaining2;
    private TextView remaining3;
    private TextView remaining4;


    /**
     * Recycler view adapters that show each group of customers' status and lines.
     */
    private CustomerRecyclerviewAdapter customerRecyclerviewAdapter1;
    private CustomerRecyclerviewAdapter customerRecyclerviewAdapter2;
    private CustomerRecyclerviewAdapter customerRecyclerviewAdapter3;

    /**
     * If the setting process is done, this boolean value will prevent from starting the setting process again.
     */
    private boolean allSet;

    /**
     * The floating buttons signifies each ride by its background image.
     * At the beginning, this buttons will enable the instantiation process.
     */
    private FloatingActionButton blueScream;
    private FloatingActionButton kingdaKnuth;
    private FloatingActionButton towerTerror;
    private FloatingActionButton geForce;
    private FloatingActionButton forward;

    /**
     * ArrayLists of each group of customers.
     * This ArrayLists are used in order to figure out the result statistics.
     */
    private ArrayList<Person> regularCustomers = new ArrayList<>();
    private ArrayList<Person> silverCustomers = new ArrayList<>();
    private ArrayList<Person> goldCustomers = new ArrayList<>();

    /**
     * If the current minute hits the simulation length, the result dialog will pop up.
     */
    private int currentMin;

    /**
     * Simulation will end if it reaches the minute of simulation length.
     */
    private int simullength;

    /**
     * Boolean value whether sequences of the showcase step has been finished.
     */
    private boolean isSequenceFinished;

    /**
     * Number of gold customers.
     */
    private int gold;

    /**
     * Number of silver customers.
     */
    private int silver;

    /**
     * Number of regular customers.
     */
    private int regular;

    /**
     * Array of each ride.
     */
    private Ride[] rides = new Ride[4];


    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Instantiates all the buttons and text views.
        randomGenerator = new RandomGenerator();

        remaining1 = (TextView) findViewById(R.id.death_remaining);
        remaining2 = (TextView) findViewById(R.id.kk_remaining);
        remaining3 = (TextView) findViewById(R.id.tower_remaining);
        remaining4 = (TextView) findViewById(R.id.ge_remaining);

        forward = (FloatingActionButton) findViewById(R.id.button_forward);
        blueScream = (FloatingActionButton) findViewById(R.id.button_1);
        kingdaKnuth = (FloatingActionButton) findViewById(R.id.button_2);
        towerTerror = (FloatingActionButton) findViewById(R.id.button_3);
        geForce = (FloatingActionButton) findViewById(R.id.button_4);
        forward.setImageResource(R.drawable.forward_button);

        //This is where the external library is used.
        //A specific sequence will be proceeded once a user taps the target.
        TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.button_forward), "Welcome to the Seven Flags!", "This is the show case step to proceed the simulation :)")
                                .dimColor(R.color.white)
                                .titleTextSize(30)
                                .descriptionTextSize(15)
                                .transparentTarget(true)
                                .outerCircleAlpha(0.96f)
                                .outerCircleColor(R.color.facebook)
                                .targetCircleColor(R.color.white)
                                .cancelable(false)
                                .textColor(android.R.color.white),

                        TapTarget.forView(findViewById(R.id.button_1), "Blue Scream of Death", "Please touch this to initiate the settings!")
                                .dimColor(R.color.white)
                                .titleTextSize(30)
                                .descriptionTextSize(15)
                                .transparentTarget(true)
                                .outerCircleAlpha(0.96f)
                                .cancelable(false)
                                .drawShadow(true)
                                .outerCircleColor(R.color.facebook)
                                .targetCircleColor(R.color.white)
                                .textColor(android.R.color.white),

                        TapTarget.forView(findViewById(R.id.button_2), "King da Knuth", "This ride is called King da knuth, Please initiate the settings")
                                .dimColor(R.color.white)
                                .titleTextSize(30)
                                .descriptionTextSize(15)
                                .cancelable(false)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .outerCircleAlpha(0.96f)
                                .outerCircleColor(R.color.facebook)
                                .targetCircleColor(R.color.white)
                                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.button_3), "Tower of Terror", "This ride is called Tower of Terror, Please initiate the settings!")
                                .dimColor(R.color.white)
                                .titleTextSize(30)
                                .descriptionTextSize(15)
                                .transparentTarget(true)
                                .cancelable(false)
                                .drawShadow(true)
                                .outerCircleAlpha(0.96f)
                                .outerCircleColor(R.color.facebook)
                                .targetCircleColor(R.color.white)
                                .textColor(android.R.color.white),
                        TapTarget.forView(findViewById(R.id.button_4), "GeForce", "This ride is called GeForce, Please initiate the settings!")
                                .dimColor(R.color.white)
                                .titleTextSize(30)
                                .descriptionTextSize(15)
                                .drawShadow(true)
                                .transparentTarget(true)
                                .cancelable(false)
                                .outerCircleAlpha(0.96f)
                                .outerCircleColor(R.color.facebook)
                                .targetCircleColor(R.color.white)
                                .textColor(android.R.color.white))
                .listener(new TapTargetSequence.Listener() {

                    //If the sequence if finished, set the isSequenceFinished value to true so that it does not repeat this sequence again.
                    @Override
                    public void onSequenceFinish() {
                        isSequenceFinished = true;
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }


                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        //If the sequence has not started, start the seqeuence.
        if (!isSequenceFinished)
            sequence.start();
        ;

        //This onclick listener is for the floating button that initiate the setting of the simulation and also proceeds one minute once clicked.
        //If the simulation reaches to the end, the calculation for statistics will be done here.
        //Once clicked, this will update the status of the rides and recycler views because as the time passes, the status will be changed too.
        View.OnClickListener settingOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If the setting has not been done yet, pop up a start dialog.
                if (!allSet) {
                    Intent intent = new Intent(v.getContext(), StartDialog.class);

                    startActivityForResult(intent, 0);

                //If the setting has made and it reaches to the end of the simulation, calculate the statistics.
                } else {

                    if (currentMin == simullength) {
                        int goldTotal = 0;
                        double goldAverage = 0;
                        int silverTotal = 0;
                        double silverAverage = 0;
                        int regTotal = 0;
                        double regAverage = 0;

                        for (int i = 0; i < goldCustomers.size(); i++) {
                            goldTotal += goldCustomers.get(i).getHowManyRides();
                        }
                        goldAverage = (double) goldTotal / goldCustomers.size();
                        for (int i = 0; i < silverCustomers.size(); i++) {
                            silverTotal += silverCustomers.get(i).getHowManyRides();
                        }
                        silverAverage = (double) silverTotal / silverCustomers.size();
                        for (int i = 0; i < regularCustomers.size(); i++) {
                            regTotal += regularCustomers.get(i).getHowManyRides();
                        }
                        regAverage = (double) regTotal / regularCustomers.size();
                        String first, second, third, fourth, fifth, sixth, seventh = "";
                        first = ("On average, Gold customers have taken " + goldAverage + " rides.");
                        second = ("On average, Silver customers have taken " + silverAverage + " rides.");
                        third = ("On average, Regular customers have taken " + regAverage + " rides.");

                        fourth = ("BSOD has completed rides for " + rides[0].getHowManyCustomers() + " people.");
                        fifth = ("KK has completed rides for " + rides[1].getHowManyCustomers() + " people.");
                        sixth = ("TOT has completed rides for " + rides[2].getHowManyCustomers() + " people.");
                        seventh = ("GF has completed rides for " + rides[3].getHowManyCustomers() + " people.");
                        Intent intent = new Intent(getApplicationContext(), ResultDialog.class);

                        intent.putExtra("FIRST", first);
                        intent.putExtra("SECOND", second);
                        intent.putExtra("THIRD", third);

                        intent.putExtra("FOURTH", fourth);
                        intent.putExtra("FIFTH", fifth);
                        intent.putExtra("SIXTH", sixth);

                        intent.putExtra("SEVENTH", seventh);
                        startActivity(intent);

                        return;
                    }


                    update();
                    updateRecyclerView();
                    currentMin++;
                }

            }

        };


        forward.setOnClickListener(settingOnClick);


        blueScream.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                String title = "Blue Scream of Death";
                Intent intent = new Intent(view.getContext(), InstantiateDialog.class);
                intent.putExtra("TITLE", title);
                startActivityForResult(intent, 1);


            }
        });
        kingdaKnuth.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                String title = "Kingda Knuth";
                Intent intent = new Intent(view.getContext(), InstantiateDialog.class);
                intent.putExtra("TITLE", title);
                startActivityForResult(intent, 2);


            }
        });
        towerTerror.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                String title = "Tower of Terror";

                Intent intent = new Intent(view.getContext(), InstantiateDialog.class);

                intent.putExtra("TITLE", title);

                startActivityForResult(intent, 3);


            }
        });
        geForce.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                String title = "GeForce";

                Intent intent = new Intent(view.getContext(), InstantiateDialog.class);

                intent.putExtra("TITLE", title);

                startActivityForResult(intent, 4);


            }
        });
    }

    /**
     *Check if all the rides are instantiated.
     *
     * @param rides
     *      All the rides.
     * @return
     *      returns true if all are instantiate.
     */
    public boolean isAllSet(Ride[] rides) {
        for (int i = 0; i < rides.length; i++) {
            if (rides[i] == null)
                return false;
        }
        return true;
    }



    /**
     * Updates the queues whenever one minute has passed.
     * Firstly, if the ride is finished, people on the ride will be removed from the ride and stored to temporary queues, then people on the behind will be on the ride if there are.
     * After that, people in the temporary queues will be added to random lines.
     */
    public void update() {

        for (int i = 0; i < 4; i++) {
            rides[i].setTimeLeft(rides[i].getTimeLeft() - 1);
        }

        ArrayList<Ride> randomRides = new ArrayList<>();

        ArrayList<VirtualLine> tempQueues = new ArrayList<>();

        removePeopleOnRide(tempQueues, randomRides);

        pushPeopleOnRide(rides);

        pushHoldingQueue(rides);

        peopleAfterRide(tempQueues, randomRides);


        for (int i = 0; i < 4; i++) {
            if (rides[i].getTimeLeft() == 0)
                rides[i].setTimeLeft(rides[i].getDuration());

        }

        //Shows the remaining minutes for each ride.
        remaining1.setText("Remaining :" + String.valueOf(rides[0].getTimeLeft()) + " minutes");
        remaining1.setTextColor(Color.parseColor("#ffffff"));
        remaining2.setText("Remaining :" + String.valueOf(rides[1].getTimeLeft()) + " minutes");
        remaining2.setTextColor(Color.parseColor("#ffffff"));
        remaining3.setText("Remaining :" + String.valueOf(rides[2].getTimeLeft()) + " minutes");
        remaining3.setTextColor(Color.parseColor("#ffffff"));
        remaining4.setText("Remaining :" + String.valueOf(rides[3].getTimeLeft()) + " minutes");
        remaining4.setTextColor(Color.parseColor("#ffffff"));


    }

    /**
     * Removes people on the ride if the time left is 0.
     * Stores the removed people to temporary queues.
     */
    public void removePeopleOnRide(ArrayList<VirtualLine> tempQueues, ArrayList<Ride> randomRides) {
        for (int i = 0; i < 4; i++) {

            if (rides[i].getTimeLeft() == 0) {
                VirtualLine tempQueue = new VirtualLine();
                Ride randomRide = RandomGenerator.selectRide(rides);
                int rideSize = rides[i].getPeopleOnRide().size();
                for (int j = 0; j < rideSize; j++) {

                    rides[i].getPeopleOnRide().get(0).setStatus(Status.Available);
                    rides[i].getPeopleOnRide().get(0).increaseHowManyRides();
                    rides[i].getPeopleOnRide().get(0).getLines().remove(rides[i].getPeopleOnRide().get(0).getLines().indexOf(rides[i]));
                    rides[i].increaseHowManyPeople();
                    tempQueue.enqueue(rides[i].getPeopleOnRide().remove(0));

                }
                randomRides.add(randomRide);
                tempQueues.add(tempQueue);
            }
        }

    }

    /**
     * After people are dequeued from the ride, then people on either virtual line or holding queue will be on the ride.
     */
    public void pushPeopleOnRide(Ride[] rides) {

        for (int i = 0; i < 4; i++) {

            if (rides[i].getTimeLeft() == 0) {

               Inner: while (rides[i].getCapacity() > rides[i].getPeopleOnRide().size()) {
                    if(!rides[i].getHoldingQueue().isEmpty()) {
                        Person personOnRide = rides[i].getHoldingQueue().dequeue();


                        personOnRide.setStatus(Status.OnRide);
                        rides[i].getPeopleOnRide().add(personOnRide);
                    } else break Inner;

                }

                int size = rides[i].getVirtualLine().size();
                int count = 0;
                while (rides[i].getPeopleOnRide().size() < rides[i].getCapacity() && count < size) {
                    if (rides[i].getVirtualLine().peek()!=null&&rides[i].getVirtualLine().peek().getStatus() != Status.OnRide && rides[i].getVirtualLine().peek().getStatus() != Status.Holding) {

                        Person personOnRide = rides[i].getVirtualLine().dequeue();
                        personOnRide.setStatus(Status.OnRide);
                        rides[i].getPeopleOnRide().add(personOnRide);


                    }if(rides[i].getVirtualLine().peek()!=null) {
                        if (rides[i].getVirtualLine().peek().getStatus() == Status.OnRide || rides[i].getVirtualLine().peek().getStatus() == Status.Holding) {
                            Person personOnVirtualLine = rides[i].getVirtualLine().dequeue();
                            rides[i].getVirtualLine().enqueue(personOnVirtualLine);
                        }
                    }
                    count++;
                }

            }

        }

    }

    /**
     * In order to set a person's status to holding, checking if he is on ride is needed.
     * If he is not on ride, then set the status to holding.
     */
    public void checkStatusToSetHolding(Person person) {
        if(person.getStatus()==Status.Available) {
                person.setStatus(Status.Holding);
        }

    }

    /**
     * In order to set a person's status to available, checking if he is on ride or on holding queue is needed.
     * If he is not on ride or holding, then set the status to available.
     */
    public void checkStatusToSetAvailable(Person person) {
        switch (person.getStatus()) {
            case OnRide:
                person.setStatus(Status.OnRide);
                break;

            case Holding:
                person.setStatus(Status.Holding);
                break;

            default:
                person.setStatus(Status.Available);
                break;
        }
    }

    /**
     * Pushes people to the holding queue from virtual lines.
     * @param rides
     *      All the rides.
     */
    public void pushHoldingQueue(Ride[] rides) {

        for (int i = 0; i < 4; i++) {

            int size = rides[i].getVirtualLine().size();
            int count = 0;
            while (rides[i].getHoldingQueue().size() < rides[i].getHoldingQueue().getMaxSize() && count < size &&!rides[i].getVirtualLine().isEmpty()) {


                if (rides[i].getVirtualLine().peek().getStatus() == Status.Available) {

                    Person personOnHolding = rides[i].getVirtualLine().dequeue();

                    checkStatusToSetHolding(personOnHolding);

                    rides[i].getHoldingQueue().enqueue(personOnHolding);

                } if(rides[i].getVirtualLine().peek()!=null) {
                    if (rides[i].getVirtualLine().peek().getStatus() == Status.OnRide || rides[i].getVirtualLine().peek().getStatus() == Status.Holding)
                        rides[i].getVirtualLine().enqueue(rides[i].getVirtualLine().dequeue());
                }

                count++;

            }

        }
    }

    /**
     * Put the people stored in the temporary queues to lines back.
     *
     * @param tempQueues
     *      The queues that people removed from the ride are stored.
     *
     */
    public void peopleAfterRide(ArrayList<VirtualLine> tempQueues, ArrayList<Ride> randomRides) {

        for (int i = 0; i < tempQueues.size(); i++) {

            if (randomRides.get(i).getTimeLeft() == 0) {
                while (!tempQueues.get(i).isEmpty()) {

                    if (tempQueues.isEmpty())
                        break;

                    if (tempQueues.get(i).peek().getStatus() == Status.Available
                            &&randomRides.get(i).getCapacity() > randomRides.get(i).getPeopleOnRide().size()) {


                        Person personOnRide = tempQueues.get(i).dequeue();
                        personOnRide.setStatus(Status.OnRide);

                        personOnRide.getLines().add(randomRides.get(i));


                        randomRides.get(i).getPeopleOnRide().add(personOnRide);



                    } else if(randomRides.get(i).getCapacity() == randomRides.get(i).getPeopleOnRide().size()&&
                            tempQueues.get(i).peek().getStatus() == Status.Available &&
                            randomRides.get(i).getHoldingQueue().size()<randomRides.get(i).getHoldingQueue().getMaxSize()){

                        Person personOnHolding = tempQueues.get(i).dequeue();
                        checkStatusToSetHolding(personOnHolding);

                        personOnHolding.getLines().add(randomRides.get(i));

                        randomRides.get(i).getHoldingQueue().enqueue(personOnHolding);

                    } else {

                        Person personOnVirtualLine = tempQueues.get(i).dequeue();

                        personOnVirtualLine.getLines().add(randomRides.get(i));

                        randomRides.get(i).getVirtualLine().enqueue(personOnVirtualLine);
                    }
                }


            } else if (randomRides.get(i).getHoldingQueue().size() < randomRides.get(i).getHoldingQueue().getMaxSize()) {

                while (randomRides.get(i).getHoldingQueue().size() < randomRides.get(i).getHoldingQueue().getMaxSize() && !tempQueues.get(i).isEmpty()) {
                    if (tempQueues.get(i).peek().getStatus() ==Status.Available) {

                        Person personOnHolding = tempQueues.get(i).dequeue();

                        personOnHolding.setStatus(Status.Holding);

                        personOnHolding.getLines().add(randomRides.get(i));

                        randomRides.get(i).getHoldingQueue().enqueue(personOnHolding);

                    } else {
                        tempQueues.get(i).peek().getLines().add(randomRides.get(i));
                        randomRides.get(i).getVirtualLine().enqueue(tempQueues.get(i).dequeue());
                    }
                }

            } else {
                while (!tempQueues.get(i).isEmpty()) {
                    Person personAvailable = tempQueues.get(i).dequeue();
                    personAvailable.getLines().add(randomRides.get(i));
                    checkStatusToSetAvailable(personAvailable);
                    randomRides.get(i).getVirtualLine().enqueue(personAvailable);
                }
            }


        }
    }











    /**
     * Assigns customers to the lines when the simulation begins and update the recycler views.
     */
    public void assign() {


        for (int i = 0, goldNumber = 1, silverNumber = 1, regNumber = 1; i < Math.max(regular, Math.max(gold, silver)); i++, goldNumber++, silverNumber++, regNumber++) {
            Ride randomRide = new Ride();
            HoldingQueue randomQueue = new HoldingQueue(0);
            Person goldPerson = new Person(goldNumber, 3);
            Person silverPerson = new Person(silverNumber, 2);
            Person regPerson = new Person(regNumber, 1);

            if (goldNumber <= gold) {

                goldCustomers.add(goldPerson);
                pushGoldPerson(goldPerson, randomRide, randomQueue);
            }


            if (silverNumber <= silver) {

                silverCustomers.add(silverPerson);
                pushSilverPerson(silverPerson, randomRide, randomQueue);
            }
            if (regNumber <= regular) {
                regularCustomers.add(regPerson);
                pushRegularPerson(regPerson, randomRide, randomQueue);

            }

            if (goldNumber <= gold)
                pushGoldPerson(goldPerson, randomRide, randomQueue);


            if (silverNumber <= silver)
                pushSilverPerson(silverPerson, randomRide, randomQueue);

            if (goldNumber <= gold)
                pushGoldPerson(goldPerson, randomRide, randomQueue);


        }

        updateRecyclerView();
    }

    /**
     * For the assign process, I factored out this code to this method to prevent redundancy.
     * Pushes gold person to a line.
     * @param goldPerson
     *      Gold customer to be pushed to the line.
     * @param randomRide
     *      Randomly selected ride.
     * @param randomQueue
     *      Holding queue from randomly selected ride.
     */
    public void pushGoldPerson(Person goldPerson, Ride randomRide, HoldingQueue randomQueue) {

        randomRide = randomGenerator.selectRide(rides);
        randomQueue = randomRide.getHoldingQueue();
        goldPerson.getLines().add(randomRide);
        if (goldPerson.getStatus()==Status.Available) {

            if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
                goldPerson.setStatus(Status.OnRide);
                randomRide.getPeopleOnRide().add(goldPerson);

            } else {

                if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
                    goldPerson.setStatus(Status.Holding);
                    randomQueue.enqueue(goldPerson);

                } else {
                    goldPerson.setStatus(Status.Available);
                    randomRide.getVirtualLine().enqueue(goldPerson);
                }

            }
        } else {

            randomRide.getVirtualLine().enqueue(goldPerson);
        }
    }

    /**
     * For the assign process, I factored out this code to this method to prevent redundancy.
     * Pushes silver person to a line.
     * @param silverPerson
     *      silver customer to be pushed to the line.
     * @param randomRide
     *      Randomly selected ride.
     * @param randomQueue
     *      Holding queue from randomly selected ride.
     */
    public void pushSilverPerson(Person silverPerson, Ride randomRide, HoldingQueue randomQueue) {

        randomRide = randomGenerator.selectRide(rides);
        randomQueue = randomRide.getHoldingQueue();
        silverPerson.getLines().add(randomRide);
        if (silverPerson.getStatus() == Status.Available) {
            if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
                silverPerson.setStatus(Status.OnRide);
                randomRide.getPeopleOnRide().add(silverPerson);

            } else {
                if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
                    silverPerson.setStatus(Status.Holding);
                    randomQueue.enqueue(silverPerson);

                } else {
                    silverPerson.setStatus(Status.Available);
                    randomRide.getVirtualLine().enqueue(silverPerson);

                }

            }
        } else {

            randomRide.getVirtualLine().enqueue(silverPerson);
        }
    }

    /**
     * For the assign process, I factored out this code to this method to prevent redundancy.
     * Pushes regular customer to a line.
     * @param regPerson
     *      Gold customer to be pushed to the line.
     * @param randomRide
     *      Randomly selected ride.
     * @param randomQueue
     *      Holding queue from randomly selected ride.
     */
    public void pushRegularPerson(Person regPerson, Ride randomRide, HoldingQueue randomQueue) {

        randomRide = randomGenerator.selectRide(rides);
        randomQueue = randomRide.getHoldingQueue();
        regPerson.getLines().add(randomRide);
        if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
            regPerson.setStatus(Status.OnRide);
            randomRide.getPeopleOnRide().add(regPerson);
        } else {
            if (randomRide.getPeopleOnRide().size() < randomRide.getCapacity()) {
                regPerson.setStatus(Status.Holding);
                randomQueue.enqueue(regPerson);
            } else {
                regPerson.setStatus(Status.Available);
                randomRide.getVirtualLine().enqueue(regPerson);
            }
        }

    }

    /**
     * After dialog activities are finished, the data passed from the dialogs will be received here.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //Settings for the simulation.
            if (requestCode == 0) {

                regular = data.getExtras().getInt("SETTING_REGULAR");
                silver = data.getExtras().getInt("SETTING_SILVER");
                gold = data.getExtras().getInt("SETTING_GOLD");
                simullength = data.getExtras().getInt("SETTING_LENGTH");


            //Instantiation of the first ride.
            } else if (requestCode == 1) {

                blueScream.setImageResource(R.drawable.button_background2);

                int duration = data.getExtras().getInt("INSTANTIATE_DURATION");
                int capacity = data.getExtras().getInt("INSTANTIATE_CAPACITY");
                int queueSize = data.getExtras().getInt("INSTANTIATE_QUEUE");


                rides[0] = new Ride("Blue Scream of Death", queueSize, capacity, duration);
                rides[0].setDuration(duration);
                blueScream.setOnClickListener(null);
            //Instantiation of the second ride.
            } else if (requestCode == 2) {

                kingdaKnuth.setImageResource(R.drawable.button_background3);
                int duration = data.getExtras().getInt("INSTANTIATE_DURATION");
                int capacity = data.getExtras().getInt("INSTANTIATE_CAPACITY");
                int queueSize = data.getExtras().getInt("INSTANTIATE_QUEUE");


                rides[1] = new Ride("Kingda Knuth", queueSize, capacity, duration);
                rides[1].setDuration(duration);

                kingdaKnuth.setOnClickListener(null);

            //Instantiation of the third ride.
            } else if (requestCode == 3) {

                towerTerror.setImageResource(R.drawable.button_background);
                int duration = data.getExtras().getInt("INSTANTIATE_DURATION");
                int capacity = data.getExtras().getInt("INSTANTIATE_CAPACITY");
                int queueSize = data.getExtras().getInt("INSTANTIATE_QUEUE");

                rides[2] = new Ride("Tower of Terror", queueSize, capacity, duration);
                rides[2].setDuration(duration);


                towerTerror.setOnClickListener(null);
            //Instantiation of the last ride.
            } else if (requestCode == 4) {

                geForce.setImageResource(R.drawable.button_background4);
                int duration = data.getExtras().getInt("INSTANTIATE_DURATION");
                int capacity = data.getExtras().getInt("INSTANTIATE_CAPACITY");
                int queueSize = data.getExtras().getInt("INSTANTIATE_QUEUE");


                rides[3] = new Ride("GeForce", queueSize, capacity, duration);
                rides[3].setDuration(duration);

                geForce.setOnClickListener(null);

            }


        }
        //If all the process are done, proceed the simulation.
        if (isAllSet(rides)) {

            allSet = true;

            assign();


        }
    }

    //Updates all the recycler views that shows the status of the lines.
    public void updateRecyclerView() {
        remaining1.setText("Remaining :" + String.valueOf(rides[0].getTimeLeft()) + " minutes");
        remaining1.setTextColor(Color.parseColor("#ffffff"));
        remaining2.setText("Remaining :" + String.valueOf(rides[1].getTimeLeft()) + " minutes");
        remaining2.setTextColor(Color.parseColor("#ffffff"));
        remaining3.setText("Remaining :" + String.valueOf(rides[2].getTimeLeft()) + " minutes");
        remaining3.setTextColor(Color.parseColor("#ffffff"));
        remaining4.setText("Remaining :" + String.valueOf(rides[3].getTimeLeft()) + " minutes");
        remaining4.setTextColor(Color.parseColor("#ffffff"));

        goldRecyclerView = (RecyclerView) this.findViewById(R.id.gold_sheet);
        silverRecyclerView = (RecyclerView) this.findViewById(R.id.silver_sheet);
        regularRecyclerView = (RecyclerView) this.findViewById(R.id.regular_sheet);
        bsodOnRide = (RecyclerView) this.findViewById(R.id.bsod_onride);
        bsodHoldingQueue = (RecyclerView) this.findViewById(R.id.bsod_holding);
        bsodVirtualQueue = (RecyclerView) this.findViewById(R.id.bsod_virtual);
        kkOnRide = (RecyclerView) this.findViewById(R.id.kk_onride);
        kkHoldingQueue = (RecyclerView) this.findViewById(R.id.kk_holding);
        kkVirtualQueue = (RecyclerView) this.findViewById(R.id.kk_virtual);
        totOnRide = (RecyclerView) this.findViewById(R.id.tot_onride);
        totHoldingQueue = (RecyclerView) this.findViewById(R.id.tot_holding);
        totVirtualQueue = (RecyclerView) this.findViewById(R.id.tot_virtual);
        gfOnRide = (RecyclerView) this.findViewById(R.id.ge_onride);
        gfHoldingQueue = (RecyclerView) this.findViewById(R.id.ge_holding);
        gfVirtualQueue = (RecyclerView) this.findViewById(R.id.ge_virtual);

        customerRecyclerviewAdapter1 = new CustomerRecyclerviewAdapter(this, regularCustomers, "regular");
        customerRecyclerviewAdapter2 = new CustomerRecyclerviewAdapter(this, silverCustomers, "silver");
        customerRecyclerviewAdapter3 = new CustomerRecyclerviewAdapter(this, goldCustomers, "gold");

        regularRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        silverRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        goldRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        horizontalRecyclerAdapter1 = new HorizontalRecyclerAdapter(this, rides[0], "onRide");
        bsodOnRide.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter2 = new HorizontalRecyclerAdapter(this, rides[0], "holdingQueue");
        bsodHoldingQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter3 = new HorizontalRecyclerAdapter(this, rides[0], "virtual");
        bsodVirtualQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter4 = new HorizontalRecyclerAdapter(this, rides[1], "onRide");
        kkOnRide.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter5 = new HorizontalRecyclerAdapter(this, rides[1], "holdingQueue");
        kkHoldingQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter6 = new HorizontalRecyclerAdapter(this, rides[1], "virtual");
        kkVirtualQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter7 = new HorizontalRecyclerAdapter(this, rides[2], "onRide");
        totOnRide.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter8 = new HorizontalRecyclerAdapter(this, rides[2], "holdingQueue");
        totHoldingQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter9 = new HorizontalRecyclerAdapter(this, rides[2], "virtual");
        totVirtualQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter10 = new HorizontalRecyclerAdapter(this, rides[3], "onRide");
        gfOnRide.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter11 = new HorizontalRecyclerAdapter(this, rides[3], "holdingQueue");
        gfHoldingQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        horizontalRecyclerAdapter12 = new HorizontalRecyclerAdapter(this, rides[3], "virtual");
        gfVirtualQueue.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        bsodOnRide.setAdapter(horizontalRecyclerAdapter1);
        bsodHoldingQueue.setAdapter(horizontalRecyclerAdapter2);
        bsodVirtualQueue.setAdapter(horizontalRecyclerAdapter3);
        kkOnRide.setAdapter(horizontalRecyclerAdapter4);
        kkHoldingQueue.setAdapter(horizontalRecyclerAdapter5);
        kkVirtualQueue.setAdapter(horizontalRecyclerAdapter6);
        totOnRide.setAdapter(horizontalRecyclerAdapter7);
        totHoldingQueue.setAdapter(horizontalRecyclerAdapter8);
        totVirtualQueue.setAdapter(horizontalRecyclerAdapter9);
        gfOnRide.setAdapter(horizontalRecyclerAdapter10);
        gfHoldingQueue.setAdapter(horizontalRecyclerAdapter11);
        gfVirtualQueue.setAdapter(horizontalRecyclerAdapter12);
        regularRecyclerView.setAdapter(customerRecyclerviewAdapter1);
        silverRecyclerView.setAdapter(customerRecyclerviewAdapter2);
        goldRecyclerView.setAdapter(customerRecyclerviewAdapter3);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

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
}
