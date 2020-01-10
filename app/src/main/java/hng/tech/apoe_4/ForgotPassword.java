package hng.tech.apoe_4;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    EditText emailInput;
    Button submitButton;
    TextView signUp;
    private boolean hasError;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.editText2);
        submitButton = findViewById(R.id.button4);
        signUp = findViewById(R.id.textView5);
        email = emailInput.getText().toString().trim();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidEmail(email)){
                    // Use Email to get user's password
                }else{

                }
            }
        });
    }

    public boolean isValidEmail(String input){
        hasError = false;

        if(input == "" || !Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            hasError = true;
            emailInput.setError("Not a valid Email Address");
        }
        return hasError;
    }
}
