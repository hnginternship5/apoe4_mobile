package hng.tech.apoe_4.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.pixplicity.easyprefs.library.Prefs;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

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
    LoginManager fbLoginManager;
    List<String> permissionNeeds;
    CallbackManager callbackManager;
    ImageView fbButton;
    ProgressDialog dialog;
    String firstname;

    // todo: waiting on api to complete login task

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize facebook SDK
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        text5 = findViewById(R.id.textView5);
        prog = findViewById(R.id.progress);

        //set up callbacks and fbloginmanager

        callbackManager = CallbackManager.Factory.create();

        permissionNeeds = Arrays.asList("email", "public_profile");
        fbLoginManager = LoginManager.getInstance();

        //perform login
        fbButton =findViewById(R.id.imageView4);

        fblogin();
        fbButton.setOnClickListener(view ->{
            fbLoginManager.logInWithReadPermissions(LoginActivity.this, permissionNeeds);

                }
        );
    }

     private void fblogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fbLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        dialog = new ProgressDialog(LoginActivity.this);
                        dialog.setMessage("Retrieving your data...");
                        dialog.show();

                        Log.d("fbaccess", "onSuccess: " + loginResult.getAccessToken().getToken());
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        //load user data

                        loaduserProfile(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        }, 100);

     }

    public void login(View view) {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();


        //display progress
        prog.setVisibility(View.VISIBLE);
        text5.setVisibility(View.INVISIBLE);



        if(validateForm(email)){
            prog.setVisibility(View.INVISIBLE);
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
                    //do not delete the loggedin Pref
                    Prefs.putBoolean("loggedIn", true);
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

    private void loaduserProfile(AccessToken token){
        GraphRequest request  = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                dialog.dismiss();
                try {
                    firstname  = object.getString("first_name");
                    String lname = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                    Prefs.putString("fullname", firstname + " " + lname);
                    Prefs.putString("url", image_url);
                    Log.d("fb", image_url);
                    Log.d("fb", firstname);
                    Prefs.putString("email", email);
                    Prefs.putBoolean("regFb", true);

                    //register after one second
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog = new ProgressDialog(LoginActivity.this);
                            dialog.setMessage("Registering user...");
                            dialog.show();
                            registerFb(firstname, lname, email, "APEO4" + token.getToken());
                        }
                    }, 1000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //set request with  async
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    //Does the callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
//register on endpoint
    private void registerFb(String fname, String lname, String regEmail, String regPassword){
        MainApplication.getApiInterface().register(
                fname,
                lname,
                regEmail,
                regPassword)
                .enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()){
                            dialog.dismiss();
                            if (response.body() != null) {
                                Toast.makeText(LoginActivity.this, "Welcome " + fname + " "+ lname,
                                        Toast.LENGTH_SHORT).show();
                                Prefs.putString("accessToken", response.body().getAccessToken());
                                //do not delete the loggedin Pref
                                Prefs.putBoolean("loggedIn", true);
                                Prefs.putBoolean("fblog", true);
                                Prefs.putString("firstName", fname);
                                Prefs.putString("lastName", lname);
                                startActivity(new Intent(LoginActivity.this, DOB_page.class));
                                finish();
                            }
                        }
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Welcome back "+ fname + " "+ lname,
                                Toast.LENGTH_SHORT).show();
                        //do not delete the loggedin Pref
                        Prefs.putBoolean("loggedIn", true);
                        Prefs.putString("firstName", fname);
                        Prefs.putString("lastName", lname);
                        startActivity(new Intent(LoginActivity.this, DOB_page.class));
                        finish();

                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {

                        //Todo Logic to handle failure
                        dialog.dismiss();
                    }
                });
    }

}
