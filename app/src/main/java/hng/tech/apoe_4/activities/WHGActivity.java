package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.pixplicity.easyprefs.library.Prefs;

public class WHGActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.button)
    Button done;

    String weigh, height, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whg);

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

        done.setOnClickListener(v ->{
            Prefs.putBoolean("selectedWHG", true);
            if (!Prefs.getBoolean("answeredQuestions", false)){
                startActivity(new Intent(WHGActivity.this, QuestionsActivity.class));
                finish();
            }else{
                startActivity(new Intent(WHGActivity.this, Home.class));
                finish();
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
