package hng.tech.apoe_4.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.utils.ProgressAnim;

import im.delight.android.location.SimpleLocation;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodayFragment extends Fragment {

    @BindView(R.id.tempProgress)
    ProgressBar tempProgress;

    @BindView(R.id.stepsProgress)
    ProgressBar stepsProgress;

    @BindView(R.id.sleepProgress)
    ProgressBar sleepProgress;

    @BindView(R.id.temp)
    TextView tempText;

    private float from = (float)10;
    private float to;
    private String temp;

    double progress, lat, lng;
    SimpleLocation location;
    Context mContext = getActivity();
    char degree = '\u00B0';

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    421);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 421:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    location.beginUpdates();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;

            }

            default:
                return;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        

// construct a new instance of SimpleLocation
        location = new SimpleLocation(getActivity());

        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            Toast.makeText(getActivity(), "Enable Location Permission", Toast.LENGTH_LONG).show();
            SimpleLocation.openSettings(getActivity());
        }else {
            lat  =location.getLatitude();
            lng= location.getLongitude();
        }


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        Context context = inflater.getContext();
        ButterKnife.bind(this, view);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl("https://fcc-weather-api.glitch.me/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        ApiInterface apiInterface = mRetrofit.create(ApiInterface.class);

        apiInterface.getWeather(lat, lng).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    double temp = response.body().getMain().getTemp();
                    double tempMax = response.body().getMain().getTempMax();
                    Log.d("TAG", "temp: " + temp);
                    Log.d("TAG", "tempMax: " + tempMax);


                    progress = (temp / tempMax) * 100;
                    Log.d("TAG", "progress: " + progress);

//                    tempProgress.setProgress((int) progress);
                    setAnimation();

                    tempText.setText(String.valueOf((int) temp) + degree +"C");


                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }
        });

        getSleepTime();
        getStepNumber();


        return view;
    }

    //this method helps with animating progress bar

    private void setAnimation() {
        to = (float)progress;
        ProgressAnim anim = new ProgressAnim(tempProgress, from, to);
        anim.setDuration(2000);
        tempProgress.startAnimation(anim);
    }

    //this method the amount of sleep later
    private  void getSleepTime () {
        to = (float)80;
        ProgressAnim anim = new ProgressAnim(sleepProgress, from, to);
        anim.setDuration(2000);
        sleepProgress.startAnimation(anim);
    }

    //this method the amount of steps later
    private  void getStepNumber () {
        to = (float)90;
        ProgressAnim anim = new ProgressAnim(stepsProgress, from, to);
        anim.setDuration(2000);
        stepsProgress.startAnimation(anim);
    }

    public static TodayFragment newInstance() {
        return new TodayFragment();
    }
}
