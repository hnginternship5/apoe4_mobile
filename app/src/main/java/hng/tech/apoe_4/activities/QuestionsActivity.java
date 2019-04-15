package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.fragments.FirstQuestion;
import hng.tech.apoe_4.fragments.ListQuestions;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

public class QuestionsActivity extends AppCompatActivity implements FirstQuestion.QuestionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        openFragment(FirstQuestion.newInstance(), "first");
        if (Prefs.getBoolean("que1", false)){
            openFragment(ListQuestions.newInstance(), "list");
            }else if (Prefs.getBoolean("que1", false) && Prefs.getBoolean("answeredQuestions", false)){
            startActivity(new Intent(this, Home.class));
            finish();
        }
    }
    private void openFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container2, fragment, tag);
        transaction.addToBackStack(fragment.getTag());
        Log.d("TAG","fragment tag: "+fragment.getTag());
        transaction.commit();
    }

    @Override
    public void changeFrag() {
        Prefs.putBoolean("que1", true);
        openFragment(ListQuestions.newInstance(), "list");
    }

    @Override
    public void nextScreen() {
        startActivity(new Intent(this, Home.class));
        finish();
        Prefs.putBoolean("answeredQuestions", true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
