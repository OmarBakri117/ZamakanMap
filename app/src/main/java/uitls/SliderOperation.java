package uitls;

import android.app.Activity;
import android.widget.Toast;

import com.example.zamakanmap.MapActivity;

import java.util.ArrayList;
import java.util.Map;

import classes.CustomToastMessage;
import Intities.Event;
import Intities.Person;

public class SliderOperation {
    private static Person currentPerson = null ;
    private static Event currentEvent = null ;
    private static int times = 0 ;

    public static void setCurrent(Object obj){
        if (obj instanceof Person){
            currentPerson = (Person) obj;
            currentEvent = null ;
            MapActivity.tabsAdapter.removeLayers();
            times = 0 ;
        }else if(obj instanceof Event){
            currentEvent = (Event) obj;
            currentPerson = null ;
            times = 0 ;
            MapActivity.tabsAdapter.removeLayers();
        } else if (obj == null){
            times = 0 ;
            currentPerson = null ;
            currentEvent = null ;
        }
    }


    public static void operate(Activity a , int value ){
        if (currentEvent == null && currentPerson == null){
            operateALL(a,value);
        } else if (currentPerson == null && currentEvent != null){
            OperateOnEvent(a,currentEvent,value);
        }else  if (currentEvent == null && currentPerson != null){
            OperateOnPerson(a,currentPerson,value);
        }
    }

    public static void operateALL(Activity a, int value) {
       //FireBaseUtils.displaySpYearPersonsOnFragments(MapActivity.tabsAdapter,value);
       FireBaseUtils.display(value);
       FireBaseUtils.displaySpYearEventsOnFragments(MapActivity.tabsAdapter,value);
    }
    public static void OperateOnPerson1(Activity a , Person p , int value ) {

    }
    public static void OperateOnPerson(Activity a , Person p , int value ) {
        if (value < p.getDateOfBirth() || value >= p.getDateOfDeath()) {
            MapActivity.tabsAdapter.deleteContentInMap();
        } else {
            if (p.getDateOfBirth() == (int) value) {
                ArrayList<Person> pp = new ArrayList<>();
                pp.add(p);
                MapActivity.tabsAdapter.deleteContentInMap();
                MapActivity.tabsAdapter.updatePersonsContentOnMap(pp);
                MapActivity.tabsAdapter.moveMapCameraTo(
                        p.getLatitude(), p.getLatitude());
                CustomToastMessage cdd = new CustomToastMessage();
                cdd.showDialog(a, "ولد " + p.getFullName() + " في عام " + p.getDateOfBirth());
                //Toast.makeText(MapActivity.this, "Person Born"+p.getDateOfDeath(), Toast.LENGTH_SHORT).show();
            } else if (p.getDateOfDeath() == (int) value) {
                MapActivity.tabsAdapter.deleteContentInMap();
                CustomToastMessage cdd = new CustomToastMessage();
                cdd.showDialog(a, "مات " + p.getFullName() + " في عام " + p.getDateOfDeath());
            } else {
                /*ArrayList<Person> pp2 = new ArrayList<>();
                pp2.add(p);
                MapActivity.tabsAdapter.deleteContentInMap();
                MapActivity.tabsAdapter.updatePersonsContentOnMap(pp2);
                MapActivity.tabsAdapter.moveMapCameraTo(
                        p.getLatitude(), p.getLatitude());*/
                for (int i = 0; i < p.getPersonTravels().size(); i++) {
                    if (p.getPersonTravels().get(i).getYearOfTravel() == (int) value) {
                        Person temp = p;
                        MapActivity.tabsAdapter.drawLineOnMap(temp.getLatitude(), temp.getLongitude(),
                                temp.getPersonTravels().get(i).getLatTravel(), temp.getPersonTravels().get(i).getLonTravel());
                        temp.setLatitude(p.getPersonTravels().get(i).getLatTravel());
                        temp.setLongitude(p.getPersonTravels().get(i).getLonTravel());
                        ArrayList<Person> pp = new ArrayList<>();
                        pp.add(temp);
                        MapActivity.tabsAdapter.deleteContentInMap();
                        MapActivity.tabsAdapter.updatePersonsContentOnMap(pp);
                        MapActivity.tabsAdapter.moveMapCameraTo(
                                p.getLatitude(), p.getLongitude());
                        //CustomToastMessage cdd=new CustomToastMessage();
                        //cdd.showDialog(a, "Traveled In "+p.getPersonTravels().get(i).getYearOfTravel());
                    }
                }
            }

        }
    }
    public static void OperateOnEvent(Activity a , Event e , int value ){
        System.out.println("----------------------------------------------------");
        System.out.println(e.getCountries().toString());
        System.out.println("----------------------------------------------------");
        if (value < e.getFromDate() || (value >= e.getToDate() && (e.getToDate() != 0 ))){
            MapActivity.tabsAdapter.deleteContentInMap();
            MapActivity.tabsAdapter.removeLayers();
            times = 0 ;
        } else if ((int)value >= e.getFromDate()){
            Toast.makeText(MapActivity.getContextOfApplication(), e.getCountries(), Toast.LENGTH_SHORT).show();
           if (times == 0 ){
               String[] parts =  e.getCountries().split(",");
               if (parts.length > 1 ){
                   for (int i = 0 ; i < parts.length ; i++){
                       MapActivity.tabsAdapter.HIGH(parts[i]);
                   }
               } else if (parts.length == 1 ){
                   MapActivity.tabsAdapter.HIGH(e.getCountries());
               }
               times = 1 ;
           }
            ArrayList<Event> events = new ArrayList<>();
            events.add(e);
            MapActivity.tabsAdapter.UpdateEventsContentOnMap(events);
            MapActivity.tabsAdapter.moveMapCameraTo(
                    e.getLatitude(),e.getLongitude());
            /*CustomToastMessage cdd=new CustomToastMessage();
            cdd.showDialog(a, " بدأت الـ  "+e.getEventName()+ " في عام " + e.getFromDate());*/
        }
    }
}
