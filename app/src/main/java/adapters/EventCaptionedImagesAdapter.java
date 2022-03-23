package adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Intities.Event;
import classes.CustomEventDialogeClass;
import classes.CustomPersonDialogClass;
import fragments.ItemClickListener;
import uitls.SharedPreferenceUtils;
import uitls.SliderOperation;

public class EventCaptionedImagesAdapter  extends RecyclerView.Adapter<EventCaptionedImagesAdapter.ViewHolder> {
    //shared preference item
    private ItemClickListener mListener;
    Activity a ;

    private ArrayList<Event> eventsList;

    public EventCaptionedImagesAdapter(Activity a , ArrayList<Event> events) {
        this.eventsList = new ArrayList<>(events);
        this.a = a ;
    }
    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }
    @Override
    public EventCaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image, parent, false);
        return new EventCaptionedImagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventCaptionedImagesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*
        Here We Will Give The Values Of Card To a Card
         */
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Picasso.get().load("" + eventsList.get(position).getPicture()).into(imageView);

        TextView txt = (TextView) cardView.findViewById(R.id.empName);
        txt.setText(eventsList.get(position).getEventName());

        TextView txtTwo = (TextView) cardView.findViewById(R.id.empEmail);
        String upToNCharacters = eventsList.get(position).getBio().substring
                (0, Math.min(eventsList.get(position).getBio().length(),100));
        txtTwo.setText(upToNCharacters + "  ....   ");
        txtTwo.setText(upToNCharacters);
        Button profileBtn = (Button) cardView.findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomEventDialogeClass cdd = new CustomEventDialogeClass();

                cdd.showDialog(a,eventsList.get(position));
            }
        });
        cardView.setOnClickListener(v -> {
           MapActivity.tabsAdapter.deleteContentInMap();
           /* SharedPreferenceUtils sp = SharedPreferenceUtils.getInstance();
            sp.putEventOnlyInSP(eventsList.get(position));
            sp.setPersonExist(0);*/
            SliderOperation.setCurrent(eventsList.get(position));
            MapActivity.viewPager.setCurrentItem(1);
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    /*
    Here We Will Deal With Updates on Data
    */
   public void updateEventsList(ArrayList<Event> events) {
        if (this.eventsList == null) {
            this.eventsList = new ArrayList<>();
        } else {
            eventsList.removeAll(eventsList);
        }

        eventsList.addAll(events);
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
