package hng.tech.apoe_4.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hng.tech.apoe_4.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class WHGActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.button)
    Button done;

    String weigh, height, gender;
    static ArrayList<String> listWHG = new ArrayList<>();
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

    public  void saveWHGInfo(ArrayList<String> list,Context c){
        SharedPreferences savedWHG = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = savedWHG.edit();

        editor.putInt("list_size",list.size());

        for(int i=0;i<list.size();i++){
            editor.remove("list_size" + i);
            editor.putString("list_size"+i,list.get(i));
        }

        editor.apply();
    }

    public static ArrayList<String> loadWHGInfo(Context c){
        SharedPreferences savedContentLoad = PreferenceManager.getDefaultSharedPreferences(c);
        listWHG.clear();
        int size = savedContentLoad.getInt("list_size",0);

        for(int i=0;i<size;i++){
            listWHG.add(savedContentLoad.getString("list_size" +i,null));
        }
        return listWHG;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.weight_spinner:
                String weight = parent.getItemAtPosition(position).toString();
                Toast.makeText(this,"W : "+weight,Toast.LENGTH_LONG).show();
                if(!weight.equals("Weight"))
                    listWHG.add(weight);
                break;
            case R.id.height_spinner:
                String height = parent.getItemAtPosition(position).toString();
                Toast.makeText(this,"H : "+height,Toast.LENGTH_LONG).show();
                if(!height.equals("Height"))
                    listWHG.add(height);
                break;
            case R.id.gender_spinner:
                String gender = parent.getItemAtPosition(position).toString();
                Toast.makeText(this,"G : "+gender,Toast.LENGTH_LONG).show();
                if(!gender.equals("Gender"))
                    listWHG.add(gender);
                break;
        }

        saveWHGInfo(listWHG,this);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
