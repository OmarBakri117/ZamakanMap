package adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zamakanmap.MapActivity;
import com.example.zamakanmap.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Intities.Person;
import classes.CustomPersonDialogClass;
import fragments.ItemClickListener;
import uitls.SharedPreferenceUtils;
import uitls.SliderOperation;


public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private ItemClickListener mListener;
    private ArrayList<Person> personList;
    Activity a ;

    public CaptionedImagesAdapter(Activity a , ArrayList<Person> persons) {
        this.personList = new ArrayList<>(persons);
        this.a = a ;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*
        Here We Will Give The Values Of Card To a Card
         */
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.image);
        Picasso.get().load("" + personList.get(position).getPictureURL()).into(imageView);

        TextView txt = (TextView) cardView.findViewById(R.id.empName);
        txt.setText(personList.get(position).getFullName());

        TextView txtTwo = (TextView) cardView.findViewById(R.id.empEmail);
        String upToNCharacters = personList.get(position).getBriefDescription().substring
                (0, Math.min(personList.get(position).getBriefDescription().length(),100));
        txtTwo.setText(upToNCharacters + "  ....   ");

       Button profileBtn = (Button) cardView.findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPersonDialogClass cdd = new CustomPersonDialogClass();

                cdd.showDialog(a,personList.get(position));
            }
        });
        cardView.setOnClickListener(v -> {
            MapActivity.tabsAdapter.deleteContentInMap();
            /*SharedPreferenceUtils sp = SharedPreferenceUtils.getInstance();
            sp.putPersonOnlyInSP(personList.get(position));
            sp.setEventExist(0);*/
            SliderOperation.setCurrent(personList.get(position));
            MapActivity.viewPager.setCurrentItem(1);
        });
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }


    /*
    Here We Will Deal With Updates on Data
     */
    public void updatePersonList(ArrayList<Person> persons) {
        if (this.personList == null) {
            this.personList = new ArrayList<>();
        } else {
            personList.removeAll(personList);
        }

        personList.addAll(persons);
        notifyDataSetChanged();
    }
    public void deletePersonList() {
        if (this.personList == null) {
            this.personList = new ArrayList<>();
        } else {
            personList.removeAll(personList);
        }
        notifyDataSetChanged();
    }

}