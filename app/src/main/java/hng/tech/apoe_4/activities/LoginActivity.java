package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        MainApplication.getApiInterface().login(new User(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful())
                    Toast.makeText(LoginActivity.this, "accessToken: " +  response.body().getAccessToken(),Toast.LENGTH_SHORT).show();
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

    public boolean validateForm(){
        //todo logic to validate input from user before sending data to server
        return false;
    }
}
