package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hng.tech.apoe_4.R;

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

        Toast.makeText(LoginActivity.this, "Email: "+email+"\nPassword: "+password, Toast.LENGTH_LONG).show();
    }

    public void sign_up(View view) {
        Toast.makeText(LoginActivity.this, "Takes user to sign up", Toast.LENGTH_LONG).show();
    }
}
