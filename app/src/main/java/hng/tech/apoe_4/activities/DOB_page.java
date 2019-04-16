package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

public class DOB_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    DatePicker dateOfBirthPicker;
    Button submitDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob_page);

        ButterKnife.bind(this);


//         This is for Height Spinner
        Spinner height_spinner = findViewById(R.id.height_spinner);
        ArrayAdapter<CharSequence> height_adapter =
                ArrayAdapter.createFromResource(this, R.array.height, android.R.layout.simple_spinner_item);
        height_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_spinner.setAdapter(height_adapter);
        height_spinner.setOnItemSelectedListener(this);


//         This is for Weight Spinner
        Spinner weight_spinner = findViewById(R.id.weight_spinner);
        ArrayAdapter<CharSequence> weight_adapter =
                ArrayAdapter.createFromResource(this, R.array.weight, android.R.layout.simple_spinner_item);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_spinner.setAdapter(weight_adapter);
        weight_spinner.setOnItemSelectedListener(this);


//         This is for Gender
        Spinner gender_spinner = findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> gender_adapter =
                ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(gender_adapter);
        gender_spinner.setOnItemSelectedListener(this);

//        done.setOnClickListener(v ->{
//
//            if (!Prefs.getBoolean("answeredQuestions", false)){
//                startActivity(new Intent(WHGActivity.this, QuestionsActivity.class));
//                finish();
//            }else{
//                startActivity(new Intent(WHGActivity.this, Home.class));
//                finish();
//            }
//
//        });

//        This is for DOB Page
        // Initialize date picker and submit button
        // initiate the date picker and a button
        dateOfBirthPicker = findViewById(R.id.datePicker);
        submitDateOfBirth = findViewById(R.id.submit_DOB);

        // perform click event for submit button
        submitDateOfBirth.setOnClickListener(v -> {
            // get the values for day of month , month and year from date picker
            String day = "Day = " + dateOfBirthPicker.getDayOfMonth();
            String month = "Month = " + (dateOfBirthPicker.getMonth() + 1);
            String year = "Year = " + dateOfBirthPicker.getYear();
            // display the values by using a toast
//            Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Thank You", Toast.LENGTH_SHORT).show();

//            This is for WHG

            Prefs.putBoolean("selectedWHG", true);
            Prefs.putBoolean("savedDOB", true);

                startActivity(new Intent(DOB_page.this, QuestionsActivity.class));
                finish();


        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
