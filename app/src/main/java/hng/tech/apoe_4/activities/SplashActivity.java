package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;

import android.content.Intent;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Launcher);
        super.onCreate(savedInstanceState);

        String accesToken = Prefs.getString("accessToken", "");

        if (accesToken.isEmpty()){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        else {
            startActivity(new Intent(SplashActivity.this, Home.class));
        }
    }


}
