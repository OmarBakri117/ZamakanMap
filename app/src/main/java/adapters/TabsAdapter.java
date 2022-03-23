package adapters;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.zamakanmap.MapActivity;

import java.util.ArrayList;

import Intities.Event;
import Intities.Person;
import fragments.MapsFragment;
import fragments.ListFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    MapsFragment mapfrag;
    ListFragment listfrag;

    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
        mapfrag = new MapsFragment();
        listfrag = new ListFragment();

    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                return listfrag;
            case 1:
                return mapfrag;
            default:
                return null;
        }
    }

    /*
    To Deal With Updates And Notify the fragments With Updates
     */

   public void updatePersonsContent(ArrayList<Person> persons) {
        mapfrag.updatePersonList(persons);
        listfrag.updatePersonList(persons);
    }

    public void UpdateEventsContent(ArrayList<Event> events){
        listfrag.updateEventsList(events);
        mapfrag.updateEventsList(events);
    }

    /* MapFragment Functions */
    public void updatePersonsContentOnMap(ArrayList<Person> persons) {
        mapfrag.updatePersonList(persons);
    }
    public void deleteContentInMap(){
        mapfrag.deleteContent();
    }


    public void moveMapCameraTo(double lat , double lon){
        mapfrag.moveCameraTo(lat,lon);
    }

    /* ListFragment Functions */
    public void UpdateEventsContentOnMap(ArrayList<Event> events){
        mapfrag.updateEventsList(events);
    }

    public void drawLineOnMap(double d1 , double d2 , double d3 , double d4 ){
        mapfrag.drawLine(d1,d2,d3,d4);
    }
    public void HIGH(String name){
        mapfrag.highlightCountry(name);
    }

    public void removeLayers(){
        mapfrag.clearLayers();
    }
}
