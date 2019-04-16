package hng.tech.apoe_4.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pixplicity.easyprefs.library.Prefs;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.fragments.ForumFragment;
import hng.tech.apoe_4.fragments.ResultsFragment;
import hng.tech.apoe_4.fragments.TodayFragment;
import hng.tech.apoe_4.retrofit.responses.DailyResponse;
import hng.tech.apoe_4.retrofit.responses.DailyResponseData;
import hng.tech.apoe_4.retrofit.responses.dailyQuestions;
import hng.tech.apoe_4.utils.MainApplication;
import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 111;
    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    private int count = 0;

//    @BindView(R.id.logout)
//    ImageView logoutImageView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.navigation)
    NavigationView navigationView;

    @BindView(R.id.circleImageView)
    ImageView circleImageView;

    @BindView(R.id.patientName)
    TextView patientName;

    @BindView(R.id.tv_username_drawer)
    TextView userNameDrawer;

    @BindView(R.id.settings)
    RelativeLayout settings;

    @BindView(R.id.schedule)
    RelativeLayout schedule;

    @BindView(R.id.drawer_signOut)
    RelativeLayout signout;



    private static final String TAG = Home.class.getSimpleName();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;



    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location current;
    public  static double lat,lng;
    private boolean mLocationPermissionsGranted;
    SimpleLocation locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        locations = new SimpleLocation(this);
        ButterKnife.bind(this);

        patientName.setText(Prefs.getString("firstName", "John") + "\t"
        + Prefs.getString("lastName", "Doe"));

        userNameDrawer.setText(Prefs.getString("firstName", "John") + "\t"
                + Prefs.getString("lastName", "Doe"));
        //get Location Permission
        getLocationPermission();
        //get device Location






        openFragment(TodayFragment.newInstance(), "today");


//        logoutImageView.setOnClickListener(x -> {
//
//        });

        circleImageView.setOnClickListener(v -> {
            drawer.openDrawer(Gravity.LEFT);
        });

        settings.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, SettingsActivity.class));
            drawer.closeDrawer(GravityCompat.START);
        });

        schedule.setOnClickListener(v -> {

            drawer.closeDrawer(GravityCompat.START);
        });

        signout.setOnClickListener(v ->{

            Prefs.putString("accessToken", "");
            Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();

            drawer.closeDrawer(GravityCompat.START);
        });

        BottomNavigationView.OnNavigationItemSelectedListener listener = item -> {
            switch (item.getItemId()){
                case R.id.navigation_results:{
                    openFragment(ResultsFragment.newInstance(), "result");
                    return true;
                }

                case R.id.navigation_today:{
                    openFragment(TodayFragment.newInstance(), "today");
                    return true;
                }

                case R.id.navigation_forum:{
                    openFragment(ForumFragment.newInstance(), "forum");
                    return true;
                }

                default:{
                    openFragment(ResultsFragment.newInstance(), "result");
                    return true;
                }
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(listener);


    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            if (locations.hasLocationEnabled()){
                                lat = currentLocation.getLatitude();
                                lng= currentLocation.getLongitude();
                                Log.d(TAG, "onComplete: found location!");
                            }else {
                                // ask the user to enable location access
                                Toast.makeText(Home.this, "Please enable location", Toast.LENGTH_SHORT).show();
                                SimpleLocation.openSettings(getApplicationContext());
                            }




                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(Home.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                mLocationPermissionsGranted = true;
                getDeviceLocation();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    getDeviceLocation();
                    //initialize our map
                }
            }
        }
    }

    private void openFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        Log.d("TAG","fragment tag: "+fragment.getTag());
        transaction.commit();
    }
//this method helps to handle backpress between fragments
    private void pressingBack() {

        TodayFragment todayFragment = (TodayFragment) getSupportFragmentManager().findFragmentByTag("today");

           if (todayFragment != null && todayFragment.isVisible()) {

               finishAffinity();
            }
            else {

               openFragment(TodayFragment.newInstance(), "today");
            }
    }

    @Override
    public void onBackPressed() {
        pressingBack();
    }
// this method post the answers to server when the submmit button is clicked
    public void SubmitQuestions(View view) {
        String day = Prefs.getString("day_answer", "");
        String night = Prefs.getString("night_answer", "");
        String plannedActivity_answer = Prefs.getString("plannedActivity_answer", "");
        Boolean reminders_answer = Prefs.getBoolean("reminders_answer", false);
        Log.d(TAG, "SubmitQuestions: " + day + night + plannedActivity_answer + reminders_answer);

         MainApplication.getApiInterface().dailyQ(new dailyQuestions(day, night,
                 plannedActivity_answer, reminders_answer)).
                enqueue(new Callback<DailyResponse>() {
                    @Override
                    public void onResponse(Call<DailyResponse> call, Response<DailyResponse> response) {
                        if (response.code()== 200){

                            DailyResponseData data = response.body().getData();
                            String day = data.getDay();
                            String night = data.getNight();
                            String plannedActivities = data.getPlannedActivities();
                            String reminders;
                            if (data.getReminders() == true) {
                                reminders = "Yes";
                            } else {
                                reminders = "No";
                            }

                            Log.d(TAG, "onResponse-> " + day + night + plannedActivities +
                                    reminders);
                            Toast.makeText(Home.this,"Your Answers have been submited",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(Home.this, "response: error" ,Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<DailyResponse> call, Throwable t) {
                        Toast.makeText(Home.this, t.getMessage() ,Toast.LENGTH_SHORT).show();

                    }
                });

    }
}
