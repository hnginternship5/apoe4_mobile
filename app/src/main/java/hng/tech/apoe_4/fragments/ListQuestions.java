package hng.tech.apoe_4.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.activities.Home;

public class ListQuestions extends Fragment {
    Button yes1, yes2, yes3, no1, no2, no3;

    public  static FirstQuestion.QuestionListener listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.listquestion, container, false);

        yes1 = v.findViewById(R.id.affirm3);
        yes2 = v.findViewById(R.id.affirm4);
        yes3 = v.findViewById(R.id.affirm5);
        no1 = v.findViewById(R.id.neg3);
        no2 = v.findViewById(R.id.neg4);
        no3 = v.findViewById(R.id.neg5);

        yes1.setOnClickListener(view -> {
            storeYes("third");
        });

        yes2.setOnClickListener(view -> {
            storeYes("fourth");

        });

        yes3.setOnClickListener(view -> {
            if (!Prefs.getString("third", "").isEmpty() && !Prefs.getString("fourth", "").isEmpty()){
                storeYes("fifth");
                listener.nextScreen();

            }else{

                Toast.makeText(getActivity(), "Answer previous questions first", Toast.LENGTH_SHORT).show();
            }

        });

        no1.setOnClickListener(view -> {
            storeNo("third");
        });

        no2.setOnClickListener(view -> {
            storeNo("fourth");
        });

        no3.setOnClickListener(view -> {
            if (!Prefs.getString("third", "").isEmpty() && !Prefs.getString("fourth", "").isEmpty()){
                storeNo("fifth");
               listener.nextScreen();

            }else{
                Toast.makeText(getActivity(), "Answer previous questions first", Toast.LENGTH_SHORT).show();

            }

        });


        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        listener = (FirstQuestion.QuestionListener) activity;
    }
    //store Affirmative answer
    private void storeYes(String key){
        Prefs.putString(key, "Yes");
    }
    //store negative answer
    private void storeNo(String key){
        Prefs.putString(key, "Yes");
    }
    public static ListQuestions newInstance() {
        return new ListQuestions();
    }
}
