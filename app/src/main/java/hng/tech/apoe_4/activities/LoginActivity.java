package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hng.tech.apoe_4.R;
import hng.tech.apoe_4.RegisterActivity;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.utils.Client;
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
        //authenticate
        ApiInterface service = Client.getInstance().create(ApiInterface.class);
        Call<User> userCall = service.login(email,password);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                //todo logic to configure app after successfull login
                Toast.makeText(LoginActivity.this,response.message(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        Toast.makeText(LoginActivity.this, "Email: "+email+"\nPassword: "+password, Toast.LENGTH_LONG).show();
    }

    public void sign_up(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        Toast.makeText(LoginActivity.this, "Takes user to sign up", Toast.LENGTH_LONG).show();
    }

    public boolean validateForm(){
        //todo logic to validate input from user before sending data to server
        return false;
    }
}
