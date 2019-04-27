package hng.tech.apoe_4;


import android.content.Intent;
import android.os.Bundle;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import android.util.Patterns;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import hng.tech.apoe_4.activities.DOB_page;
import hng.tech.apoe_4.activities.Home;
import hng.tech.apoe_4.activities.LoginActivity;
import hng.tech.apoe_4.presenters.RegisterPresenter;
import hng.tech.apoe_4.retrofit.responses.AuthResponse;
import hng.tech.apoe_4.utils.MainApplication;
import hng.tech.apoe_4.views.RegisterView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    //DECLARING AND INITIALIZING VIEWS WITH BUTTERKNIFE
    @BindView(R.id.nameRegInput)
    EditText fullName;
    @BindView(R.id.emailRegInput)
    EditText email;
    @BindView(R.id.passReg)
    TextInputEditText password;
    @BindView(R.id.confirmPassReg)
    TextInputEditText confirmPassword;

    @BindView(R.id.reg_btn)
    Button registerButton;


    @BindView(R.id.progressBar)
    ProgressBar prog2;

    RegisterPresenter registerPresenter;



    @BindView(R.id.accept_terms_and_conditions)
    CheckBox termsAndCondition;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);





        ButterKnife.bind(this);

        registerPresenter = new RegisterPresenter(this, this);

        registerButton.setOnClickListener(v -> {
            //make progress bar visible
            prog2.setVisibility(View.VISIBLE);
            String[] name = fullName.getText().toString().trim().split(" ");
            String firstName = name[0];
            String lastName = name.length > 1 ? name[1] : "";
            String regEmail = email.getText().toString().trim();
            String regPassword = password.getText().toString().trim();
            if(validateForm(firstName,lastName,regEmail,regPassword)){
                prog2.setVisibility(View.GONE);
                return;
            }

            registerPresenter.beginRegistration(firstName, lastName, regEmail, regPassword);


        });

        //dont show hint onfocus
        final TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.passRegInput);

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Below code will check if editext has focus then show hint

                //else if it dosen't have focus and user has entered some value only then hide hint.
                if (hasFocus){
                    textInputLayout.setHint(" ");
                }
            }
        });

        final TextInputLayout textInputLayout1 = (TextInputLayout) findViewById(R.id.confirmPassRegInput);
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //Below code will check if editext has focus then show hint

                //else if it dosen't have focus and user has entered some value only then hide hint.
                if (hasFocus){
                    textInputLayout1.setHint(" ");
                }
            }
        });

//        CheckBox show_password = findViewById(R.id.reg_show_password);
//        show_password.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                // show password
//                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//            } else {
//                // hide password
//                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//            }
//        });
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
            hasError = true;
            Toast.makeText(this,"You must agree to our terms and condition", Toast.LENGTH_SHORT).show();
        }

        return hasError;
    }

    public void sign_in (View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        RegisterActivity.this.finish();
    }

    @Override
    public void toastSuccess(String msg) {

    }

    @Override
    public void toastError(String msg) {
        Toasty.error(this, msg).show();
    }

    @Override
    public void beginRegistration() {

    }

    @Override
    public void registrationEnd() {

    }

    @Override
    public void registrationSuccessful() {
        startActivity(new Intent(RegisterActivity.this, Home.class));
    }

    @Override
    public void registrationFail() {
        prog2.setVisibility(View.GONE);
    }
}
