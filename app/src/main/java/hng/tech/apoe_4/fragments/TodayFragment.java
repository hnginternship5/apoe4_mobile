package hng.tech.apoe_4.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.adapters.QuestionAdapter;
import hng.tech.apoe_4.models.AnswerData;
import hng.tech.apoe_4.models.Question;
import hng.tech.apoe_4.models.QuestionData;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.Main;
import hng.tech.apoe_4.retrofit.responses.QuestionServed;
import hng.tech.apoe_4.retrofit.responses.QuestionsResponse;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import hng.tech.apoe_4.utils.CONSTANTS;
import hng.tech.apoe_4.utils.DataUtil;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.utils.PermisionManager;
import hng.tech.apoe_4.utils.ProgressAnim;
import im.delight.android.location.SimpleLocation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static hng.tech.apoe_4.activities.Home.lat;
import static hng.tech.apoe_4.activities.Home.lng;


public class TodayFragment extends Fragment {

    @BindView(R.id.exerciseProgress)
    ProgressBar tempProgress;

    @BindView(R.id.progress_APO)
    ProgressBar stepsProgress;

    @BindView(R.id.sleepProgress)
    ProgressBar sleepProgress;

    @BindView(R.id.questions_view)
    LinearLayout questionsLayout;

    @BindView(R.id.temp)
    TextView tempText;

    boolean questionAvailable = false;

    LayoutInflater genInflater;


    List<QuestionsResponse> questions = new ArrayList<>(Arrays.asList(
            new QuestionsResponse("How are you today?", Arrays.asList("Great", "Good", "Ok", "Bad")),
            new QuestionsResponse("How was your night?", Arrays.asList("Great", "Good", "Ok", "Bad")),
            new QuestionsResponse("What do ypu think of this App?", Arrays.asList("Awesome", "Awesome", "Awesome", "Awesome")),
            new QuestionsResponse("How are you today?", Arrays.asList("Great", "Good", "Ok", "Bad"))
    ));



    private float from = (float)10;
    private float to;
    private String temp;
    double progress;

    Context mContext = getActivity();
    char degree = '\u00B0';

    SimpleLocation location;
    private QuestionAdapter questionAdapter;
    private Button submit_button;
    private LinearLayoutManager linearLayoutManager;
    private String assetName;
    private String arrayName;
    private List<QuestionData> questionDataList;
    private List<AnswerData> answerDataList;
    private int LOCATION_REQUEST_CODE = 1;
    int a = 0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();
        }

    }

    private void requestStoragePermission() {
        PermisionManager.requestPermision(getContext(), LOCATION_REQUEST_CODE,getActivity());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission GRANTED", Toast.LENGTH_SHORT).show();
                location.beginUpdates();
            } else {
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private View.OnClickListener buttonTap = v -> {
        String questionType = CONSTANTS.getState();
        getQuestion(questionType);
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        ButterKnife.bind(this, view);

        genInflater = inflater;


//        submit_button = view.findViewById(R.id.submit_button);

        Toasty.info(getActivity(), CONSTANTS.getState()).show();


        questionsLayout.removeAllViews();
        View questionView = inflater.inflate(R.layout.no_more_questions, questionsLayout);

        getQuestion(CONSTANTS.getState());
//        questionsLayout.addView(questionView);


        assetName = "Questions";



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
            Log.d("TAG", "location-> " + lat + " " + lng );
        }



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        Context context = inflater.getContext();
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

        if (lng == 0.0 && lat == 0.0) {
            lng = location.getLongitude();
            lat = location.getLatitude();

        }else {
            apiInterface.getWeather(lng, lat).enqueue(new Callback<WeatherResponse>() {
                @Override
                public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                    if (response.isSuccessful()) {
                        double temp = lat == 0 ? 27.6 : response.body().getMain().getTemp();
                        double tempMax = lat == 0 ? 36.5 : response.body().getMain().getTempMax();
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
        }



        getSleepTime();
        getStepNumber();


        return view;
    }

    private void showNextQuestion(@NonNull LayoutInflater inflater, QuestionsResponse questionsResponse) {
       if (questionAvailable){
           questionsLayout.removeAllViews();
           View questionView = inflater.inflate(R.layout.daily_questions_layout, questionsLayout);
           TextView title = questionView.findViewById(R.id.question_title);
           Button one = questionView.findViewById(R.id.answer1);
           Button two = questionView.findViewById(R.id.answer2);
           Button thre = questionView.findViewById(R.id.answer3);
           Button four = questionView.findViewById(R.id.answer4);

           one.setOnClickListener(buttonTap);
           two.setOnClickListener(buttonTap);
           thre.setOnClickListener(buttonTap);
           four.setOnClickListener(buttonTap);

           title.setText(questionsResponse.getText());
           one.setText(questionsResponse.getAnswers().get(0));
           two.setText(questionsResponse.getAnswers().get(1));
           thre.setText(questionsResponse.getAnswers().get(2));
           four.setText(questionsResponse.getAnswers().get(3));
           a++;
       }

       else {
           questionsLayout.removeAllViews();
           View questionView = inflater.inflate(R.layout.no_more_questions, questionsLayout);
       }
    }

    private void getQuestion(String timeOfDay){
        MainApplication.getApiInterface().getQuestion(timeOfDay).enqueue(new Callback<QuestionServed>() {
            @Override
            public void onResponse(Call<QuestionServed> call, Response<QuestionServed> response) {
                if (response.isSuccessful()){
                    assert  response.body() != null;

                    QuestionServed questionServed = response.body();
                    if (!questionServed.getError()){
                        questionAvailable = true;
                        QuestionsResponse questionsResponse =  new QuestionsResponse();
                        questionsResponse.setText(questionServed.getQuestion().getText());
                        questionsResponse.setAnswers(questionServed.getQuestion().getOptions());
                        showNextQuestion(genInflater, questionsResponse);
                    }
                    else {
                        questionAvailable = false;
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionServed> call, Throwable t) {

            }
        });
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
//this prepares the recycler view
// this methods fetch the data from the json asset file and displays the data


    public static TodayFragment newInstance() {
        return new TodayFragment();
    }
}
