package hng.tech.apoe_4.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;


import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;

public class question extends AppCompatActivity {

    // Declaring views
    TextView question_title;

    // Firebase components
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

//        // Initializing views
//        question_title = findViewById(R.id.question_title);
//
//        // Initializing Firebase components
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        databaseReference = firebaseDatabase.getReference();
//
//        // Setting time constraints
//        Date currentTime = Calendar.getInstance().getTime();














    }
}
