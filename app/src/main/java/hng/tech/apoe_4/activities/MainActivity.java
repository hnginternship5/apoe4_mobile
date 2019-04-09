package hng.tech.apoe_4.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.showProfile)
    Button showProfile;

    @BindView(R.id.splash)
    Button splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        splash.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, splashScreen.class);
                startActivity(intent);
            }
        });

        showProfile.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class)));

        findViewById(R.id.show_login).setOnClickListener( v ->
            startActivity(new Intent(MainActivity.this, LoginActivity.class)));

    }
}
