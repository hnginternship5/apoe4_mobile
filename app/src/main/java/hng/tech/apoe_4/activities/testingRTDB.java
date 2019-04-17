package hng.tech.apoe_4.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hng.tech.apoe_4.R;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class testingRTDB extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Button testButton;

    private static final String TAG = "test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_rtdb);

        testButton = findViewById(R.id.test_button);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("questions").child("morning")
        .child("q1");

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, String.valueOf(databaseReference.getKey()));

            }
        });

    }
}

//    FirebaseDatabase mFirebaseDatabase;
//    DatabaseReference regularEasy;
//    String list;
//    String string;
//
//    LinearLayout myRoot;
//
//    // Debugging TAG
//    private static final String TAG = "regular_beginner_lect2";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regular_beginner_lecture2);
//
//        // Create a new reference to the first lecture title in the database
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        regularEasy = mFirebaseDatabase.getReference().child("lectures")
//                .child("Regular").child("Easy").child("Lecture 2");
//
//        Query query = regularEasy.orderByKey();
//        query.addListenerForSingleValueEvent(queryValueListener);
//    }
//
//    // Event listener for dataSnapshot
//    //Getting Lecture 1 text and populating views dynamically
//    ValueEventListener queryValueListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            Iterable<DataSnapshot> snapshotIterable;
//
//            snapshotIterable = dataSnapshot.getChildren();
//
//            Iterator<DataSnapshot> iterator;
//
//            // This currently returns the value just typed
//            for (iterator = snapshotIterable.iterator(); iterator.hasNext(); ) // initialization; condition ; increment/decrement
//            {
//                DataSnapshot next = iterator.next();
//
//                list = String.valueOf(next.child("lectureText").getValue());