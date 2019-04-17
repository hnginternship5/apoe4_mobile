package hng.tech.apoe_4.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.adapters.QuestionAdapter;
import hng.tech.apoe_4.models.AnswerData;
import hng.tech.apoe_4.models.QuestionData;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.QuestionsResponse;
import hng.tech.apoe_4.retrofit.responses.WeatherResponse;
import hng.tech.apoe_4.utils.DataUtil;
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
    FirebaseFirestore db;

    @BindView(R.id.tempProgress)
    ProgressBar tempProgress;

    @BindView(R.id.stepsProgress)
    ProgressBar stepsProgress;

    @BindView(R.id.sleepProgress)
    ProgressBar sleepProgress;

//    @BindView(R.id.loading)
//    ProgressBar progressBar;

    @BindView(R.id.temp)
    TextView tempText;



    private float from = (float)10;
    private float to;
    private String temp;
    double progress;

    Context mContext = getActivity();
    char degree = '\u00B0';

    SimpleLocation location;
    private QuestionAdapter questionAdapter;
    private RecyclerView questions_view;
    private LinearLayoutManager linearLayoutManager;
    private String assetName;
    private String arrayName;
    private List<QuestionData> questionDataList;
    private List<AnswerData> answerDataList;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    421);

        } else {
            location.beginUpdates();
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

        questions_view = view.findViewById(R.id.questions_view);

        assetName = "Questions";
        setRecyclerView();
        showData();

        db = FirebaseFirestore.getInstance();

        Query query = db.collection("questions");

        FirestoreRecyclerOptions<QuestionsResponse> response = new FirestoreRecyclerOptions.Builder<QuestionsResponse>()
                .setQuery(query, QuestionsResponse.class)
                .build();

        Log.e("snap", response.getSnapshots().isEmpty() + "");

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<QuestionsResponse, QuestionsHolder>(response) {

            @Override
            public void onBindViewHolder(QuestionsHolder holder, int position, QuestionsResponse model) {
//                progressBar.setVisibility(View.GONE);
                holder.textTitle.setText(model.getText());

                List<QuestionsResponse> responses = response.getSnapshots();

                List<String> answers = model.getAnswers();
                for (int i =0; i < answers.size(); i++){
                    Button anAnswer = new Button(getActivity());
                    anAnswer.setGravity(Gravity.CENTER);
                    anAnswer.setAllCaps(false);
                    anAnswer.setTextColor(getActivity().getResources().getColor(R.color.figmaBlue));
                    anAnswer.setTextSize(20);


                    anAnswer.setBackground(getActivity().getResources().getDrawable(R.drawable.button_bg));
                    anAnswer.setText(answers.get(i));
                    anAnswer.setId(i);
                    holder.answersContainer.addView(anAnswer);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)anAnswer.getLayoutParams();


                    params.setMargins(10, 10, 10, 10); //substitute parameters for left, top, right, bottom
                    anAnswer.setLayoutParams(params);

                    anAnswer.setOnClickListener(v -> {

//                        questions_view.removeViewAt(position);
                        for (QuestionsResponse questionsResponse : responses){
                            if (questionsResponse.getText().equals(model.getText())){
                                Toast.makeText(getActivity().getBaseContext(), questionsResponse.getText(), Toast.LENGTH_SHORT).show();
//                                adapter.getSnapshots().remove(position);
                            }
                        }
                    });
                }


            }

            @Override
            public QuestionsHolder onCreateViewHolder(ViewGroup group, int i) {
//                if (getItem(i).)
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.question_firebase, group, false);

                return new QuestionsHolder(view);
            }

            @Override
            public int getItemCount() {
                Log.e("aCount", "" + super.getItemCount());
                return super.getItemCount();
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }

        };



        db.collection("questions")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                        Log.e("aTag", documentSnapshot.getId() + " => " +  documentSnapshot.getData());

                    }
                }
            }


        });
        adapter.startListening();
        Log.e("count", "" + adapter.getItemCount());
        adapter.notifyDataSetChanged();
        questions_view.setAdapter(adapter);



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
    //this prepares the recycler view
    private void setRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        questions_view.setLayoutManager(linearLayoutManager);
        questionAdapter = new QuestionAdapter(getActivity());
        questions_view.setHasFixedSize(true);
//        questions_view.setAdapter(questionAdapter);

    }
    // this methods fetch the data from the json asset file and displays the data
    public void showData(){
        questionDataList = DataUtil.openTheData(getContext(),assetName);
        if (questionDataList.size() != 0) {
            questionAdapter.setQuestionDataList(questionDataList);

        }
    }


    public static TodayFragment newInstance() {
        return new TodayFragment();
    }
    class QuestionsHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.question_title)
        TextView textTitle;

        @BindView(R.id.questionsLayout)
        ConstraintLayout layout;

        @BindView(R.id.answersContainer)
        LinearLayout answersContainer;
        public QuestionsHolder(@NonNull View itemView) {
            super(itemView);

            layout = (ConstraintLayout) itemView;

            ButterKnife.bind(this, itemView);
        }
    }

}