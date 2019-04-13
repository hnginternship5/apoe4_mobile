package hng.tech.apoe_4.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import hng.tech.apoe_4.R;

public class FirstQuestion extends Fragment {
    int count = 0;
    TextView questions;
    Button yesButton,noButton;
    Animation animation;
    public interface QuestionListener{
        public void changeFrag();
        public void nextScreen();
    }
    public  static  QuestionListener listener;
    public FirstQuestion() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.firstquestion, container, false);

        //define views
        questions = v.findViewById(R.id.que);
        yesButton = v.findViewById(R.id.affirm);
        noButton  = v.findViewById(R.id.neg);

        //check first count


            //first question

                questions.setText(R.string.quest1);
                yesButton.setOnClickListener(view -> {
                    if (count == 0) {
                        storeYes("first");
                        count = 1;
                        Log.d("tag", "answered1 yes");
                        questions.setText(R.string.quest2);
                    }else{
                        storeYes("second");
                        Log.d("tag", "answered2 yes");
                        Prefs.putBoolean("que1", true);
                        //next fragment
                        listener.changeFrag();
                    }
                });
                noButton.setOnClickListener(view -> {
                    if (count == 0) {
                        storeNo("first");
                        count = 1;
                        Log.d("tag", "answered1 no");
                        questions.setText(R.string.quest2);
                    }else{
                        storeNo("second");
                        Log.d("tag", "answered2 no");
                        Prefs.putBoolean("que1", true);
                        //next fragment
                        listener.changeFrag();
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

    public static FirstQuestion newInstance() {
        return new FirstQuestion();
    }
}
