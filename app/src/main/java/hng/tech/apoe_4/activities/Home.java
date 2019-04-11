package hng.tech.apoe_4.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.fragments.ForumFragment;
import hng.tech.apoe_4.fragments.ResultsFragment;
import hng.tech.apoe_4.fragments.TodayFragment;

public class Home extends AppCompatActivity {


    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        ButterKnife.bind(this);

        openFragment(TodayFragment.newInstance());



        BottomNavigationView.OnNavigationItemSelectedListener listener = item -> {
            switch (item.getItemId()){
                case R.id.navigation_results:{
                    openFragment(ResultsFragment.newInstance());
                    return true;
                }

                case R.id.navigation_today:{
                    openFragment(TodayFragment.newInstance());
                    return true;
                }

                case R.id.navigation_forum:{
                    openFragment(ForumFragment.newInstance());
                    return true;
                }

                default:{
                    openFragment(ResultsFragment.newInstance());
                    return true;
                }
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(listener);


    }

    private void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(fragment.getTag());
        transaction.commit();
    }
//this method helps to handle backpress between fragments
    private void pressingBack() {

            if (count == 0) {
                openFragment(TodayFragment.newInstance());
                count =1;
            }
            else {
               finishAffinity();
            }
    }

    @Override
    public void onBackPressed() {
        pressingBack();
    }
}
