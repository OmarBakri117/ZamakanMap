package uitls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zamakanmap.MapActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import classes.CustomEventDialogeClass;
import classes.CustomPersonDialogClass;
import classes.EventItemCluster;

public class RenderEventClusterInfoWindow extends DefaultClusterRenderer<EventItemCluster> {

    Context context ;
    GoogleMap mymap ;
    ClusterManager<EventItemCluster> clusterManager;
    Activity a ;
    public RenderEventClusterInfoWindow(Activity a , Context context, GoogleMap map, ClusterManager<EventItemCluster> clusterManager) {
        super(context, map, clusterManager);
        this.context = context ;
        this.clusterManager = clusterManager ;
        this.mymap = map ;
        this.a = a ;
    }
    @Override
    protected void onClusterRendered(Cluster<EventItemCluster> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);

    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    protected void onBeforeClusterItemRendered(EventItemCluster item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        BitmapUtils bt = BitmapUtils.getInstance();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bt.getBitmapFromLink(item.getPath())));

       mymap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                marker.hideInfoWindow();
                LinearLayout info = new LinearLayout(context);
                info.setClickable(false);
                info.setMinimumHeight(70);
                info.setMinimumWidth(80);
                info.setOrientation(LinearLayout.VERTICAL);
                info.setBackgroundColor(Color.parseColor("#ede0cd"));

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                title.setTextColor(Color.parseColor("#563628"));

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());
                snippet.setTextColor(Color.parseColor("#563628"));


                info.addView(title);
                info.addView(snippet);
                return info;
            } });

        /*mymap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker arg0) {
                CustomEventDialogeClass cdd = new CustomEventDialogeClass();
                cdd.showDialog(a,item.getE());

            }
        });*/
    }
}
