package classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;

public class CustomToastMessage {

    public void showDialog(Activity activity, String msg){

        Toast toast = new Toast(MapActivity.getContextOfApplication());

        View view = LayoutInflater.from(MapActivity.getContextOfApplication())
                .inflate(R.layout.custom_toast_layout, null);

        TextView text = (TextView) view.findViewById(R.id.tvMessage);
        text.setText(msg);

        toast.setView(view);
        toast.show();

}
}