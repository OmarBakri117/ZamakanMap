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

import java.sql.BatchUpdateException;

import Intities.Person;

public class CustomPersonDialogClass {

    Person person ;
    TextView nameAndCat , Description , DateOfBirth , DateOfDeath , Country , personCat ;
    ImageView profilePic,closeImg ;
    Button goToRef , cls_me;

    public void showDialog(Activity a,Person person) {

        this.person = person ;
        final Dialog dialog = new Dialog(a);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.person_custom_dialoge);
        nameAndCat = dialog.findViewById(R.id.event_name_d);
        Description = dialog.findViewById(R.id.event_bio);
        DateOfBirth = dialog.findViewById(R.id.start_d);
        DateOfDeath = dialog.findViewById(R.id.end_d);
        Country = dialog.findViewById(R.id.country);
        profilePic = dialog.findViewById(R.id.event_pic);
        personCat = dialog.findViewById(R.id.personCat);
        goToRef = dialog.findViewById(R.id.goToRef);
        goToRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Toast.makeText(MapActivity.getContextOfApplication(), "IN", Toast.LENGTH_SHORT).show();
                Intent sharingIntent = new Intent(Intent.ACTION_VIEW);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sharingIntent.setData(Uri.parse(person.getRefLink()));

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
    nameAndCat.setText(person.getFullName());
    personCat.setText(person.getCategory());
    DateOfBirth.setText(String.valueOf(person.getDateOfBirth()));
    DateOfDeath.setText(String.valueOf(person.getDateOfDeath()));
    Country.setText(person.getCountry());
    Description.setText(person.getBriefDescription());
    Picasso.get().load("" + person.getPictureURL()).into(profilePic);
    }
}
