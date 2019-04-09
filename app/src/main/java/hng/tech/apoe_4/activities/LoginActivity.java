package hng.tech.apoe_4.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import hng.tech.apoe_4.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login_email, login_password;
    private Button login_button;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signUp = findViewById(R.id.sign_up_link);
        signUp.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    public void login() {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        Toast.makeText(LoginActivity.this, "Email: "+email+"\nPassword: "+password, Toast.LENGTH_LONG).show();
    }

    public void sign_up() {
        Toast.makeText(LoginActivity.this, "Takes user to sign up", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.login_button:
                login();
                break;
            case R.id.sign_up_link:
                sign_up();
                break;
            default:
                Toast.makeText(this,"Invalid action",Toast.LENGTH_SHORT).show();
        }
    }
}
