package uitls;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zamakanmap.MapActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import Intities.Country;
import adapters.TabsAdapter;
import Intities.Event;
import Intities.Person;
import Intities.Travels;
import classes.CustomToastMessage;

public class FireBaseUtils {
    // for store persons and events in sp and able to be one
    public static ArrayList<Person> personsList = new ArrayList<>() ;
    public static ArrayList<Event> eventsList = new ArrayList<>() ;
    // for store persons and events and not able to be one
    public static ArrayList<Person> AllPersonsList = new ArrayList<>() ;
    public static ArrayList<Event> AllEventsList = new ArrayList<>() ;
    //to display
    public static ArrayList<String> names = new ArrayList<>();
    public static DatabaseReference database ;

    // //Display All Persons On map and list
    public static void displayAllPersonsOnFragments(TabsAdapter tabsAdapter){
        SharedPreferenceUtils spu = SharedPreferenceUtils.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Persons");  // To reach persons on firebase
        database.addChildEventListener(new ChildEventListener() {  // To Reach each person
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ArrayList<Travels> theseTravels = new ArrayList<>();
                //to reach the travels
                DatabaseReference rf = snapshot.getRef().child("travels");
                //to add travels for each person
                rf.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot2, @Nullable String previousChildName) {
                            theseTravels.add(snapshot2.getValue(Travels.class));
                       // Toast.makeText(MapActivity.getContextOfApplication(),snapshot2.getValue(Travels.class).toString()
                                //+" ADDED To" + snapshot.getValue(Person.class).getFullName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // to add person to our data lists
                Person p = snapshot.getValue(Person.class);
                p.setPersonTravels(theseTravels);
                AllPersonsList.add(p);
                personsList.add(p);
                names.add(p.getFullName());
                spu.putPersonArrayListInSP(personsList);
                tabsAdapter.updatePersonsContent(personsList);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // To Display all events and persons On Map
    public static void DisplayAllOnMap(TabsAdapter tabsAdapter){
        tabsAdapter.updatePersonsContent(AllPersonsList);
        tabsAdapter.UpdateEventsContent(AllEventsList);
    }

    public static void display(int year){
        personsList.clear();
        for (int i = 0 ; i < AllPersonsList.size() ; i++ ){

            if (AllPersonsList.get(i).getDateOfBirth() <= year && AllPersonsList.get(i).getDateOfDeath() >= year){
                //personsList.add(AllPersonsList.get(i));
                for (int j = 0; j < AllPersonsList.get(i).getPersonTravels().size(); j++) {
                        if (AllPersonsList.get(i).getPersonTravels().get(j).getYearOfTravel() <= year ) {
                            //Toast.makeText(MapActivity.getContextOfApplication(), "TRAVELED", Toast.LENGTH_SHORT).show();
                            MapActivity.tabsAdapter.drawLineOnMap(AllPersonsList.get(i).getLatitude(),
                                    AllPersonsList.get(i).getLongitude(),
                                    AllPersonsList.get(i).getPersonTravels().get(j).getLatTravel(),
                                    AllPersonsList.get(i).getPersonTravels().get(j).getLonTravel());
                            AllPersonsList.get(i).setLatitude(AllPersonsList.get(i).getPersonTravels().get(j).getLatTravel());
                            AllPersonsList.get(i).setLongitude(AllPersonsList.get(i).getPersonTravels().get(j).getLonTravel());
                        }
                    }
                personsList.add(AllPersonsList.get(i));
            }
        }
        MapActivity.tabsAdapter.updatePersonsContentOnMap(personsList);

    }
    // To Display one person on map
    public static void displaySpYearPersonsOnFragments(TabsAdapter tabsAdapter, int year) {
        personsList.clear();
        SharedPreferenceUtils spu = SharedPreferenceUtils.getInstance();
        ArrayList<Person> myList = new ArrayList<>();
        myList.addAll(spu.getPersonArrayListFromSP());
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getDateOfBirth() <= year) {
                personsList.add(myList.get(i));
            }
            if (true) {
                for (int j = 0; j < myList.get(i).getPersonTravels().size(); j++) {
                    if (myList.get(i).getPersonTravels().get(j).getYearOfTravel() == year) {
                        Toast.makeText(MapActivity.getContextOfApplication(), "TRAVELED", Toast.LENGTH_SHORT).show();
                        Person temp = myList.get(i);
                        MapActivity.tabsAdapter.drawLineOnMap(temp.getLatitude(), temp.getLongitude(),
                                temp.getPersonTravels().get(i).getLatTravel(), temp.getPersonTravels().get(i).getLonTravel());
                        temp.setLatitude(temp.getPersonTravels().get(i).getLatTravel());
                        temp.setLongitude(temp.getPersonTravels().get(i).getLonTravel());
                        personsList.add(temp);
                    }
                }
            }
               /* if (myList.get(i).getDateOfBirth() <= year && myList.get(i).getDateOfDeath() > year) {
                    personsList.add(myList.get(i));
                }*/
            tabsAdapter.updatePersonsContentOnMap(personsList);
        }
    }

    // To Display one person on map
    public static void displayInYear(int year) {
        personsList.clear();
        SharedPreferenceUtils spu = SharedPreferenceUtils.getInstance();
        ArrayList<Person> myList = new ArrayList<>();
        myList.addAll(spu.getPersonArrayListFromSP());
        for (int i = 0; i < myList.size(); i++) {
            if (myList.get(i).getDateOfBirth() <= year && myList.get(i).getDateOfDeath() > year) {
                personsList.add(myList.get(i));
            }
        }
        for (int i = 0; i < personsList.size(); i++) {
            for (int j = 0 ; j < personsList.get(i).getPersonTravels().size() ; j++){
                if (personsList.get(i).getPersonTravels().get(j).getYearOfTravel() == year){
                    MapActivity.tabsAdapter.drawLineOnMap(personsList.get(i).getLatitude(),personsList.get(i).getLongitude(),
                            personsList.get(i).getPersonTravels().get(j).getLatTravel(),
                            personsList.get(i).getPersonTravels().get(j).getLonTravel());
                    personsList.get(i).setLatitude(myList.get(i).getPersonTravels().get(j).getLatTravel());
                    personsList.get(i).setLongitude(myList.get(i).getPersonTravels().get(j).getLonTravel());
                }
            }
        }
        spu.putPersonArrayListInSP(personsList);
        MapActivity.tabsAdapter.deleteContentInMap();
        MapActivity.tabsAdapter.updatePersonsContentOnMap(personsList);
    }

    //Display All Events On map
    public static void displayAllEventsOnFragments(TabsAdapter tabsAdapter){
        SharedPreferenceUtils spu = SharedPreferenceUtils.getInstance();

        database = FirebaseDatabase.getInstance().getReference("Events"); // To Reach the events path
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Event event = snapshot.getValue(Event.class);
                AllEventsList.add(event);
                eventsList.add(event);
                names.add(event.getEventName());
                spu.putEventArrayListInSP(eventsList);
                tabsAdapter.UpdateEventsContent(eventsList);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // To Display One Events on map
    public static void displaySpYearEventsOnFragments(TabsAdapter tabsAdapter, int year) {
        eventsList.clear();
        MapActivity.tabsAdapter.removeLayers();
        for (int i = 0 ; i < AllEventsList.size() ; i++ ){
            if (year < AllEventsList.get(i).getFromDate() || (year >= AllEventsList.get(i).getToDate()
                    && (AllEventsList.get(i).getToDate() != 0 ))){

            } else if (AllEventsList.get(i).getFromDate() <= year &&
                    (AllEventsList.get(i).getToDate() > year || (AllEventsList.get(i).getToDate() == 0) ) ){
                eventsList.add(AllEventsList.get(i));
                    String[] parts =  AllEventsList.get(i).getCountries().split(",");
                    if (parts.length > 1 ){
                        for (int j = 0 ; j < parts.length ; j++){
                            MapActivity.tabsAdapter.HIGH(parts[j]);
                        }
                    } else if (parts.length == 1 ){
                        MapActivity.tabsAdapter.HIGH(AllEventsList.get(i).getCountries());
                    }

            }
        }
        tabsAdapter.UpdateEventsContentOnMap(eventsList);
    }


    public static ArrayList<String>  GetAllNames (){
        //Toast.makeText(MapActivity.getContextOfApplication(),names.toString(), Toast.LENGTH_SHORT).show();
        return  names ;
    }

    public static Object getByName(String name){
        for (int i=0 ; i<personsList.size() ; i++){
            if (personsList.get(i).getFullName().equalsIgnoreCase(name)){
                return personsList.get(i);
            }
        }
        for (int i=0 ; i<eventsList.size() ; i++){
            if (eventsList.get(i).getEventName().equalsIgnoreCase(name)){
                return eventsList.get(i);
            }
        }
    return null ;
    }
}
