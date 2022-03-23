package fragments;

import java.util.ArrayList;

import Intities.Event;
import Intities.Person;

public interface BaseFragment {
        void updatePersonList(ArrayList<Person> persons);
        void updateEventsList(ArrayList<Event> events);
        void deleteContent();

}
