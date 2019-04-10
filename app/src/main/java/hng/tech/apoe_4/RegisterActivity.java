package hng.tech.apoe_4;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.AccessToken;
import hng.tech.apoe_4.utils.ApiUtils;
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

    //Declaring ApiInterface
    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        apiInterface = ApiUtils.getAPIService();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = fullName.getText().toString();
                String lastName = fullName.getText().toString();
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                Call<AccessToken> call = apiInterface.registerUser(firstName,lastName,
                        userEmail,userPassword);

                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                        Toast.makeText(getApplicationContext(),"SUCCESSFUL",
                                Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),response.body().toString(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }


}
