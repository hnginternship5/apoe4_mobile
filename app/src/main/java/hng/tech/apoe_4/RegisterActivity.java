package hng.tech.apoe_4;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerButton.setOnClickListener(v -> {
            Toast.makeText(this, "aToast", Toast.LENGTH_SHORT).show();
            String[] name = fullName.getText().toString().trim().split(" ");
            String firstName = name[0];
            String lastName = name.length > 1 ? name[1] : "aLastName";
            String regEmail = email.getText().toString().trim();
            String regPassword = password.getText().toString().trim();
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

    public boolean validateForm() {

        //todo logic to validate form before sending request to server
        return true;
    }
}
