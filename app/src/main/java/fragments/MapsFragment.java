package fragments;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.loader.AssetsProvider;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.google.maps.android.data.kml.KmlLayer;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;

import javax.sql.DataSource;

import Intities.Event;
import Intities.MapLine;
import classes.EventItemCluster;
import Intities.Person;
import classes.PersonItemCluster;
import uitls.RenderClusterInfoWindow;
import uitls.RenderEventClusterInfoWindow;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback, BaseFragment{


    private ClusterManager<PersonItemCluster> clusterManager;
    private ClusterManager<EventItemCluster> eventClusterManager;

    private boolean needsInit = false;
    private GoogleMap mymap;
    private ArrayList<Person> persons;
    private ArrayList<Event> events ;

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if (android.os.Build.VERSION.SDK_INT > 16) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            if (savedInstanceState == null) {
                needsInit = true;
            }
            getMapAsync(this);
        }

        @SuppressLint("PotentialBehaviorOverride")
        @Override
        public void onMapReady(final GoogleMap map) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            this.mymap = map;
            clusterManager = new ClusterManager<>(getContext(), mymap);
            eventClusterManager = new ClusterManager<>(getContext(),mymap);
            mymap.setOnCameraIdleListener(clusterManager);
           mymap.setOnMarkerClickListener(clusterManager);
            mymap.setOnInfoWindowClickListener(eventClusterManager);

            if (needsInit) {
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(new LatLng(30.0444,
                                31.2357));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);
                mymap.moveCamera(center);
                mymap.animateCamera(zoom);
            }

            updatePersonsMapContent();
        }

        @Override
        public void updatePersonList(ArrayList<Person> personslist) {

            if (persons == null)
                persons = new ArrayList<>();
            persons.clear();
            persons.addAll(personslist);
            updatePersonsMapContent();
        }

        @Override
        public void updateEventsList(ArrayList<Event> myevents) {
            if (events == null)
                events = new ArrayList<>();
            events.clear();
            events.addAll(myevents);
            updateEventsMapContent();
        }

    @Override
    public void deleteContent() {
        clusterManager.clearItems();
        clusterManager.cluster();
        eventClusterManager.clearItems();
        eventClusterManager.cluster();
    }


    private void updatePersonsMapContent() {
            if (persons == null || mymap == null) return;
            clusterManager.clearItems();
            for (int i = 0; i < persons.size(); i++) {
                clusterManager.setRenderer(new RenderClusterInfoWindow(getActivity(),getContext(), mymap, clusterManager));
                clusterManager.addItem(new PersonItemCluster(persons.get(i).getLatitude(),persons.get(i).getLongitude()
                        , persons.get(i).getFullName(),
                        persons.get(i).getBriefDescription(),persons.get(i).getPictureURL(),persons.get(i)));
            }
            clusterManager.cluster();
        }

    private void updateEventsMapContent(){
        if (events == null || mymap == null) return;
        eventClusterManager.clearItems();
            for (int i = 0; i < events.size(); i++) {
                eventClusterManager.setRenderer(new RenderEventClusterInfoWindow(getActivity(),getContext(), mymap, eventClusterManager));
                eventClusterManager.addItem(new EventItemCluster(events.get(i).getLatitude(),events.get(i).getLongitude()
                        , events.get(i).getEventName(),
                        events.get(i).getBio(),events.get(i).getPicture(),events.get(i)));
            }
            eventClusterManager.cluster();
        }

        public void moveCameraTo(double lat , double lon ){
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(2);
            mymap.moveCamera(center);
            mymap.animateCamera(zoom);
        }

        public void drawLine( double d1 , double d2 , double d3 , double d4 ){
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Polyline line = mymap.addPolyline(new PolylineOptions()
                    .add(new LatLng(d1, d2), new LatLng(d3, d4))
                    .width(20)
                    .color(color));
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  line.remove();
                }
            }, 2000);
        }

        public static  ArrayList<GeoJsonLayer> layers = new ArrayList<>();
        public void highlightCountry(String name){
            int t = 0 ;

            if (name.equalsIgnoreCase("europe")){
                t = R.raw.europe ;
            }else if (name.equalsIgnoreCase("asia")) {
                t = R.raw.asia ;
            } else if (name.equalsIgnoreCase("egypt")){
                t = R.raw.egypt;
            } else if (name.equalsIgnoreCase("america")){
                t = R.raw.america ;
            } else if (name.equalsIgnoreCase("palestine")){
                t = R.raw.palestine ;
            } else if (name.equalsIgnoreCase("kwait")){
                t = R.raw.kwait ;
            } else if (name.equalsIgnoreCase("uk")){
                t = R.raw.uk ;
            } else if (name.equalsIgnoreCase("syria")){
                t = R.raw.syria ;
            } else if (name.equalsIgnoreCase("iraq")){
                t = R.raw.iraq ;
            } else if (name.equalsIgnoreCase("jordan")){
                t = R.raw.jordan ;
            } else if (name.equalsIgnoreCase("lebanon")){
                t = R.raw.lebanon ;
            } else if (name.equalsIgnoreCase("saudi")){
                t = R.raw.saudi ;
            }
            try {
                GeoJsonLayer layer = new GeoJsonLayer(mymap, t, MapActivity.getContextOfApplication());
                if (layer == null ){
                    Toast.makeText(MapActivity.getContextOfApplication(), "NULL", Toast.LENGTH_SHORT).show();
                }else {
                    layers.add(layer);
                    GeoJsonPolygonStyle style = new GeoJsonPolygonStyle();
                    style = layer.getDefaultPolygonStyle();
                    style.setFillColor(0x80ff0000);
                    style.setStrokeColor(Color.MAGENTA);
                    style.setStrokeWidth(1F);
                    layer.addLayerToMap();

                }
            } catch (IOException ex) {
                Log.e("IOException", ex.getLocalizedMessage());
            } catch (JSONException ex) {
                Log.e("JSONException", ex.getLocalizedMessage());
            }
        }

        public void clearLayers(){
            for (int i = 0 ; i < layers.size() ; i++){
                layers.get(i).removeLayerFromMap();
            }
        }
}