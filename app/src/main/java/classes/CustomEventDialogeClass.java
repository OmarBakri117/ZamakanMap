package classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;
import com.squareup.picasso.Picasso;

import Intities.Event;

public class CustomEventDialogeClass {
    Event event ;
    TextView eventName , eventBio ,eventStart , eventEnd , country ;
    ImageView eventPic ,closeImg ;
    Button goToRef , cls_me;

    public void showDialog(Activity a, Event event) {
        this.event = event ;
        final Dialog dialog = new Dialog(a);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.event_custom_dialoge);
        eventName = dialog.findViewById(R.id.event_name_d);
        eventBio = dialog.findViewById(R.id.event_bio);
        eventStart = dialog.findViewById(R.id.start_d);
        eventEnd = dialog.findViewById(R.id.end_d);
        eventPic = dialog.findViewById(R.id.event_pic);
        goToRef = dialog.findViewById(R.id.goToRef);
        goToRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.getContextOfApplication(), "IN", Toast.LENGTH_SHORT).show();
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse(event.getRefLink()));

                Intent chooserIntent = Intent.createChooser(sharingIntent, "Open With");
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                MapActivity.getContextOfApplication().startActivity(chooserIntent);
            }
        });

        cls_me = dialog.findViewById(R.id.cls_me);
        cls_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        setValuesOnComponents();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void setValuesOnComponents() {
        eventName.setText(event.getEventName());
        eventStart.setText(String.valueOf(event.getFromDate()));
        eventEnd.setText(String.valueOf(event.getToDate()));
        eventBio.setText(event.getBio() + " "+ event.getDescription());
        Picasso.get().load("" + event.getPicture()).into(eventPic);

    }
}
