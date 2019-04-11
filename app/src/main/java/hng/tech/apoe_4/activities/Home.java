package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

    @BindView(R.id.logout)
    ImageView logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        ButterKnife.bind(this);

        openFragment(TodayFragment.newInstance(), "today");


        logoutImageView.setOnClickListener(x -> 
                 {
                    Prefs.putString("accessToken", "");
                    Toast.makeText(this, "You are logged out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
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
}
