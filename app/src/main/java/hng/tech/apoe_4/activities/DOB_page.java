package hng.tech.apoe_4.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;

public class DOB_page extends AppCompatActivity {


    DatePicker dateOfBirthPicker;
    Button submitDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob_page);

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
            Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

            Prefs.putBoolean("savedDOB", true);

                startActivity(new Intent(DOB_page.this, WHGActivity.class));
                finish();


        });
    }
}
