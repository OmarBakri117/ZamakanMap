package uitls;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import Intities.Person;
import classes.CustomPersonDialogClass;
import classes.PersonItemCluster;

public class RenderClusterInfoWindow extends DefaultClusterRenderer<PersonItemCluster> {

    Context context ;
    GoogleMap mymap ;
    ClusterManager<PersonItemCluster> clusterManager;
    Activity a;
    Person person1 ;
    public RenderClusterInfoWindow(Activity a ,Context context, GoogleMap map, ClusterManager<PersonItemCluster> clusterManager) {
            super(context, map, clusterManager);
            this.context = context ;
            this.clusterManager = clusterManager ;
            this.mymap = map ;
            this.a = a ;
        }

        @Override
        protected void onClusterRendered(Cluster<PersonItemCluster> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);

        }

        @SuppressLint("PotentialBehaviorOverride")
        @Override
        protected void onBeforeClusterItemRendered(PersonItemCluster item, MarkerOptions markerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions);
            BitmapUtils bt = BitmapUtils.getInstance();
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bt.getBitmapFromLink(item.getPath())));
            //Toast.makeText(MapActivity.getContextOfApplication(), item.getP().getFullName(), Toast.LENGTH_SHORT).show();
            person1 = item.getP();

            mymap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
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

          /* mymap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker arg0) {
                    Toast.makeText(MapActivity.getContextOfApplication(), item.getP().getFullName(), Toast.LENGTH_SHORT).show();
                    CustomPersonDialogClass cdd = new CustomPersonDialogClass();
                    cdd.showDialog(a,person1);
                }
            });*/
        }
}
