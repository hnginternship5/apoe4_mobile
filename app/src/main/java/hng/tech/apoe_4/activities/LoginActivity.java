package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;
import hng.tech.apoe_4.RegisterActivity;
import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.utils.MainApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText login_email, login_password;

    // todo: waiting on api to complete login task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
    }

    public void login(View view) {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();
        if(validateForm(email)){
            return;
        }
        MainApplication.getApiInterface().login(new User(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Success: " ,Toast.LENGTH_SHORT).show();
                    Prefs.putString("accessToken", response.body().getAccessToken());
                    startActivity(new Intent(LoginActivity.this, Home.class));
                }
                else Toast.makeText(LoginActivity.this, "accessToken: error" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
//        Toast.makeText(LoginActivity.this, "Email: "+email+"\nPassword: "+password, Toast.LENGTH_LONG).show();
    }

    public void sign_up(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public boolean validateForm(String email){
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            login_email.setError("Invalid email address");
            return true;
        }
        return false;
    }
}
