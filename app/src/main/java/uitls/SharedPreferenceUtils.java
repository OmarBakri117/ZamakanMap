package uitls;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.zamakanmap.MapActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import Intities.Event;
import Intities.Person;

public class SharedPreferenceUtils {

    public static final String PERSON_KEY = "persons"; //For All Persons
    public static final String EVENT_KEY = "events";  // For All Events
    public static final String PERSON_ONLY_KEY = "PERSON"; // For One Person In SP
    public static final String EVENT_ONLY_KEY = "EVENT";  // For One Event In Sp
    public static final String PERSON_EXIST = "person_exist" ; // Is There A person
    public static final String EVENT_EXIST = "event_exist" ; // Is There A Event

    /* Singleton Implementation */
    private static SharedPreferenceUtils obj;

    private SharedPreferenceUtils() { }

    public static SharedPreferenceUtils getInstance() {
        if (obj == null) {
            obj = new SharedPreferenceUtils();
            obj.createSPInstance();
        }
        return obj;
    }
    private void createSPInstance() {
        prefs = PreferenceManager.getDefaultSharedPreferences(MapActivity.getContextOfApplication());
    }


    // SharedPreference Utils
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    /* To STORE All Persons In SP */
    public void putPersonArrayListInSP(ArrayList<Person> arrayList){
        editor = prefs.edit();
        Gson gson = new Gson();
        if (arrayList!=null){
            String jsonText = gson.toJson(arrayList);
            editor.putString(PERSON_KEY, jsonText);
            editor.apply();
        }
    }

    /* Persons Utils Part */
    /* To GET All Persons In SP */
    public ArrayList<Person> getPersonArrayListFromSP(){
        Gson gson = new Gson();
        String jsonText = prefs.getString(PERSON_KEY,null);
        ArrayList<Person> result = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Person>>() {}.getType();
        result = gson.fromJson(jsonText,type);
        if (result != null){
            return result;
        }
        return null ;
    }
    /* To Store ONE Persons In SP */
    public void putPersonOnlyInSP(Person p ){
        Gson gson = new Gson();
        editor = prefs.edit();
        if (p != null){
            System.out.println("PERSON To PUT"+p.toString());
            String jsonText = gson.toJson(p);
            System.out.println(jsonText);
            editor.putString(PERSON_ONLY_KEY,jsonText);
            //System.out.println("PERSON PUTTED"+getPersonOnlyFromSP().toString());
            editor.apply();
            setPersonExist(1);
        }
    }
    /* To GET ONE Persons From SP */
    public Person getPersonOnlyFromSP(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Person>>() {}.getType();
        String jsonText = prefs.getString(PERSON_ONLY_KEY,null);
        System.out.println(jsonText);
        Person myp = new Person();
        myp = gson.fromJson(jsonText,Person.class);
        return myp ;
    }
    /* To Store person Exist  */
    public void setPersonExist(int value){
        editor = prefs.edit();
        editor.putInt(PERSON_EXIST,value);
        editor.apply();
    }
    public int getPersonExist(){
        int e = prefs.getInt(PERSON_EXIST,0);
        return e ;
    }

    /* Events Utils Part */
    /* To Store All EVENTS In SP */
    public void putEventArrayListInSP(ArrayList<Event> arrayList){
        editor = prefs.edit();
        Gson gson = new Gson();
        if (arrayList!=null){
            String jsonText = gson.toJson(arrayList);
            editor.putString(EVENT_KEY, jsonText);
            editor.apply();
        }
    }

    /* To GET All Persons In SP */
    public ArrayList<Event> getEventArrayListFromSP(){
        Gson gson = new Gson();
        String jsonText = prefs.getString(EVENT_KEY,null);
        ArrayList<Event> result = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        result = gson.fromJson(jsonText,type);
        if (result != null){
            return result;
        }
        return null ;
    }

    /* To Store One Event In SP */
    public void putEventOnlyInSP(Event e ){
        Gson gson = new Gson();
        editor = prefs.edit();
        if (e != null){
            String jsonText = gson.toJson(e);
            editor.putString(EVENT_ONLY_KEY,jsonText);
            editor.apply();
            setEventExist(1);
        }
    }
    /* To GET One Event In SP */
    public Event getEventOnlyFromSP(){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Event>>() {}.getType();
        String jsonText = prefs.getString(EVENT_ONLY_KEY,null);
        Event mye = new Event();
        mye = gson.fromJson(jsonText,Event.class);
        return mye ;
        /*ArrayList<Event> result =  new ArrayList<>();
        result.addAll(gson.fromJson(jsonText,type));
        return result.get(0) ;*/
    }
    public void setEventExist(int value){
        editor = prefs.edit();
        editor.putInt(EVENT_EXIST,value);
        editor.apply();
    }
    public int getEventExist(){
        int e = prefs.getInt(EVENT_EXIST,0);
        return e ;
    }

}
