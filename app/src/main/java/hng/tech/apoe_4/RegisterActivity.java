package hng.tech.apoe_4;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.retrofit.ApiInterface;
import hng.tech.apoe_4.retrofit.responses.User;
import hng.tech.apoe_4.utils.Client;
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
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] name = fullName.getText().toString().trim().split(" ");
                String firstName = name[0];
                String lastName = name.length  > 1? name[1]:"";
                String regEmail = email.getText().toString().trim();
                String regPassword = password.getText().toString().trim();
                ApiInterface service = Client.getInstance().create(ApiInterface.class);
                Call<User> userRegister = service.register(
                        firstName,
                        lastName,
                        regEmail,
                        regPassword);
                userRegister.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body() !=null ){

                            //Todo logic to redirect and configure app after successfull login

                            Toast.makeText(RegisterActivity.this,response.message(),
                                    Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(RegisterActivity.this,"",
//                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        //Todo Logic to handle failure
                    }
                });

            }
        });
    }
    public boolean validateForm(){

        //todo logic to validate form before sending request to server
        return true;
    }
}
