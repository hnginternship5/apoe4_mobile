package hng.tech.apoe_4.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
    RelativeLayout prog;
    TextView text5;
    private GoogleSignInClient mGoogleSignInClient;

    // todo: waiting on api to complete login task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.client_ID))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        text5 = findViewById(R.id.textView5);
        prog = findViewById(R.id.progress);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null)
            loginLogic(account.getEmail(),account.getId());
    }

    public void googgleSignIn(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        loginLogic(account.getEmail(),account.getId());
                        Log.e("TAG", account.getEmail());
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
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

        loginLogic(email,password);

//        Toast.makeText(LoginActivity.this, "Email: "+email+"\nPassword: "+password, Toast.LENGTH_LONG).show();
    }

    public void loginLogic(String email, String password){
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
                    else
                        startActivity(new Intent(LoginActivity.this, WHGActivity.class));
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
