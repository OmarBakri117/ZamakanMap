package com.example.zamakanmap;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.slider.Slider;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import adapters.TabsAdapter;
import Intities.Person;
import uitls.FireBaseUtils;
import uitls.SharedPreferenceUtils;
import uitls.SliderOperation;

public class MapActivity extends AppCompatActivity {

    public static Context contextOfApplication;

    //some layout components
    public static TabsAdapter tabsAdapter;
    static Slider yearsSlider ;
    public static ViewPager viewPager;
    TextView ActualYear ;

    ArrayList<Person> myPersonList ;

    private int[] tabIcons = {
            R.drawable.search_icon,
            R.drawable.tap_map_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To Remove Title Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_map);

        //To Start Application With No Event Or Person Exist in SP
        contextOfApplication = getApplicationContext();

        SharedPreferenceUtils sp = SharedPreferenceUtils.getInstance();
        sp.setEventExist(0);
        sp.setPersonExist(0);

        // Layout setup
        setUpTabs();
        ActualYear = findViewById(R.id.actualYear);
        // Code Setup
        initializeMapActivity();
    }

    /* To Return The Context Of Application */
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    /*--------------------------Tab Layout Part ----------------*/

    private void setUpTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager =findViewById(R.id.view_pager);
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /*----------------------------------------------------------*/
    /*-------------------------- Code Part ---------------------*/
    /*----------------------------------------------------------*/
    public void initializeMapActivity(){
        //initial state Of App
        myPersonList = new ArrayList<>( );
        FireBaseUtils.displayAllPersonsOnFragments(tabsAdapter);
        FireBaseUtils.displayAllEventsOnFragments(tabsAdapter);
        yearsSlider = findViewById(R.id.yearsSlider);

        //Operations on slider
        yearsSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                ActualYear.setText(String.valueOf((int) value));
                // We Create a Thread that Will Do the Function Of Slider separated from main thread
                SliderOperation.operate(MapActivity.this,(int)value);
                /*Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SliderOperation.operate(MapActivity.this,(int)value);
                            }
                        });
                    }
                });
                t1.start();*/
            }
        });
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
