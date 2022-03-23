package classes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import Intities.Event;

public class EventItemCluster implements ClusterItem {
    private final LatLng position;
    private final String title;
    private final String snippet;
    private final String path ;
    private final Event e  ;

    public EventItemCluster(double lat, double lng, String title, String snippet, String path ,Event e ) {
        this.path = path;
        position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
        this.e = e  ;
    }
    public Event getE() {
        return e;
    }
    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public String getPath() {
        return path;
    }
}
