package hng.tech.apoe_4.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.pixplicity.easyprefs.library.Prefs;


import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
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

public class Home extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, OnDataPointListener {


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


    @BindView(R.id.editProfile)
    TextView editProfile;

    @BindView(R.id.drawer_signOut)
    RelativeLayout signout;
    
    @BindView(R.id.civ_user_drawer)
    CircleImageView pic;


    @BindView(R.id.weight_drawer)
    TextView weightDrawer;

    @BindView(R.id.height_drawer)
    TextView heightDrawer;

    @BindView(R.id.tv_phone_number_drawer)
    TextView infoDrawer;

    static String gender,height;

    private static final int REQUEST_OAUTH = 1001;

    private static final String TAG = Home.class.getSimpleName();
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;



    private GoogleApiClient googleApiClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location current;
    public  static double lat,lng;
    private boolean mLocationPermissionsGranted;
    SimpleLocation locations;

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .addApi(Fitness.SENSORS_API)  // Required for SensorsApi calls
                // Optional: specify more APIs used with additional calls to addApi
//                .useDefaultAccount()
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        locations = new SimpleLocation(this);
        ButterKnife.bind(this);


        if (!Prefs.getBoolean("fblog", false)){
            patientName.setText((Prefs.getString("firstName", "John") + "\t"
                    + Prefs.getString("lastName", "Doe")));

            userNameDrawer.setText((Prefs.getString("firstName", "John") + "\t"
                    + Prefs.getString("lastName", "Doe")));
        }else{
            patientName.setText((Prefs.getString("firstName", "John") + " "
                    + Prefs.getString("lastName", "Doe")));

            userNameDrawer.setText((Prefs.getString("firstName", "John") + " "
                    + Prefs.getString("lastName", "Doe")));

            Glide.with(this).load(Prefs.getString("url", "")).placeholder(R.drawable.ic_app_icon).into(pic);
        }


//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Fitness.SENSORS_API)  // Required for SensorsApi calls
//                // Optional: specify more APIs used with additional calls to addApi
//                .useDefaultAccount()
//                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
//
//        googleApiClient.connect();

      //set WHG
        setWHGValues();
        calculateAge();

        //get Location Permission
//        getLocationPermission();
        //get device Location






        openFragment(TodayFragment.newInstance(), "today");


//        logoutImageView.setOnClickListener(x -> {
//
//        });

        circleImageView.setOnClickListener(v -> {
            drawer.openDrawer(Gravity.LEFT);
        });

        settings.setOnClickListener(v -> {
            Intent mine = new Intent(Home.this, SettingsActivity.class);
            //startActivity(mine);
            startActivityForResult(mine,101);
            drawer.closeDrawer(GravityCompat.START);
        });

        // Launches edit profile activity
        editProfile.setOnClickListener (v -> {
                startActivity(new Intent(this, EditProfile.class));
                drawer.closeDrawer(GravityCompat.START);
        });


//        schedule.setOnClickListener(v -> {
//
//            drawer.closeDrawer(GravityCompat.START);
//        });

        signout.setOnClickListener(v ->{


            if (GoogleSignIn.getLastSignedInAccount(this) != null){
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
//                        Toast.makeText(Home.this, "You are logged out", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Home.this, LoginActivity.class));
                    }
                });
            }
            //clear all saved data

            Prefs.putString("accessToken", "");
            //I commented this lines of code because its should not request for my DOB whenever I log out and login again
//            Prefs.putBoolean("savedDOB", false);
//            Prefs.putBoolean("selectedWHG", false);
            Prefs.putBoolean("answeredQuestions", false);
            Prefs.putBoolean("que1", false);
            Prefs.putBoolean("fblog", false);
            Prefs.putBoolean("loggedIn", false);
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


    //SETS WHG VALUES
    private void setWHGValues(){
        ArrayList<String> list = DOB_page.loadWHGInfo();

        if(list.isEmpty()){
            weightDrawer.setText("Weight");
            heightDrawer.setText("Height");
            gender = "Gender";
        }else{
            weightDrawer.setText(list.get(0));
            heightDrawer.setText(list.get(1));
            gender = list.get(2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode==RESULT_OK){
                //String replaceHeight = data.getStringExtra("Height");
                ArrayList<String> replaceInfo = data.getStringArrayListExtra("USER_SELECT");

                heightDrawer.setText(replaceInfo.get(1));
                weightDrawer.setText(replaceInfo.get(0));

                Prefs.putString("list_size1",replaceInfo.get(1));
                Prefs.putString("list_size0",replaceInfo.get(0));
            }
        }
    }

    //CALCULATES USERS AGE
    private void calculateAge(){
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(DOB_page.getDOB());

        int currentAge = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            currentAge--;
        }

        String info = gender+", "+currentAge+" years";

        infoDrawer.setText(info);
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
//    private void getDeviceLocation(){
//        Log.d(TAG, "getDeviceLocation: getting the devices current location");
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try{
//            if(mLocationPermissionsGranted){
//
//                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if(task.isSuccessful()){
//                            Log.d(TAG, "onComplete: found location!");
//                            Location currentLocation = (Location) task.getResult();
//                            if (locations.hasLocationEnabled()){
//                                lat = currentLocation.getLatitude();
//                                lng= currentLocation.getLongitude();
//                                Log.d(TAG, "onComplete: found location!");
//                            }else {
//                                // ask the user to enable location access
//                                Toast.makeText(Home.this, "Please enable location", Toast.LENGTH_SHORT).show();
//                                SimpleLocation.openSettings(getApplicationContext());
//                            }
//
//
//
//
//                        }else{
//                            Log.d(TAG, "onComplete: current location is null");
//                            Toast.makeText(Home.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//            }
//        }catch (SecurityException e){
//            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
//        }
//    }

//    private void getLocationPermission(){
//        Log.d(TAG, "getLocationPermission: getting location permissions");
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
//                mLocationPermissionsGranted = true;
//                getDeviceLocation();
//            }else{
//                ActivityCompat.requestPermissions(this,
//                        permissions,
//                        LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        }else{
//            ActivityCompat.requestPermissions(this,
//                    permissions,
//                    LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }

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
//                    getDeviceLocation();
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
