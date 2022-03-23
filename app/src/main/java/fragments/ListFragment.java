package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;

import java.util.ArrayList;

import adapters.CaptionedImagesAdapter;
import adapters.EventCaptionedImagesAdapter;
import classes.CustomEventDialogeClass;
import Intities.Event;
import Intities.Person;
import uitls.FireBaseUtils;
import uitls.SharedPreferenceUtils;
import uitls.SliderOperation;

public class ListFragment extends Fragment implements BaseFragment, ItemClickListener {

    AutoCompleteTextView sampleAC ;
    RecyclerView personRecycler;
    RecyclerView eventRecycler ;
    CaptionedImagesAdapter personAdapter;
    EventCaptionedImagesAdapter eventAdapter;
    View view;
    Button displayAll ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);
        personRecycler = view.findViewById(R.id.myRecycler);
        personRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        personAdapter = new CaptionedImagesAdapter(getActivity(),new ArrayList<>());
        personAdapter.setItemClickListener(ListFragment.this);
        personRecycler.setAdapter(personAdapter);

        eventRecycler = view.findViewById(R.id.eventsRecycler);
        eventRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));
        eventAdapter = new EventCaptionedImagesAdapter(getActivity(),new ArrayList<>());
        eventAdapter.setItemClickListener(ListFragment.this);
        eventRecycler.setAdapter(eventAdapter);

        sampleAC = view.findViewById(R.id.simpleAC);
        ArrayAdapter adapter = new ArrayAdapter(MapActivity.getContextOfApplication(), android.R.layout.simple_list_item_1, FireBaseUtils.GetAllNames());

        sampleAC.setAdapter(adapter);
        sampleAC.setThreshold(1);//start searching from 1 character
        sampleAC.setAdapter(adapter);

        //Display All Button
        displayAll = view.findViewById(R.id.displayAll);
        displayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleAC.setText("");
                MapActivity.tabsAdapter.deleteContentInMap();
                FireBaseUtils.DisplayAllOnMap(MapActivity.tabsAdapter);
                //SharedPreferenceUtils sp = SharedPreferenceUtils.getInstance();
                //sp.setPersonExist(0);
                SliderOperation.setCurrent(null);
                MapActivity.hideKeyboardFrom(getContext(),v);
                //MapActivity.viewPager.setCurrentItem(1);
            }
        });
        // Search Button
        Button search_btn = view.findViewById(R.id.Search_Btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity.hideKeyboardFrom(getContext(),v);
                ArrayList<Person> myp = new ArrayList<>();
                ArrayList<Event> mye = new ArrayList<>();
                String text = sampleAC.getText().toString() ;
                Object obj = FireBaseUtils.getByName(text);
                //Toast.makeText(MapActivity.getContextOfApplication(),obj.toString(), Toast.LENGTH_SHORT).show();
                if (obj instanceof Person){
                    myp.add((Person) obj);
                    MapActivity.tabsAdapter.updatePersonsContent(myp);
                    MapActivity.tabsAdapter.UpdateEventsContent(mye);
                } else if (obj instanceof Event){
                    mye.add((Event) obj);
                    MapActivity.tabsAdapter.updatePersonsContent(myp);
                    MapActivity.tabsAdapter.UpdateEventsContent(mye);
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClickListener(Person person) {
        ArrayList<Person> arrayList = new ArrayList<>();
        arrayList.add(person);
        MapsFragment fragment = new MapsFragment();
        fragment.updatePersonList(arrayList);
    }

    @Override
    public void onItemClickListener(Event event) {
        CustomEventDialogeClass cdd = new CustomEventDialogeClass();
        cdd.showDialog(getActivity(),event);
    }

    public void updatePersonList(ArrayList<Person> persons) {
        personAdapter.updatePersonList(persons);
    }

    @Override
    public void updateEventsList(ArrayList<Event> events) {
        eventAdapter.updateEventsList(events);
    }

    @Override
    public void deleteContent() {
        personAdapter.deletePersonList();
    }

}