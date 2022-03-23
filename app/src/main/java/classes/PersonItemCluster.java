package classes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import Intities.Person;

/*
This is an class to deal with a map and display users to the map
 */
public class PersonItemCluster implements ClusterItem {
    private final LatLng position;
    private final String title;
    private final String snippet;
    private final String path ;
    private final Person p  ;

    public PersonItemCluster(double lat, double lng, String title, String snippet, String path , Person p ) {
        this.path = path;
        position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
        this.p = p  ;
    }
    public Person getP() {
        return p;
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