package hng.tech.apoe_4;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.activities.Home;
import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.utils.MainApplication;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    //DECLARING AND INITIALIZING VIEWS WITH BUTTERKNIFE
    @BindView(R.id.nameRegInput)
    EditText fullName;
    @BindView(R.id.emailRegInput)
    EditText email;
    @BindView(R.id.passRegInput)
    EditText password;
    @BindView(R.id.confirmPassRegInput)
    EditText confirmPassword;

    @BindView(R.id.reg_btn)
    Button registerButton;


    @BindView(R.id.accept_terms_and_conditions)
    CheckBox termsAndCondition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        registerButton.setOnClickListener(v -> {
            Toast.makeText(this, "aToast", Toast.LENGTH_SHORT).show();
            String[] name = fullName.getText().toString().trim().split(" ");
            String firstName = name[0];
            String lastName = name.length > 1 ? name[1] : "";
            String regEmail = email.getText().toString().trim();
            String regPassword = password.getText().toString().trim();
            if(validateForm(firstName,lastName,regEmail,regPassword)){
                return;
            }
            MainApplication.getApiInterface().register(
                    firstName,
                    lastName,
                    regEmail,
                    regPassword)
                    .enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            if (response.isSuccessful()){
                                if (response.body() != null) {

                                    Toast.makeText(RegisterActivity.this, "Registration Success",
                                            Toast.LENGTH_SHORT).show();

                                    Prefs.putString("accessToken", response.body().getAccessToken());
                                    startActivity(new Intent(RegisterActivity.this, Home.class));
                                }
                            }
//                        Toast.makeText(RegisterActivity.this,"",
//                                Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            //Todo Logic to handle failure
                        }
                    });

        });
    }

    public boolean validateForm(String firstName,
                                String lastName,
                                String regEmail,
                                String regPassword) {
        boolean hasError = false;
        if(firstName.isEmpty() || lastName.isEmpty()){
            hasError = true;
            fullName.setError("Please enter your full name");
        }
        if (regEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(regEmail).matches()){
            hasError = true;
            email.setError("Invalid email address");
        }
        if (regPassword.isEmpty()){
            hasError = true;
            password.setError("Please enter a password");
        }else if(!regPassword.equals(confirmPassword.getText().toString().trim())){
            hasError = true;
            password.setError("Password do no match");
        }
        if(!termsAndCondition.isChecked()){
            termsAndCondition.setError("You must agree to our terms and condition");
        }

        return hasError;
    }
}
