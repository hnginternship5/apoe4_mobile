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

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

public class DOB_page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static ArrayList<String> listWHG = new ArrayList<>();
    DatePicker dateOfBirthPicker;
    Button submitDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob_page);

        ButterKnife.bind(this);

        //Initializing list
//        listWHG = new ArrayList<>();

        listWHG.add("a");listWHG.add("a");listWHG.add("a");

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

            //Inputing DOB in calendar instance
            Calendar dob = Calendar.getInstance();
            dob.set(dateOfBirthPicker.getYear(),dateOfBirthPicker.getMonth()+1,dateOfBirthPicker.getDayOfMonth());


//            This is for WHG
            if(listWHG.get(0).contains("kg")||listWHG.get(0).contains("lb")||listWHG.get(1).contains("cm")||listWHG.get(1).contains("ft")||
                    listWHG.get(2).contains("Male")||listWHG.get(2).contains("Female")||listWHG.get(2).contains("Other")){
                Prefs.putBoolean("selectedWHG", true);
                Prefs.putBoolean("savedDOB", true);
                saveDOB(dob);
                Toast.makeText(this, "Thank You", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DOB_page.this, QuestionsActivity.class));
                finish();
            }else{
                Toast.makeText(this,"Please Complete all fields",Toast.LENGTH_LONG).show();
            }


        });
    }

    public void saveDOB(Calendar dob){
        Prefs.putLong("DOB",dob.getTimeInMillis());
    }

    public static long getDOB(){
        return Prefs.getLong("DOB", 0);
    }

    public  void saveWHGInfo(ArrayList<String> list){

        Prefs.putInt("list_size",list.size());

        for(int i=0;i<list.size();i++){
            Prefs.remove("list_size" + i);
            Prefs.putString("list_size"+i,list.get(i));
        }
    }

    public static ArrayList<String> loadWHGInfo(){
        listWHG.clear();
        int size = Prefs.getInt("list_size",0);

        for(int i=0;i<size;i++){
            listWHG.add(Prefs.getString("list_size" +i,null));
        }

        return listWHG;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.weight_spinner:
                String weight = parent.getItemAtPosition(position).toString();
                //Toast.makeText(this,"W : "+weight,Toast.LENGTH_LONG).show();
                if(!weight.equals("Weight"))
                    listWHG.set(0,weight);
                break;
            case R.id.height_spinner:
                String height = parent.getItemAtPosition(position).toString();
                //Toast.makeText(this,"H : "+height,Toast.LENGTH_LONG).show();
                if(!height.equals("Height"))
                    listWHG.set(1,height);
                break;
            case R.id.gender_spinner:
                String gender = parent.getItemAtPosition(position).toString();
                //Toast.makeText(this,"G : "+gender,Toast.LENGTH_LONG).show();
                if(!gender.equals("Gender"))
                    listWHG.set(2,gender);
                break;
        }

        saveWHGInfo(listWHG);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
