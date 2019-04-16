package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;


import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.ForgotPassword;
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
    RelativeLayout prog;
    TextView text5;
    TextView forgotpass;

    // todo: waiting on api to complete login task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        text5 = findViewById(R.id.textView5);
        prog = findViewById(R.id.progress);
    }

    public void login(View view) {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();


        //display progress
        prog.setVisibility(View.VISIBLE);
        text5.setVisibility(View.INVISIBLE);



        if(validateForm(email)){
            return;
        }

        MainApplication.getApiInterface().login(new User(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful()) {
                    prog.setVisibility(View.INVISIBLE);
                    text5.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Login Success: " ,Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onResponse: " + response.body().getAccessToken());
                    Prefs.putString("accessToken", response.body().getAccessToken());
                    if (!Prefs.getBoolean("savedDOB", false)) {
                        startActivity(new Intent(LoginActivity.this, DOB_page.class));
                        finish();
                    }
                    else if (Prefs.getBoolean("savedDOB", false) && Prefs.getBoolean("selectedWHG", false)){
                        startActivity(new Intent(LoginActivity.this, Home.class));
                        finish();
                    }
//                    Commented this out because the logic is no longer needed since DOB and WHG Page has been joined together.
//                    else
//                        startActivity(new Intent(LoginActivity.this, WHGActivity.class));
                }
                else {
                    prog.setVisibility(View.INVISIBLE);
                    text5.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "accessToken: error" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                prog.setVisibility(View.INVISIBLE);
                text5.setVisibility(View.VISIBLE);
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
    public void passwordforgot(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }
}
